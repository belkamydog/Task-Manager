package ru.comment.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.comment.service.model.Comment;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer> {
    Comment findByTaskIdAndId(Long taskId, Long id);
    Page<Comment> findByTaskId(Long task_id, Pageable pageable);
    void deleteByIdAndTaskId(Long id, Long task_id);
}
