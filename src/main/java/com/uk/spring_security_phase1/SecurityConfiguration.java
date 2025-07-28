package com.uk.spring_security_phase1;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Security configuration class.
 */
@Configuration
public class SecurityConfiguration {

    /**
     * Configures an in-memory user details service with predefined user credentials
     * and associated roles. This method defines users with their usernames, passwords,
     * and roles for authentication and authorization purposes.
     *
     * @return an instance of {@link InMemoryUserDetailsManager} containing user credentials and roles.
     */
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        UserDetails userDetails = User.withDefaultPasswordEncoder()
                .username("user")
                .password("footest")
                .roles("USER")
                .username("admin") // multiple users can be defined in the same manager
                .password("fooadmin")
                .roles("USER", "ADMIN") // multiple roles can be assigned to a user
                .build();
        return new InMemoryUserDetailsManager(userDetails);
    }

}
