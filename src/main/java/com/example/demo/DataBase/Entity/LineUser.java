package com.example.demo.DataBase.Entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

//@EqualsAndHashCode(callSuper = false)
//public class LineUser extends BaseEntity {
@Data
@Entity
@Table(name = "tbl_line_user")
public class LineUser {

  @Id
  @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
  private String userId;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @LastModifiedDate
  @Column(name = "modify_date", nullable = false)
  private LocalDateTime modifyDate = LocalDateTime.now();

  @Column(name = "user_name", nullable = false)
  private String userName;

  @Column(name = "is_use", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
  private Boolean isUse = new Boolean(false);

  @Column(name = "is_admin", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
  private Boolean isAdmin;

  @Column(name = "beg_dt")
  private LocalDateTime begDt;

  @Column(name = "end_dt")
  private LocalDateTime endDt;

  @Column(name = "location_id")
  private Long locationId;

  @Column(name = "location_detail_name")
  private String locationDetailName;

  /** RichMenu 最後發送時間 **/
  @Column(name = "rich_menu_link_datetime")
  private LocalDateTime richMenuLinkDateTime;
  /** RichMenu 當前使用頁面 **/
  @Column(name = "rich_menu_id")
  private String richMenuId;
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "rich_menu_id", referencedColumnName = "rich_menu_id", insertable = false, updatable = false, nullable = true)
  private RichMenu richMenu;

}
