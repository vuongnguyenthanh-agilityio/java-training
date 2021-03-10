package com.agility.marketservice.service;

import com.agility.marketservice.controller.request.ProductRequest;
import com.agility.marketservice.controller.response.PageResponse;
import com.agility.marketservice.dto.FilterConditionDto;
import com.agility.marketservice.dto.ProductDto;
import com.agility.marketservice.exception.ExceptionType;
import com.agility.marketservice.exception.MarketException;
import com.agility.marketservice.model.Category;
import com.agility.marketservice.model.Product;
import com.agility.marketservice.model.ProductStatus;
import com.agility.marketservice.repository.ICategoryRepository;
import com.agility.marketservice.repository.IProductRepository;
import com.agility.marketservice.util.GenericFilterCriteriaBuilder;
import com.agility.marketservice.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
  @Autowired
  private IProductRepository iProductRepository;
  @Autowired
  private ICategoryRepository iCategoryRepository;
  @Autowired
  private IFilterBuilderService iFilterBuilderService;

  /**
   * Handle get products. Can search filter search, sort
   *
   * @param page
   * @param size
   * @param search
   * @param filterAnd
   * @param filterOr
   * @param orders
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
    Category category = getCategory(productRequest.getCategoryId());
    Product product = new Product()
        .setName(productRequest.getName())
        .setStatus(ProductStatus.PENDING)
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
        .setStatus(ProductStatus.PENDING)
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
   * Handle get a product
   *
   * @param id
   * @return
   */
  private Product getProduct(String id) {
    Optional<Product> productOp = iProductRepository.findById(id);
    if (!productOp.isPresent()) {
      throw MarketException.throwException(
          ExceptionType.NOT_FOUND,
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
          ExceptionType.NOT_FOUND,
          "The category id: " + id + " not found."
      );
    }

    return categoryData.get();
  }
}
