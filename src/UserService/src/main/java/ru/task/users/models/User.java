package ru.task.users.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
@Table (name = "users")
public class User {

    public enum Role {
        Admin,
        User
    }

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;
    @Column(unique = true, nullable = false)
    private String login;
    @Column(nullable = false)
    private String password;
    @Column(unique = true, nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role = Role.User;
    boolean enabled = true;

    public User(String password, String email, Role userRole) {
        this.password = password;
        this.email = email;
        this.role = userRole;
    }

    public User(String password, String email) {
        this.password = password;
        this.email = email;
    }
}
