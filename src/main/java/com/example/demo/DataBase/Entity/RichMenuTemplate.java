package com.example.demo.DataBase.Entity;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.tomcat.util.codec.binary.Base64;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import com.example.demo.LineModel.RichMenu.LineRichMenu;
import com.google.gson.Gson;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_rich_menu_template")
public class RichMenuTemplate {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@CreatedDate
	@Column(name = "create_date", nullable = false, updatable = false)
	private LocalDateTime createDate = LocalDateTime.now();

	@Column(name = "name")
	private String name;

	@Lob
	@Basic(fetch = FetchType.LAZY)
	private byte[] image;

	@Type(type = "jsonb")
	@Column(name = "template", columnDefinition = "jsonb")
	private LineRichMenu template;

	public String getTemplateJson() {
		return new Gson().toJson(this.template);
	}

	public String getImage() {
		return Base64.encodeBase64String(image);
	}

}
