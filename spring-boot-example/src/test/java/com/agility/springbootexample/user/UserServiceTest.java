package com.agility.springbootexample.user;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

  @TestConfiguration
  public static class UserServiceTestConfiguration{
    @Bean
    UserService userService() {
      return new UserService();
    }
  }

  @MockBean
  IUserRepository userRepository;

  @Autowired
  private UserService userService;

  @BeforeEach
  public void setUp() {
    Mockito.when(userRepository.getAll())
        .thenReturn(IntStream.range(0, 5)
        .mapToObj(i -> new User(i, "Vuong " + i, "Vuong@gmail.com", "9999"+i))
        .collect(Collectors.toList()));
  }

  @Test
  public void testCountUser() {
    Assertions.assertEquals(5, userService.totalUser());
  }
}
