package ru.gateway.service;

import feign.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import ru.gateway.models.UserDto;

@FeignClient(name = "user-service", url = "http://user-service:8080")
public interface UserServiceClient {

    @GetMapping("/users")
    ResponseEntity<Page<UserDto>> getAllUsers(@RequestHeader (value = "Authorization") String authHeader, Pageable pageable);

    @GetMapping("/users/{id}")
    UserDto getUser(@PathVariable Long id, @RequestHeader (value = "Authorization") String authHeader);

    @PostMapping("/registration")
    ResponseEntity<?> registration(UserDto userDto);

    @PostMapping ("/authorization")
    String authorization(@RequestHeader (value = "Authorization") String authHeader);
}
