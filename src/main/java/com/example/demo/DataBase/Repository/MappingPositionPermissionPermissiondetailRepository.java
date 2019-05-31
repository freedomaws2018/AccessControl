package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.IdClass.MappingPositionPermissionPermissiondetailId;
import com.example.demo.DataBase.Entity.Mapping.MappingPositionPermissionPermissiondetail;

public interface MappingPositionPermissionPermissiondetailRepository
    extends JpaRepository<MappingPositionPermissionPermissiondetail, MappingPositionPermissionPermissiondetailId> {

  @Modifying
  @Transactional
  @Query(value = "UPDATE mapping_position_permission_permissiondetail SET is_use = false WHERE position_id = :positionId ;", nativeQuery = true)
  void updateAllIsUseFalseWithPositionId(@Param("positionId") Long positionId);

  List<MappingPositionPermissionPermissiondetail> findByPositionIdAndIsUseTrue(Long positionId);

}
