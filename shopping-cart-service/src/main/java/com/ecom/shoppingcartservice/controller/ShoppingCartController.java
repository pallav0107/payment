package com.ecom.shoppingcartservice.controller;

import com.ecom.shoppingcartservice.dto.ShoppingCartDTO;
import com.ecom.shoppingcartservice.service.ShoppingCartService;
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
@RequestMapping("/cart")
public class ShoppingCartController {
  private static final Logger log = LoggerFactory.getLogger(ShoppingCartController.class);

  @Autowired
  private ShoppingCartService shoppingCartService;

  @GetMapping(value = "/{cartId}")
  public ResponseEntity<ShoppingCartDTO> getShoppingCartById(@PathVariable long cartId) {
    return new ResponseEntity<>(shoppingCartService.getShoppingCartById(cartId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<ShoppingCartDTO> addShoppingCart(
      @RequestBody @NonNull final ShoppingCartDTO inventoryDTO) {
    log.info("Inside ShoppingCart controller:: add new ShoppingCart");
    return new ResponseEntity<>(shoppingCartService.addShoppingCart(inventoryDTO),
        HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{cartId}")
  public ResponseEntity<String> deleteShoppingCart(@PathVariable long cartId) {
    log.info("Inside ShoppingCart controller:: delete ShoppingCart");
    shoppingCartService.deleteShoppingCart(cartId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<ShoppingCartDTO>> getAllShoppingCarts(@RequestParam int page,
      @RequestParam int size) {
    return new ResponseEntity<>(shoppingCartService.getAllShoppingCarts(PageRequest.of(page, size)),
        HttpStatus.OK);
  }
}
