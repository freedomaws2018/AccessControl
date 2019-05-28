package com.example.demo.LineModel.RichMenu;

import java.io.Serializable;

import lombok.Data;

@Data
public class LineRichMenuAction implements Serializable{

	private String type;

	private String data;

	private String text;
}
