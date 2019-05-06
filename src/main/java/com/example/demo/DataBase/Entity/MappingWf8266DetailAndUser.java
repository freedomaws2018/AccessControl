package com.example.demo.DataBase.Entity;

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
@Table(name = "mapping_wf8266_detail_user")
@IdClass(MappingWf8266DetailAndUserId.class)
public class MappingWf8266DetailAndUser extends BaseEntity {

	@Id
	@Column(name = "wf8266_trigger_text")
	private String triggerText;

	@Id
	@Column(name = "user_id")
	private String userId;

	@Column(name = "is_use", nullable = false)
	private Boolean isUse;

//	@Column(name = "beg_dt")
//	private LocalDateTime begDt;
//
//	@Column(name = "end_dt")
//	private LocalDateTime endDt;

}
