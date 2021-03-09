package com.agility.marketservice.service;

import com.agility.marketservice.controller.request.ProductRequest;
import com.agility.marketservice.controller.response.PageResponse;
import com.agility.marketservice.dto.ProductDto;
import com.agility.marketservice.model.Product;

import java.util.List;

public interface IProductService {
  PageResponse<ProductDto> getProducts(int page, int size, String search, String filterAnd, String filterOr, String orders);
  ProductDto getProductById(String id);
  ProductDto createProduct(ProductRequest productRequest);
  ProductDto updateProduct(String id, ProductRequest productRequest);
  ProductDto deleteProduct(String id);
}
