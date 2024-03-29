package com.example.demo.DataBase.Entity.IdClass;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class MappingEmployeeMenunId implements Serializable {

  private static final long serialVersionUID = 2114886744313680255L;

  private Long employeeId;

  private String menuName;

}
