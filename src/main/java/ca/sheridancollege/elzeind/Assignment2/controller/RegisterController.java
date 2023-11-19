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
    @Lazy
    private DatabaseAccess da;

    @GetMapping("/register")
    public String getRegister () {
        return "register";
    }
    @PostMapping("/register")
    public String postRegister(@RequestParam String email, @RequestParam String password) {
        da.addUser(email, password);
        User newUser = da.findUserAccount(email);
        if (newUser != null) {
            boolean roleAdded = da.addRole(newUser.getUserId(), "ROLE_USER");
            if (!roleAdded) {
                // Handle the error appropriately
                return "redirect:/registration-failed"; // Redirect to a failure page
            }
            // Continue with the registration process
                return "redirect:/login"; // Redirect to a success page
        } else {
                return "redirect:/registration-failed"; // Redirect to a failure page
        }
    }


}




