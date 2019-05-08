package com.example.demo.DataBase.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tbl_location")
public class Location extends BaseEntity {

	/** 地點對應ID **/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/** 地點名稱 **/
	@Column(name = "name")
	private String name;

	/** 地點地址 **/
	@Column(name = "address")
	private String address;

	/** 地點電話 **/
	@Column(name = "phone")
	private String phone;

	/** 負責人 - 對應 Employee **/
	@Column(name = "keeper")
	private String keeper;

}
