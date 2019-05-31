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

  public Permission getPermission() {
    Permission permission = new Permission();
    permission.setId(this.id);
    permission.setName(this.name);
    permission.setMenuName(StringUtils.isNotBlank(this.menuName) ? this.menuName : null);
    permission.setDetails(this.getPermissionDetail());
    return permission;
  }

  public List<PermissionDetail> getPermissionDetail() {
    List<FormPermissionDetail> fpds = detail.stream().filter(d -> Arrays.asList("A", "U").contains(d.getStatus()))
        .collect(Collectors.toList());
    return fpds.stream().map(fpd -> {
      PermissionDetail detail = new PermissionDetail();
      detail.setPermissionId(this.id);
      detail.setType(fpd.getType());
      detail.setName(fpd.getName());
      detail.setRemarks(fpd.getRemarks());
      detail.setIsSuperAdmin(fpd.getIsSuperAdmin());
      return detail;
    }).collect(Collectors.toList());
  }

}
