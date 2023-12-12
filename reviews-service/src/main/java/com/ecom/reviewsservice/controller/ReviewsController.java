package com.ecom.reviewsservice.controller;

import com.ecom.reviewsservice.dto.ReviewsDTO;
import com.ecom.reviewsservice.service.ReviewsService;
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
@RequestMapping("/review")
public class ReviewsController {

  private static final Logger log = LoggerFactory.getLogger(ReviewsController.class);
  @Autowired
  private ReviewsService reviewsService;

  @PostMapping
  public ResponseEntity<ReviewsDTO> addReviews(@RequestBody @NonNull final ReviewsDTO reviewsDTO) {
    log.info("Inside Reviews controller:: add new Reviews");
    return new ResponseEntity<>(reviewsService.addReviews(reviewsDTO), HttpStatus.CREATED);
  }

  @GetMapping(value = "/{reviewId}")
  public ResponseEntity<ReviewsDTO> getReviewsById(@PathVariable long reviewId) {
    return new ResponseEntity<>(reviewsService.getReviewById(reviewId), HttpStatus.OK);
  }

  @DeleteMapping(value = "/{reviewId}")
  public ResponseEntity<String> deleteReviews(@PathVariable long reviewId) {
    log.info("Inside Reviews controller:: delete Reviews");
    reviewsService.deleteReviews(reviewId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<ReviewsDTO>> getAllReviews(@RequestParam int page,
      @RequestParam int size) {
    return new ResponseEntity<>(reviewsService.getReviews(PageRequest.of(page, size)),
        HttpStatus.OK);
  }

}
