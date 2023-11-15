package ca.sheridancollege.elzeind.Assignment2.controller;


import ca.sheridancollege.elzeind.Assignment2.beans.Book;
import ca.sheridancollege.elzeind.Assignment2.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookController {
    @Autowired
    private DatabaseAccess da;
    @GetMapping("/secure/index")
    public String index(Model model){
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", da.getBookList());
        return "secure/index";
    }
    @PostMapping("/addBook")
    public String insertBook(Model model, @ModelAttribute Book book){
        List<Book> existingBooks = da.getBookById(book.getId());
        if(existingBooks.isEmpty()){
            da.insertBook(book);
        } else {
            da.updateBook(book);
        }
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", da.getBookList());
        return "secure/index";
    }
    @GetMapping("/deleteBookById/{id}")
    public String deleteBookById(Model model, @PathVariable Long id){
        da.deleteBookById(id);
        model.addAttribute("book", new Book());
        model.addAttribute("bookList", da.getBookList());
        return "/secure/index";
    }
    @GetMapping("/editBookById/{id}")
    public String editBookById(Model model, @PathVariable Long id){
        Book book = da.getBookById(id).get(0);
        da.updateBook(book);
        model.addAttribute("book", book);
        model.addAttribute("bookList", da.getBookList());
        return "secure/index";
    }
    // Method to display the list of books
    @GetMapping("/books")
    public String bookList(Model model) {
        List<Book> books = da.getBookList();
        model.addAttribute("books", books);
        return "secure/book-list";
    }

    @GetMapping("/book/{id}")
    public String bookDetail(@PathVariable Long id, Model model) {
        Book book = da.getBookById(id).get(0); // Assuming getBookById returns a list
        model.addAttribute("book", book);
        return "secure/book-detail"; // Assuming the Thymeleaf template is named book-detail.html
    }
}
