package com.example.demo.DataBase.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.demo.DataBase.Entity.Permission;
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

}
