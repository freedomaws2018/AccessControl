package com.example.demo.DataBase.Entity.Mapping;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.example.demo.DataBase.Entity.Base.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "mapping_wf8266_lineuser")
@IdClass(MappingWf8266AndLineUserId.class)
public class MappingWf8266AndLineUser extends BaseEntity {

	@Id
	@Column(name = "wf8266_id")
	private String wf8266Id;

	@Id
	@Column(name = "line_user_id")
	private String lineUserId;

	@Column(name = "is_use", nullable = false)
	private Boolean isUse;

}
