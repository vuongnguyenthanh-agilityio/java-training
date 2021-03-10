package com.agility.marketservice.repository;

import com.agility.marketservice.model.*;
import com.agility.marketservice.util.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class InitializeDatabase {

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;
  @Autowired
  private IRoleRepository roleRepository;
  @Autowired
  private IUserRepository userRepository;
  @Autowired
  private ICategoryRepository categoryRepository;
  @Autowired
  private IShippingServiceRepository shippingServiceRepository;

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

  /**
   * Initialize database for Categories table
   *
   * @return
   */
  public List<Category> initCategories() {
    List<Category> categories = categoryRepository.findAll();
    if (categories != null && !categories.isEmpty()) {
      return categories;
    }

    Category category1 = new Category()
        .setName("Vehicle")
        .setDescription("Vehicle category");
    Category category2 = new Category()
        .setName("Phone")
        .setDescription("Phone category");

    return categoryRepository.saveAll(List.of(category1, category2));
  }

  public List<ShippingService> initShippingService() {
    List<ShippingService> shippingServices = shippingServiceRepository.findAll();
    if (shippingServices != null && !shippingServices.isEmpty()) {
      return shippingServices;
    }

    ShippingService shippingService1 = new ShippingService()
        .setName("Grab")
        .setDescription("Grab service");

    ShippingService shippingService2 = new ShippingService()
        .setName("ProShip")
        .setDescription("ProShip service");

    return shippingServiceRepository.saveAll(List.of(shippingService1, shippingService2));
  }
}
