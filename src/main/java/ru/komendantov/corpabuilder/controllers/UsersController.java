package ru.komendantov.corpabuilder.controllers;

import com.mongodb.client.result.UpdateResult;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.komendantov.corpabuilder.auth.models.User;
import ru.komendantov.corpabuilder.auth.models.UserSettings;
import ru.komendantov.corpabuilder.auth.repositories.UserRepository;
import ru.komendantov.corpabuilder.auth.models.UserDetailsImpl;
import ru.komendantov.corpabuilder.auth.services.UserDetailsServiceImpl;


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


    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/me")
    public UpdateResult updateUser(User user) throws Exception {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //need to check

            return userDetailsService.updateUser(userRepository.getByUsername(userDetails.getUsername()).get().getUsername(), user);
        } else throw new Exception();
    }


    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/me")
    public User getUser() throws Exception {
        //need checking if user exists
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //need to check
            return userRepository.getByUsername(userDetails.getUsername()).get();
        } else throw new Exception();
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @GetMapping("/me/settings")
    public UserSettings getUserSettings() throws Exception {
        //need checking if user exists
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //need to check
            return userRepository.getByUsername(userDetails.getUsername()).get().getUserSettings();
        } else throw new Exception();
    }

    @ApiOperation(value = "", authorizations = {@Authorization(value = "Bearer")})
    @PostMapping("/me/settings")
    public UserSettings setUserSettings() throws Exception {
        //need checking if user exists
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            //need to check
            return userRepository.getByUsername(userDetails.getUsername()).get().getUserSettings();
        } else throw new Exception();
    }

}
