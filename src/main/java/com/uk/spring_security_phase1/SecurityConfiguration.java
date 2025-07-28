package com.uk.spring_security_phase1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

/**
 * Security configuration class.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Bean
    public DataSource dataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("schema.sql")
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    /**
     * Configures an in-memory user details service with predefined user credentials
     * and associated roles. This method defines users with their usernames, passwords,
     * and roles for authentication and authorization purposes.
     *
     * @return an instance of {@link InMemoryUserDetailsManager} containing user credentials and roles.
     */
    @Bean
    public UserDetailsManager userDetailsService() {
        return new JdbcUserDetailsManager(dataSource());
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurityIn) throws Exception {
        httpSecurityIn.authorizeHttpRequests(authz -> authz
                .requestMatchers("/admin").hasRole("ADMIN")
                .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
                .requestMatchers("/").permitAll()
                .anyRequest().authenticated()).formLogin(Customizer.withDefaults());
        return httpSecurityIn.build();
    }

}
