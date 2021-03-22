package com.agility.marketservice.service;

import com.agility.marketservice.controller.request.ProductRequest;
import com.agility.marketservice.controller.response.PageResponse;
import com.agility.marketservice.dto.FilterConditionDto;
import com.agility.marketservice.dto.ProductDto;
import com.agility.marketservice.exception.MarketException;
import com.agility.marketservice.model.Category;
import com.agility.marketservice.model.Product;
import com.agility.marketservice.repository.ICategoryRepository;
import com.agility.marketservice.repository.IProductRepository;
import com.agility.marketservice.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * ** Create by Vuong Nguyen **
 *
 * The Class is used for handle business logic for products
 */
@RequiredArgsConstructor
// Project Lombok will generate a constructor for all properties declared final
@Service
@Primary
public class ProductService implements IProductService {
  private final IProductRepository iProductRepository;
  private final ICategoryRepository iCategoryRepository;
  private final IFilterBuilderService iFilterBuilderService;

  @Override
  public PageResponse<ProductDto> getProducts(int page, int size, String filterAnd, String orders) {
    return getProducts(page, size,null, filterAnd, null, orders);
  }

  /**
   * Handle get products from database
   * Handle search, filter, sort, pagination
   *
   * @param page - The current page
   * @param size - The number of items in a page
   * @param search - The text search
   * @param filterAnd - The text filter and. Ex: category.id|eq|1234
   * @param filterOr - The text filter or. Ex: category.id|eq|1234&category.id|eq|987
   * @param orders - The text sort products. Ex: price%7Cdesc%7C
   * @return
   */
  @Override
  public PageResponse<ProductDto> getProducts(int page,
                                      int size,
                                      String search,
                                      String filterAnd,
                                      String filterOr,
                                      String orders) {
    // Get pageable
    Pageable pageable = iFilterBuilderService.getPageable(size, page, orders);
    // Get condition filter
    List<FilterConditionDto> andConditions = iFilterBuilderService.createFilterCondition(filterAnd);
    List<FilterConditionDto> orConditions = iFilterBuilderService.createFilterCondition(filterOr);
    // Create query
    GenericFilterCriteriaBuilder builder = new GenericFilterCriteriaBuilder();
    Query query = builder.addCondition(andConditions, orConditions);

    // TODO: Will update merge full-text search with the filter in one query
    Page<Product> pageProduct = null;
    if (search != null && !search.isEmpty()) {
      TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(search);
      pageProduct = iProductRepository.findAllBy(criteria, pageable);
    } else {
      pageProduct = iProductRepository.findAll(query, pageable);
    }
    PageResponse<ProductDto> pageResponse = new PageResponse<>();
    List<ProductDto> productDtoList = Mapper.convertProductList(pageProduct.getContent());
    pageResponse.setPageResponse(pageProduct, productDtoList);

    return pageResponse;
  }

  /**
   * Handle create product into Database
   *
   * @param productRequest
   * @return
   */
  @Override
  public ProductDto createProduct(ProductRequest productRequest) {
    // Get category from DB If doesn't exist will throws
    Category category = getCategory(productRequest.getCategoryId());
    Product product = new Product()
        .setName(productRequest.getName())
        .setStatus(ProductStatusEnum.PENDING)
        .setCategory(category)
        .setDescription(productRequest.getDescription())
        .setPrice(productRequest.getPrice())
        .setShippingServices(productRequest.getShippingServices());

    Product newProduct = iProductRepository.insert(product);

    return Mapper.convertProductDto(newProduct);
  }

  /**
   * Handle update product into Database
   *
   * @param id
   * @param productRequest
   * @return
   */
  @Override
  public ProductDto updateProduct(String id, ProductRequest productRequest) {
    Product product = getProduct(id);
    Category category = getCategory(productRequest.getCategoryId());
    product.setName(productRequest.getName())
        .setCategory(category)
        .setDescription(productRequest.getDescription())
        .setPrice(productRequest.getPrice())
        .setShippingServices(productRequest.getShippingServices());

    Product newProduct = iProductRepository.save(product);

    return Mapper.convertProductDto(newProduct);
  }

  /**
   * Handle delete a product
   *
   * @param id
   * @return
   */
  @Override
  public ProductDto deleteProduct(String id) {
    Product product = getProduct(id);
    iProductRepository.delete(product);

    return Mapper.convertProductDto(product);
  }

  /**
   * Handle get a product by id
   *
   * @param id
   * @return
   */
  @Override
  public ProductDto getProductById(String id) {
    Product product = getProduct(id);

    return  Mapper.convertProductDto(product);
  }

  /**
   * Handle update status for product
   *
   * @param id
   * @param status
   * @return
   */
  @Override
  public ProductDto updateStatusProduct(String id, String status) {
    String[] arrStatus = {ProductStatusEnum.APPROVED.name(), ProductStatusEnum.REJECTED.name()};
    if (!ArrayUtil.isContainValue(status, arrStatus)) {
      throw MarketException.throwException(ExceptionTypeEnum.BAD_REQUEST, "Status invalid.");
    }

    Product product = getProduct(id);
    if (ArrayUtil.isContainValue(product.getStatus().name(), arrStatus)) {
      throw MarketException.throwException(ExceptionTypeEnum.BAD_REQUEST, "Can not update status for this products.");
    }

    product.setStatus(ProductStatusEnum.valueOf(status.toUpperCase()));
    Product newProduct = iProductRepository.save(product);

    return Mapper.convertProductDto(newProduct);
  }

  /**
   * Handle get a product
   *
   * @param id
   * @return
   */
  private Product getProduct(String id) {
    Optional<Product> productOp = iProductRepository.findById(id);
    if (!productOp.isPresent()) {
      throw MarketException.throwException(
          ExceptionTypeEnum.NOT_FOUND,
          "Product id: " + id + " not found."
      );
    }

    return productOp.get();
  }

  /**
   * Handle get category from database
   *
   * @param id
   * @return
   */
  private Category getCategory(String id) {
    Optional<Category> categoryData = iCategoryRepository.findById(id);
    if (!categoryData.isPresent()) {
      throw MarketException.throwException(
          ExceptionTypeEnum.NOT_FOUND,
          "The category id: " + id + " not found."
      );
    }

    return categoryData.get();
  }
}
