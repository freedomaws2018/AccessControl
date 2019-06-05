package com.example.demo.Controller.FormEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.example.demo.DataBase.Entity.Location;
import com.example.demo.DataBase.Entity.LocationDetail;
import com.example.demo.DataBase.Entity.Mapping.MappingEmployeeLocation;

import lombok.Data;

@Data
public class FormLocation {

  private Long id;

  private String name;

  private String address;

  private String phone;

  private String wifiSsid;

  private String wifiPasswd;

  private String beaconKey;

  private List<Long> keepers = new ArrayList<>();

  private List<FormLocaionDetail> detail = new ArrayList<>();

  public Location getLocaiton() {
    Location location = new Location();
//    location.setId(this.id);
    location.setName(this.name);
    location.setAddress(this.address);
    location.setPhone(this.phone);
    location.setWifiSsid(this.wifiSsid);
    location.setWifiPasswd(this.wifiPasswd);
    location.setBeaconKey(this.beaconKey);
    return location;
  }

  public Location getLocaitonWithId() {
    Location location = new Location();
    location.setId(this.id);
    location.setName(this.name);
    location.setAddress(this.address);
    location.setPhone(this.phone);
    location.setWifiSsid(this.wifiSsid);
    location.setWifiPasswd(this.wifiPasswd);
    location.setBeaconKey(this.beaconKey);
    return location;
  }

  public List<LocationDetail> getLocationDetails(Location location) {
    return detail.stream().map(d -> {
      LocationDetail dd = new LocationDetail();
      dd.setName(d.getName());
      dd.setLocationId(location.getId());
      dd.setIsSpace(d.getIsSpace());
      dd.setRentMonth(d.getRentMonth());
      dd.setTaxMonth(d.getTaxMonth());
      dd.setElectricityFeeDegree(d.getElectricityFeeDegree());
      dd.setRichMenuId(StringUtils.isNotBlank(d.getRichMenuId()) ? d.getRichMenuId() : null);
      return dd;
    }).collect(Collectors.toList());
  }

  public List<MappingEmployeeLocation> getMappingEL(Location location) {
    return keepers.stream().map(keeper -> {
      MappingEmployeeLocation mel = new MappingEmployeeLocation();
      mel.setLocationId(location.getId());
      mel.setEmployeeId(keeper);
      mel.setIsUse(true);
      return mel;
    }).collect(Collectors.toList());
  }

}
