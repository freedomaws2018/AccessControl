package com.example.demo.DataBase.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import lombok.Data;

//@EqualsAndHashCode(callSuper = false)
//public class Location extends BaseEntity {
@Data
@Entity
@Table(name = "tbl_location")
//@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
/**
 * 據點 資料表
 */
public class Location {

	/** 地點對應ID **/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

  @OneToMany(fetch = FetchType.LAZY)
  @JoinColumn(name = "location_id")
  private List<LocationDetail> details = new ArrayList<>();

  @CreatedDate
  @Column(name = "create_date", nullable = false, updatable = false)
  private LocalDateTime createDate = LocalDateTime.now();

  @LastModifiedDate
  @Column(name = "modify_date", nullable = false)
  private LocalDateTime modifyDate = LocalDateTime.now();

	/** 地點名稱 **/
	@Column(name = "name")
	private String name;

	/** 地點地址 **/
	@Column(name = "address")
	private String address;

	/** 地點電話 **/
	@Column(name = "phone")
	private String phone;

	/** 據點裝置使用的 WIFI SSID **/
	@Column(name = "wifi_ssid")
  private String wifiSsid;

	/** 據點裝置使用的 WIFI Password **/
	@Column(name = "wifi_passwd")
  private String wifiPasswd;

	/** 據點的Beacon 對應值 **/
	@Column(name = "beacon_key")
	private String beaconKey;

//	/** 負責人 - 對應 Employee **/
//	@Type(type = "jsonb")
//	@Column(name = "keepers", columnDefinition = "jsonb default '[]' ")
//	private List<Long> keepers;
//  @Transient
//  private List<Employee> employees;

//	/** 對應的設備 **/
//	@OneToMany(mappedBy = "locationId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<Wf8266> wf8266s;

//	@OneToMany(mappedBy = "lineuser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	private List<MappingLineUserAndLocation> lineusers;
}
