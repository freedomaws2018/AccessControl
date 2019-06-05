package com.example.demo.Controller.FormEntity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.LineModel.RichMenu.LineRichMenuAction;
import com.example.demo.LineModel.RichMenu.LineRichMenuBounds;
import com.example.demo.LineModel.RichMenu.LineRichMenuSize;

import lombok.Data;

@Data
public class FormRichMenu {

  private String oldRichMenuId;

  @NotBlank
  private String name;

  private Long templateId;

//  private Long locationId;

  private MultipartFile image;

  private LineRichMenuSize size;

  private List<LineRichMenuBounds> bounds = new ArrayList<>();

  private List<LineRichMenuAction> actions = new ArrayList<>();

}
