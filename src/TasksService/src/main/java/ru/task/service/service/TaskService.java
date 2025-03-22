package ru.task.service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.task.service.exceptions.AccessDenyException;
import ru.task.service.exceptions.NoSuchTaskException;
import ru.task.service.model.Task;
import ru.task.service.model.TaskDto;
import ru.task.service.repository.TasksRepository;


@Service
public class TaskService {
    private final TasksRepository tasksRepository;

    public TaskService(TasksRepository tasksRepository) {
        this.tasksRepository = tasksRepository;
    }

    public Task createTask(final TaskDto task, Long userId) {
        task.setOwnerId(userId);
        return tasksRepository.save(new Task(task));
    }

    public Task getTaskById(Long taskId) {
        return tasksRepository.findById(taskId);
    }

    public Page <Task> getUserTasks(Pageable pageable, Long userId) {
        return tasksRepository.findAllByOwnerId(userId, pageable);
    }

    public Page <Task> getTasks(Pageable pageable, Long userId) {
        return tasksRepository.findAllByExecutorId(userId, pageable);
    }

    public Task updateTask(final Long id, final Long user_id, final Task task) {
        if (!tasksRepository.existsByIdAndOwnerId(id , user_id)) throw new NoSuchTaskException(id.toString());
        Task taskToUpdate = tasksRepository.findById(id);
        taskToUpdate.updateTask(task);
        return tasksRepository.save(taskToUpdate);
    }

    public void deleteTask(final Long id, final Long userId) {
        if (!tasksRepository.existsByIdAndOwnerId(id, userId)) throw new NoSuchTaskException(id.toString());
        tasksRepository.deleteById(id);
    }

    public Task changeTaskStatus(final Long id, final Long userId, final String status) {
        if (!tasksRepository.existsById(id)) throw new NoSuchTaskException(id.toString());
        else if (!tasksRepository.existsByIdAndOwnerId(id , userId)
            && !tasksRepository.existsByIdAndExecutorId(id, userId)) {
            throw new AccessDenyException();
        }
        Task taskToUpdate = tasksRepository.findById(id);
        taskToUpdate.setStatus(Task.Status.valueOf(status));
        return tasksRepository.save(taskToUpdate);
    }

    public Task updateTaskExecutor(final Long owner_id, final Long executor_id, final Long task_id) {
        if (!tasksRepository.existsById(task_id)) throw new NoSuchTaskException(task_id.toString());
        else if (!tasksRepository.existsByIdAndOwnerId(task_id, owner_id)) throw new AccessDenyException();
        Task taskToUpdate = tasksRepository.findById(task_id);
        taskToUpdate.setExecutorId(executor_id);
        return tasksRepository.save(taskToUpdate);
    }

    public boolean isOwner(Long userId, Long taskId) {
        return tasksRepository.existsByIdAndOwnerId(userId, taskId);
    }

    public boolean isExecutor(Long userId, Long taskId) {
        tasksRepository.existsByIdAndExecutorId(userId, taskId);
        return tasksRepository.existsByIdAndExecutorId(userId, taskId);
    }
}
