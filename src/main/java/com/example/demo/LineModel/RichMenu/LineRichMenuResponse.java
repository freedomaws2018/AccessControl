package com.example.demo.LineModel.RichMenu;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class LineRichMenuResponse implements Serializable{

	private List<LineRichMenu> richmenus;

}
