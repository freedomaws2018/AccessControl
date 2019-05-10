package com.example.demo.Controller.FormEntity;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Entity.Mapping.MappingWf8266AndLineUser;

import lombok.Data;

@Data
public class FormLineUser {

	@NotNull
	private String userId;

	@NotNull
	private String userName;

	@NotNull
	private Boolean isUse;

	private String begDt;

	private String endDt;

	private List<MappingWf8266AndLineUser> mappings;

	private String richMenuId;

	public LineUser toLineUser() {
		LineUser lineUser = new LineUser();
		lineUser.setUserId(this.userId);
		lineUser.setUserName(this.userName);
		lineUser.setIsUse(this.isUse);
		try {
			lineUser.setBegDt(LocalDateTime.parse(this.begDt, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
			lineUser.setEndDt(LocalDateTime.parse(this.endDt, DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")));
		} catch (DateTimeParseException dtpex) {
			throw new RuntimeException(dtpex.getMessage());
		}
		return lineUser;
	}

	public List<MappingWf8266AndLineUser> toMappingWf8266DetailAndUser() {
		return this.mappings.stream().filter(entity -> entity.getIsUse() != null).collect(Collectors.toList());
	}

}
