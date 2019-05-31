package com.example.demo.DataBase.Entity.Mapping;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.example.demo.DataBase.Entity.IdClass.MappingEmployeePermissondetailPositionId;

import lombok.Data;

@Data
@Entity
@Table(name = "mapping_employee_permissiondetail_position")
@IdClass(MappingEmployeePermissondetailPositionId.class)
/**
 * 對應 Employee 的 PermissionDetail 與 Position 的關係
 */
public class MappingEmployeePermissondetailPosition {

  @Id
  @Column(name = "employee_id")
  private Long employeeId;

  @Id
  @Column(name = "permission_id")
  private Long permissionId;

  @Id
  @Column(name = "permission_detail_type")
  private String permissionDetailType;

  @Id
  @Column(name = "position_id")
  private Long positionId;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @Column(name = "is_use")
  private Boolean isUse;

}
