package ru.task.users;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.task.users.controller.UserController;
import ru.task.users.models.User;
import ru.task.users.service.JwtService;
import ru.task.users.service.UserService;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;

    private String generateTestToken() {
        return Jwts.builder()
                .setSubject("test-user")
                .signWith(SignatureAlgorithm.HS256, "secretkeydfhdfhdfhdfhdfhdfhdfhformytests1234567890")
                .compact();
    }


    @Test
    @WithMockUser
    public void getUser() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setEmail("email@email.com");
        user.setLogin("login");
        given(userService.findById(1L)).willReturn(user);

        mockMvc.perform(get("/users/1")
                        .header("Authorization", "Bearer " + generateTestToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.email").value("email@email.com"))
                .andExpect(jsonPath("$.login").value("login"));
    }
}
