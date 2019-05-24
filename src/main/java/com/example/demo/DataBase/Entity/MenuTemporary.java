package com.example.demo.DataBase.Entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.databind.JsonNode;
import com.vladmihalcea.hibernate.type.json.JsonNodeBinaryType;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_menu_temporay")
@TypeDef(name = "jsonb-node", typeClass = JsonNodeBinaryType.class)
public class MenuTemporary {

  @Id
  @Column(name = "id", nullable = false, updatable = false)
  private Long employeeId;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @Type(type = "jsonb-node")
  @Column(columnDefinition = "jsonb")
  private JsonNode temporary;

}
