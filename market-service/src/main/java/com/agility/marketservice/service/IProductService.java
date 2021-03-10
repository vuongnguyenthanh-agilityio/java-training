package com.agility.marketservice.service;

import com.agility.marketservice.controller.request.ProductRequest;
import com.agility.marketservice.controller.response.PageResponse;
import com.agility.marketservice.dto.ProductDto;

public interface IProductService {
  PageResponse<ProductDto> getProducts(int page, int size, String search, String filterAnd, String filterOr, String orders);
  PageResponse<ProductDto> getProducts(int page, int size, String filterAnd, String orders);
  ProductDto getProductById(String id);
  ProductDto createProduct(ProductRequest productRequest);
  ProductDto updateProduct(String id, ProductRequest productRequest);
  ProductDto deleteProduct(String id);
  ProductDto updateStatusProduct(String id, String status);
}
