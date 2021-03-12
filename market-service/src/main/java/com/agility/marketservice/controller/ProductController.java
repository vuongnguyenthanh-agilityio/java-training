package com.agility.marketservice.controller;

import com.agility.marketservice.controller.request.ProductRequest;
import com.agility.marketservice.controller.response.PageResponse;
import com.agility.marketservice.dto.ProductDto;
import com.agility.marketservice.exception.MarketException;
import com.agility.marketservice.service.FilterBuilderService;
import com.agility.marketservice.service.IProductService;
import com.agility.marketservice.util.ExceptionTypeEnum;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * ** Created by Vuong Nguyen **
 *
 * The class define API endpoint that client can access
 */

@RequiredArgsConstructor // Lombok provided constructor
@RestController
@RequestMapping("/api/")
public class ProductController {
  private final Logger LOG = LoggerFactory.getLogger(getClass());
  private final IProductService iProductService;

  /**
   * POST: /api/products
   * The API to create a product
   *
   * @param product
   * @return
   */
  @PostMapping("products")
  public ResponseEntity<ProductDto> createProduct(@RequestBody @Valid ProductRequest product) {
    LOG.info("Product input: ", product.toString());
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
  public ResponseEntity<ProductDto> updateProduct(@PathVariable @NotNull String id,
                                                   @RequestBody @Valid ProductRequest product) {
    LOG.info("Product id: ",id);
    LOG.info("Product update input: ",product.toString());
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
  public ResponseEntity<ProductDto> getProduct(@PathVariable String id) {
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
  public ResponseEntity<ProductDto> deleteProduct(@PathVariable String id) {
    ProductDto newProduct = iProductService.deleteProduct(id);

    return new ResponseEntity<>(newProduct, HttpStatus.OK);
  }

  /**
   * GET: /api/products/
   * Get products. We can filter, search, sort dynamic field
   *
   * @param page
   * @param size
   * @param search
   * @param filterOr
   * @param filterAnd
   * @param orders
   * @return
   */
  @GetMapping(path = "products")
  public ResponseEntity<PageResponse<ProductDto>> getProducts( @Valid
      @RequestParam(value = "page", defaultValue = "" + FilterBuilderService.DEFAULT_PAGE) int page,
      @RequestParam(value = "size", defaultValue = "" + FilterBuilderService.DEFAULT_PAGE_SIZE) int size,
      @RequestParam(value = "search", required = false) String search,
      @RequestParam(value = "filterOr", required = false) String filterOr,
      @RequestParam(value = "filterAnd", required = false) String filterAnd,
      @RequestParam(value = "orders", required = false) String orders
  ) {
    if ((filterAnd != null && filterAnd.contains("status"))
        || (filterOr != null && filterOr.contains("status"))
    ) {
      throw MarketException.throwException(ExceptionTypeEnum.BAD_REQUEST, "Filter status does not support.");
    }
    // Only get product is approved by admin
    String newFilterAnd = "status|eq|APPROVED&";
    if (filterAnd != null && !filterAnd.isEmpty()) {
      newFilterAnd += filterAnd;
    }
    PageResponse<ProductDto> dtoProducts = iProductService.getProducts(
        page,
        size,
        search,
        newFilterAnd,
        filterOr,
        orders
    );

    return new ResponseEntity<>(dtoProducts, HttpStatus.OK);
  }

  /**
   * GET: /api/603f37bfbca3fc59fedbab62/products
   * Get products by shipping services
   *
   * @param shippingServiceId
   * @param page
   * @param size
   * @param status
   * @param orders
   * @return
   */
  @GetMapping(path = "shipping-services/{shippingServiceId}/products")
  public ResponseEntity<PageResponse<ProductDto>> getProductsByShipping(
      @PathVariable String shippingServiceId,
      @RequestParam(value = "page", defaultValue = "" + FilterBuilderService.DEFAULT_PAGE) int page,
      @RequestParam(value = "size", defaultValue = "" + FilterBuilderService.DEFAULT_PAGE_SIZE) int size,
      @RequestParam(value = "status", required = false) String status,
      @RequestParam(value = "orders", required = false) String orders
  ) {
    // Add filter by shipping service id and product status
    String filter = "shippingServices|eq|" + shippingServiceId;
    if (status != null && !status.isEmpty()) {
      filter += "&status|eq|" + status.toUpperCase();
    }
    PageResponse<ProductDto> dtoProducts = iProductService.getProducts(page, size, filter, orders);

    return new ResponseEntity<>(dtoProducts, HttpStatus.OK);
  }

  /**
   * GET: /api/603f37bfbca3fc59fedbab62/products
   * Get products by user id
   *
   * @param userId
   * @param page
   * @param size
   * @param status
   * @param orders
   * @return
   */
  @GetMapping(path = "users/{userId}/products")
  public ResponseEntity<PageResponse<ProductDto>> getProductsByUserId(
      @PathVariable String userId,
      @RequestParam(value = "page", defaultValue = "" + FilterBuilderService.DEFAULT_PAGE) int page,
      @RequestParam(value = "size", defaultValue = "" + FilterBuilderService.DEFAULT_PAGE_SIZE) int size,
      @RequestParam(value = "status", required = false) String status,
      @RequestParam(value = "orders", required = false) String orders
  ) {
    // Add filter by shipping service id and product status
    String filter = "createdBy.id|eq|" + userId;
    if (status != null && !status.isEmpty()) {
      filter += "&status|eq|" + status.toUpperCase();
    }
    PageResponse<ProductDto> dtoProducts = iProductService.getProducts(page, size, filter, orders);

    return new ResponseEntity<>(dtoProducts, HttpStatus.OK);
  }

  @PreAuthorize("hasAuthority('ADMIN')")
  @PutMapping(path = "products/{id}/{status}")
  public ResponseEntity<ProductDto> updateStatus(
      @PathVariable String id,
      @PathVariable String status
  ) {
    ProductDto productDto = iProductService.updateStatusProduct(id, status);

    return new ResponseEntity<>(productDto, HttpStatus.CREATED);
  }
}
