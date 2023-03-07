package com.library.service;

import com.library.exceptions.ResourceNotFoundException;
import com.library.exceptions.TokenRefreshException;
import com.library.models.RefreshToken;
import com.library.models.User;
import com.library.repositories.RefreshTokenRepository;
import com.library.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${library.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;

    private final UserRepository userRepository;

    public RefreshTokenService( RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();
        for (
                RefreshToken token : refreshTokenRepository.findAll()
        ) {
            if (token.getUser().getId().equals(userId)) {
                refreshTokenRepository.delete(token);
            }
        }
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            refreshToken.setUser(user.get());
        }
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return refreshToken;
    }

    public void verifyRefreshToken(RefreshToken refreshToken) {
        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException(refreshToken.getToken(), "Refresh token was expired");
        }
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            refreshTokenRepository.deleteByUser(user.get());
        }
        throw new ResourceNotFoundException("Could not find user with id " + userId);
    }
}
