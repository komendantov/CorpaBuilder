package ru.komendantov.corpabuilder.controllers;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
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
//@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
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

//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
//    @PostMapping("/me/settings")
//    public void setUserSettings(UserSettings userSettings) {
//        //need to check
//        User user = userRepository.getByUsername(getUserDetails().getUsername()).get();
//        user.setUserSettings(userSettings);
//        userRepository.save(user);
//        //return new ResponseEntity<String>(HttpStatus.NO_CONTENT);
//    }


    @GetMapping("/me/settings/replaces")
    public HashMap<String, String> getUserReplaces() {
        //need to check
        return userUtils.getUser().getUserSettings().getReplaces();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/me/settings/replaces")
    public void setUserReplaces(@RequestBody HashMap<String, String> replaces) {
        //need to check
        User user = userUtils.getUser();
        user.getUserSettings().setReplaces(replaces);
        userRepository.save(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
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