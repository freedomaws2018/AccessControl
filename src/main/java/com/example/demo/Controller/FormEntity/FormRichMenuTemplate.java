package com.example.demo.Controller.FormEntity;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DataBase.Entity.RichMenuTemplate;

import lombok.Data;

@Data
public class FormRichMenuTemplate {

	String name;

	Integer size;

	MultipartFile jsonFile;

	MultipartFile imageFile;

	public RichMenuTemplate toRichMenuTemplate() {
		RichMenuTemplate template = null;
		try {
			template = new RichMenuTemplate();
			template.setName(getName());
			template.setSize(getSize());
			template.setImage(imageFile.getBytes());
			template.setTemplate(new String(jsonFile.getBytes(), "UTF-8"));
		} catch (Exception ex) {

		}
		return template;

	}

}
