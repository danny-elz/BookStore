package ca.sheridancollege.elzeind.Assignment2.beans;

import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@Data

public class User {
    private Long Id;
    private String username;
    private String password;
    private String email;
}
