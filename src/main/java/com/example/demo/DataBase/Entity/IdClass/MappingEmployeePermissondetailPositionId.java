package com.example.demo.DataBase.Entity.IdClass;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class MappingEmployeePermissondetailPositionId implements Serializable {

  private static final long serialVersionUID = 5870006910011866336L;

  private Long employeeId;

  private Long permissionId;

  private String permissionDetailType;

  private Long positionId;

}
