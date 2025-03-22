package ru.task.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.task.users.exceptions.UserNotFoundException;
import ru.task.users.models.User;
import ru.task.users.models.UserDto;
import ru.task.users.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<?> findAll(Pageable pageable) {
        return convertPage(userRepository.findAll(pageable));
    }

    public User findById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public Page<UserDto> convertPage(Page<User> userPage) {
        List<UserDto> userDtoList = userPage.getContent()
                .stream()
                .map(UserDto::userMapper)
                .collect(Collectors.toList());
        return new PageImpl<>(
                userDtoList,
                userPage.getPageable(),
                userPage.getTotalElements()
        );
    }
}
