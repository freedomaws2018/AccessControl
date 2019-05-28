package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.IdClass.MappingEmployeePermissondetailPositionId;
import com.example.demo.DataBase.Entity.Mapping.MappingEmployeePermissondetailPosition;

@Repository
public interface MappingEmployeePermissondetailPositionRepository
    extends JpaRepository<MappingEmployeePermissondetailPosition, MappingEmployeePermissondetailPositionId> {

  List<MappingEmployeePermissondetailPosition> findByEmployeeIdAndPositionIdAndIsUseTrue(Long employeeId, Long positionId);

  @Modifying
  @Transactional
  @Query(value = "UPDATE mapping_employee_permissiondetail_position SET is_use = false WHERE employee_id = :employeeId ;", nativeQuery = true)
  void updateAllIsUseFalseWithEmployeeId(@Param("employeeId") Long employeeId);

}
