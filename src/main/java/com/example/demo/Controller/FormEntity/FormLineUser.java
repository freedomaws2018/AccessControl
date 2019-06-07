package com.example.demo.Controller.FormEntity;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.DataBase.Entity.LineUser;

import lombok.Data;

@Data
public class FormLineUser {

  @NotNull
  private String userId;

  @NotNull
  private String userName;

  @NotNull
  private Boolean isUse;

  @NotNull
  private Boolean isAdmin;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime begDt;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime endDt;

  private Long locationId;

  private String locationDetailName;

  private String richMenuId;

  public LineUser toLineUser() {
    LineUser lineUser = new LineUser();
    lineUser.setUserId(this.userId);
    lineUser.setUserName(this.userName);
    lineUser.setIsUse(this.isUse);
    lineUser.setIsAdmin(this.isAdmin);
    lineUser.setBegDt(this.begDt);
    lineUser.setEndDt(this.endDt);
    lineUser.setLocationId(this.locationId);
    lineUser.setLocationDetailName(StringUtils.isNotBlank(this.locationDetailName) ? this.locationDetailName : null);
    lineUser.setRichMenuId(StringUtils.isNotBlank(this.richMenuId) ? this.richMenuId : null);
    return lineUser;
  }

}
