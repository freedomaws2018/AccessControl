package com.example.demo.DataBase.Entity.Mapping;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.example.demo.DataBase.Entity.IdClass.MappingLineuserRichmenuId;

import lombok.Data;

@Data
@Entity
@Table(name = "mapping_lineuser_richmenu")
@IdClass(MappingLineuserRichmenuId.class)
/**
 * 對應 LineUser 與 RichMenu 對應關係
 */
public class MappingLineuserRichmenu {

  @Id
  @Column(name="line_user_id")
  private String lineUserId;

  @Id
  @Column(name = "rich_menu_id")
  private String richMenuId;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @Column(name = "is_use")
  private Boolean isUse;

}
