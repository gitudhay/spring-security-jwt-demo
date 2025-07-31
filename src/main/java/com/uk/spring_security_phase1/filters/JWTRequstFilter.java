package com.uk.spring_security_phase1.filters;

import com.uk.spring_security_phase1.MyUserDetailsService;
import com.uk.spring_security_phase1.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTRequstFilter extends OncePerRequestFilter {

    public final JWTUtil jwtUtil;
    public final MyUserDetailsService myUserDetailsService;

    public JWTRequstFilter(JWTUtil jwtUtilIn, final MyUserDetailsService myUserDetailsServiceIn) {
        this.jwtUtil = jwtUtilIn;
        myUserDetailsService = myUserDetailsServiceIn;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String userName = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            userName = jwtUtil.extractUsername(jwt);
        }

        if(userName != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            final UserDetails userDetails = myUserDetailsService.loadUserByUsername(userName);
            if(jwtUtil.validateToken(jwt, userDetails)) {
                final UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
