package com.example.demo.Controller.FormEntity;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.DataBase.Entity.RichMenuTemplate;
import com.example.demo.LineModel.RichMenu.LineRichMenu;
import com.google.gson.Gson;

import lombok.Data;

@Data
public class FormRichMenuTemplate {

  Long id;

  String name;

  MultipartFile jsonFile;

  MultipartFile imageFile;

  public RichMenuTemplate toRichMenuTemplate() throws IOException {
    RichMenuTemplate template = new RichMenuTemplate();
    template.setId(this.id);
    template.setName(this.getName());
    template.setImage(this.imageFile.getBytes());
    String jsonTemplate = new String(this.jsonFile.getBytes(), "UTF-8");
    template.setTemplate(new Gson().fromJson(jsonTemplate, LineRichMenu.class));
    return template;
  }

}
