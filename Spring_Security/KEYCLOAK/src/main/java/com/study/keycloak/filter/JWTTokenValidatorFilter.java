//package com.study.keycloak.filter;
//
//import com.study.keycloak.constants.SecurityConstants;
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.authority.AuthorityUtils;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.crypto.SecretKey;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//
//public class JWTTokenValidatorFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String jwt = request.getHeader(SecurityConstants.JWT_HEADER);
//        if (null != jwt) {
//            try {
//                SecretKey key = Keys.hmacShaKeyFor(
//                        SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
//
//                Claims claims = Jwts.parser()
//                        .verifyWith(key)
//                        .build()
//                        .parseSignedClaims(jwt)
//                        .getPayload();
//                String username = String.valueOf(claims.get("username"));
//                String authorities = (String) claims.get("authorities");
//                Authentication auth = new UsernamePasswordAuthenticationToken(username, null,
//                        AuthorityUtils.commaSeparatedStringToAuthorityList(authorities));
//                SecurityContextHolder.getContext().setAuthentication(auth);
//            } catch (Exception e) {
//                throw new BadCredentialsException("Invalid Token received!");
//            }
//
//        }
//        filterChain.doFilter(request, response);
//    }
//
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        // "/user" 경로가 아닐 때만 유효성 검사를 하도록 설정
//        return request.getServletPath().equals("/user");
//    }
//}
