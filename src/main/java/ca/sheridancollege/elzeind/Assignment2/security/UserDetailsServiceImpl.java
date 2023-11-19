package ca.sheridancollege.elzeind.Assignment2.security;

import ca.sheridancollege.elzeind.Assignment2.beans.User;
import ca.sheridancollege.elzeind.Assignment2.database.DatabaseAccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    @Lazy
    private DatabaseAccess da;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = da.findUserAccount(email);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + email);
        }

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getEncryptedPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        // Assuming every user has the role "ROLE_USER". Modify as per your role setup.
    }
}

