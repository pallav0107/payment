package com.ecom.useractivityservice.dto;


import com.ecom.useractivityservice.constant.ActionType;
import com.ecom.useractivityservice.constant.DeviceType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserActivityDTO {

  private long activityId;

  private long userId;

  private LocalDateTime timestamp;

  private long productId;

  private ActionType actionType;

  private DeviceType deviceType;

  private String browser;

  private String location;

  private long duration;

  private long referrer;

}
