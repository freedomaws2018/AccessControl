package com.example.demo.DataBase.Entity.Mapping;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.example.demo.DataBase.Entity.IdClass.MappingEmployeeLocationId;

import lombok.Data;

@Data
@Entity
@Table(name = "mapping_employee_location")
@IdClass(MappingEmployeeLocationId.class)
/**
 * 對應 Employee 與 Location 的關係表
 */
public class MappingEmployeeLocation {

	@Id
	@Column(name="employee_id")
	private Long employeeId;

	@Id
	@Column(name = "location_id")
	private Long locationId;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @Column(name = "is_use")
  private Boolean isUse;

}
