package com.agility.springbootexample.user;

import java.util.List;

public interface IUserRepository {
  List<User> getAll();
  User getUser(int id);
  User updateUser(User user);
  User deleteUser(String id);
}
