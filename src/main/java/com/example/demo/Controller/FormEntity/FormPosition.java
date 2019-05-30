package com.example.demo.Controller.FormEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.DataBase.Entity.Position;
import com.example.demo.DataBase.Entity.Mapping.MappingPositionPermissionPermissiondetail;

import lombok.Data;

@Data
public class FormPosition {

  private Long id;

  private String name;

  private Boolean isDefault;

  private List<FormPositionDetail> permission = new ArrayList<>();

  public Position getPosition() {
    Position position = new Position();
    position.setId(id);
    position.setName(name);
    position.setIsDefault(isDefault);
    return position;
  }

  public List<MappingPositionPermissionPermissiondetail> getMappingPPP(Long positionId) {
    return permission.stream().map(p -> {
      MappingPositionPermissionPermissiondetail mapping = new MappingPositionPermissionPermissiondetail();
      mapping.setIsUse(true);
      mapping.setPositionId(positionId);
      mapping.setPermissionId(p.getPermissionId());
      mapping.setPermissionDetailType(p.getPermissionDetailType());
      return mapping;
    }).collect(Collectors.toList());
  }

}

@Data
class FormPositionDetail {

  private Long permissionId;

  private String permissionDetailType;

}
