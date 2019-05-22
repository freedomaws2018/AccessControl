package com.example.demo.DataBase.Entity.Mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.example.demo.DataBase.Entity.Base.BaseMapping;
import com.example.demo.DataBase.Entity.IdClass.MappingEmployeeAndPermissonDetailAndPositionId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "mapping_employee_permissiondetail_position")
@IdClass(MappingEmployeeAndPermissonDetailAndPositionId.class)
/**
 * 對應 Employee 的 PermissionDetail 與 Position 的關係
 */
public class MappingEmployeeAndPermissonDetailAndPosition extends BaseMapping {

  @Id
  @Column(name = "employee_id")
  private Long employeeId;

  @Id
  @Column(name = "permission_detail_type")
  private String permissionDetailType;

  @Id
  @Column(name = "position_id")
  private Long positionId;

}
