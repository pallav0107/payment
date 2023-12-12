package com.ecom.productmanagementservice.controller;


import com.ecom.productmanagementservice.dto.ProductDTO;
import com.ecom.productmanagementservice.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

  private static final Logger log = LoggerFactory.getLogger(ProductController.class);

  @Autowired
  private ProductService productService;

  @GetMapping(value = "/{productId}")
  public ResponseEntity<ProductDTO> getProductById(@PathVariable long productId) {
    return new ResponseEntity<>(productService.getProductById(productId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<ProductDTO> addProduct(@RequestBody @NonNull final ProductDTO productDTO) {
    log.info("Inside Product controller:: add new Product");
    return new ResponseEntity<>(productService.addProduct(productDTO), HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{productId}")
  public ResponseEntity<String> deleteProduct(@PathVariable long productId) {
    log.info("Inside Product controller:: delete Product");
    productService.deleteProduct(productId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<ProductDTO>> getAllOrders(@RequestParam int page,
      @RequestParam int size) {
    return new ResponseEntity<>(productService.getProducts(PageRequest.of(page, size)),
        HttpStatus.OK);
  }
}
