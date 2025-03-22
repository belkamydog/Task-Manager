package ru.comment.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.comment.service.model.Comment;
import ru.comment.service.model.CommentDto;
import ru.comment.service.service.CommentService;

@RestController
@RequestMapping("/tasks/{task_id}/comments")
public class CommentsController {
    private final CommentService commentService;

    public CommentsController(CommentService commentService) {
        this.commentService = commentService;
    }

    @Operation (summary = "Создание коментария к задаче")
    @PostMapping
    public ResponseEntity<Comment> addComment(@RequestBody CommentDto comment, @PathVariable Long task_id) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.createComment(comment, task_id));
    }

    @Operation (summary = "Получение конкретного коментария к задаче")
    @GetMapping("/{comment_id}")
    public ResponseEntity<Comment> getComment(@PathVariable Long comment_id, @PathVariable Long task_id) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getComment(comment_id, task_id));
    }

    @Operation (summary = "Получение всех доступных коментариев к задаче")
    @GetMapping
    public ResponseEntity< Page <Comment>> getTaskComments(Pageable pageable, @PathVariable Long task_id) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.getTaskComments(pageable, task_id));
    }

    @Operation (summary = "Обновление коментария к задаче")
    @PutMapping("/{comment_id}")
    public ResponseEntity<Comment> updateComment(@PathVariable Long task_id,
                                                 @PathVariable Long comment_id,
                                                 @RequestBody Comment comment) {
        return ResponseEntity.status(HttpStatus.OK).body(commentService.updateComment(task_id, comment_id, comment));
    }

    @Operation (summary = "Удаление коментария к задаче")
    @DeleteMapping("/{comment_id}")
    public ResponseEntity <?> deleteComment(@PathVariable Long task_id,
                                            @PathVariable Long comment_id) {
        commentService.deleteComment(task_id, comment_id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
