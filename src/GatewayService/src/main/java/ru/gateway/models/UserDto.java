package ru.gateway.models;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserDto {
    private Long id;
    private String login;
    private String password;
    private String email;
    private String role = "User";
}
