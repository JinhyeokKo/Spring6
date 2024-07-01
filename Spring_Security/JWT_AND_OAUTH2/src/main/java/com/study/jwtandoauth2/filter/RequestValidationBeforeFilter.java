package com.study.jwtandoauth2.filter;



import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.util.StringUtils;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

// 필요한 경우 필터를 추가하여 사용 가능 -> 필요한 기능 직접 구현 가능
public class RequestValidationBeforeFilter implements Filter {

    public static final String AUTHENTICATION_SCHEME_BASIC = "Basic";
    private final Charset credentialsCharset = StandardCharsets.UTF_8;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // HttpServletRequest로 캐스팅
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        // 헤더에서 Authorization 정보를 가져옴
        String header = req.getHeader(AUTHORIZATION);
        if (header != null) {
            // 헤더값 내 공백 제거
            header = header.trim();
            // Basic으로 시작하는지 확인
            if (StringUtils.startsWithIgnoreCase(header, AUTHENTICATION_SCHEME_BASIC)) {
                // 헤더의 여섯 번째 부분에서 문자열 추출 -> 초기 문자 basic 제외
                byte[] base64Token = header.substring(6).getBytes(StandardCharsets.UTF_8);
                byte[] decoded;
                try {
                    decoded = Base64.getDecoder().decode(base64Token);
                    String token = new String(decoded, credentialsCharset);
                    int delim = token.indexOf(":");
                    if (delim == -1) {
                        throw new BadCredentialsException("Invalid basic authentication token");
                    }
                    // 가장 첫 두 값을 추출
                    String email = token.substring(0, delim);
                    // 이메일에 test가 포함되어 있으면 400 에러 반환
                    if (email.toLowerCase().contains("test")) {
                        res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                        return;
                    }
                } catch (IllegalArgumentException e) {
                    throw new BadCredentialsException("Failed to decode basic authentication token");
                }
            }
        }
        // doFilter 메소드에 세 번째 매개변수로 등록
        filterChain.doFilter(req, res);
    }
}
