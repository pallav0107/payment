package com.ecom.useractivityservice.service;

import com.ecom.ordermanagementservice.exception.BusinessException;

import com.ecom.useractivityservice.constant.ErrorConstant;
import com.ecom.useractivityservice.dto.UserActivityDTO;
import com.ecom.useractivityservice.model.UserActivity;
import com.ecom.useractivityservice.repository.UserActivityRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserActivityService {

  private static final Logger log = LoggerFactory.getLogger(UserActivityService.class);

  @Autowired
  private UserActivityRepository userActivityRepository;

  @Autowired
  private ModelMapper modelMapper;

  public UserActivityDTO addUserActivity(UserActivityDTO userActivityDTO) {
    UserActivity savedUserActivity =
        userActivityRepository.save(modelMapper.map(userActivityDTO, UserActivity.class));
    log.info("Inside UserActivity Service:: userActivity added successfully ");
    return modelMapper.map(savedUserActivity, UserActivityDTO.class);
  }

  public void deleteUserActivity(long activityId) {
    UserActivity userActivity = userActivityRepository.findById(activityId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.USER_ACTIVITY_NOT_FOUND));
    log.info("Inside UserActivity Service:: userActivity deleted successfully with {}", activityId);
  }

  public List<UserActivityDTO> getActivities(PageRequest pageRequest) {
    log.info("Inside UserActivity Service:: get userActivity");
    return userActivityRepository.findAll(pageRequest).stream()
        .map(userActivity -> modelMapper.map(userActivity, UserActivityDTO.class)).toList();
  }

  public UserActivityDTO getUserActivityById(long activityId) {
    UserActivity userActivity = userActivityRepository.findById(activityId)
        .orElseThrow(() -> new BusinessException(ErrorConstant.USER_ACTIVITY_NOT_FOUND));
    return modelMapper.map(userActivity, UserActivityDTO.class);
  }
}
