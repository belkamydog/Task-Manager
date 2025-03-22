package ru.task.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.task.service.model.Task;
import ru.task.service.model.TaskDto;
import ru.task.service.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TasksController {
    private final TaskService taskService;

    public TasksController(TaskService taskService) {
        this.taskService = taskService;
    }

    @Operation (summary = "Добавление задачи")
    @Transactional
    @PostMapping("/{user_id}")
    public ResponseEntity<Task> addTask(@RequestBody TaskDto task, @PathVariable Long user_id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(task, user_id));
    }

    @Operation (summary = "Получение задачи по ее идентификатору")
    @GetMapping("/{task_id}")
    public ResponseEntity<Task> getTask(@PathVariable Long task_id) {
        return  ResponseEntity.status(HttpStatus.OK).body(taskService.getTaskById(task_id));
    }

    @Operation (summary = "Получение задач по id пользователя")
    @GetMapping("/user/{user_id}")
    public ResponseEntity<Page <Task>> getUserTasks(@PathVariable Long user_id,
                                                    Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getUserTasks(pageable, user_id));
    }

    @Operation (summary = "Удаление задачи по id")
    @Transactional
    @DeleteMapping("/{user_id}/{task_id}")
    public ResponseEntity <?> deleteTask(@PathVariable Long task_id,
                                         @PathVariable Long user_id) {
        taskService.deleteTask(task_id, user_id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Operation (summary = "Обновление задачи по id")
    @Transactional
    @PutMapping("/{user_id}/{task_id}")
    public ResponseEntity <?> updateTask(@PathVariable Long task_id,
                                         @PathVariable Long user_id,
                                         @RequestBody Task task) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.updateTask(task_id, user_id, task));
    }

    @Operation (summary = "Получение всех доступных задач, где пользователь является исполнителем")
    @GetMapping("/user/executor/{user_id}")
    public ResponseEntity< Page <Task>> getTasks(Pageable pageable, @PathVariable Long user_id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.getTasks(pageable, user_id));
    }


    @Operation (summary = " Изменение статуса задачи (доступно для владельца задачи и исполнителя)")
    @Transactional
    @PutMapping("/status/{user_id}/{task_id}")
    public ResponseEntity <?> changeTaskStatus(@PathVariable Long task_id,
                                               @PathVariable Long user_id,
                                               @RequestParam String status) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.changeTaskStatus(task_id, user_id, status));
    }

    @Operation (summary = " Изменение исполнителя задачи (доступно для владельца задачи)")
    @Transactional
    @PutMapping("/executor/{owner_id}/{task_id}")
    public ResponseEntity <?> updateTaskExecutor(@PathVariable Long task_id,
                                                 @PathVariable Long owner_id,
                                                 @RequestParam Long executor_id){
        return ResponseEntity.status(HttpStatus.OK)
                .body(taskService.updateTaskExecutor(owner_id, executor_id, task_id));
    }

    @GetMapping("/is_owner/{task_id}/{user_id}")
    public ResponseEntity <?> isOwner(@PathVariable Long task_id,
                                      @PathVariable Long user_id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.isOwner(task_id, user_id));
    }

    @GetMapping("/is_executor/{task_id}/{user_id}")
    public ResponseEntity <?> isExecutor(@PathVariable Long task_id,
                                      @PathVariable Long user_id) {
        return ResponseEntity.status(HttpStatus.OK).body(taskService.isExecutor(task_id, user_id));
    }

}
