package com.agility.marketservice.service;

import com.agility.marketservice.controller.request.ProductRequest;
import com.agility.marketservice.dto.ProductDto;
import com.agility.marketservice.exception.MarketException;
import com.agility.marketservice.model.Category;
import com.agility.marketservice.model.Product;
import com.agility.marketservice.model.ProductStatus;
import com.agility.marketservice.repository.ICategoryRepository;
import com.agility.marketservice.repository.IProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductServiceTest {
  @Mock
  private IProductRepository iProductRepository;
  @Mock
  private ICategoryRepository iCategoryRepository;

  @InjectMocks
  private ProductService productService;

  private Category category = new Category()
      .setName("Vehicle")
      .setDescription("Vehicle category");
  private Product product = new Product()
      .setName("Product 1")
      .setCategory(category)
      .setPrice(9999)
      .setStatus(ProductStatus.PENDING)
      .setShippingServices(List.of("123", "456"));
  private ProductRequest productRequest = new ProductRequest()
      .setName("Product 1")
      .setCategoryId("category_1")
      .setPrice(9999)
      .setShippingServices(List.of("123", "456"));

  @BeforeAll
  public static void init(){
    System.out.println("BeforeAll init test data for product service");
  }


  @BeforeEach
  void setUp() {
    category.setId("category_1");
    product.setId("product_1");
    when(iCategoryRepository.findById("category_1")).thenReturn(Optional.of(category));
    when(iProductRepository.insert(any(Product.class))).thenReturn(product);
    when(iProductRepository.save(any(Product.class))).thenReturn(product);
    when(iProductRepository.findById("product_1")).thenReturn(Optional.of(product));
//    when(iProductRepository.delete(product)).
  }

  @Test
  void shouldCreateProductSuccessfully() {
    ProductDto productDto = productService.createProduct(productRequest);

    Assertions.assertNotEquals(null, productDto);
    Assertions.assertEquals(productRequest.getName(), productDto.getName());
    Assertions.assertEquals(productRequest.getPrice(), productDto.getPrice());
    Assertions.assertEquals(productRequest.getCategoryId(), productDto.getCategory().getId());
  }

  @Test
  void shouldCreateFailedWhenNotFoundCategoryId() {
    productRequest.setCategoryId("category_2");
    Assertions.assertThrows(MarketException.NotFoundException.class, () -> {
      productService.createProduct(productRequest);
    });
  }

  @Test
  void shouldUpdateProductSuccessfully() {
    productRequest.setName("Product 2");
    productRequest.setDescription("test update");
    ProductDto productDto = productService.updateProduct("product_1", productRequest);

    Assertions.assertNotEquals(null, productDto);
    Assertions.assertEquals(productRequest.getName(), productDto.getName());
    Assertions.assertEquals(productRequest.getDescription(), productDto.getDescription());
    Assertions.assertEquals(productRequest.getCategoryId(), productDto.getCategory().getId());
  }

  @Test
  void shouldUpdateProductFailedWhenNotFoundId() {
    productRequest.setName("Product 2");
    productRequest.setDescription("test update");
    Assertions.assertThrows(MarketException.NotFoundException.class, () -> {
      productService.updateProduct("product_2", productRequest);
    });
  }

  @Test
  void shouldGetProductSuccessfully() {
    ProductDto productDto = productService.getProductById(product.getId());

    Assertions.assertNotEquals(null, productDto);
    Assertions.assertEquals(productRequest.getName(), productDto.getName());
    Assertions.assertEquals(productRequest.getPrice(), productDto.getPrice());
    Assertions.assertEquals(productRequest.getCategoryId(), productDto.getCategory().getId());
  }

  @Test
  void shouldGetProductFailedWhenNotFoundId() {
    Assertions.assertThrows(MarketException.NotFoundException.class, () -> {
      productService.getProductById("product_2");
    });
  }

  @Test
  void shouldDeleteProductSuccessfully() {
    ProductDto productDto = productService.deleteProduct(product.getId());

    Assertions.assertNotEquals(null, productDto);
    Assertions.assertEquals(product.getId(), productDto.getId());
    Assertions.assertEquals(product.getName(), productDto.getName());
    Assertions.assertEquals(product.getPrice(), productDto.getPrice());
    Assertions.assertEquals(product.getCategory().getId(), productDto.getCategory().getId());
  }

  @Test
  void shouldDeleteProductFailedWhenNotFoundId() {
    Assertions.assertThrows(MarketException.NotFoundException.class, () -> {
      productService.deleteProduct("product_2");
    });
  }

}
