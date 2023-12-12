package com.ecom.promotionsservice.controller;

import com.ecom.promotionsservice.dto.PromotionDTO;
import com.ecom.promotionsservice.service.PromotionService;
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
@RequestMapping("/promotion")
public class PromotionController {

  private static final Logger log = LoggerFactory.getLogger(PromotionController.class);

  @Autowired
  private PromotionService promotionService;

  @GetMapping(value = "/{discountId}")
  public ResponseEntity<PromotionDTO> getPromotionById(@PathVariable long discountId) {
    return new ResponseEntity<>(promotionService.getPromotionById(discountId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<PromotionDTO> addPromotion(
      @RequestBody @NonNull final PromotionDTO promotionDTO) {
    log.info("Inside Promotion controller:: add new Promotion");
    return new ResponseEntity<>(promotionService.addPromotion(promotionDTO), HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{discountId}")
  public ResponseEntity<String> deletePromotion(@PathVariable long discountId) {
    log.info("Inside Promotion controller:: delete Promotion");
    promotionService.deletePromotion(discountId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<PromotionDTO>> getAllPromotions(@RequestParam int page,
      @RequestParam int size) {
    return new ResponseEntity<>(promotionService.getPromotions(PageRequest.of(page, size)),
        HttpStatus.OK);
  }
}
