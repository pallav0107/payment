package com.ecom.shoppingcartservice.service;

import com.ecom.ordermanagementservice.exception.BusinessException;
import com.ecom.shoppingcartservice.constant.ErrorConstant;
import com.ecom.shoppingcartservice.dto.ShoppingCartDTO;
import com.ecom.shoppingcartservice.model.ShoppingCart;
import com.ecom.shoppingcartservice.repository.ShoppingCartRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShoppingCartService {
  private static final Logger log = LoggerFactory.getLogger(ShoppingCartService.class);

  @Autowired
  private ShoppingCartRepository shoppingCartRepository;

  @Autowired
  private ModelMapper modelMapper;

  public ShoppingCartDTO addShoppingCart(ShoppingCartDTO shoppingCartDTO) {
    ShoppingCart savedShoppingCart =
        shoppingCartRepository.save(modelMapper.map(shoppingCartDTO, ShoppingCart.class));
    log.info("Inside ShoppingCart Service:: shoppingCart added successfully ");
    return modelMapper.map(savedShoppingCart, ShoppingCartDTO.class);
  }

  public void deleteShoppingCart(long cartId) {
    ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.SHOPPING_CART_NOT_FOUND));
    log.info("Inside Product Service:: shoppingCart deleted successfully with product {}", cartId);
  }

  public List<ShoppingCartDTO> getAllShoppingCarts(PageRequest pageRequest) {
    log.info("Inside ShoppingCart Service:: get shoppingCart");
    return shoppingCartRepository.findAll(pageRequest).stream()
        .map(shoppingCart -> modelMapper.map(shoppingCart, ShoppingCartDTO.class)).toList();
  }

  public ShoppingCartDTO getShoppingCartById(long cartId) {
    ShoppingCart shoppingCart = shoppingCartRepository.findById(cartId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.SHOPPING_CART_NOT_FOUND));
    return modelMapper.map(shoppingCart, ShoppingCartDTO.class);
  }
}
