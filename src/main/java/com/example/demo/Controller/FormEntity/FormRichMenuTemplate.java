package com.example.demo.Controller.FormEntity;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DataBase.Entity.RichMenuTemplate;
import com.example.demo.LineModel.RichMenu.LineRichMenu;
import com.google.gson.Gson;

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
			template.setName(this.getName());
			template.setSize(this.getSize());
			template.setImage(this.imageFile.getBytes());
			String jsonTemplate = new String(this.jsonFile.getBytes(), "UTF-8");
			template.setTemplate(new Gson().fromJson(jsonTemplate, LineRichMenu.class));
		} catch (Exception ex) {

		}
		return template;

	}

}
