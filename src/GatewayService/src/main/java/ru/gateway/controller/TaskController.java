package ru.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.gateway.models.TaskDto;
import ru.gateway.service.TaskService;


@Controller
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation (summary = "Создание новой задачи авторизованным пользователем",
            description = "Добавление задачи текущего пользователя")
    @PostMapping
    public ResponseEntity <TaskDto> addTask(@RequestBody TaskDto task, @RequestHeader (value = "Authorization") String auhHeader) {
        return  ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(auhHeader, task));
    }

    @Operation (summary = "Удаление задачи авторизованным пользователем",
            description = "Удаление задачи текущего пользователя")
    @DeleteMapping("/{task_id}")
    public ResponseEntity <?> deleteTask(@PathVariable Long task_id, @RequestHeader (value = "Authorization") String auhHeader) {
        taskService.deleteTask(auhHeader, task_id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation (summary = "Обновление задачи авторизованным пользователем",
            description = "Обновление задачи текущего пользователя")
    @PutMapping("/{task_id}")
    public ResponseEntity <?> updateTask(@PathVariable Long task_id,
                                         @RequestHeader (value = "Authorization") String auhHeader,
                                         @RequestBody TaskDto task) {
        return taskService.updateTask(auhHeader, task_id, task);
    }

    @Operation (summary = "Изменить статус задачи",
            description = "Обновление статуса задачи текущего пользователя " +
            "(доступно только исполнителю и владельцу)")
    @PutMapping("/status/{task_id}")
    public ResponseEntity <?> updateTaskState(@PathVariable Long task_id,
                                              @RequestHeader (value = "Authorization") String auhHeader,
                                              @RequestParam String status) {
        return taskService.updateTaskState(auhHeader, task_id, status);
    }

    @Operation (summary = "Назначить исполнителя задачи",
            description = "Назначить или обновить исполнителя задачи " +
            "(доступно только владельцу)")
    @PutMapping("/executor/{task_id}")
    public ResponseEntity <?> updateTaskExecutor(@PathVariable Long task_id,
                                                 @RequestHeader (value = "Authorization") String auhHeader,
                                                 @RequestParam Long executor_id) {
        return taskService.updateTaskExecutor(auhHeader, task_id, executor_id);
    }

    @Operation (summary = "Получение задач созданных пользователем",
            description = "Получение всех задач котрорые создал пользователь с пагинацией")
    @GetMapping("/owner/me")
    public ResponseEntity <Page<TaskDto>> getTasksByOwner(@RequestHeader (value = "Authorization") String auhHeader,
                                                   Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTasks(auhHeader, pageable));
    }

    @Operation (summary = "Получение задач где пользователь является исполнителем",
            description = "Получение всех задач в которых пользователь заявлен как исполнитель с пагинацией")
    @GetMapping("/executor/me")
    public ResponseEntity <Page<TaskDto>> getTasksByExecutor(@RequestHeader (value = "Authorization") String auhHeader,
                                                   Pageable pageable) {
        return taskService.getTasksWhereUserIsExecutor(auhHeader, pageable);
    }

    @Operation (summary = "Получение задачи с комментариями по идентификатору")
    @GetMapping("/{task_id}/comments")
    public ResponseEntity <?> getTaskWithComments(@RequestHeader (name = "Authorization") String authHeader,
                                                  @PathVariable Long task_id){
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskWithComments(authHeader, task_id));
    }
}
