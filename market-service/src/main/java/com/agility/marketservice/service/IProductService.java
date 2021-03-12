package com.agility.marketservice.service;

import com.agility.marketservice.controller.request.ProductRequest;
import com.agility.marketservice.controller.response.PageResponse;
import com.agility.marketservice.dto.ProductDto;

/**
 * Create by Vuong Nguyen
 *
 * The Interface used to define all method for Product service
 */
public interface IProductService {
  /**
   * Handle get products from database
   *
   * @param page - The current page
   * @param size - The number of items in a page
   * @param search - The text search
   * @param filterAnd - The text filter and. Ex: category.id|eq|1234
   * @param filterOr - The text filter or. Ex: category.id|eq|1234&category.id|eq|987
   * @param orders - The text sort products. Ex: price%7Cdesc%7C
   * @return
   */
  PageResponse<ProductDto> getProducts(int page, int size, String search, String filterAnd, String filterOr, String orders);
  PageResponse<ProductDto> getProducts(int page, int size, String filterAnd, String orders);
  ProductDto getProductById(String id);
  ProductDto createProduct(ProductRequest productRequest);
  ProductDto updateProduct(String id, ProductRequest productRequest);
  ProductDto deleteProduct(String id);
  ProductDto updateStatusProduct(String id, String status);
}
