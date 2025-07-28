package com.uk.spring_security_phase1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
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
        UserDetails userDetails1 = User.withUsername("user")
                .password(passwordEncoder().encode("footest"))
                .roles("USER")
                .build();
        UserDetails userDetails2 = User.withUsername("admin")
                .password(passwordEncoder().encode("fooadmin"))
                .roles("ADMIN")
                .build();
        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource());
        jdbcUserDetailsManager.createUser(userDetails1);
        jdbcUserDetailsManager.createUser(userDetails2);
        return jdbcUserDetailsManager;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurityIn) throws Exception {
        httpSecurityIn.authorizeHttpRequests(authz -> {
            authz
                    .requestMatchers("/admin").hasRole("ADMIN")
                    .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
                    .requestMatchers("/").permitAll()
                    .anyRequest().authenticated();
        }).formLogin(Customizer.withDefaults());
        return httpSecurityIn.build();
    }

}
