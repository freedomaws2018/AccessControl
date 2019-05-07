package com.example.demo.DataBase.Entity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tbl_employee_permission")
@IdClass(EmployeePermissionId.class)
public class EmployeePermission extends BaseEntity {

	@Id
	@Column(name = "id", nullable = false, insertable = false, updatable = false)
	private Long id;

	@Id
	@Column(name = "emp_id",nullable = false, updatable = false)
	private Long emp_id;

	@Column(name = "name")
	private String name;

	@Column(name = "is_use")
	private Boolean isUse;

}
