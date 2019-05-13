package com.example.demo.LineModel.RichMenu;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class LineRichMenu implements Serializable{

	private static final long serialVersionUID = 905184618007304420L;

	private String richMenuId;

	private String name;

	private String chatBarText;

	private Boolean selected;

	private LineRichMenuSize size;

	private List<LineRichMenuArea> areas;

}
