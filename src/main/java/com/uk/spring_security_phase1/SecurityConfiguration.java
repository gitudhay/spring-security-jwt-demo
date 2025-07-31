package com.uk.spring_security_phase1;

import com.uk.spring_security_phase1.filters.JWTRequstFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.sql.DataSource;

/**
 * Security configuration class.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    public final DataSource dataSource;
    public final MyUserDetailsService userDetailsService;
    public final JWTRequstFilter jwtRequestFilter;

    public SecurityConfiguration(final DataSource dataSource, final MyUserDetailsService userDetailsService,
                                 final JWTRequstFilter jwtRequestFilterIn) {
        this.dataSource = dataSource;
        this.userDetailsService = userDetailsService;
        jwtRequestFilter = jwtRequestFilterIn;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurityIn) throws Exception {
        httpSecurityIn
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for these specific URLs
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/authenticate").permitAll()
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint((request, response,
                                                   authException) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
                        })
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );

        httpSecurityIn.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurityIn.build();
    }

}
