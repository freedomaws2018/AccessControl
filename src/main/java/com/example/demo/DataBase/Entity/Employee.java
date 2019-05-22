package com.example.demo.DataBase.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.DataBase.Entity.Base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tbl_employee")
public class Employee extends BaseEntity {

	@Id
	@Column(name = "id", nullable = false, insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	/** 帳號 **/
	@Column(name = "account", nullable = false, updatable = false)
	private String account;

	/** 密碼  BASE64([FDCe&9WY@EzVp^D99m][account][password]) **/
	@Column(name = "password", nullable = false)
	private String password;

	/** 職位 ID **/
	@Column(name = "positionId")
	private Long positionId;

}
