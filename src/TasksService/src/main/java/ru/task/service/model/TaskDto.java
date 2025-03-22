package ru.task.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {
    private String title;
    private String description;
    private Task.Status status;
    private Task.Priority priority;
    private Long ownerId;
    private Long executorId;
}
