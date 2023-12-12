package com.ecom.useractivityservice.controller;

import com.ecom.useractivityservice.dto.UserActivityDTO;
import com.ecom.useractivityservice.service.UserActivityService;
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
@RequestMapping("/activity")
public class UserActivityController {


  private static final Logger log = LoggerFactory.getLogger(UserActivityController.class);

  @Autowired
  private UserActivityService userActivityService;

  @PostMapping
  public ResponseEntity<UserActivityDTO> addUserActivity(
      @RequestBody @NonNull final UserActivityDTO inventoryDTO) {
    log.info("Inside UserActivity controller:: add new UserActivity");
    return new ResponseEntity<UserActivityDTO>(userActivityService.addUserActivity(inventoryDTO),
        HttpStatus.CREATED);
  }

  @DeleteMapping(value = "/{activityId}")
  public ResponseEntity<String> deleteUserActivity(@PathVariable long activityId) {
    log.info("Inside UserActivity controller:: delete UserActivity");
    userActivityService.deleteUserActivity(activityId);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  @GetMapping
  public ResponseEntity<List<UserActivityDTO>> getActivities(@RequestParam int page,
      @RequestParam int size) {
    return new ResponseEntity<>(userActivityService.getActivities(PageRequest.of(page, size)),
        HttpStatus.OK);
  }

  @GetMapping(value = "/{activityId}")
  public ResponseEntity<UserActivityDTO> getUserActivityById(@PathVariable long activityId) {
    return new ResponseEntity<>(userActivityService.getUserActivityById(activityId), HttpStatus.OK);
  }


}
