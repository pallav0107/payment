package com.ecom.promotionsservice.service;

import com.ecom.promotionsservice.constant.ErrorConstant;
import com.ecom.promotionsservice.dto.PromotionDTO;
import com.ecom.promotionsservice.exception.BusinessException;
import com.ecom.promotionsservice.model.Promotion;
import com.ecom.promotionsservice.repository.PromotionRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PromotionService {


  private static final Logger log = LoggerFactory.getLogger(PromotionService.class);

  @Autowired
  private PromotionRepository promotionRepository;

  @Autowired
  private ModelMapper modelMapper;

  public PromotionDTO addPromotion(PromotionDTO promotionDTO) {
    Promotion savedPromotion =
        promotionRepository.save(modelMapper.map(promotionDTO, Promotion.class));
    log.info("Inside Promotion Service:: promotion added successfully ");
    return modelMapper.map(savedPromotion, PromotionDTO.class);
  }

  public void deletePromotion(long discountId) {
    Promotion promotion = promotionRepository.findById(discountId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.PROMOTION_NOT_FOUND));
    log.info("Inside Product Service:: promotion deleted successfully with product {}", discountId);
  }

  public List<PromotionDTO> getPromotions(PageRequest pageRequest) {
    log.info("Inside Promotion Service:: get promotion");
    return promotionRepository.findAll(pageRequest).stream()
        .map(promotion -> modelMapper.map(promotion, PromotionDTO.class)).toList();
  }

  public PromotionDTO getPromotionById(long discountId) {
    Promotion promotion = promotionRepository.findById(discountId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.PROMOTION_NOT_FOUND));
    return modelMapper.map(promotion, PromotionDTO.class);
  }
}
