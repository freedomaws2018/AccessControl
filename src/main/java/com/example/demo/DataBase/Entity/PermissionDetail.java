package com.example.demo.DataBase.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_permission_detail")
public class PermissionDetail {

	@Id
	@Column(name = "type", nullable = false, updatable = false)
	private String type;

	@Column(name = "permission_id")
	private Long permissionId;

  @OneToOne(fetch = FetchType.LAZY )
  @JoinColumn(name = "permission_id", referencedColumnName = "id" , insertable = false , updatable = false , nullable = true)
  private Permission permission;

	@Column(name = "name")
	private String name;

  @Column(name = "is_super_admin" ,columnDefinition = "bool DEFAULT FALSE")
  private Boolean isSuperAdmin;

}
