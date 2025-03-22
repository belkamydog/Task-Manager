package ru.gateway.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.gateway.exceptions.AccessDenyException;
import ru.gateway.exceptions.TaskNotFoundException;
import ru.gateway.exceptions.UserNotFoundException;
import ru.gateway.models.TaskDto;
import ru.gateway.models.UserDto;

@Service
public class AdminService {
    private final UserServiceClient userClient;
    private final TaskServiceClient taskClient;
    private final CommentClientService commentClient;
    private final JwtService jwtService;


    public AdminService(UserServiceClient userClient, TaskServiceClient taskClient, CommentClientService commentClient, JwtService jwtService) {
        this.userClient = userClient;
        this.taskClient = taskClient;
        this.commentClient = commentClient;
        this.jwtService = jwtService;
    }

    public ResponseEntity<?> createTask(String authHeader, TaskDto task, Long user_id) {
        if (!jwtService.getUserRoleFromAuthHeader(authHeader).equals("Admin"))
            throw  new AccessDenyException();
        else if (userClient.getUser(user_id, authHeader) == null)
            throw  new UserNotFoundException();
        return taskClient.addTask(authHeader, user_id, task);
    }

    public ResponseEntity<?> updateTask(String authHeader, TaskDto task, Long task_id, Long user_id) {
        if (!jwtService.getUserRoleFromAuthHeader(authHeader).equals("Admin"))
            throw  new AccessDenyException();
        else if (taskClient.getTask(authHeader, task_id) == null)
            throw  new TaskNotFoundException(task_id.toString());
        return taskClient.updateTask(authHeader, user_id, task_id, task);
    }


    public ResponseEntity<?> updateTaskState(String authHeader, String status, Long task_id, Long user_id) {
        if (!jwtService.getUserRoleFromAuthHeader(authHeader).equals("Admin"))
            throw  new AccessDenyException();
        else if (taskClient.getTask(authHeader, task_id) == null)
            throw  new TaskNotFoundException(task_id.toString());
        return taskClient.updateTaskState(authHeader, user_id, task_id, status);
    }
}
