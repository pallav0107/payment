package com.ecom.returnsservice.controller;

import com.ecom.returnsservice.dto.ReturnsDTO;
import com.ecom.returnsservice.service.ReturnsService;
import lombok.AllArgsConstructor;
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
@RequestMapping("/returns")
@AllArgsConstructor
public class ReturnsController {


  private static final Logger log = LoggerFactory.getLogger(ReturnsController.class);
  @Autowired
  private ReturnsService returnsService;

  @GetMapping(value = "/{returnId}")
  public ResponseEntity<ReturnsDTO> getReturnsById(@PathVariable long returnId) {
    return new ResponseEntity<>(returnsService.getReturnsById(returnId), HttpStatus.OK);
  }

  @PostMapping
  public ResponseEntity<ReturnsDTO> addReturns(
      @RequestBody @NonNull final ReturnsDTO returnsDTO) {
    log.info("Inside Returns controller:: add new Returns");
    return new ResponseEntity<>(returnsService.addReturns(returnsDTO), HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{returnsId}")
  public ResponseEntity<String> deleteReturns(@PathVariable long returnsId) {
    log.info("Inside Returns controller:: delete Returns");
    returnsService.deleteReturns(returnsId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<ReturnsDTO>> getAllReturnss(@RequestParam int page,
      @RequestParam int size) {
    log.info("Inside Returns controller:: get all Returnss");
    return new ResponseEntity<>(returnsService.getReturns(PageRequest.of(page, size)),
        HttpStatus.OK);
  }

  }
