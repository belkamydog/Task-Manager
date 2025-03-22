package ru.gateway.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
public class TaskWithComments {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private Long ownerId;
    private Long executorId;
    private List<Comment> comments;

    public TaskWithComments(TaskDto taskDto, List<Comment> comments) {
        this.id = taskDto.getId();
        this.title = taskDto.getTitle();
        this.description = taskDto.getDescription();
        this.status = taskDto.getStatus();
        this.priority = taskDto.getPriority();
        this.ownerId = taskDto.getOwnerId();
        this.executorId = taskDto.getExecutorId();
        this.comments = new ArrayList<>();
        this.comments.addAll(comments);
    }
}
