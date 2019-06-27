package com.example.demo.DataBase.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

  @Transactional
  @Query(value = "SELECT p.* FROM tbl_permission p , tbl_permission_detail pd WHERE p.key = pd.permission_key AND (p.key || ':' || pd.type) IN :permissionIdAndPermissionDetailType ;", nativeQuery = true)
  List<Permission> findByPermissionIdAndTypeIn(List<String> permissionIdAndPermissionDetailType);

  Optional<Permission> findByMenuName(String menuName);

}
