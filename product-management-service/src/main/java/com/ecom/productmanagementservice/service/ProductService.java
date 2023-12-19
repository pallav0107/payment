package com.ecom.productmanagementservice.service;


import com.ecom.productmanagementservice.config.KafkaPublisher;
import com.ecom.productmanagementservice.constant.ErrorConstant;
import com.ecom.productmanagementservice.dto.InventoryDTO;
import com.ecom.productmanagementservice.dto.ProductDTO;
import com.ecom.productmanagementservice.exception.BusinessException;
import com.ecom.productmanagementservice.model.Product;
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
  ModelMapper modelMapper;
  @Autowired
  private KafkaPublisher kafkaPublisher;

  public ProductDTO addProduct(ProductDTO productDTO) {
    Product savedProduct;
    savedProduct = productRepository.save(modelMapper.map(productDTO, Product.class));
    log.info("Inside Product Service:: product added successfully");

    // Creating Inventory based on the product
    InventoryDTO inventoryDTO = new InventoryDTO();
    inventoryDTO.setProductId(savedProduct.getProductId());
    inventoryDTO.setVendor("TEST VENDOR");
    inventoryDTO.setSupplierId(new BigDecimal(123));
    inventoryDTO.setReorderQuantity(1000);
    inventoryDTO.setQuantityAvailable(1000);
    inventoryDTO.setLastUpdated(LocalDate.now());
    inventoryDTO.setCostPrice(productDTO.getPrice());
    inventoryDTO.setMinStockThreshold(1);
    inventoryDTO.setMaxStockThreshold(100);
    kafkaPublisher.sendMessage(inventoryDTO);
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
