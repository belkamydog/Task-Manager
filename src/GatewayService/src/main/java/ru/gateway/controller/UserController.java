package ru.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gateway.models.UserDto;
import ru.gateway.service.UserService;

@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation (summary = "Регистрация нового пользователя",
            description = "Регистрация нового пользователя")
    @PostMapping("/registration")
    public ResponseEntity<?> registration(@RequestBody UserDto userDto) {
        System.err.println(userDto);
        return  userService.createUser(userDto);
    }

    @Operation (summary = "Аутентификация пользователя",
            description = "Аутентификация пользователя")
    @PostMapping("/authorization")
    public ResponseEntity<?> authorization(@RequestHeader (value = "Authorization") String authHeader) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.authorization(authHeader));
    }

    @Operation (summary = "Получение данных об авторизованном пользователе",
            description = "Получение данных об авторизованном пользователе")
    @GetMapping ("/users/me")
    public ResponseEntity<?> getMe(@RequestHeader (value = "Authorization") String authHeader) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getMe(authHeader));
    }

    @Operation (summary = "Получение данных о пользователе по идентификатору",
            description = "Получение данных о пользователе по идентификатору")
    @GetMapping ("/users/{user_id}")
    public ResponseEntity<?> getUserById(@RequestHeader (value = "Authorization") String authHeader,
                                         @PathVariable Long user_id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.getUser(authHeader, user_id));
    }

    @Operation (summary = "Получение списка всех пользователей",
            description = "Получение данных о пользователях")
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers(@RequestHeader (value = "Authorization") String authHeader, Pageable pageable) {
        return userService.getAllUsers(authHeader,  pageable);
    }
}
