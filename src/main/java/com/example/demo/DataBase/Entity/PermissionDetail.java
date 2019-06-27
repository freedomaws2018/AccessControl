package com.example.demo.DataBase.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.example.demo.DataBase.Entity.IdClass.PermissionDetailId;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_permission_detail")
@IdClass(PermissionDetailId.class)
public class PermissionDetail {

  @Id
  @Column(name = "type", nullable = false, updatable = false)
  private String type;

  @Id
  @Column(name = "permission_key")
  private String permissionKey;

  @JsonManagedReference
  @OneToOne(fetch = FetchType.EAGER)
  @JoinColumn(name = "permission_key", referencedColumnName = "key", insertable = false, updatable = false, nullable = true)
  private Permission permission;

  @Column(name = "name")
  private String name;

  @Column(name = "remarks")
  private String remarks;

  @Column(name = "is_super_admin", columnDefinition = "bool DEFAULT FALSE")
  private Boolean isSuperAdmin;

}
