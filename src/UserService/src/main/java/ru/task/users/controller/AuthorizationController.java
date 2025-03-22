package ru.task.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.task.users.models.User;
import ru.task.users.models.UserDto;
import ru.task.users.service.AuthorizationService;

@RestController
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @Operation (description = "Регистрация нового пользователя")
    @PostMapping("/registration")
    public ResponseEntity<UserDto> registration(@RequestBody User user) {
        return ResponseEntity.status(HttpStatus.CREATED).body(authorizationService.registration(user));
    }

    @Operation (description = "Вход в систему")
    @PostMapping("/authorization")
    public ResponseEntity<?> authentication(@RequestHeader (value = "Authorization") String authHeader) {
        return ResponseEntity.status(HttpStatus.OK).body(authorizationService.authorization(authHeader));
    }

    @Operation (description = "Проверка авторизации")
    @GetMapping("/authorization")
    public ResponseEntity<?> authorisation(@RequestHeader (value = "Authorization") String authHeader) {
        return ResponseEntity.status(HttpStatus.OK).body(authorizationService.authorization(authHeader));
    }
}
