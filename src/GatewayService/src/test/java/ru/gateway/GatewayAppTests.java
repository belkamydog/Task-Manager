package ru.gateway;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import ru.gateway.models.UserDto;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"JWT_SECURE_KEY=5%2Nnf/-+.gf_=Cws@saxzcdam.,.*dfg~`v^*gcv(%%%gbf"})
@TestPropertySource(properties = {"user-service.url=http://user-service"})
class GatewayAppTests {

	@Container
	public static DockerComposeContainer<?> environment =
			new DockerComposeContainer<>(new File("/Users/artemefimov/Documents/GitHub/TestTasks/Java/TaskManager/docker-compose.yml"))
					.withExposedService("gateway-service", 8080);

	@Autowired
	private  TestRestTemplate restTemplate;



    @Test
	public void testRegistration() {
		UserDto userDto = new UserDto();
		userDto.setEmail("test-user@example.com");
		userDto.setPassword("password123");
		userDto.setLogin("test-user");

		ResponseEntity<UserDto> response = restTemplate.postForEntity("/registration", userDto, UserDto.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());

		assertEquals(userDto, response.getBody());
	}

}
