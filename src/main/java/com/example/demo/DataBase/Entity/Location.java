package com.example.demo.DataBase.Entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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

	@OneToMany(mappedBy = "locationId", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private List<Wf8266> wf8266s;

//	@OneToMany(mappedBy = "userId", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//	private List<LineUser> lineUsers;
}
