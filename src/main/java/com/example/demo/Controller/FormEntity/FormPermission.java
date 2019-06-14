package com.example.demo.Controller.FormEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.example.demo.DataBase.Entity.Permission;
import com.example.demo.DataBase.Entity.PermissionDetail;

import lombok.Data;

@Data
public class FormPermission {

  private Long id;

  private String name;

  private String menuName;

  private List<FormPermissionDetail> detail = new ArrayList<>();

  public Permission getPermission(Permission permission) {
    if(permission == null) {
      permission = new Permission();
      permission.setName(name);
      permission.setMenuName(StringUtils.isNotBlank(menuName) ? menuName : null);
    }else {
      permission.setName(name);
      permission.setMenuName(StringUtils.isNotBlank(menuName) ? menuName : null);
    }
    return permission;
  }

  public List<PermissionDetail> getPermissionDetail(Permission permission) {
    List<FormPermissionDetail> fpds = detail.stream().filter(d -> Arrays.asList("A", "U").contains(d.getStatus())).collect(Collectors.toList());
    return fpds.stream().map(fpd -> {
      PermissionDetail detail = new PermissionDetail();
      detail.setPermissionId(permission.getId());
      detail.setType(fpd.getType());
      detail.setName(fpd.getName());
      detail.setRemarks(fpd.getRemarks());
      detail.setIsSuperAdmin(fpd.getIsSuperAdmin());
      return detail;
    }).collect(Collectors.toList());
  }

}
