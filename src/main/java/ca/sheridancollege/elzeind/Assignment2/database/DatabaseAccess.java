package ca.sheridancollege.elzeind.Assignment2.database;

import ca.sheridancollege.elzeind.Assignment2.beans.CartItem;
import ca.sheridancollege.elzeind.Assignment2.beans.Order;
import org.slf4j.Logger;

import ca.sheridancollege.elzeind.Assignment2.beans.Book;
import ca.sheridancollege.elzeind.Assignment2.beans.User;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DatabaseAccess {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseAccess.class);

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    public boolean insertBook(Book book) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            String query = "INSERT INTO books (title, author, isbn, price, description) VALUES (:title, :author, :isbn, :price, :description)";
            namedParameters.addValue("title", book.getTitle());
            namedParameters.addValue("author", book.getAuthor());
            namedParameters.addValue("isbn", book.getIsbn());
            namedParameters.addValue("price", book.getPrice());
            namedParameters.addValue("description", book.getDescription());
            return jdbc.update(query, namedParameters) > 0;
        } catch (Exception e) {
            logger.error("Error inserting book: ", e);
            return false;
        }
    }
    public boolean insertUser(User user){
        try{
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            String query = "INSERT INTO users (username, password, email) VALUES (:username, :password, :email)";
            namedParameters.addValue("username", user.getUsername());
            namedParameters.addValue("password", user.getPassword());
            namedParameters.addValue("email", user.getEmail());
            return jdbc.update(query, namedParameters) > 0;
        } catch (Exception e) {
            logger.error("Error Inserting User: " + e);
            return false;
        }
    }


    public List<Book> getBookList() {
        try {
            MapSqlParameterSource namedParameter = new MapSqlParameterSource();
            String query = "SELECT * FROM books";
            return jdbc.query(query, namedParameter, new BeanPropertyRowMapper<>(Book.class));
        } catch (Exception e) {
            logger.error("Error retrieving book list: ", e);
            return null;
        }
    }

    public List<User> getUserList() {
        try {
            MapSqlParameterSource namedParameter = new MapSqlParameterSource();
            String query = "SELECT * FROM users";
            return jdbc.query(query, namedParameter, new BeanPropertyRowMapper<>(User.class));
        } catch (Exception e) {
            logger.error("Error retrieving user list: ", e);
            return null;
        }
    }

    public boolean updateBook(Book updatedBook) {
        try {
            MapSqlParameterSource namedParameter = new MapSqlParameterSource();
            String query = "UPDATE books SET title = :title, author = :author, isbn = :isbn, price = :price, description = :description WHERE id = :id";
            namedParameter.addValue("title", updatedBook.getTitle());
            namedParameter.addValue("author", updatedBook.getAuthor());
            namedParameter.addValue("isbn", updatedBook.getIsbn());
            namedParameter.addValue("price", updatedBook.getPrice());
            namedParameter.addValue("description", updatedBook.getDescription());
            namedParameter.addValue("id", updatedBook.getId());
            return jdbc.update(query, namedParameter) > 0;
        } catch (Exception e) {
            logger.error("Error updating book: ", e);
            return false;
        }
    }

    public boolean deleteBookById(Long id) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            String query = "DELETE from books WHERE id = :id";
            namedParameters.addValue("id", id);
            return jdbc.update(query, namedParameters) > 0;
        } catch (Exception e) {
            logger.error("Error deleting book: ", e);
            return false;
        }
    }
    public List<Book> getBookById(Long id) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            String query = "SELECT * FROM books WHERE id = :id";
            namedParameters.addValue("id", id);
            return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(Book.class));
        } catch (Exception e) {
            logger.error("Error retrieving book by id: ", e);
            return null;
        }
    }
    public boolean addToCart(CartItem cartItem) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            String query = "INSERT INTO cart (userId, bookId, quantity) VALUES (:userId, :bookId, :quantity)";
            namedParameters.addValue("userId", cartItem.getUserId());
            namedParameters.addValue("bookId", cartItem.getBookId());
            namedParameters.addValue("quantity", cartItem.getQuantity());
            return jdbc.update(query, namedParameters) > 0;
        } catch (Exception e) {
            logger.error("Error adding to cart: ", e);
            return false;
        }
    }
    public List<CartItem> getCartItems(Long userId) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            String query = "SELECT * FROM cart WHERE userId = :userId";
            namedParameters.addValue("userId", userId);
            return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(CartItem.class));
        } catch (Exception e) {
            logger.error("Error retrieving cart items: ", e);
            return new ArrayList<>();
        }
    }

    public boolean placeOrder(Order order) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            String query = "INSERT INTO orders (userId, bookId, quantity) VALUES (:userId, :bookId, :quantity)";
            namedParameters.addValue("userId", order.getUserId());
            namedParameters.addValue("bookId", order.getBookId());
            namedParameters.addValue("quantity", order.getQuantity());
            return jdbc.update(query, namedParameters) > 0;
        } catch (Exception e) {
            logger.error("Error placing order: ", e);
            return false;
        }
    }




}