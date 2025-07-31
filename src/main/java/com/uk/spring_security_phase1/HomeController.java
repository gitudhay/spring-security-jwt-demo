package com.uk.spring_security_phase1;

import com.uk.spring_security_phase1.models.AuthenticationRequest;
import com.uk.spring_security_phase1.models.AuthenticationResponse;
import com.uk.spring_security_phase1.util.JWTUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    public final JWTUtil jwtUtil;
    public final MyAuthenticationManager authenticationManager;
    public final MyUserDetailsService myUserDetailsService;

    public HomeController(JWTUtil jwtUtilIn, final MyAuthenticationManager authenticationManager,
                          final MyUserDetailsService myUserDetailsServiceIn) {
        this.jwtUtil = jwtUtilIn;
        this.authenticationManager = authenticationManager;
        myUserDetailsService = myUserDetailsServiceIn;
    }

    @GetMapping("/hello")
    public String home() {
        return "Spring Security - Hello Kaushik!";
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Incorrect username or password", e);
        }
        final String jwtToken =
                jwtUtil.generateToken(myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername()));

        return ResponseEntity.ok(new AuthenticationResponse(jwtToken));
    }

}
