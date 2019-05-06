package com.example.demo.LineModel.RichMenu;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class RichMenu implements Serializable{

	private String richMenuId;

	private String name;

	private String chatBarText;

	private Boolean selected;

	private RichMenuSize size;

	private List<RichMenuArea> areas;

}
