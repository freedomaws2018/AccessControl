package com.example.demo.DataBase.Entity;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class EmployeePermissionId implements Serializable {

	private static final long serialVersionUID = 3369945165157202136L;

	private Long id;

	private Long emp_id;

}
