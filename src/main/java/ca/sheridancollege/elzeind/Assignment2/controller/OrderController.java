package ca.sheridancollege.elzeind.Assignment2.controller;

import ca.sheridancollege.elzeind.Assignment2.beans.CartItem;
import ca.sheridancollege.elzeind.Assignment2.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    DatabaseAccess db;
    @GetMapping("/order-success")
    public String orderSuccess(Model model, Principal principal) {
        Long userId = db.findUserIdByEmail(principal.getName());
        List<CartItem> cartItems = db.getCartItems(userId);
        model.addAttribute("cartItems", cartItems);
        return "order-success";
    }


}
