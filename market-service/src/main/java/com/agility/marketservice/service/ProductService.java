package com.agility.marketservice.service;

import com.agility.marketservice.controller.request.ProductRequest;
import com.agility.marketservice.dto.ProductDto;
import com.agility.marketservice.exception.ExceptionType;
import com.agility.marketservice.exception.MarketException;
import com.agility.marketservice.model.Category;
import com.agility.marketservice.model.Product;
import com.agility.marketservice.repository.ICategoryRepository;
import com.agility.marketservice.repository.IProductRepository;
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
    Optional categoryData = iCategoryRepository.findById(productRequest.getCategoryId());
    System.out.println("categoryData: "+ categoryData.isPresent());
    if (!categoryData.isPresent()) {
      throw MarketException.throwException(
          ExceptionType.NOT_FOUND,
          "The category id: " + productRequest.getCategoryId() + " not found.",
          null
      );
    }
    Product product = new Product();
    return null;
  }
}
