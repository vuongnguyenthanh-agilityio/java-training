package com.agility.springbootexample.user;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {
  private MockMvc mvc;
  @Mock
  private UserService userService;
  @InjectMocks
  private UserController userController;
  private JacksonTester<User> jacksonUser;

  @BeforeEach
  public void setUp() {
    JacksonTester.initFields(this, new ObjectMapper());
    mvc = MockMvcBuilders.standaloneSetup(userController)
        .build();
  }

  @Test
  public void testGetAllUsers() throws Exception {
    List<User> users = List.of(
        new User("1", "Vuong", "a", "0"),
        new User("2", "Vuong1", "a1", "01")
    );
    given(userService.getAll(null)).willReturn(users);
    MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.get(
        "/users");
    MockHttpServletResponse response = mvc.perform(builder)
        .andReturn()
        .getResponse();

    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
  }

  @Test
  public void testGetUser() throws Exception {
    User user = new User("1", "Vuong", "a", "0");
    User user1 = new User("1", "Vuong1", "a", "0");
    Optional<User> userData = Optional.of(user);
    given(userService.getUser(1))
        .willReturn(userData);

    MockHttpServletResponse response = mvc.perform(MockMvcRequestBuilders.get("/users/1"))
        .andReturn()
        .getResponse();

    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    Assertions.assertEquals(jacksonUser.write(user).getJson(), response.getContentAsString());
  }
}
