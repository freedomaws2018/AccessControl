package com.example.demo.DataBase.Entity;

import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;

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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.linecorp.bot.model.richmenu.RichMenuResponse;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
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

  @Type(type = "jsonb")
  @Column(name = "rich_menu_json", columnDefinition = "jsonb")
  private RichMenuResponse richMenuResponse;

  @Lob
  @Basic(fetch = FetchType.LAZY)
  @Column(name = "image")
  private byte[] image;

  public String getImage() {
    try {
      return new String(Base64Utils.encode(image), "UTF-8");
    } catch (UnsupportedEncodingException uee) {
      uee.printStackTrace();
      return null;
    }
  }

}
