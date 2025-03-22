package ru.task.users.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    private long id;
    private String login;
    private String email;

    public UserDto(long id, String login, String email) {
        this.id = id;
        this.login = login;
        this.email = email;
    }
    public UserDto(final User user){
        this.id = user.getId();
        this.login = user.getLogin();
        this.email = user.getEmail();
    }

    public static UserDto userMapper(User user){
        return new UserDto(user.getId(), user.getLogin(), user.getEmail());
    }
}
