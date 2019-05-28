package com.example.demo.DataBase.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    return permissionRepository.save(permission);
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

  public List<Permission> getPermissionByPermissionDetailType(List<String> premissionDetailType){
    List<PermissionDetail> detail = permissionDetailRepository.findAllById(premissionDetailType);
    List<Long> pid = detail.stream().map(PermissionDetail::getPermissionId).collect(Collectors.toList());
    return permissionRepository.findAllById(pid);
  }
}
