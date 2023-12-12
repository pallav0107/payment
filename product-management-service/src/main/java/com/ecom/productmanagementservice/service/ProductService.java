package com.ecom.productmanagementservice.service;


import com.ecom.productmanagementservice.constant.ErrorConstant;
import com.ecom.productmanagementservice.dto.InventoryDTO;
import com.ecom.productmanagementservice.dto.ProductDTO;
import com.ecom.productmanagementservice.exception.BusinessException;
import com.ecom.productmanagementservice.model.Product;
import com.ecom.productmanagementservice.proxy.InventoryServiceProxy;
import com.ecom.productmanagementservice.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {

  private static final Logger log = LoggerFactory.getLogger(ProductService.class);

  @Autowired
  ProductRepository productRepository;
  @Autowired
  InventoryServiceProxy inventoryServiceProxy;
  @Autowired
  ModelMapper modelMapper;

  public ProductDTO addProduct(ProductDTO productDTO) {
    Product savedProduct;
    savedProduct = productRepository.save(modelMapper.map(productDTO, Product.class));
    log.info("Inside Product Service:: product added successfully");

    // after product add successfully
    InventoryDTO inventoryDTO = new InventoryDTO();
    inventoryDTO.setProductId(productDTO.getProductId());
    inventoryDTO.setCostPrice(productDTO.getPrice());
    inventoryDTO.setLastUpdated(LocalDate.now());
    inventoryDTO.setQuantityAvailable(100);
    inventoryDTO.setSupplierId(BigDecimal.valueOf(1));
    inventoryDTO.setVendor("TEST");
    inventoryServiceProxy.addInventory(inventoryDTO);
    return modelMapper.map(savedProduct, ProductDTO.class);
  }

  public void deleteProduct(long productId) {
    productRepository.findById(productId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.PRODUCT_NOT_FOUND));
    log.info("Inside Product Service:: product deleted successfully with product {}", productId);
  }

  public List<ProductDTO> getProducts(PageRequest pageRequest) {
    log.info("Inside Product Service:: get product");
    return productRepository.findAll(pageRequest).stream()
        .map(product -> modelMapper.map(product, ProductDTO.class)).toList();
  }

  public ProductDTO getProductById(long productId) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.PRODUCT_NOT_FOUND));
    return modelMapper.map(product, ProductDTO.class);
  }
}
