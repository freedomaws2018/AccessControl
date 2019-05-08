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
@Table(name = "tbl_permission")
public class Permission extends BaseEntity {


	@Id
	@Column(name = "id", nullable = false, insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "is_use")
	private Boolean isUse;

	@Column(name = "is_default")
	private Boolean isDefault;

	@OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<PermissionDetail> detail;

}
