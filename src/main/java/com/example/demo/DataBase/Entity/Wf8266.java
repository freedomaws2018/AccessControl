package com.example.demo.DataBase.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

//@EqualsAndHashCode(callSuper = false)
//public class Wf8266 extends BaseEntity {
@Data
@Entity
@Table(name = "tbl_wf8266")
public class Wf8266 {

	/** wf8266 編號 **/
	@Id
	@Column(name = "sn", updatable = false)
	private String sn;

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @LastModifiedDate
  @Column(name = "modify_date", nullable = false)
  private LocalDateTime modifyDate = LocalDateTime.now();

	/** 區域編號 - 對應 Location **/
	@Transient
	private Location location;
	@Column(name = "location_id")
	private Long locationId;

	/** wf8266 線上金鑰 **/
	@Column(name = "key", updatable = false)
	private String key;

	/** 是否啟用 **/
	@Column(name = "is_use", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
	private Boolean isUse = new Boolean(false);

	@OneToMany(mappedBy = "wf8266", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@OrderBy("relay ASC")
	private List<Wf8266Detail> details = new ArrayList<>();

	public String getStatusUrl() {
		// https://service.wf8266.com/api/mqtt/SN/RequestState/key
		return String.format("https://service.wf8266.com/api/mqtt/%s/RequestState/%s", this.getSn(), this.getKey());
	}

}
