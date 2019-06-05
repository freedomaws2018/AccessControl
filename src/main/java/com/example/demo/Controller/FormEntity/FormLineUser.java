package com.example.demo.Controller.FormEntity;

import java.time.LocalDateTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

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

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime begDt;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime endDt;

//	private List<MappingWf8266AndLineUser> mappings;

//	private String richMenuId;

	public LineUser toLineUser() {
		LineUser lineUser = new LineUser();
		lineUser.setUserId(this.userId);
		lineUser.setUserName(this.userName);
		lineUser.setIsUse(this.isUse);
		lineUser.setBegDt(this.begDt);
		lineUser.setEndDt(this.endDt);
//		lineUser.setRichMenuId(this.richMenuId);
		return lineUser;
	}

//	public List<MappingWf8266AndLineUser> toMappingWf8266DetailAndUser() {
//		return this.mappings.stream().filter(entity -> entity.getIsUse() != null).collect(Collectors.toList());
//	}

}
