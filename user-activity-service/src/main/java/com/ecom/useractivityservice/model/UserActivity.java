package com.ecom.useractivityservice.model;


import com.ecom.useractivityservice.constant.ActionType;
import com.ecom.useractivityservice.constant.DeviceType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Order entity map to product table
 */
@Getter
@Setter
@Entity
@Table(name = "user_activity")
public class UserActivity {
  
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "ACTIVITY_ID")
  private long activityId;

  //@ManyToOne
  //@JoinColumn(name = "USER_ID", referencedColumnName = "USER_ID")
  @Column(name = "USER_ID")
  private long userId;

  @Column(name = "TIMESTAMP")
  private LocalDateTime timestamp;

  //@ManyToMany
  //@JoinColumn(name = "PRODUCT_ID", referencedColumnName = "PRODUCT_ID")
  @Column(name = "PRODUCT_ID")
  private long productId;

  @Column(name = "ACTION_TYPE")
  private ActionType actionType;

  @Column(name = "DEVICE_TYPE")
  private DeviceType deviceType;

  @Column(name = "BROWSER")
  private String browser;

  @Column(name = "LOCATION")
  private String location;

  @Column(name = "DURATION")
  private long duration;

  @Column(name = "REFERRER")
  private long referrer;

}
