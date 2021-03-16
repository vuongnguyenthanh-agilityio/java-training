package com.agility.marketservice.controller;

import com.agility.marketservice.controller.request.ProductRequest;
import com.agility.marketservice.controller.response.PageResponse;
import com.agility.marketservice.dto.ProductDto;
import com.agility.marketservice.exception.CustomResponseException;
import com.agility.marketservice.mock.CategoryMock;
import com.agility.marketservice.mock.PageableMock;
import com.agility.marketservice.mock.ProductMock;
import com.agility.marketservice.util.ExceptionTypeEnum;
import com.agility.marketservice.exception.MarketException;
import com.agility.marketservice.model.Category;
import com.agility.marketservice.util.ProductStatusEnum;
import com.agility.marketservice.service.IProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class ProductControllerTest {
  public static final String PATH = "/api/products";
  public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(
      MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(),
      Charset.forName("utf8")
  );
  private MockMvc mockMvc;
  private JacksonTester<ProductDto> jacksonProductDto;
  private JacksonTester<PageResponse> pageResponseJacksonTester;

  @Mock
  private IProductService iProductService;
  @InjectMocks
  private ProductController productController;
  private Faker faker = new Faker();
  private Category category = CategoryMock.getCategory();
  private ProductDto productDto = ProductMock.getProduct();
  private ProductRequest productRequest = new ProductRequest()
      .setName(faker.name().name())
      .setCategoryId("category_1")
      .setPrice(faker.number().numberBetween(0, 100000))
      .setShippingServices(List.of(
          faker.number().randomNumber(10, true) + "",
          faker.number().randomNumber(10, true) + ""));

  @BeforeEach
  void setUp() {
    JacksonTester.initFields(this, new ObjectMapper());
    mockMvc = MockMvcBuilders.standaloneSetup(productController)
        .setControllerAdvice(new CustomResponseException())
        .build();
  }

  /**
   * Testing successfully for API GET: /api/products/{id}
   *
   * @throws Exception
   */
  @Test
  void apiGetProductResponseSuccess() throws Exception {
    productDto.setId("product_1");
    category.setId("category_1");

    given(iProductService.getProductById("product_1")).willReturn(productDto);
    MockHttpServletResponse response = mockMvc
        .perform(MockMvcRequestBuilders.get(PATH + "/product_1"))
        .andReturn()
        .getResponse();

    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    Assertions.assertEquals(jacksonProductDto.write(productDto).getJson(), response.getContentAsString());
  }

  /**
   * Testing failed for API GET: /api/products/{id}
   *
   * @throws Exception
   */
  @Test
  void apiGetProductResponseNotFound() throws Exception {
    String errorMessage = "Not found";
    given(iProductService.getProductById("product_2"))
        .willThrow(MarketException.throwException(ExceptionTypeEnum.NOT_FOUND, errorMessage));

    mockMvc
        .perform(MockMvcRequestBuilders.get(PATH + "/product_2"))
        .andExpect(status().isNotFound())
        .andExpect((result -> Assertions.assertTrue(result.getResolvedException() instanceof MarketException.NotFoundException)))
        .andExpect((result -> Assertions.assertEquals(errorMessage, result.getResolvedException().getMessage())));
  }

  @Test
  void apiCreateProductSuccess() throws Exception{
    given(iProductService.createProduct(productRequest)).willReturn(productDto);
    String body = new ObjectMapper().writeValueAsString(productRequest);
    MockHttpServletResponse response = mockMvc
        .perform(MockMvcRequestBuilders.post(PATH).contentType(APPLICATION_JSON_UTF8).content(body))
        .andReturn()
        .getResponse();

    Assertions.assertEquals(HttpStatus.CREATED.value(), response.getStatus());
    Assertions.assertEquals(jacksonProductDto.write(productDto).getJson(), response.getContentAsString());
  }

  /**
   * Testing failed for API POST: /api/products
   *
   * @throws Exception
   */
  @Test
  void apiGetProductResponseBadRequest() throws Exception {
    productRequest.setName("");
    productRequest.setPrice(-100);
    String body = new ObjectMapper().writeValueAsString(productRequest);

    mockMvc
        .perform(MockMvcRequestBuilders.post(PATH).contentType(APPLICATION_JSON_UTF8).content(body))
        .andExpect(status().isBadRequest())
        .andExpect((result -> Assertions.assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException)));
  }

  @Test
  void apiGetProductsSuccess() throws Exception {
    List<ProductDto> productDtoList = ProductMock.getListProducts(10);
    PageResponse pageResponse = new PageResponse();
    Page<ProductDto> page = new PageImpl<>(productDtoList);
    pageResponse.setPageResponse(page, productDtoList);
    String newFilterAnd = "status|eq|APPROVED&";
    given(iProductService.getProducts(1, 10, null, newFilterAnd, null, null))
        .willReturn(pageResponse);

    MockHttpServletResponse response = mockMvc
        .perform(MockMvcRequestBuilders.get(PATH).contentType(APPLICATION_JSON_UTF8))
        .andReturn()
        .getResponse();

    String content = response.getContentAsString();
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    Assertions.assertEquals(pageResponseJacksonTester.write(pageResponse).getJson(), content);
  }

  @Test
  void apiGetProductsPaginationSuccess() throws Exception {
    List<ProductDto> productDtoList = ProductMock.getListProducts(5);
    PageResponse pageResponse = new PageResponse();
    Pageable pageable = new PageableMock()
        .setItemsPerPage(10)
        .setPageSize(10);
    Page<ProductDto> page = new PageImpl<>(productDtoList, pageable, 5);
    pageResponse.setPageResponse(page, productDtoList);
    String newFilterAnd = "status|eq|APPROVED&";
    given(iProductService.getProducts(1, 5, null, newFilterAnd, null, null))
        .willReturn(pageResponse);

    MockHttpServletResponse response = mockMvc
        .perform(MockMvcRequestBuilders.get(PATH)
            .contentType(APPLICATION_JSON_UTF8)
            .param("page", "1")
            .param("size", "5")
        )
        .andReturn()
        .getResponse();

    String content = response.getContentAsString();
    Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
    Assertions.assertEquals(pageResponseJacksonTester.write(pageResponse).getJson(), content);
  }
}
