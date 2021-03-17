package com.agility.marketservice.controller;

import com.agility.marketservice.controller.request.ProductRequest;
import com.agility.marketservice.dto.ProductDto;
import com.agility.marketservice.mock.CategoryMock;
import com.agility.marketservice.mock.ProductMock;
import com.agility.marketservice.model.Category;
import com.agility.marketservice.model.Product;
import com.agility.marketservice.repository.ICategoryRepository;
import com.agility.marketservice.repository.IProductRepository;
import com.agility.marketservice.util.ProductStatusEnum;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import java.net.http.HttpClient;
import java.util.List;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
public class ProductIntegrationTest {
  @LocalServerPort
  private int port;

  @Autowired
  private TestRestTemplate restTemplate;

  @Autowired
  private IProductRepository iProductRepository;
  @Autowired
  private ICategoryRepository iCategoryRepository;

  @BeforeEach
  void beforeEachTestCase() {
    iProductRepository.deleteAll();
    iCategoryRepository.deleteAll();
  }

  @AfterEach
  void afterEachTestCase() {
    iProductRepository.deleteAll();
    iCategoryRepository.deleteAll();
  }

  @Test
  void apiCreateProductSuccess() {
    String path = "http://localhost:" + port + "/api/products";
    Category category = CategoryMock.getCategory();
    category.setId("category_1234");
    iCategoryRepository.save(category);
    ProductRequest productRequest = new ProductRequest()
        .setCategoryId("category_1234")
        .setName("Test product")
        .setPrice(1000)
        .setShippingServices(List.of("123", "456"));
    HttpEntity<ProductRequest> request = new HttpEntity<>(productRequest);
    ResponseEntity<ProductDto> response = this.restTemplate
        .withBasicAuth("customer@asnet.com.vn", "customer@123")
        .postForEntity(path, request, ProductDto.class);

    Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    ProductDto productDto = response.getBody();
    Assertions.assertNotNull(productDto.getId());
    Assertions.assertNotNull(productDto.getName());
  }

  @Test
  void apiCreateProductFailed_Unauthorized() {
    String path = "http://localhost:" + port + "/api/products";
    Category category = CategoryMock.getCategory();
    category.setId("category_1234");
    iCategoryRepository.save(category);
    ProductRequest productRequest = new ProductRequest()
        .setCategoryId("category_1234")
        .setName("Test product")
        .setPrice(1000)
        .setShippingServices(List.of("123", "456"));
    HttpEntity<ProductRequest> request = new HttpEntity<>(productRequest);
    ResponseEntity<String> response = this.restTemplate
        .exchange(path, HttpMethod.POST, request, String.class);

    Assertions.assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    String body = response.getBody();
    Assertions.assertNotNull(body);
  }

  @Test
  void apiCreateProductFailed_BadRequest() {
    String path = "http://localhost:" + port + "/api/products";
    Category category = CategoryMock.getCategory();
    category.setId("category_1234");
    iCategoryRepository.save(category);
    ProductRequest productRequest = new ProductRequest()
        .setCategoryId("category_1234")
        .setName("T")
        .setPrice(1000)
        .setShippingServices(List.of("123", "456"));
    HttpEntity<ProductRequest> request = new HttpEntity<>(productRequest);
    ResponseEntity<String> response = this.restTemplate
        .withBasicAuth("customer@asnet.com.vn", "customer@123")
        .exchange(path, HttpMethod.POST, request, String.class);

    Assertions.assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    String body = response.getBody();
    Assertions.assertNotNull(body);
  }

  @Test
  void apiCreateProductFailed_NotFound_CategoryId() {
    String path = "http://localhost:" + port + "/api/products";
    Category category = CategoryMock.getCategory();
    category.setId("category_9999");
    iCategoryRepository.save(category);
    ProductRequest productRequest = new ProductRequest()
        .setCategoryId("category_1234")
        .setName("Test")
        .setPrice(1000)
        .setShippingServices(List.of("123", "456"));
    HttpEntity<ProductRequest> request = new HttpEntity<>(productRequest);
    ResponseEntity<String> response = this.restTemplate
        .withBasicAuth("customer@asnet.com.vn", "customer@123")
        .exchange(path, HttpMethod.POST, request, String.class);

    Assertions.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    String body = response.getBody();
    Assertions.assertNotNull(body);
  }

  @Test
  void apiGetProductsSuccess() throws JsonProcessingException {
    String path = "http://localhost:" + port + "/api/products";
    Category category = iCategoryRepository.save(CategoryMock.getCategory());
    Product product = ProductMock.getProduct();
    product.setCategory(category);
    product.setStatus(ProductStatusEnum.APPROVED);
    iProductRepository.save(product);
    ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(path, String.class);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(responseEntity.getBody());
    JsonNode items = root.path("items");
    JsonNode totalItems = root.path("totalItems");
    Assertions.assertEquals("1", totalItems.toString());
    Assertions.assertNotNull(items.asText());
  }

  @Test
  void apiGetProductsSuccess_Pagination() throws JsonProcessingException {
    String path = "http://localhost:" + port + "/api/products?size=5&page=7";
    Category category = iCategoryRepository.save(CategoryMock.getCategory());
    List<Product> products = ProductMock.getListProduct(50, category);
    iProductRepository.saveAll(products);
    ResponseEntity<String> responseEntity = this.restTemplate.getForEntity(path, String.class);

    Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    ObjectMapper mapper = new ObjectMapper();
    JsonNode root = mapper.readTree(responseEntity.getBody());
    JsonNode items = root.path("items");
    JsonNode totalItems = root.path("totalItems");
    JsonNode totalPages = root.path("totalPages");
    JsonNode currentPage = root.path("currentPage");
    Assertions.assertEquals("50", totalItems.toString());
    Assertions.assertEquals("10", totalPages.toString());
    Assertions.assertEquals("7", currentPage.toString());
    Assertions.assertNotNull(items.asText());
  }
}
