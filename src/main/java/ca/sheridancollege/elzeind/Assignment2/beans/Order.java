package ca.sheridancollege.elzeind.Assignment2.beans;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Order {
    private Long id;
    private Long userId;
    private Long bookId;
    private int quantity;
}
