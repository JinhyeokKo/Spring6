//package com.study.keycloak.filter;
//
//import com.study.keycloak.constants.SecurityConstants;
//import io.jsonwebtoken.Jwts;
//import io.jsonwebtoken.security.Keys;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.crypto.SecretKey;
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.Collection;
//import java.util.Date;
//import java.util.HashSet;
//import java.util.Set;
//
//public class JWTTokenGeneratorFilter extends OncePerRequestFilter {
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (null != authentication) {
//            SecretKey key = Keys.hmacShaKeyFor(SecurityConstants.JWT_KEY.getBytes(StandardCharsets.UTF_8));
//            // issuer: 발급자, subject: 토큰 제목, claim: 토큰에 담을 정보, issuedAt: 토큰 발급 시간, expiration: 토큰 만료 시간
//            String jwt = Jwts.builder().issuer("Eazy Bank").subject("JWT Token")
//                    .claim("username", authentication.getName())
//                    .claim("authorities", populateAuthorities(authentication.getAuthorities()))
//                    .issuedAt(new Date())
//                    .expiration(new Date((new Date()).getTime() + 30000000))
//                    .signWith(key).compact();
//            response.setHeader(SecurityConstants.JWT_HEADER, jwt);
//        }
//
//        filterChain.doFilter(request, response);
//    }
//
//    // 조건에 따라 필터 실행여부 결정
//    @Override
//    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
//        // "/user" 경로 외 요청 모두 shouldNotFilter 적용 -> 즉 shouldNotFilter()가 false를 반환하면 doFilterInternal()이 실행됨
//        return !request.getServletPath().equals("/user");
//    }
//
//    // 내 권한 읽어옴
//    private String populateAuthorities(Collection<? extends GrantedAuthority> collection) {
//        Set<String> authoritiesSet = new HashSet<>();
//        for (GrantedAuthority authority : collection) {
//            authoritiesSet.add(authority.getAuthority());
//        }
//        return String.join(",", authoritiesSet);
//    }
//
//}
