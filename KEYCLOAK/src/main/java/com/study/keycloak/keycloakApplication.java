package com.study.keycloak;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

// 테스트용 어노테이션
//@EnableWebSecurity(debug = true)
// 메소드 보안을 활성화(사용할 어노테이션 활성화)
@EnableMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
@SpringBootApplication
public class keycloakApplication {

    public static void main(String[] args) {
        SpringApplication.run(keycloakApplication.class, args);
    }

}
