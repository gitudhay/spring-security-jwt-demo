package com.uk.spring_security_phase1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.uk.spring_security_phase1")
public class SpringSecurityPhase1Application {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityPhase1Application.class, args);
    }

}
