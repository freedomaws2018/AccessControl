package com.example.demo.DataBase.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_permission_detail")
public class PermissionDetail {

	@Id
	@Column(name = "type", nullable = false, updatable = false)
	private String type;

	@Column(name = "permission_id")
	private Long permissionId;

	@Column(name = "name")
	private String name;

	@Column(name = "is_super_admin")
	private Boolean isSuperAdmin;

	@Column(name = "remarks")
	private String remarks;

}
