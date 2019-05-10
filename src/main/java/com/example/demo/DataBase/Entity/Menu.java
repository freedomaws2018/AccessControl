package com.example.demo.DataBase.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.demo.DataBase.Entity.Base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tbl_menu")
public class Menu extends BaseEntity {

	@Id
	@Column(name = "key")
	private String key;

	/** 主類別 <沒有主類別就是頂層> **/
	@Column(name = "main_class")
	private String mainClass;

	/** 父類別 <該類別的上一層key值> **/
	@Column(name = "parent_class")
	private String parentClass;

	@Column(name = "level")
	private Integer level;

	@Column(name = "name")
	private String name;

	@Column(name = "url")
	private String url;

	@Column(name = "sort")
	private Integer sort;

}
