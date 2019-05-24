package com.example.demo.DataBase.Entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Id;

import org.springframework.data.annotation.CreatedDate;

//@Data
//@EqualsAndHashCode(callSuper = false)
//@Entity
//@Table(name = "tbl_menu")
public class MenuTemporary {

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  private Long employeeId;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

}
