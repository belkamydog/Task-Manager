package ru.gateway.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.gateway.exceptions.TaskNotFoundException;
import ru.gateway.exceptions.UserNotFoundException;
import ru.gateway.models.Comment;
import ru.gateway.models.TaskDto;
import ru.gateway.models.TaskWithComments;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskService {
    private final TaskServiceClient taskServiceClient;
    private final UserServiceClient userServiceClient;
    private final CommentClientService commentClientService;
    private final JwtService jwtService;

    public TaskService(TaskServiceClient taskServiceClient, UserServiceClient userServiceClient, CommentClientService commentClientService, JwtService jwtService) {
        this.taskServiceClient = taskServiceClient;
        this.userServiceClient = userServiceClient;
        this.commentClientService = commentClientService;
        this.jwtService = jwtService;
    }

    public TaskDto createTask(@RequestHeader (value = "Authorization") String authHeader,
                              TaskDto taskDto) {
        Long user_id = jwtService.getUserIdFromAuthHeader(authHeader);
        return taskServiceClient.addTask(authHeader, user_id, taskDto).getBody();
    }

    public ResponseEntity <?> updateTask(@RequestHeader (value = "Authorization") String authHeader,
                                         Long task_id,
                                         TaskDto taskDto){
        Long user_id = jwtService.getUserIdFromAuthHeader(authHeader);
        return taskServiceClient.updateTask(authHeader, user_id, task_id, taskDto);
    }

    public ResponseEntity <?> updateTaskExecutor(@RequestHeader (value = "Authorization") String authHeader,
                                                 Long task_id, Long executor_id){
        Long user_id = jwtService.getUserIdFromAuthHeader(authHeader);
        if (userServiceClient.getUser(executor_id, authHeader) == null){
            throw  new UserNotFoundException();
        }
        return taskServiceClient.updateExecutor(authHeader, user_id, task_id, executor_id);
    }

    public ResponseEntity <?> updateTaskState(@RequestHeader (value = "Authorization") String authHeader,
                                              Long task_id,
                                              String status){
        Long user_id = jwtService.getUserIdFromAuthHeader(authHeader);
        System.err.println(user_id);
        return taskServiceClient.updateTaskState(authHeader, user_id, task_id, status);
    }

    public void deleteTask(@RequestHeader (value = "Authorization") String authHeader,
                                            Long task_id){
        Long user_id = jwtService.getUserIdFromAuthHeader(authHeader);
        taskServiceClient.deleteTask(authHeader, user_id, task_id);
    }


    public Page<TaskDto> getTasks(@RequestHeader (value = "Authorization") String authHeader,
                                  Pageable pageable){
        Long user_id = jwtService.getUserIdFromAuthHeader(authHeader);
        return taskServiceClient.getTasksByOwner(authHeader, user_id, pageable).getBody();
    }

    public ResponseEntity <Page<TaskDto>> getTasksWhereUserIsExecutor(@RequestHeader (value = "Authorization") String authHeader,
                                                     Pageable pageable){
        Long user_id = jwtService.getUserIdFromAuthHeader(authHeader);
        return  taskServiceClient.getTasksByExecutor(authHeader, user_id);
    }

    public TaskWithComments getTaskWithComments(
                                String authHeader,
                                Long task_id){
        TaskDto taskDto =  taskServiceClient.getTask(authHeader , task_id).getBody();
        if (taskDto == null) throw new TaskNotFoundException(task_id.toString());
        Page<Comment> comments = commentClientService.getComments(authHeader, task_id).getBody();
        List<Comment> commentList = new ArrayList<>();
        if (comments != null) commentList.addAll(comments.getContent());
        return new TaskWithComments(taskDto, commentList);
    }
}
