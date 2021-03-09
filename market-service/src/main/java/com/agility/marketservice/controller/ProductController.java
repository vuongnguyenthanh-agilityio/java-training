package com.agility.marketservice.controller;

import com.agility.marketservice.controller.request.ProductRequest;
import com.agility.marketservice.controller.response.PageResponse;
import com.agility.marketservice.dto.ProductDto;
import com.agility.marketservice.model.Product;
import com.agility.marketservice.service.FilterBuilderService;
import com.agility.marketservice.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Created by Vuong Nguyen
 */
@RestController
@RequestMapping("/api/")
public class ProductController {
  private final Logger LOG = LoggerFactory.getLogger(getClass());
  @Autowired
  private IProductService iProductService;

  /**
   * POST: /api/products
   * The API to create a product
   *
   * @param product
   * @return
   */
  @PostMapping("products")
  private ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductRequest product) {
    LOG.info(product.toString());
    ProductDto newProduct = iProductService.createProduct(product);

    return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
  }

  /**
   * PUT: /api/products/603f37bfbca3fc59fedbab60
   * The API to update product
   *
   * @param id
   * @param product
   * @return
   */
  @PutMapping("products/{id}")
  private ResponseEntity<ProductDto> updateProduct(@PathVariable @NotNull String id,
                                                   @RequestBody @Valid ProductRequest product) {
    LOG.info(id);
    LOG.info(product.toString());
    ProductDto newProduct = iProductService.updateProduct(id, product);

    return new ResponseEntity<>(newProduct, HttpStatus.CREATED);
  }

  /**
   * GET: /api/products/603f37bfbca3fc59fedbab60
   * The API get a product
   *
   * @param id
   * @return
   */
  @GetMapping("products/{id}")
  private ResponseEntity<ProductDto> getProduct(@PathVariable String id) {
    ProductDto newProduct = iProductService.getProductById(id);

    return new ResponseEntity<>(newProduct, HttpStatus.OK);
  }

  /**
   * DELETE: /api/products/603f37bfbca3fc59fedbab60
   * The API delete a product
   *
   * @param id
   * @return
   */
  @DeleteMapping("products/{id}")
  private ResponseEntity<ProductDto> deleteProduct(@PathVariable String id) {
    ProductDto newProduct = iProductService.deleteProduct(id);

    return new ResponseEntity<>(newProduct, HttpStatus.OK);
  }

  /**
   * TODO: Will add filter sort and pagination
   * @return
   */
  @GetMapping(path = "products")
  public ResponseEntity<PageResponse<ProductDto>> getProducts(
      @RequestParam(value = "page", defaultValue = "" + FilterBuilderService.DEFAULT_PAGE) int page,
      @RequestParam(value = "size", defaultValue = "" + FilterBuilderService.DEFAULT_PAGE_SIZE) int size,
      @RequestParam(value = "search", required = false) String search,
      @RequestParam(value = "filterOr", required = false) String filterOr,
      @RequestParam(value = "filterAnd", required = false) String filterAnd,
      @RequestParam(value = "orders", required = false) String orders
  ) {
    LOG.info(orders);
    PageResponse<ProductDto> dtoProducts = iProductService.getProducts(page, size, search, filterAnd, filterOr, orders);

    return new ResponseEntity<>(dtoProducts, HttpStatus.OK);
  }
}
