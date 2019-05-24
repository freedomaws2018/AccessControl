package com.example.demo.DataBase.Entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

//@EqualsAndHashCode(callSuper = false)
//public class Employee extends BaseEntity {
@Data
@Entity
@Table(name = "tbl_employee")
public class Employee {

  @Id
  @Column(name = "id", nullable = false, insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @LastModifiedDate
  @Column(name = "modify_date", nullable = false)
  private LocalDateTime modifyDate = LocalDateTime.now();

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  /** 帳號 **/
  @Column(name = "account", nullable = false, updatable = false)
  private String account;

  /** 密碼 BASE64([FDCe&9WY@EzVp^D99m][account][password]) **/
  @Column(name = "password", nullable = false)
  private String password;

  /** 職位 ID **/
  @Column(name = "position_id")
  private Long positionId;

  /**
   * 職位狀態<BR>
   * ( 0:在職 , 1:離職 , 2:留職停薪 )
   **/
  @Column(name = "position_status")
  public Integer positionStatus;

  public String getName() {
    return String.format("%s %s", firstName, lastName);
  }

  public String getPositionStatusTw() {
    switch (positionStatus) {
    case 0:
      return "在職";
    case 1:
      return "離職";
    case 2:
      return "留停";
    default:
      return "未知";
    }
  }
}
