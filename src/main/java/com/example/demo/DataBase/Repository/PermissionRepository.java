package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.DataBase.Entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

  @Query(value = "SELECT p.* FROM tbl_permission p , tbl_permission_detail pd WHERE p.id = pd.permission_id AND (p.id || ':' || pd.type) IN :permissionIdAndPermissionDetailType ;", nativeQuery = true)
  List<Permission> findByPermissionIdAndTypeIn(List<String> permissionIdAndPermissionDetailType);

}
