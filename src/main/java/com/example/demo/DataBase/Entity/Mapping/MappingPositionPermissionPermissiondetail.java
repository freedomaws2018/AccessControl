package com.example.demo.DataBase.Entity.Mapping;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.example.demo.DataBase.Entity.IdClass.MappingPositionPermissionPermissiondetailId;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "mapping_position_permission_permissiondetail")
@IdClass(MappingPositionPermissionPermissiondetailId.class)
public class MappingPositionPermissionPermissiondetail {

  @Id
  @Column(name = "position_id")
  private Long positionId;

  @Id
  @Column(name = "permission_id")
  private Long permissionId;

  @Id
  @Column(name = "permission_detail_type")
  private String permissionDetailType;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @Column(name = "is_use")
  private Boolean isUse;

}
