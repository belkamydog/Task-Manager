package ru.task.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.task.service.model.Task;

@Repository
public interface TasksRepository extends JpaRepository<Task, Integer> {
    Task findById(Long id);
    void deleteById(Long id);
    boolean existsById(Long id);
    boolean existsByIdAndOwnerId(Long id, Long ownerId);
    boolean existsByIdAndExecutorId(Long id, Long executorId);
    Page <Task> findAllByOwnerId(Long ownerId, Pageable pageable);
    Page <Task> findAllByExecutorId(Long executorId, Pageable pageable);
}
