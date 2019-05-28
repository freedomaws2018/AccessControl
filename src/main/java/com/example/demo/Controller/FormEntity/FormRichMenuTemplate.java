package com.example.demo.Controller.FormEntity;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DataBase.Entity.RichMenuTemplate;
import com.example.demo.LineModel.RichMenu.LineRichMenu;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Data;

@Data
public class FormRichMenuTemplate {

  Long id;

  String name;

  Integer size;

  MultipartFile jsonFile;

  MultipartFile imageFile;

  public RichMenuTemplate toRichMenuTemplate() {
    RichMenuTemplate template = null;
    try {
      template = new RichMenuTemplate();
      template.setId(this.id);
      template.setName(this.getName());
      template.setSize(this.getSize());
      template.setImage(this.imageFile.getBytes());
      String jsonTemplate = new String(this.jsonFile.getBytes(), "UTF-8");
      template.setTemplate(new ObjectMapper().readValue(jsonTemplate, LineRichMenu.class));
    } catch (Exception ex) {

    }
    return template;

  }

}
