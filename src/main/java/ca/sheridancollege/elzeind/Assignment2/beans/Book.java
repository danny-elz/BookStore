package ca.sheridancollege.elzeind.Assignment2.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data

public class Book {
    private Long id;
    private String title;
    private String author;
    private String isbn;
    private String price;
    private String description;
}
