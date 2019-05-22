package com.example.demo.DataBase.Entity.Mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.example.demo.DataBase.Entity.Base.BaseMapping;
import com.example.demo.DataBase.Entity.IdClass.MappingEmployeeAndLocationId;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "mapping_employee_location")
@IdClass(MappingEmployeeAndLocationId.class)
/**
 * 對應 Employee 與 Location 的關係表
 */
public class MappingEmployeeAndLocation  extends BaseMapping {

	@Id
	@Column(name="employee_id")
	private Long employeeId;

	@Id
	@Column(name = "location_id")
	private Long locationId;

}
