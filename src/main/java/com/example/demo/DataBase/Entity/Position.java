package com.example.demo.DataBase.Entity;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.example.demo.DataBase.Entity.Mapping.MappingPositionPermissionPermissiondetail;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tbl_position")
public class Position {

  @Id
  @Column(name = "id", nullable = false, insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @Column(name = "is_default")
  private Boolean isDefault;

  @Column(name = "name", unique = true)
  private String name;

  @OneToMany(mappedBy = "positionId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<MappingPositionPermissionPermissiondetail> mappings;

}
