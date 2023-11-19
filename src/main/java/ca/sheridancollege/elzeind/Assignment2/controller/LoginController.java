package ca.sheridancollege.elzeind.Assignment2.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;

@Controller
public class LoginController {

    @GetMapping("/")
    public String getlogin() {
        return "/login"; // Ensure this page is accessible without login
    }

    @GetMapping("/login")
    public String login() {
        return "/login"; // Ensure this page is accessible without login
    }

    @GetMapping("/permission-denied")
    public String permissionDenied() {
        return "/error/permission-denied"; // Accessible without authentication
    }

}
