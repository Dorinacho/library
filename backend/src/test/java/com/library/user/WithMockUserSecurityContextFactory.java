//package com.library.user;
//
//import com.library.service.UserDetailsImpl;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContext;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.context.support.WithSecurityContextFactory;
//
//public class WithMockUserSecurityContextFactory implements WithSecurityContextFactory<WithMockUser> {
//    @Override
//    public SecurityContext createSecurityContext(WithMockUser mockUser) {
//        SecurityContext context = SecurityContextHolder.createEmptyContext();
//
//        UserDetailsImpl principal =
//                new UserDetailsImpl(mockUser.name(), mockUser.username());
//        Authentication auth =
//                new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
//        context.setAuthentication(auth);
//        return context;
//    }
//}
