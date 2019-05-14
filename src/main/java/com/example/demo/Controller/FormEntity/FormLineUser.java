package com.example.demo.Controller.FormEntity;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import com.example.demo.DataBase.Entity.LineUser;

import lombok.Data;

@Data
public class FormLineUser {

	@NotNull
	private String userId;

	@NotNull
	private String userName;

	@NotNull
	private Boolean isUse;

	private LocalDateTime begDt;

	private LocalDateTime endDt;

//	private List<MappingWf8266AndLineUser> mappings;

	private String richMenuId;

	public LineUser toLineUser() {
		LineUser lineUser = new LineUser();
		lineUser.setUserId(this.userId);
		lineUser.setUserName(this.userName);
		lineUser.setIsUse(this.isUse);
		lineUser.setBegDt(this.begDt);
		lineUser.setEndDt(this.endDt);
		return lineUser;
	}

//	public List<MappingWf8266AndLineUser> toMappingWf8266DetailAndUser() {
//		return this.mappings.stream().filter(entity -> entity.getIsUse() != null).collect(Collectors.toList());
//	}

}
