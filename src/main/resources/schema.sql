CREATE TABLE books (id LONG PRIMARY KEY AUTO_INCREMENT,
                    title VARCHAR (255),
                    author VARCHAR (255),
                    isbn VARCHAR (255),
                    price VARCHAR (255),
                    description VARCHAR (255));

CREATE TABLE users (id LONG PRIMARY KEY AUTO_INCREMENT,
                    username VARCHAR (255),
                    password VARCHAR (255),
                    email VARCHAR (255));

CREATE TABLE cart (id LONG PRIMARY KEY AUTO_INCREMENT,
                   userId LONG,
                   bookId LONG,
                   quantity int);
