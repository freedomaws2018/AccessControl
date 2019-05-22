package com.example.demo.DataBase.Entity;

import javax.persistence.Column;
import javax.persistence.Id;

public class MenuTemporary {

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  private Long employeeId;



}
