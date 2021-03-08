package com.agility.marketservice.util;

import com.agility.marketservice.dto.ProductDto;
import com.agility.marketservice.dto.UserDto;
import com.agility.marketservice.model.Product;
import com.agility.marketservice.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Mapper {
  private static ModelMapper modelMapper = new ModelMapper();

  /**
   * Convert User entity to UserDto
   *
   * @param user
   * @return
   */
  public static UserDto convertUserDto(User user) {
    return  modelMapper.map(user, UserDto.class);
  }

  /**
   * Convert UserDto to User entity
   *
   * @param userDto
   * @return
   */
  public static User convertUserEntity(UserDto userDto) {
    return modelMapper.map(userDto, User.class);
  }

  /**
   * Convert Product entity to ProductDto
   *
   * @param product
   * @return
   */
  public static ProductDto convertProductDto(Product product) {
    return modelMapper.map(product, ProductDto.class);
  }
}
