package com.agility.oauthservice.repository;

import com.agility.oauthservice.model.Role;
import com.agility.oauthservice.model.User;
import com.agility.oauthservice.util.UserRoleEnum;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InitializeDatabase {
  private RoleRepository roleRepository;
  private UserRepository userRepository;
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public InitializeDatabase(RoleRepository roleRepository, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
    this.roleRepository = roleRepository;
    this.userRepository = userRepository;
    this.bCryptPasswordEncoder = bCryptPasswordEncoder;
  }

  /**
   * Initialize database for Roles table
   */
  public List<Role> initRoles() {
    List<Role> roles = roleRepository.findAll();
    if (roles != null && !roles.isEmpty()) {
      return roles;
    }
    Role role1 = new Role()
        .setName("Admin role")
        .setCode(UserRoleEnum.ADMIN)
        .setDescription("Full permission to manage the app");
    Role role2 = new Role()
        .setName("Customer role")
        .setCode(UserRoleEnum.CUSTOMER)
        .setDescription("The customer role");

    return roleRepository.saveAll(List.of(role1, role2));
  }

  /**
   * Initialize database for Users table
   *
   * @return
   */
  public List<User> initUsers() {
    List<User> users = userRepository.findAll();
    if (users != null && !users.isEmpty()) {
      return users;
    }

    List<Role> roles = roleRepository.findAll();
    if (roles == null || roles.isEmpty()) {
      roles = initRoles();
    }

    Role adminRole = roles.stream()
        .filter(r -> UserRoleEnum.ADMIN.equals(r.getCode()))
        .findAny()
        .orElse(null);
    Role customerRole = roles.stream()
        .filter(r -> UserRoleEnum.CUSTOMER.equals(r.getCode()))
        .findAny()
        .orElse(null);
    User user1 = new User()
        .setEmail("admin@asnet.com.vn")
        .setFullName("Admin")
        .setRole(adminRole)
        .setPassword(bCryptPasswordEncoder.encode("admin@123"));
    User user2 = new User()
        .setEmail("customer@asnet.com.vn")
        .setFullName("Customer")
        .setRole(customerRole)
        .setPassword(bCryptPasswordEncoder.encode("customer@123"));

    return userRepository.saveAll(List.of(user1, user2));
  }
}
