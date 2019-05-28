package com.example.demo.Controller.FormEntity;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.NotBlank;

import org.springframework.web.multipart.MultipartFile;

import com.example.demo.LineModel.RichMenu.LineRichMenuAction;
import com.example.demo.LineModel.RichMenu.LineRichMenuBounds;

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

  private List<LineRichMenuBounds> bounds = new ArrayList<>();

  private List<LineRichMenuAction> actions = new ArrayList<>();

  private Long locationId;

}
