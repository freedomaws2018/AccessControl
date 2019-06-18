package com.example.demo.DataBase.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DataBase.Entity.IdClass.MappingEmployeeLocationId;
import com.example.demo.DataBase.Entity.Mapping.MappingEmployeeMenu;

public interface MappingEmployeeMenuRepository extends JpaRepository<MappingEmployeeMenu, MappingEmployeeLocationId> {

  List<MappingEmployeeMenu> findByEmployeeIdAndIsUseTrue(Long employeeId);

  @Modifying(flushAutomatically = true)
  @Transactional
  @Query(value = "UPDATE mapping_employee_menu SET is_use = false WHERE employee_id = :employeeId ;", nativeQuery = true)
  void updateAllIsUseFalseByEmployeeId(@Param("employeeId") Long employeeId);

}
