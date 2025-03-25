package ru.task.users;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.task.users.config.SecurityConfig;
import ru.task.users.controller.AuthorizationController;
import ru.task.users.models.User;
import ru.task.users.models.UserDto;
import ru.task.users.service.AuthorizationService;
import ru.task.users.service.JwtService;
import ru.task.users.service.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorizationController.class)
@Import(SecurityConfig.class)
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private AuthorizationService authorizationService;
    @MockitoBean
    private UserService userService;
    @MockitoBean
    private JwtService jwtService;
    @MockitoBean
    private UserDetailsService userDetailsService;


    @Test
    @WithAnonymousUser
    public void registrationTest() throws Exception {
        String json = """
                {
                    "email":"test-user@test.com",
                    "password":"1234",
                    "login":"test-user"
                }
                """;

        UserDto userDto = new UserDto();
        userDto.setLogin("test-user");
        userDto.setEmail("test-user@test.com");
        userDto.setId(1);
        given(authorizationService.registration(any(User.class))).willReturn(userDto);

        mockMvc.perform(post("/registration")
                        .content(json)
                        .contentType("application/json"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.login").value("test-user"))
                .andExpect(jsonPath("$.email").value("test-user@test.com"))
        ;
    }
}
