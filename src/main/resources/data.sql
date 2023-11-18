INSERT INTO books (title, author, isbn, price, description) VALUES
                                                                ('1984', 'George Orwell', '978-0451524935', '8.99', 'A dystopian social science fiction novel and cautionary tale.'),
                                                                ('To Kill a Mockingbird', 'Harper Lee', '978-0446310789', '7.19', 'A novel of warmth and humor despite dealing with serious issues of rape and racial inequality.'),
                                                                ('The Great Gatsby', 'F. Scott Fitzgerald', '978-0743273565', '9.89', 'A novel about the themes of resistance to change, decadence, and social upheaval.')
;

INSERT INTO sec_user (email, encryptedPassword, enabled)
VALUES ('fahad.jan@sheridancollege.ca', '$2a$10$1ltibqiyyBJMJQ4hqM7f0OusP6np/IHshkYc4TjedwHnwwNChQZCy', 1);

INSERT INTO sec_user (email, encryptedPassword, enabled)
VALUES ('frank@sheridancollege.ca', '$2a$10$PrI5Gk9L.tSZiW9FXhTS8O8Mz9E97k2FZbFvGFFaSsiTUIl.TCrFu', 1);


INSERT INTO sec_role (roleName)
VALUES ('ROLE_USER');

INSERT INTO sec_role (roleName)
VALUES ('ROLE_GUEST');



INSERT INTO user_role (userId, roleId)
VALUES (1, 1);

INSERT INTO user_role (userId, roleId)
VALUES (2, 2);

