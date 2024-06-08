package com.study.corsandcsrf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

// 테스트용 어노테이션
@EnableWebSecurity(debug = true)
@SpringBootApplication
public class corsandcsrfApplication {

    public static void main(String[] args) {
        SpringApplication.run(corsandcsrfApplication.class, args);
    }

}
