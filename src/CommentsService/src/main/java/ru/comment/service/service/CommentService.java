package ru.comment.service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.comment.service.exceptions.NoSuchCommentException;
import ru.comment.service.model.Comment;
import ru.comment.service.model.CommentDto;
import ru.comment.service.repository.CommentsRepository;

@Service
public class CommentService {
    private final CommentsRepository commentsRepository;

    public CommentService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    public Comment createComment(final CommentDto commentDto, final Long taskId) {
        Comment comment = new Comment(commentDto);
        comment.setTaskId(taskId);
        return commentsRepository.save(comment);
    }

    public Comment getComment(final Long commentId, final Long taskId) {
        Comment comment = commentsRepository.findByTaskIdAndId(taskId, commentId);
        if (comment == null) throw new NoSuchCommentException("task " + taskId.toString()
                + "comment " + commentId.toString());
        return comment;
    }

    public Page<Comment> getTaskComments(Pageable pageable, Long taskId) {
        return commentsRepository.findByTaskId(taskId, pageable);
    }

    public Comment updateComment(final Long taskId,  final Long commentId, final Comment comment) {
        Comment updated = commentsRepository.findByTaskIdAndId(taskId, commentId);
        if (updated == null) throw new NoSuchCommentException("task " + taskId.toString()
                + "comment " + commentId.toString());
        updated.updateComment(comment);
        return commentsRepository.save(updated);
    }

    @Transactional
    public void deleteComment(final Long taskId, final Long commentId) {
        Comment comment = commentsRepository.findByTaskIdAndId(taskId, commentId);
        if (comment == null) throw new NoSuchCommentException("task "
                + taskId.toString() + "comment " + commentId.toString());
        commentsRepository.deleteByIdAndTaskId(commentId, taskId);
    }
}
