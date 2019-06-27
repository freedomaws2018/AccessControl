package com.example.demo.DataBase.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.Permission;
import com.example.demo.DataBase.Entity.PermissionDetail;
import com.example.demo.DataBase.Repository.PermissionDetailRepository;
import com.example.demo.DataBase.Repository.PermissionRepository;

@Service
public class PermissionService {

  @Autowired
  private PermissionRepository permissionRepository;

  @Autowired
  private PermissionDetailRepository permissionDetailRepository;

  public Permission save(Permission permission) {
    return permissionRepository.saveAndFlush(permission);
  }

  public void deleteAllDetailByPKey(String permissionKey) {
    permissionDetailRepository.deleteAllDetailByPKey(permissionKey);
    permissionDetailRepository.flush();
  }

  public List<PermissionDetail> saveAllPermissionDetail(List<PermissionDetail> details) {
    details = permissionDetailRepository.saveAll(details);
    permissionDetailRepository.flush();
    return details;
  }

  public Permission getByMenuName(String menuName) {
    return permissionRepository.findByMenuName(menuName).orElse(null);
  }

  public List<Permission> getAllPermission() {
    return permissionRepository.findAll(Sort.by(Order.asc("menuName")));
  }

  public Page<Permission> getAllPermission(Pageable pageable) {
    return this.permissionRepository.findAll(pageable);
  }

  public Permission getPermissionByKey(String key) {
    return permissionRepository.findById(key).orElse(null);
  }

  public void delete(String permissionKey) {
    permissionRepository.deleteById(permissionKey);
  }

  public List<Permission> getPermissionByPermissionIdAndPermissionDetailType(
      List<String> permissionIdAndPermissionDetailTyle) {
    return permissionRepository.findByPermissionIdAndTypeIn(permissionIdAndPermissionDetailTyle);
  }

}
