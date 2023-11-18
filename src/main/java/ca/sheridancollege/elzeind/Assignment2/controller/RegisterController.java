package ca.sheridancollege.elzeind.Assignment2.controller;

import ca.sheridancollege.elzeind.Assignment2.beans.User;
import ca.sheridancollege.elzeind.Assignment2.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller

public class RegisterController {
    @Autowired
    private DatabaseAccess da;

    @GetMapping("/register")
    public String getRegister () {
        return "/login";
    }

    @Controller
    public class RegistrationController {

        @Autowired
        @Lazy
        private DatabaseAccess da;

        @PostMapping("/register")
        public ModelAndView postRegister(@RequestParam String email, @RequestParam String password) {
            ModelAndView modelAndView = new ModelAndView();

            // Add user
            da.addUser(email, password);

            // Find user account to get the userId
            User user = da.findUserAccount(email);
            if (user == null) {
                modelAndView.setViewName("registration");
                modelAndView.addObject("error", "User not found after registration.");
                return modelAndView;
            }

            // Add role to user
            boolean roleAdded = da.addRole(user.getUserId(), "ROLE_USER");
            if (!roleAdded) {
                modelAndView.setViewName("registration");
                modelAndView.addObject("error", "Failed to assign role to user.");
                return modelAndView;
            }

            // Registration successful
            modelAndView.setViewName("registrationSuccess");
            modelAndView.addObject("user", user);
            return modelAndView;
        }
    }




}
