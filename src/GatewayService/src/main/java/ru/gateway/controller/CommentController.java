package ru.gateway.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.gateway.models.CommentDto;
import ru.gateway.service.CommentClientService;
import ru.gateway.service.CommentService;

@Controller
public class CommentController {
    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation (summary = "Добавление комментария к задаче",
    description = "Добавление коментария к задаче (досткпно создателю и исполнтелю)")
    @PostMapping("/tasks/{task_id}/comment")
    public ResponseEntity<?> addComment(@RequestHeader (name = "Authorization") String authHeader,
                                        @PathVariable Long task_id,
                                        @RequestBody CommentDto comment) {
       return commentService.createComment(authHeader, comment, task_id);
    }
}
