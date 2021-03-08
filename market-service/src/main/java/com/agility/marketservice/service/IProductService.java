package com.agility.marketservice.service;

import com.agility.marketservice.controller.request.ProductRequest;
import com.agility.marketservice.dto.ProductDto;

import java.util.List;

public interface IProductService {
  List<ProductDto> searchProductByName(String name);
  ProductDto getProductById(String id);
  ProductDto createProduct(ProductRequest productRequest);
  ProductDto updateProduct(String id, ProductRequest productRequest);
  ProductDto deleteProduct(String id);
}
