package com.example.demo.LineModel.RichMenu;

import java.io.Serializable;

import lombok.Data;

@Data
public class LineRichMenuArea implements Serializable{

	private static final long serialVersionUID = 1419137717186476934L;

	private LineRichMenuBounds bounds;

	private LineRichMenuActoin action;

}
