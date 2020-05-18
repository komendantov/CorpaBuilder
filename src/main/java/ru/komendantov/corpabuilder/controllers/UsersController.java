package ru.komendantov.corpabuilder.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.komendantov.corpabuilder.auth.models.User;
import ru.komendantov.corpabuilder.auth.models.UserDetailsImpl;
import ru.komendantov.corpabuilder.auth.models.UserSettings;
import ru.komendantov.corpabuilder.auth.repositories.UserRepository;
import ru.komendantov.corpabuilder.auth.services.UserDetailsServiceImpl;

import java.util.HashMap;


@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private PasswordEncoder encoder;

    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/{id}")
    public User getUser(String id) {
        //need checking if user exists
        return userRepository.getById(id).get();
    }


//    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
//    @PostMapping("/me")
//    public UpdateResult updateUser(User user) throws Exception {
//        //need to check
//        return userDetailsService.updateUser(userRepository.getByUsername(getUserDetails().getUsername()).get().getUsername(), user);
//    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/me/username")
    public User updateUserUsername(String username) {
        //need to check
        User user = userRepository.getByUsername(getUserDetails().getUsername()).get();
        user.setUsername(username);
        return userRepository.save(user);
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/me")
    public User getUser() {
        //need to check
        return userRepository.getByUsername(getUserDetails().getUsername()).get();
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/me/settings")
    public UserSettings getUserSettings() {
        //need to check
        return userRepository.getByUsername(getUserDetails().getUsername()).get().getUserSettings();
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

    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/me/settings/replaces")
    public HashMap<String, String> getUserReplaces() {
        //need to check
        return userRepository.getByUsername(getUserDetails().getUsername()).get().getUserSettings().getReplaces();
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/me/settings/replaces")
    public void setUserReplaces(@RequestParam JSONObject replaces) throws JsonProcessingException {
        //need to check
        User user = userRepository.getByUsername(getUserDetails().getUsername()).get();
        user.getUserSettings().setReplaces(new ObjectMapper().readValue(replaces.toString(), HashMap.class));
        userRepository.save(user);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @PutMapping("/me/password")
    public void updateUserPassword(String password) {
        User user = userRepository.getByUsername(getUserDetails().getUsername()).get();
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
    }


    private UserDetailsImpl getUserDetails() {
        //need checking if user exists
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            return (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } else throw new RuntimeException();
    }

}
