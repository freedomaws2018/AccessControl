package com.example.demo.DataBase.Entity.IdClass;

import java.io.Serializable;

import javax.persistence.Embeddable;

import lombok.Data;

@Data
@Embeddable
public class MappingPositionPermissionPermissiondetailId implements Serializable {

  private static final long serialVersionUID = 4739572585186317564L;

  private Long positionId;

  private Long permissionId;

  private String permissionDetailType;

}
