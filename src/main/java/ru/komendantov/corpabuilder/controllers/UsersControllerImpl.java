package ru.komendantov.corpabuilder.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.komendantov.corpabuilder.auth.models.User;
import ru.komendantov.corpabuilder.auth.models.UserSettings;
import ru.komendantov.corpabuilder.auth.repositories.UserRepository;
import ru.komendantov.corpabuilder.auth.services.UserDetailsServiceImpl;
import ru.komendantov.corpabuilder.models.requests.UserPasswordPutRequest;
import ru.komendantov.corpabuilder.models.requests.UserUpdateUsernamePutRequest;
import ru.komendantov.corpabuilder.swagger.interfaces.UsersController;
import ru.komendantov.corpabuilder.utils.UserUtils;

import java.util.HashMap;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping("/api/v1/users")
public class UsersControllerImpl implements UsersController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserUtils userUtils;


    @GetMapping("/{id}")
    public User getUser(String id) {
        //need checking if user exists
        return userRepository.getById(id).get();
    }


    @PutMapping("/me/username")
    public User updateUserUsername(@RequestBody UserUpdateUsernamePutRequest userUpdateUsernamePutRequest) {
        //need to check
        User user = userUtils.getUser();
        user.setUsername(userUpdateUsernamePutRequest.getUsername());
        return userRepository.save(user);
    }


    @GetMapping("/me")
    public User getUser() {
        //need to check
        return userUtils.getUser();
    }


    @GetMapping("/me/settings")
    public UserSettings getUserSettings() {
        //need to check
        return userUtils.getUser().getUserSettings();
    }


    @GetMapping("/me/settings/replaces")
    public HashMap<String, String> getUserReplaces() {
        //need to check
        return userUtils.getUser().getUserSettings().getReplaces();
    }


    @PutMapping("/me/settings/replaces")
    public User setUserReplaces(@RequestBody HashMap<String, String> replaces) {
        //need to check
        User user = userUtils.getUser();
        user.getUserSettings().setReplaces(replaces);
        return userRepository.save(user);
    }

    @PostMapping("/me/settings/replaces")
    public User addUserReplaces(@RequestBody HashMap<String, String> replace) {
        //need to check
        User user = userUtils.getUser();
        HashMap<String, String> replaces = user.getUserSettings().getReplaces();
        replaces.putAll(replace);
        user.getUserSettings().setReplaces(replaces);
        return userRepository.save(user);
    }

    @PutMapping("/me/password")
    public void updateUserPassword(@RequestBody UserPasswordPutRequest userPasswordPutRequest) {
        User user = userUtils.getUser();
        if (!userPasswordPutRequest.getNewPassword().equals(userPasswordPutRequest.getNewPasswordConfirm()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password mismatch");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), userPasswordPutRequest.getCurrentPassword()));
        } catch (AuthenticationException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Check the current password");
        }
        user.setPassword(encoder.encode(userPasswordPutRequest.getNewPassword()));

        userRepository.save(user);
    }
}