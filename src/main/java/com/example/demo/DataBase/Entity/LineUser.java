package com.example.demo.DataBase.Entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
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

	@Column(name = "beg_dt")
	private LocalDateTime begDt;

	@Column(name = "end_dt")
	private LocalDateTime endDt;

	@Column(name = "rich_menu_id")
	private String richMenuId;


}
