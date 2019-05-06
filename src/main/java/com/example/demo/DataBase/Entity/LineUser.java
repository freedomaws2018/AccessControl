package com.example.demo.DataBase.Entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@Entity
@Table(name = "tbl_line_user")
public class LineUser extends BaseEntity {

	@Id
	@Column(name = "user_id", nullable = false, insertable = false, updatable = false)
	private String userId;

	@Column(name = "user_name", nullable = false)
	private String userName;

	@Column(name = "is_use", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean isUse = new Boolean(false);

	@Column(name = "is_admin", nullable = false, columnDefinition = "BOOLEAN DEFAULT false")
	private Boolean isAdmin = new Boolean(false);

	@Column(name = "beg_dt")
	private LocalDateTime begDt;

	@Column(name = "end_dt")
	private LocalDateTime endDt;

}
