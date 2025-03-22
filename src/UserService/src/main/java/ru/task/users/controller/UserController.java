package ru.task.users.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.task.users.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(description = "Получение списка всех пользователей")
    @GetMapping
    public ResponseEntity<?> getUsers(Pageable pageable) {
        return ResponseEntity.ok().body(userService.findAll(pageable));
    }

    @Operation(description = "Получение пользователя по идентификатору")
    @GetMapping("/{user_id}")
    public  ResponseEntity<?> getUser(@PathVariable Long user_id) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findById(user_id));
    }
}

