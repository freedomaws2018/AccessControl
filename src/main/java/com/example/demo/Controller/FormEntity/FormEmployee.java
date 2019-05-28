package com.example.demo.Controller.FormEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.DataBase.Entity.Employee;
import com.example.demo.DataBase.Entity.Mapping.MappingEmployeePermissondetailPosition;

import lombok.Data;

@Data
public class FormEmployee {

  private Long id;

  private String account;

  private String password;

  private String firstName;

  private String lastName;

  private Boolean isUse;

  private Long positionId;

  private Integer positionStatus;

  private List<String> permissionDetailType = new ArrayList<>();

  public Employee getEmployee() {
    Employee employee = new Employee();
    employee.setId(id);
    employee.setAccount(account);
    if (id == null) {
      employee.setPassword(account, true);
    } else {
      employee.setPassword(password, false);
    }
    employee.setFirstName(firstName);
    employee.setLastName(lastName);
    employee.setIsUse(isUse);
    employee.setPositionId(positionId);
    employee.setPositionStatus(positionStatus);
    return employee;
  }

  public List<MappingEmployeePermissondetailPosition> getMappingEmployeePermissondetailPosition() {
    return permissionDetailType.stream().map(mepp -> {
      MappingEmployeePermissondetailPosition meppt = new MappingEmployeePermissondetailPosition();
      meppt.setEmployeeId(this.id);
      meppt.setPositionId(this.positionId);
      meppt.setPermissionDetailType(mepp);
      meppt.setIsUse(true);
      return meppt;
    }).collect(Collectors.toList());
  }

}
