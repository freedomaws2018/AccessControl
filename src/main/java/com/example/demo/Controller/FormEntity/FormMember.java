package com.example.demo.Controller.FormEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.DataBase.Entity.Member;

import lombok.Data;

@Data
public class FormMember {

  private Long id;

  private String firstName;

  private String lastName;

  private Boolean isUse;

  private Boolean isAdmin;

  private String personId;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDate birthday;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime begDt;

  @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
  private LocalDateTime endDt;

  private String lineUserId;

  private String richMenuId;

  public Member toMember(Member member) {
    if (member == null) {
      member = new Member();
      member.setId(id);
      member.setFirstName(firstName);
      member.setLastName(lastName);
      member.setIsUse(isUse);
      member.setIsAdmin(isAdmin);
      member.setPersonId(personId);
      member.setBirthday(birthday);
      member.setBegDt(begDt);
      member.setEndDt(endDt);
      member.setRichMenuId(StringUtils.isNotBlank(richMenuId) ? richMenuId : null);
      member.setLineUserId(StringUtils.isNotBlank(lineUserId) ? lineUserId : null);
    } else {
      member.setFirstName(firstName);
      member.setLastName(lastName);
      member.setIsUse(isUse);
      member.setIsAdmin(isAdmin);
      member.setPersonId(personId);
      member.setBirthday(birthday);
      member.setBegDt(begDt);
      member.setEndDt(endDt);
      member.setRichMenuId(StringUtils.isNotBlank(richMenuId) ? richMenuId : null);
      member.setLineUserId(StringUtils.isNotBlank(lineUserId) ? lineUserId : null);
    }
    return member;
  }

}
