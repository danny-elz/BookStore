package ca.sheridancollege.elzeind.Assignment2.controller;

import ca.sheridancollege.elzeind.Assignment2.beans.CartItem;
import ca.sheridancollege.elzeind.Assignment2.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
public class CartController {

    @Autowired
    private DatabaseAccess db;

    @GetMapping("/secure/shopping-cart")
    public String viewShoppingCart(Model model, Principal principal) {
        String userEmail = principal.getName();
        Long userId = db.findUserIdByEmail(userEmail); // Retrieve user ID based on email

        List<CartItem> cartItems = db.getCartItems(userId); // Get cart items for this user

        model.addAttribute("cartItems", cartItems);
        return "secure/shopping-cart";
    }


    @PostMapping("/add-to-cart")
    public String addToCart(@RequestParam Long bookId, @RequestParam int quantity, Principal principal) {
        Long userId = db.findUserIdByEmail(principal.getName());
        if (userId != null && bookId != null && quantity > 0) {
            db.addToCart(new CartItem(userId, bookId, quantity));
        } else {
            // Handle the case where data is null or invalid
            System.out.println("Invalid cart data: userId=" + userId + ", bookId=" + bookId + ", quantity=" + quantity);
        }
        return "redirect:/books";
    }
    @GetMapping("/shopping-cart")
    public String viewCart(Model model, Principal principal) {
        Long userId = db.findUserIdByEmail(principal.getName());
        List<CartItem> cartItems = db.getCartItems(userId);
        model.addAttribute("cartItems", cartItems);
        return "secure/shopping-cart";
    }



}
