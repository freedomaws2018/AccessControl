package com.example.demo.Controller.FormEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.demo.DataBase.Entity.Location;
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

  public Location getLocaiton() {
    Location location = new Location();
    location.setId(this.id);
    location.setName(this.name);
    location.setAddress(this.address);
    location.setPhone(this.phone);
    location.setWifiSsid(this.wifiSsid);
    location.setWifiPasswd(this.wifiPasswd);
    location.setBeaconKey(this.beaconKey);
    List<MappingEmployeeLocation> mels = keepers.stream().map(keeper -> {
      MappingEmployeeLocation mel = new MappingEmployeeLocation();
      mel.setLocationId(this.id);
      mel.setEmployeeId(keeper);
      mel.setIsUse(true);
      return mel;
    }).collect(Collectors.toList());
    location.setMappingEL(mels);
    return location;
  }

}
