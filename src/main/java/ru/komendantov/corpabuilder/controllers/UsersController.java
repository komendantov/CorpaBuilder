package ru.komendantov.corpabuilder.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.komendantov.corpabuilder.auth.interfaces.UserDetailsService;
import ru.komendantov.corpabuilder.auth.models.User;
import ru.komendantov.corpabuilder.auth.repositories.UserRepository;
import ru.komendantov.corpabuilder.auth.models.UserDetailsImpl;
import ru.komendantov.corpabuilder.auth.services.UserDetailsServiceImpl;

import java.util.Optional;


@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;


    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/{id}")
    public User getUser(String id) {
        //need checking if user exists
        return userRepository.getById(id).get();
    }

//
//    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
//    @PostMapping("/me")
//    public ResponseEntity<String> getUser(User user) throws Exception {
//        Optional<User> existingUserRes = userRepository.getByUsername(user.getUsername());
//        User existingUser;
//        if (existingUserRes.isPresent()) {
//            existingUser = existingUserRes.get();
//            String existingUserId = existingUser.getId();
//            user.setId(existingUserId);
//            userRepository.save(user);
//            return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
//        } else {
//            throw new Exception();
//        }
//    }


    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/me")
    public UserDetailsImpl getUser() throws Exception {
        //need checking if user exists
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } else throw new Exception();
    }
}
