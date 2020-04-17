package ru.komendantov.corpabuilder.controllers.users;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.komendantov.corpabuilder.exceptions.EmailExistsException;
import ru.komendantov.corpabuilder.models.User;
import ru.komendantov.corpabuilder.services.UsersService;

@Controller
public class UserRegisterController {

    @Autowired
    private UsersService usersService;


    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/api/users/register")
    public String registerUser(@RequestBody String request) throws EmailExistsException {
        JSONObject requestJson = new JSONObject(request);
        User user = new User();
        user.setLogin(requestJson.getString("login"));
        user.setPassword(passwordEncoder.encode(requestJson.getString("password")));
        usersService.register(user);
        return "hey";
    }
}
