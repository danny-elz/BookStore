package ca.sheridancollege.elzeind.Assignment2.database;

import ca.sheridancollege.elzeind.Assignment2.beans.CartItem;
//import ca.sheridancollege.elzeind.Assignment2.beans.Order;
import org.slf4j.Logger;

import ca.sheridancollege.elzeind.Assignment2.beans.Book;
import ca.sheridancollege.elzeind.Assignment2.beans.User;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class DatabaseAccess {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseAccess.class);

    @Autowired
    protected NamedParameterJdbcTemplate jdbc;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        public void addToCart(CartItem cartItem) {
            String query = "INSERT INTO cart (userId, bookId, quantity) VALUES (:userId, :bookId, :quantity)";
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("userId", cartItem.getUserId());
            namedParameters.addValue("bookId", cartItem.getBookId());
            namedParameters.addValue("quantity", cartItem.getQuantity());
            jdbc.update(query, namedParameters);
        }

    public Long findUserIdByEmail(String email) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("email", email);

            String query = "SELECT userId FROM sec_user WHERE email = :email";
            return jdbc.queryForObject(query, namedParameters, Long.class);
        } catch (EmptyResultDataAccessException e) {
            return null;
        } catch (Exception e) {
            logger.error("Error finding user ID by email: ", e);
            return null;
        }
    }

    public List<CartItem> getCartItems(Long userId) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            String query = "SELECT c.id as cartId, c.userId, c.bookId, c.quantity, b.title as bookName, b.price as bookPrice " +
                    "FROM cart c INNER JOIN books b ON c.bookId = b.id " +
                    "WHERE c.userId = :userId";

            namedParameters.addValue("userId", userId);
            return jdbc.query(query, namedParameters, new BeanPropertyRowMapper<>(CartItem.class));
        } catch (Exception e) {
            logger.error("Error retrieving cart items for user ID " + userId + ": ", e);
            return new ArrayList<>();
        }
    }


    /*public boolean placeOrder(Order order) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            String query = "INSERT INTO orders (userId, Address, TotalAmount) VALUES (:userId, :Address, :TotalAmount)";
            namedParameters.addValue("userId", order.getUserId());
            namedParameters.addValue("Address", order.getAddress());
            namedParameters.addValue("TotalAmount", order.getTotalAmount());
            return jdbc.update(query, namedParameters) > 0;
        } catch (Exception e) {
            logger.error("Error placing order: ", e);
            return false;
        }
    }

     */
    public User findUserAccount(String email) {
        try {
            String query = "SELECT * FROM sec_user WHERE email = :email";
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            namedParameters.addValue("email", email);
            return jdbc.queryForObject(query, namedParameters, new BeanPropertyRowMapper<>(User.class));
        } catch (EmptyResultDataAccessException e) {
            // This exception is thrown when the result is empty, i.e., user not found
            return null;
        }
    }
    public List<String> getRolesById(Long userId) {
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        String query = "SELECT sec_role.roleName "
                + "FROM user_role, sec_role "
                + "WHERE user_role.roleId = sec_role.roleId "
                + "AND userId = :userId";
        namedParameters.addValue("userId", userId);
        return jdbc.queryForList(query, namedParameters, String.class);
    }

    public String addUser(String email, String password) {

        try {
            String encryptedPassword = passwordEncoder.encode(password);

            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            String query = "INSERT INTO sec_user (email, encryptedPassword, enabled) VALUES (:email, :encryptedPassword, 1)";
            namedParameters.addValue("email", email);
            namedParameters.addValue("encryptedPassword", encryptedPassword);

            jdbc.update(query, namedParameters);
        } catch (DataIntegrityViolationException e) {
            if (e.getCause() != null) {
                return "EmailExists";
            }
            throw e;

        }
        return null;
    }


    public boolean addRole(Long userId, String roleName) {
        Long roleId = findRoleIdByRoleName(roleName);
        if (roleId == null) {
            logger.error("Role not found for role name: " + roleName);
            return false;
        }

        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            String query = "INSERT INTO user_role (userId, roleId) VALUES (:userId, :roleId)";
            namedParameters.addValue("userId", userId);
            namedParameters.addValue("roleId", roleId);
            return jdbc.update(query, namedParameters) > 0;
        } catch (Exception e) {
            logger.error("Error adding role to user: ", e);
            return false;
        }
    }
    public Long findRoleIdByRoleName(String roleName) {
        try {
            MapSqlParameterSource namedParameters = new MapSqlParameterSource();
            String query = "SELECT roleId FROM sec_role WHERE roleName = :roleName";
            namedParameters.addValue("roleName", roleName);
            return jdbc.queryForObject(query, namedParameters, Long.class);
        } catch (Exception e) {
            logger.error("Error finding role ID by role name: ", e);
            return null;
        }
    }







}