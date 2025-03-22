package ru.gateway.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.gateway.exceptions.AccessDenyException;
import ru.gateway.models.Comment;
import ru.gateway.models.CommentDto;

@Service
public class CommentService {
    private final CommentClientService commentClientService;
    private final TaskServiceClient taskServiceClient;
    private  final JwtService jwtService;


    public CommentService(CommentClientService commentClientService, TaskServiceClient taskServiceClient, JwtService jwtService) {
        this.commentClientService = commentClientService;
        this.taskServiceClient = taskServiceClient;
        this.jwtService = jwtService;
    }

    public ResponseEntity<?> createComment(String authHeader, CommentDto commentDto, Long taskId) {
        Long user_id = jwtService.getUserIdFromAuthHeader(authHeader);
        System.err.println("comment creation");
        if (!taskServiceClient.isOwner(authHeader, taskId, user_id)
                && !taskServiceClient.isExecutor(authHeader, taskId, user_id)) throw new AccessDenyException();
        Comment comment = new Comment(commentDto.getComment(), user_id, taskId);
        return  commentClientService.addComment(authHeader, taskId, comment);
    }

}
