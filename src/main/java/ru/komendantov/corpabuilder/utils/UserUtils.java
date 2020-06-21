package ru.komendantov.corpabuilder.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.komendantov.corpabuilder.auth.models.User;
import ru.komendantov.corpabuilder.auth.models.UserDetailsImpl;
import ru.komendantov.corpabuilder.auth.repositories.UserRepository;
import ru.komendantov.corpabuilder.exceptions.EntityNotFoundException;

import java.util.Optional;

@Service
public class UserUtils {
    @Autowired
    UserRepository userRepository;

    public UserDetailsImpl getUserDetails() {
        //need checking if user exists
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } else throw new RuntimeException();
    }

    public User getUser() {
        Optional<User> user = userRepository.getByUsername(getUserDetails().getUsername());
        if (user.isPresent()) {
            return user.get();
        }
        //change exception type
        else throw new EntityNotFoundException();
    }
}