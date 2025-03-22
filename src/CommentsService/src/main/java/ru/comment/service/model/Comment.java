package ru.comment.service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class Comment {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    private String comment;
    @Column (nullable = false)
    private Long ownerId;
    @Column (nullable = false)
    private Long taskId;

    public Comment(CommentDto commentDto){
        this.comment = commentDto.getComment();
        this.ownerId = commentDto.getOwnerId();
    }

    public void updateComment(final Comment src){
        this.comment = src.getComment();
        this.ownerId = src.getOwnerId();
        this.taskId = src.getTaskId();
    }
}
