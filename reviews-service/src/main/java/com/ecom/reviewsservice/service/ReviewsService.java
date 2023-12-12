package com.ecom.reviewsservice.service;


import com.ecom.reviewsservice.constant.ErrorConstant;
import com.ecom.reviewsservice.dto.ReviewsDTO;
import com.ecom.reviewsservice.exception.BusinessException;
import com.ecom.reviewsservice.model.Reviews;
import com.ecom.reviewsservice.repository.ReviewsRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReviewsService {
  private static final Logger log = LoggerFactory.getLogger(ReviewsService.class);

  @Autowired
  private ReviewsRepository reviewsRepository;

  @Autowired
  private ModelMapper modelMapper;
  public ReviewsDTO addReviews(ReviewsDTO reviewsDTO) {
    Reviews reviews = reviewsRepository.save(modelMapper.map(reviewsDTO, Reviews.class));
    log.info("Inside Reviews Service:: reviews added successfully ");
    return modelMapper.map(reviews, ReviewsDTO.class);
  }

  public ReviewsDTO getReviewById(long reviewId) {
    Reviews reviews = reviewsRepository.findById(reviewId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.REVIEWS_NOT_FOUND));
    return modelMapper.map(reviews, ReviewsDTO.class);
  }

  public void deleteReviews(long reviewId) {
    Reviews reviews = reviewsRepository.findById(reviewId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.REVIEWS_NOT_FOUND));
    log.info("Inside Reviews Service:: Reviews deleted successfully with product {}", reviewId);
  }

  public List<ReviewsDTO> getReviews(PageRequest pageRequest) {
    log.info("Inside Reviews Service:: get reviews");
    return reviewsRepository.findAll(pageRequest).stream()
        .map(review -> modelMapper.map(review, ReviewsDTO.class)).toList();
  }
}
