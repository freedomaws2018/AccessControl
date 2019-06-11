package com.example.demo.DataBase.Entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;

//@EqualsAndHashCode(callSuper = false)
//public class LineUser extends BaseEntity {
@Setter
@Getter
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" })
@Entity
@Table(name = "tbl_line_user")
public class LineUser {

  /** line的UUID **/
  @Id
  @Column(name = "user_id", nullable = false, insertable = false, updatable = false)
  private String userId;

  /** 加入時間 **/
  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  /** 離開時間 **/
  @Column(name = "leave_date")
  private LocalDateTime leaveDate = null;

  @Column(name = "is_leave")
  private Boolean isLeave = new Boolean(false);

  /** 使用者名稱 LineBotService.getProfileByUserId(userId) **/
  @Column(name = "user_name", nullable = false)
  private String userName;

  /** RichMenu 最後發送時間 **/
  @Column(name = "rich_menu_link_datetime")
  private LocalDateTime richMenuLinkDateTime;
  /** RichMenu 當前使用頁面 **/
  @Column(name = "rich_menu_id")
  private String richMenuId;
//  /** RichMenu關聯 **/
//  @Lob
//  @OneToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "rich_menu_id", referencedColumnName = "rich_menu_id", insertable = false, updatable = false, nullable = true)
//  private RichMenu richMenu;

}
