package ru.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.config.Task;
import org.springframework.web.bind.annotation.*;
import ru.gateway.models.Comment;
import ru.gateway.models.TaskDto;
import ru.gateway.models.UserDto;
import ru.gateway.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation (summary = "Создание задачи с для любого пользователя",
                description = "Администратор может создать задачу для любого пользователя")
    @PostMapping("/tasks/{user_id}")
    ResponseEntity<?> createTaskForUserByIdAdmin(@RequestHeader (name = "Authorization") String authString,
                                            @PathVariable Long user_id,
                                            @RequestBody TaskDto task) {
        return adminService.createTask(authString, task, user_id);
    }

    @Operation (summary = "Обновление задачи любого пользователя",
            description = "Администратор может обновить задачу любого пользователя")
    @PutMapping("/tasks/{user_id}/{task_id}")
    ResponseEntity<?> updateTaskForUserByIdAdmin(@RequestHeader (name = "Authorization") String authString,
                                            @PathVariable Long task_id,
                                            @PathVariable Long user_id,
                                            @RequestBody TaskDto task) {
        return adminService.updateTask(authString, task, task_id, user_id);
    }

    @Operation (summary = "Обновление статуса задачи любого пользователя",
            description = "Администратор может обновить статус задачи любого пользователя")
    @PutMapping("/tasks/{user_id}/{task_id}/state")
    ResponseEntity<?> updateTaskStateForUserById(@RequestHeader (name = "Authorization") String authString,
                                            @PathVariable Long task_id,
                                            @PathVariable Long user_id,
                                            @RequestParam String state) {
        return adminService.updateTaskState(authString, state, task_id, user_id);
    }

    @Operation (summary = "Обновление приоритета задачи любого пользователя",
            description = "Администратор может изменить приоритет задачи любого пользователя")
    @PutMapping("/tasks/{task_id}/priority")
    ResponseEntity<?> updateTaskPriorityForUserById(@RequestHeader (name = "Authorization") String authString,
                                                 @PathVariable Long task_id,
                                                 @RequestParam String priority) {
        return ResponseEntity.ok(priority);
    }

    @Operation (summary = "Назначить исполнителя задачи для любой задачи",
            description = "Администратор может назначить исполнителя задачи любого пользователя")
    @PutMapping("/tasks/{task_id}/executor")
    ResponseEntity<?> setTaskExecutorForUserById(@RequestHeader (name = "Authorization") String authString,
                                                    @PathVariable Long task_id,
                                                    @RequestParam Long executor_id) {
        return ResponseEntity.ok(executor_id);
    }


    @Operation (summary = "Создание комментария к любой доступной задаче",
            description = "Администратор может создать коментарий к задаче любого пользователя")
    @PostMapping("/tasks/{task_id}/comments")
    ResponseEntity<?> createCommentForTaskAdmin(@RequestHeader (name = "Authorization") String authString,
                                                @PathVariable Long task_id,
                                                @RequestBody Comment comment) {
        return ResponseEntity.ok(comment);
    }

    @Operation (summary = "Удаление комментария к любой доступной задаче",
            description = "Администратор может удалить коментарий к задаче любого пользователя")
    @DeleteMapping("/tasks/{task_id}/comments/{comment_id}")
    ResponseEntity<?> deleteCommentForTaskAdmin(@RequestHeader (name = "Authorization") String authString,
                                                @PathVariable Long task_id,
                                                @PathVariable Long comment_id) {
        return ResponseEntity.ok(comment_id);
    }


    @Operation (summary = "Блокировка любого пользователя",
            description = "Администратор может заблокировать любого пользователя")
    @PutMapping("/users/{user_id}/block")
    ResponseEntity<?> blockUserById(@RequestHeader (name = "Authorization") String authString,
                                                 @PathVariable Long user_id) {
        return ResponseEntity.ok().build();
    }

    @Operation (summary = "Разблокировка любого пользователя",
            description = "Администратор может разблокировать любого пользователя")
    @PutMapping("/users/{user_id}/unblock")
    ResponseEntity<?> unblockUserById(@RequestHeader (name = "Authorization") String authString,
                                      @PathVariable Long user_id) {
        return ResponseEntity.ok().build();
    }

    @Operation (summary = "Изменение данных любого пользователя",
            description = "Администратор может изменить данные любого пользователя")
    @PutMapping("/users/{user_id}")
    ResponseEntity<?> updateUserById(@RequestHeader (name = "Authorization") String authString,
                                     @PathVariable Long user_id,
                                     @RequestBody UserDto user) {
        return ResponseEntity.ok().build();
    }
}
