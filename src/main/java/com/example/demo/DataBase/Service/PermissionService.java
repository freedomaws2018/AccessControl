package com.example.demo.DataBase.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

  public void deleteAllDetailByPId(Long permissionId) {
    permissionDetailRepository.deleteAllDetailByPId(permissionId);
    permissionDetailRepository.flush();
  }

  public List<PermissionDetail> saveAllPermissionDetail(List<PermissionDetail> details) {
    details = permissionDetailRepository.saveAll(details);
    permissionDetailRepository.flush();
    return details;
  }

  public List<Permission> getAllPermission() {
    return this.permissionRepository.findAll();
  }

  public Page<Permission> getAllPermission(Pageable pageable) {
    return this.permissionRepository.findAll(pageable);
  }

  public Permission getPermissionById(Long id) {
    return permissionRepository.findById(id).orElse(null);
  }

  public void delete(Long permissionId) {
    permissionRepository.deleteById(permissionId);
  }

  public List<Permission> getPermissionByPermissionIdAndPermissionDetailType(List<String> permissionIdAndPermissionDetailTyle) {
    return permissionRepository.findByPermissionIdAndTypeIn(permissionIdAndPermissionDetailTyle);
  }

}
