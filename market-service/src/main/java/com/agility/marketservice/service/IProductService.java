package com.agility.marketservice.service;

import com.agility.marketservice.controller.request.ProductRequest;
import com.agility.marketservice.dto.ProductDto;

public interface IProductService {
  ProductDto createProduct(ProductRequest productRequest);
}
