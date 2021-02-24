package com.agility.springbootexample.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
  @Autowired
  private IUserRepository userRepository;

  public List<User> getAll(String name) {
    List<User> users = new ArrayList<>();
    if (name == null) {
      userRepository
          .findAll(Sort.by(Sort.Direction.ASC, "createDate"))
          .forEach(users::add);
    } else {
      userRepository
          .findByNameContaining(name)
          .forEach(users::add);
    }

    return users;
  }

  /**
   * Get user by id
   *
   * @param id - The id of user
   * @return
   */
  public User getUser(String id) {
    Optional<User> userData = userRepository.findById(id);
    if (!userData.isPresent()) {
      throw new UserNotFoundException("The user: " + id +" not found");
    }

    return userData.get();
  }

  public User createUser(User user) {
    return userRepository.save(user);
  }

  public User updateUser(String id, User user) {
    Optional<User> userData = userRepository.findById(id);
    if (!userData.isPresent()) {
      throw new UserNotFoundException("The user: " + id +" not found");
    }

    User oldUser = userData.get();
    oldUser.setEmail(user.getEmail());
    oldUser.setName(user.getName());
    oldUser.setPhone(user.getPhone());

    return userRepository.save(oldUser);
  }

  public User deleteUser(String id) {
    Optional<User> userData = userRepository.findById(id);
    if (!userData.isPresent()) {
      throw new UserNotFoundException("The user: " + id +" not found");
    }

    User user = userData.get();
    userRepository.delete(user);

    return user;
  }

  public long totalUser() {
    return userRepository.count();
  }

  public Optional<User> getUser(int id) {
    return userRepository.findById(""+id);
  }
}
