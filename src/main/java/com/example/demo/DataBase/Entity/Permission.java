package com.example.demo.DataBase.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

@Data
@Entity
@Table(name = "tbl_permission")
public class Permission {

  @Id
  @Column(name = "id", nullable = false, insertable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @LastModifiedDate
  @Column(name = "modify_date", nullable = false)
  private LocalDateTime modifyDate = LocalDateTime.now();

  /** 權限名稱 **/
  @Column(name = "name")
  private String name;

  /** 權限對應選單 **/
  @Column(name = "menu_name")
  private String menuName;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "menu_name", referencedColumnName = "menu_name", insertable = false, updatable = false, nullable = true)
  private Menu menu;

  /** 權限細節 ( 新刪改查...權限 ) **/
  @OneToMany(mappedBy = "permissionId", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @OrderBy(value = "type")
  private List<PermissionDetail> details = new ArrayList<>();

}
