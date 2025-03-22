package ru.task.users.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.task.users.exceptions.AlreadyExistException;
import ru.task.users.models.User;
import ru.task.users.models.UserDto;
import ru.task.users.repository.UserRepository;

import java.util.Base64;

@Service
public class AuthorizationService {
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;


    @Autowired
    public AuthorizationService(PasswordEncoder passwordEncoder,
                                JwtService jwtService,
                                UserRepository userRepository,
                                AuthenticationManager authenticationManager) {
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
    }

    public UserDto registration(User user) {
        checkUserFields(user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new UserDto(user);
    }

    public String authorization(final String authHeader) {
        User user = encodeUserData(authHeader);
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        return createJwtToken(user);
    }

    private String createJwtToken(final User user) {
        return jwtService.generateToken(userRepository.findByEmail(user.getEmail()));
    }

    private User encodeUserData(final String authHeader) {
        String base64Credentials = authHeader.substring("Basic ".length()).trim();
        byte [] credentials = Base64.getDecoder().decode(base64Credentials);
        String credentialsString = new String(credentials);
        String[] credentialsArray = credentialsString.split(":");
        String email = credentialsArray[0];
        String password = credentialsArray[1];
        return new User(password, email);
    }

    private void checkUserFields(final User user) {
        if (user.getPassword() == null || user.getPassword().isEmpty())
            throw new IllegalArgumentException("Password cannot be empty or null");
        if (user.getEmail() == null || user.getEmail().isEmpty())
            throw new IllegalArgumentException("Email cannot be empty or null");
        if (user.getLogin() == null || user.getLogin().isEmpty())
            throw new IllegalArgumentException("Login cannot be empty or null");
        if (userRepository.existsByEmail(user.getEmail()))
            throw  new AlreadyExistException("User with email " + user.getEmail());
        if (userRepository.existsByLogin(user.getLogin()))
            throw new AlreadyExistException("User with login " + user.getLogin());
    }

}
