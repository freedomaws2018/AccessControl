package com.example.demo.Controller.FormEntity;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class FromLogWf8266 {

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime begDt;

	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
	private LocalDateTime endDt;

	private Long locationId;

	private Long employeeId;

	private String triggerText;

}
