package com.example.demo.DataBase.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.PermissionDetail;
import com.example.demo.DataBase.Entity.IdClass.PermissionDetailId;

@Repository
public interface PermissionDetailRepository extends JpaRepository<PermissionDetail, PermissionDetailId> {

  @Modifying(clearAutomatically = true)
  @Transactional
  @Query(value = "DELETE FROM tbl_permission_detail WHERE permission_key = :PKey ; ", nativeQuery = true)
  void deleteAllDetailByPKey(@Param("PKey") String permissionKey);

}
