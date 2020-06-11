package ru.komendantov.corpabuilder.swagger.interfaces;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import ru.komendantov.corpabuilder.auth.models.LoginRequest;
import ru.komendantov.corpabuilder.auth.models.SignupRequest;

import javax.validation.Valid;

@Api(value = "Аутентификация и регистрация", tags = "authentication")
public interface AuthController {
    @ApiOperation(value = "Аутентифицировать пользователя")
    ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest);

    @ApiOperation(value = "Регистрация пользователя")
    ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest);
}