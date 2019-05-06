package com.example.demo.DataBase.Entity;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.util.Base64Utils;

import com.example.demo.LineModel.RichMenu.RichMenuArea;
import com.example.demo.LineModel.RichMenu.RichMenuSize;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_rich_menu")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class RichMenu {

	@Id
	@Column(name = "rich_menu_id")
	private String richMenuId;

	@CreatedDate
	@Column(name = "create_date", nullable = false, updatable = false)
	private LocalDateTime createDate = LocalDateTime.now();

	@Column(name = "name")
	private String name;

	@Column(name = "chatBarText")
	private String chatBarText;

	@Column(name = "selected")
	private Boolean selected;

	@Type(type = "jsonb")
	@Column(name = "size", columnDefinition = "jsonb")
	private RichMenuSize size;

	@Type(type = "jsonb")
	@Column(name = "areas", columnDefinition = "jsonb")
	private List<RichMenuArea> areas;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;

	public RichMenu() {

	}

	public RichMenu(com.example.demo.LineModel.RichMenu.RichMenu richMenu) {
		this.setRichMenuId(richMenu.getRichMenuId());
		this.setName(richMenu.getName());
		this.setChatBarText(richMenu.getChatBarText());
		this.setSelected(richMenu.getSelected());
		this.setSize(richMenu.getSize());
		this.setAreas(richMenu.getAreas());
	}

	public String getImageBase64() {
		byte[] encodeBase64 = Base64Utils.encode(this.getImage());
		try {
			return new String(encodeBase64, "UTF-8");
		} catch (UnsupportedEncodingException uee) {
			return null;
		}
	}

}
