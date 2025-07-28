package com.uk.spring_security_phase1;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "<h1>Spring Security - Hello Kaushik!</h1>";
    }

    @GetMapping("/user")
    public String user() {
        return "<h1>Spring Security - Hello User!</h1>";
    }

    @GetMapping("/admin")
    public String admin() {
        return "<h1>Spring Security - Hello Admin!</h1>";
    }

}
