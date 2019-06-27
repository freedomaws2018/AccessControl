package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.IdClass.MappingEmployeePermissonPositionId;
import com.example.demo.DataBase.Entity.Mapping.MappingEmployeePermissonPosition;

@Repository
public interface MappingEmployeePermissonPositionRepository
    extends JpaRepository<MappingEmployeePermissonPosition, MappingEmployeePermissonPositionId> {

  @Query(value = "SELECT (permission_key || ':' || permission_detail_type)\\:\\:character varying(255) FROM mapping_employee_permission_position WHERE is_use = true AND employee_id = :employeeId AND position_id = :positionId ; ", nativeQuery = true)
  List<String> getEmployeeEPPs(@Param("employeeId") Long employeeId, @Param("positionId") Long positionId);

  List<MappingEmployeePermissonPosition> findByEmployeeIdAndPositionIdAndIsUseTrue(Long employeeId, Long positionId);

  List<MappingEmployeePermissonPosition> findByEmployeeIdAndPositionIdAndPermissionKeyAndIsUseTrue(Long employeeId,
      Long positionId, String permissionKey);

  @Modifying(flushAutomatically = true)
  @Transactional
  @Query(value = "UPDATE mapping_employee_permission_position SET is_use = false WHERE employee_id = :employeeId ;", nativeQuery = true)
  void updateAllIsUseFalseWithEmployeeId(@Param("employeeId") Long employeeId);

}
