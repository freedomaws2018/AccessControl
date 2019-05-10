package com.example.demo.DataBase.Entity.Mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "mapping_employee_location")
@IdClass(MappingEmployeeAndLocationId.class)
public class MappingEmployeeAndLocation {

	@Id
	@Column(name="employee_id")
	private Long employeeId;

	@Id
	@Column(name = "location_id")
	private Long locationId;

	@Column(name = "is_use")
	private Boolean isUse;

}
