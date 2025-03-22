package ru.task.service.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Table
public class Task {

    public enum Status {
        Waiting,
        Processing,
        Finished
    }

    public enum Priority {
        High,
        Middle,
        Low
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    @Enumerated(EnumType.STRING)
    private Priority priority;
    private Long ownerId;
    private Long executorId;

    public Task(TaskDto task) {
        this.title = task.getTitle();
        this.description = task.getDescription();
        this.status = task.getStatus();
        this.priority = task.getPriority();
        this.ownerId = task.getOwnerId();
        this.executorId = task.getExecutorId();
    }

    public void updateTask(final Task src) {
        this.title = src.getTitle() != null && src.getTitle().isEmpty() ? src.getTitle():title;
        this.description = src.getDescription() != null && src.getDescription().isEmpty() ? src.getDescription():description;
        this.status = src.getStatus();
        this.priority = src.getPriority();
        this.executorId = src.getExecutorId();
    }
}
