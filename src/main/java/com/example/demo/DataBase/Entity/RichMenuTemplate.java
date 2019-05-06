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
import org.springframework.data.annotation.CreatedDate;

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

	@Column(name = "template", columnDefinition = "TEXT")
	private String template;

	@Column(name = "size")
	private Integer size;

	public String getBase64Image() {
		return Base64.encodeBase64String(image);
	}

}
