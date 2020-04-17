package ru.komendantov.corpabuilder.controllers.users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.komendantov.corpabuilder.services.UsersService;

import java.util.Optional;

@RestController
public class UserProfileController {

    @Autowired
    private UsersService customerService;

    @GetMapping(value = "/api/users/user/{username}",produces = "application/json")
    public Optional getUserDetail(@PathVariable String username){
    return customerService.findByLogin(username);
    }
}