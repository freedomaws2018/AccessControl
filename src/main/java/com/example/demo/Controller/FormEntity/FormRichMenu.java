package com.example.demo.Controller.FormEntity;

import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class FormRichMenu {

	@NotBlank
	private String name;

//	@NotBlank
//	private String chatBarText;

	private String oldRichMenuId;

	private Long templateId;

	private MultipartFile image;

	private List<FormRichMenuAction> actions;

}
