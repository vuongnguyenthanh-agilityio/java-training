package com.agility.marketservice.service;

import com.agility.marketservice.controller.request.ProductRequest;
import com.agility.marketservice.dto.ProductDto;
import com.agility.marketservice.exception.ExceptionType;
import com.agility.marketservice.exception.MarketException;
import com.agility.marketservice.model.Category;
import com.agility.marketservice.model.Product;
import com.agility.marketservice.model.ProductStatus;
import com.agility.marketservice.repository.ICategoryRepository;
import com.agility.marketservice.repository.IProductRepository;
import com.agility.marketservice.util.Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements IProductService {
  @Autowired
  private IProductRepository iProductRepository;
  @Autowired
  private ICategoryRepository iCategoryRepository;

  @Override
  public ProductDto createProduct(ProductRequest productRequest) {
    Optional<Category> categoryData = iCategoryRepository.findById(productRequest.getCategoryId());
    if (!categoryData.isPresent()) {
      throw MarketException.throwException(
          ExceptionType.NOT_FOUND,
          "The category id: " + productRequest.getCategoryId() + " not found.",
          null
      );
    }
    Product product = new Product()
        .setName(productRequest.getName())
        .setStatus(ProductStatus.PENDING)
        .setCategory(categoryData.get())
        .setDescription(productRequest.getDescription())
        .setPrice(productRequest.getPrice())
        .setShippingServices(productRequest.getShippingServices());

    Product newProduct = iProductRepository.save(product);
    ProductDto productDto = Mapper.convertProductDto(newProduct);
    return productDto;
  }
}
