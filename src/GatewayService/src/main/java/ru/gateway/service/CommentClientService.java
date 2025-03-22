package ru.gateway.service;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gateway.models.Comment;

@FeignClient (name = "comment-service", url = "http://comment-service:8080")
public interface CommentClientService {

    @PostMapping("/tasks/{task_id}/comments")
    ResponseEntity <?> addComment(@RequestHeader (name = "Authorization") String authHeader,
                                  @PathVariable("task_id") Long taskId,
                                  @RequestBody Comment comment);

    @GetMapping("/tasks/{task_id}/comments")
    ResponseEntity <Page<Comment>> getComments(@RequestHeader (name = "Authorization") String authHeader,
                                               @PathVariable Long task_id);
}
