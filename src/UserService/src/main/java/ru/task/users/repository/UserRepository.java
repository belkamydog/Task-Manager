package ru.task.users.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.task.users.models.User;
import ru.task.users.models.UserDto;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByLogin(String login);
    User findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByLogin(String login);
    Page <UserDto> findAllByEnabledEquals(Pageable pageable, boolean isEnabled);
}
