package com.example.demo.DataBase.Entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_menu_temporay")
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
public class MenuTemporary {

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  private Long employeeId;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @Type(type = "jsonb")
  @Column(columnDefinition = "jsonb")
  private List<Menu> temporary;

}
