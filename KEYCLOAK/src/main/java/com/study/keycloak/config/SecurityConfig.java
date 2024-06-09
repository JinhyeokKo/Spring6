package com.study.keycloak.config;

//import com.study.keycloak.filter.AuthoritiesLoggingAfterFilter;
//import com.study.keycloak.filter.AuthoritiesLoggingAtFilter;
//import com.study.keycloak.filter.CsrfCookieFilter;
//import com.study.keycloak.filter.JWTTokenGeneratorFilter;
//import com.study.keycloak.filter.JWTTokenValidatorFilter;
//import com.study.keycloak.filter.RequestValidationBeforeFilter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        CsrfTokenRequestAttributeHandler requestAttributeHandler = new CsrfTokenRequestAttributeHandler();
        requestAttributeHandler.setCsrfRequestAttributeName("_csrf");
        // converter에 커스텀 로직을 반환하여 keycloak에서 받은 jwt 액세스토큰을 읽어 grantedauthorities로 변환
        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());

        // 사용자 정의 보안 설정
        http
                // 세션 관리 정책 설정 -> 항상 새로운 세션 생성
                // 이로 인해 첫 로그인 후 만들어진 세션id로 이후 후속 요청들을 계속 활용
                // 매번 보안된API에 접근할 때마다 자격증명 필요X
//                .sessionManagement((session) -> session
//                        .sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
                // 이제 JSESSIONID 쿠키를 사용하지 않아 생성 중지 요청 -> JWT 사용
                .sessionManagement((session) -> session
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                        // csrf는 get요청에 대해서는 무시 --> db나 서버에 영향을 주지 않는 요청에 대해서는 무시
                        // 단 get 방식이 아닌 방식이지만 서버에 영향을 주지 않은 요청에 대해서는 예외를 두어야 함
                        // 일치하는 요청에 대해 무시 ignoringRequestMatchers()
                ).csrf((csrf) -> csrf
                                // csrf 토큰 값을 생성하고 값이 처리되거나 UI app에 헤더 또는 쿠키의 값을 전달하기 위함
                                .csrfTokenRequestHandler(requestAttributeHandler)
                                .ignoringRequestMatchers(
                                        new AntPathRequestMatcher("/register"),
                                        new AntPathRequestMatcher("/contact")
                                )
                                // csrf 토큰을 쿠키에 저장하고 httpOnly 속성을 false로 설정
                                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                        // Basic ~ 필터를 먼저 실행하고 Csrf ~ 필터를 실행해라는 의미
                        // Basic ~ 필터가 실행이 된 후에 로그인 동작이 완료 가능하고 이후 Csrf 토큰이 생성되기 때문
                        // Basic ~ 필터가 실행되기 전에 Csrf ~ 필터가 실행되면 로그인 동작이 완료되지 않아서 Csrf 토큰이 생성되지 않음
                )
//                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class)
//                .addFilterBefore(new RequestValidationBeforeFilter(), BasicAuthenticationFilter.class)
//                .addFilterAt(new AuthoritiesLoggingAtFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(new AuthoritiesLoggingAfterFilter(), BasicAuthenticationFilter.class)
//                .addFilterAfter(new JWTTokenGeneratorFilter(), BasicAuthenticationFilter.class)
//                .addFilterBefore(new JWTTokenValidatorFilter(), BasicAuthenticationFilter.class)
                .authorizeHttpRequests((requests) -> requests
//                        .requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
//                        .requestMatchers("/myBalance").hasAnyAuthority("VIEWBALANCE", "VOEWBALANCE")
//                        .requestMatchers("/myLoans").hasAuthority("VIEWLOANS")
//                        .requestMatchers("/myCards").hasAuthority("VIEWCARDS")
                                .requestMatchers("/myAccount").hasRole("USER")
                                .requestMatchers("/myBalance").hasAnyRole("USER", "ADMIN")
                                .requestMatchers("/myLoans").authenticated()
                                .requestMatchers("/myCards").hasRole("USER")
                                .requestMatchers("/user").authenticated()
                                .requestMatchers("/notices", "/contact", "/register").permitAll()
//                                .requestMatchers(
//                                        new AntPathRequestMatcher("/")
//                                ).authenticated()
                )
//                .oauth2Login(withDefaults())
//                .formLogin(withDefaults())
//                .httpBasic(withDefaults());
                .oauth2ResourceServer(oauth2ResourceServerCustomizer -> oauth2ResourceServerCustomizer
                        .jwt(jwtCustomizer -> jwtCustomizer
                                .jwtAuthenticationConverter(jwtAuthenticationConverter)));

        return http.build();

        // 모든 요청 거부
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .anyRequest().denyAll()
//                )
//                .formLogin(withDefaults())
//                .httpBasic(withDefaults());
//        return http.build();
//    }

        // 모든 요청 허용(비권장)
//        http
//                .authorizeHttpRequests((requests) -> requests
//                        .anyRequest().permitAll()
//                )
//                .formLogin(withDefaults())
//                .httpBasic(withDefaults());
//        return http.build();
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
    // 방법1. 세부사항을 작성하는동안 withDefaultPasswordEncoder()로 사용자를 생성(임시)
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("1234")
//                .authorities("admin")
//                .build();
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("1234")
//                .authorities("read")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);

    // 방법2. NoOpPasswordEncoder를 사용하여 사용자를 생성
//        UserDetails admin = User.withUsername("admin")
//                .password("1234")
//                .authorities("admin")
//                .build();
//        UserDetails user = User.withUsername("user")
//                .password("1234")
//                .authorities("read")
//                .build();
//        return new InMemoryUserDetailsManager(admin, user);
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder(){
//        // 암호화, 해싱 등 하지 않고 일반 텍스트로 저장
//        return NoOpPasswordEncoder.getInstance();
//    }

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    // 기본적으로 제공해주는 인터페이스 대신 커스텀 인터페이스 사용
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

    // cors 설정
    @Bean
    CorsFilter corsFilter() {
        // cors 설정 저장 객체
        CorsConfiguration configuration = new CorsConfiguration();
        // 허용 출처
        configuration.setAllowedOrigins(List.of("http://localhost:4200"));
        // 허용 메서드
        configuration.setAllowedMethods(List.of("*"));
        // 클라이언트에서 쿠키나 HTTP 인증정보 전송 허용
        configuration.setAllowCredentials(true);
        // 허용 헤더
        configuration.setAllowedHeaders(List.of("*"));
        // 노출 헤더
        configuration.setExposedHeaders(List.of("Authorization"));
        // 클라이언트에서 CORS 프리플라이트 요청의 결과를 1시간동안 캐싱 가능
        configuration.setMaxAge(3600L);
        // url 기반 CORS 설정 구성 위한 객체 생성
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        // 모든 URL 패턴에 대해 구성된 cors 설정 등록
        source.registerCorsConfiguration("/**", configuration);
        return new CorsFilter(source);
    }
//    // OAuth2 클라이언트 등록
//    @Bean
//    public ClientRegistrationRepository clientRepository() {
//        ClientRegistration clientReg = clientRegistration();
//        return new InMemoryClientRegistrationRepository(clientReg);
//    }
//    // OAuth2 클라이언트 등록 정보
//    // 실제로는 하드코딩X
//    // application.properties에 등록 후 @Value로 가져오는 방식 권장
//    private ClientRegistration clientRegistration() {
//		return CommonOAuth2Provider.GITHUB.getBuilder("github").clientId("클라이언트 아이디 입력")
//	           .clientSecret("클라이언트 시크릿키 입력").build();
//	 }
}
