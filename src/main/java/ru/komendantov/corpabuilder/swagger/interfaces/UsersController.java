package ru.komendantov.corpabuilder.swagger.interfaces;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.komendantov.corpabuilder.auth.models.User;
import ru.komendantov.corpabuilder.auth.models.UserSettings;
import ru.komendantov.corpabuilder.models.requests.UserUpdateUsernamePutRequest;

import java.util.HashMap;

@Api(value = "users",tags = "users")
public interface UsersController {

    @ApiOperation(value = "Пролучить информацию о пользователе по id", authorizations = {@Authorization(value = "Bearer")})
    User getUser(String id);

    @ApiOperation(value = "Изменить имя пользователя", authorizations = {@Authorization(value = "Bearer")})
    User updateUserUsername(@RequestBody UserUpdateUsernamePutRequest userUpdateUsernamePutRequest);

    @ApiOperation(value = "Получить информацию пользователя, вошедшего в систему", authorizations = {@Authorization(value = "Bearer")})
    User getUser();

    @ApiOperation(value = "Получить настройки пользователя, вошедшего в систему", authorizations = {@Authorization(value = "Bearer")})
    UserSettings getUserSettings();

    @ApiOperation(value = "Получить настройки замены букв пользователя", authorizations = {@Authorization(value = "Bearer")})
    HashMap<String, String> getUserReplaces();

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @ApiOperation(value = "Установить замены для пользователя", authorizations = {@Authorization(value = "Bearer")})
    void setUserReplaces(@RequestBody HashMap<String, String> replaces);

}
