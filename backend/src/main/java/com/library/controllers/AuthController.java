package com.library.controllers;

import com.library.dto.UserCreationDTO;
import com.library.dto.UserLoginDTO;
import com.library.exceptions.TokenRefreshException;
import com.library.models.ERole;
import com.library.models.RefreshToken;
import com.library.models.Role;
import com.library.models.User;
import com.library.repositories.RoleRepository;
import com.library.repositories.UserRepository;
import com.library.security.jwt.JwtResponse;
import com.library.security.jwt.JwtUtils;
import com.library.security.jwt.TokenRefreshResponse;
import com.library.service.RefreshTokenService;
import com.library.service.UserDetailsImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/library/auth")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthController {
    public static final String ERROR_MESSAGE = "Error: Role is not found.";
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;
    Logger logger = LoggerFactory.getLogger(AuthController.class);

    public AuthController(RefreshTokenService refreshTokenService, AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    /**
     * @param loginRequest, containing the username and password
     *
     * @return a response entity containing the JWTResponse
     */
    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody UserLoginDTO loginRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtUtils.generateJwtToken(authentication);

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());

        List<ERole> roles = userDetails.getAuthorities().stream()
                .map(item -> ERole.valueOf(item.getAuthority()))
                .toList();

        return ResponseEntity.ok(new JwtResponse(jwt,
                refreshToken.getToken(),
                userDetails.getUsername(),
                roles));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<TokenRefreshResponse> refreshToken(@Valid @RequestBody String requestRefreshToken) {
        String test = requestRefreshToken.substring(1, requestRefreshToken.length() - 1);
        Optional<RefreshToken> refreshToken = refreshTokenService.findByToken(test);

        if (refreshToken.isPresent()) {
            User user = refreshToken.get().getUser();
            try {
                refreshTokenService.verifyRefreshToken(refreshToken.get());
                String accessToken = jwtUtils.generateTokenFromUsername(user.getUsername());
                return ResponseEntity.ok(new TokenRefreshResponse(accessToken, refreshToken.get().getToken()));
            } catch (TokenRefreshException exception) {
//                refreshTokenService.deleteByUserId(user.getId());
                logger.error(exception.getMessage());
                throw new TokenRefreshException(refreshToken.get().getToken(), "Refresh token was expired");
            }
        } else {
            throw new TokenRefreshException(requestRefreshToken,
                    "Refresh token is not in database!");
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody UserCreationDTO signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body("Error: Email is already in use!");
        }

        // Create new user's account
        User user = new User(signUpRequest.getName(),
                signUpRequest.getEmail(),
                signUpRequest.getUsername(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRoles();
        Set<Role> roles = new HashSet<>();

        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(ERROR_MESSAGE));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException(ERROR_MESSAGE));
                        roles.add(adminRole);
                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException(ERROR_MESSAGE));
                        roles.add(modRole);
                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException(ERROR_MESSAGE));
                        roles.add(userRole);
                        break;
                }

            });

        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok("User registered successfully!");
    }

//    @PostMapping("/signout")
//    public ResponseEntity<?> logoutUser() {
//        ResponseCookie cookie = jwtUtils.getCleanJwtCookie();
//        return ok().header(HttpHeaders.SET_COOKIE, cookie.toString())
//                .body("You've been signed out!");
//    }

}
