package ru.gateway.service;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.gateway.models.UserDto;

@Service
public class UserService {
    private final UserServiceClient userServiceClient;
    private final JwtService jwtService;

    public UserService(UserServiceClient userServiceClient, JwtService jwtService) {
        this.userServiceClient = userServiceClient;
        this.jwtService = jwtService;
    }

    public UserDto getMe(String authHeader) {
        long userId = jwtService.getUserIdFromAuthHeader(authHeader);
        return userServiceClient.getUser(userId, authHeader);
    }

    public UserDto getUser(String authHeader, Long userId) {
        return userServiceClient.getUser(userId, authHeader);
    }

    public ResponseEntity <?> getAllUsers(String authHeader, Pageable pageable) {
        return userServiceClient.getAllUsers(authHeader, pageable);
    }

    public ResponseEntity<?> createUser(UserDto userDto) {
        ResponseEntity <?> response = userServiceClient.registration(userDto);
        return ResponseEntity.status(response.getStatusCode()).body(response.getBody());
    }

    public String authorization(String authHeader) {
        return userServiceClient.authorization(authHeader);
    }
}
