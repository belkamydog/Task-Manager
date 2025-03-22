package ru.gateway.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gateway.models.TaskDto;


@FeignClient(name = "task-service", url = "http://task-service:8080")
public interface TaskServiceClient {
    @PostMapping("/tasks/{user_id}")
    ResponseEntity<TaskDto> addTask(@RequestHeader (value = "Authorization") String authHeader,
                                    @PathVariable Long user_id,
                                    TaskDto taskDto);

    @GetMapping("/tasks/{task_id}")
    ResponseEntity<TaskDto> getTask(@RequestHeader (value = "Authorization") String authHeader,
                                     @PathVariable Long task_id);

    @DeleteMapping("/tasks/{user_id}/{task_id}")
    ResponseEntity<?> deleteTask(@RequestHeader (value = "Authorization") String authHeader,
                                 @PathVariable Long user_id,
                                 @PathVariable Long task_id);

    @PutMapping("/tasks/{user_id}/{task_id}")
    ResponseEntity<?> updateTask(@RequestHeader (value = "Authorization") String authHeader,
                                 @PathVariable Long user_id,
                                 @PathVariable Long task_id,
                                 @RequestBody TaskDto taskDto);

    @PutMapping("/tasks/status/{user_id}/{task_id}")
    ResponseEntity<?> updateTaskState(@RequestHeader (value = "Authorization") String authHeader,
                                      @PathVariable Long user_id,
                                      @PathVariable Long task_id,
                                      @RequestParam String status);

    @GetMapping("/tasks/user/{user_id}")
    ResponseEntity<Page<TaskDto>> getTasksByOwner(@RequestHeader (value = "Authorization") String authHeader,
                                           @PathVariable Long user_id,
                                           Pageable pageable);

    @GetMapping("/tasks/user/executor/{user_id}")
    ResponseEntity<Page<TaskDto>> getTasksByExecutor(@RequestHeader (value = "Authorization") String authHeader,
                                                     @PathVariable Long user_id);

    @PutMapping("/tasks/executor/{owner_id}/{task_id}")
    ResponseEntity <?> updateExecutor(@RequestHeader (value = "Authorization") String authHeader,
                                      @PathVariable Long owner_id,
                                      @PathVariable Long task_id,
                                      @RequestParam Long executor_id);

    @GetMapping("/tasks/is_owner/{task_id}/{user_id}")
    boolean isOwner(@RequestHeader (value = "Authorization") String authHeader,
                               @PathVariable Long task_id,
                               @PathVariable Long user_id);

    @GetMapping("/tasks/is_executor/{task_id}/{user_id}")
    boolean isExecutor(@RequestHeader (value = "Authorization") String authHeader,
                               @PathVariable Long task_id,
                               @PathVariable Long user_id);
}
