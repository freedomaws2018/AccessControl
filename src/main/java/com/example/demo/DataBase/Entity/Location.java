package com.example.demo.DataBase.Entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import com.example.demo.DataBase.Entity.Mapping.MappingEmployeeLocation;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tbl_location")
/**
 * 據點 資料表
 */
public class Location {

  /** 地點對應ID **/
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

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

//  @JsonIgnore
  @OneToMany(mappedBy = "locationId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @OrderBy(value = "name ASC")
  private List<LocationDetail> details = new ArrayList<>();

  /** 據點負責人 **/
  @JsonIgnore
  @OneToMany(mappedBy = "locationId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @OrderBy("employeeId ASC")
  private List<MappingEmployeeLocation> mappingEL = new ArrayList<>();

  public List<Long> getEmployeeIds() {
    return mappingEL.stream().filter(MappingEmployeeLocation::getIsUse).map(MappingEmployeeLocation::getEmployeeId)
        .collect(Collectors.toList());
  }

}
