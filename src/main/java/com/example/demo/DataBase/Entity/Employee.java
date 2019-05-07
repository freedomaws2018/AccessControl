package com.example.demo.DataBase.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tbl_employee")
public class Employee extends BaseEntity {

	@Id
	@Column(name = "id", nullable = false, insertable = false, updatable = false)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "account", nullable = false, updatable = false)
	private String account;

	@Column(name = "password", nullable = false)
	private String password;

}
