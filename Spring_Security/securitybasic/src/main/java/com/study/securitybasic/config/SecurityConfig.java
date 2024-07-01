package com.study.securitybasic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        // 사용자 정의 보안 설정
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/myCards", "/myLoans", "/myAccount", "/myBalance").authenticated()
                        .requestMatchers("/notices", "/contact", "/register").permitAll()
                ).formLogin(withDefaults())
                .httpBasic(withDefaults());
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

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 기본적으로 제공해주는 인터페이스 대신 커스텀 인터페이스 사용
//    @Bean
//    public UserDetailsService userDetailsService(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }
}
