package com.example.demo.DataBase.Entity;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_member")
public class Member {

  @Id
  @Column(name = "id", nullable = false, insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "line_user_id")
  private String lineUserId;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "line_user_id", referencedColumnName = "user_id", insertable = false, updatable = false, nullable = true)
  private LineUser lineUser;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @LastModifiedDate
  @Column(name = "modify_date", nullable = false)
  private LocalDateTime modifyDate = LocalDateTime.now();

  /** 姓 **/
  @Column(name = "first_name", nullable = false)
  private String firstName;

  /** 名 **/
  @Column(name = "last_name", nullable = false)
  private String lastName;

  /** 生日 **/
  @Column(name = "birthday")
  private LocalDate birthday;

  /** 身分證字號 **/
  @Column(name = "person_id", unique = true)
  private String personId;

  /** 是否啟用 **/
  @Column(name = "is_use", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
  private Boolean isUse;

  @Column(name = "is_admin", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
  private Boolean isAdmin;

  @Column(name = "beg_dt", nullable = false)
  private LocalDateTime begDt;

  @Column(name = "end_dt", nullable = false)
  private LocalDateTime endDt;

  public String getFullName() {
    return (StringUtils.isNotBlank(firstName) ? firstName : "") + (StringUtils.isNotBlank(lastName) ? lastName : "");
  }

  public Boolean getIsEffective() {
    return isUse //
        && Duration.between(LocalDateTime.now(), begDt).toMillis() > 0 //
        && Duration.between(LocalDateTime.now(), endDt).toMillis() < 0;
  }

}
