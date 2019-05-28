package com.example.demo.Controller.FormEntity;

import java.util.List;

import com.example.demo.DataBase.Entity.Employee;

import lombok.Data;

@Data
public class FormEmployee {

  private Long id;

  private String account;

  private String firstName;

  private String lastName;

  private Boolean isUse;

  private Long positionId;

  private Integer positionStatus;

  private List<String> mepps;

  public Employee getEmployee() {
    Employee employee = new Employee();

    return employee;
  }

}
