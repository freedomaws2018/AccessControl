package com.example.demo.DataBase.Entity.IdClass;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class MappingEmployeeAndLocationId implements Serializable {

	private static final long serialVersionUID = 244595643448889556L;

	private Long employeeId;

	private Long locationId;

}
