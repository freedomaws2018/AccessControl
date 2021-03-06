package com.example.demo.Controller.FormEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.example.demo.DataBase.Entity.Wf8266;
import com.example.demo.DataBase.Entity.Wf8266Detail;

import lombok.Data;

@Data
public class FormWf8266 {

  private String sn;

  private String key;

  private Boolean isUse;

  private Long locationId;

  private List<FormWf8266Detail> detail = new ArrayList<>();

  public Wf8266 getWf8266WithDetail(Wf8266 wf8266) {
    if (wf8266 == null) {
      wf8266 = new Wf8266();
      wf8266.setSn(sn);
      wf8266.setKey(key);
      wf8266.setIsUse(isUse);
      wf8266.setLocationId(locationId);
      List<Wf8266Detail> details = new ArrayList<>();
      details = detail.stream().filter(d -> StringUtils.isNotBlank(d.getName())).map(d -> {
        Wf8266Detail td = new Wf8266Detail();
        BeanUtils.copyProperties(d, td);
        td.setWf8266Sn(sn);
        return td;
      }).collect(Collectors.toList());
      wf8266.setDetails(details);
    } else {
      wf8266.setKey(key);
      wf8266.setIsUse(isUse);
      wf8266.setLocationId(locationId);
      List<Wf8266Detail> details = new ArrayList<>();
      details = detail.stream().filter(d -> StringUtils.isNotBlank(d.getName())).map(d -> {
        Wf8266Detail td = new Wf8266Detail();
        BeanUtils.copyProperties(d, td);
        td.setWf8266Sn(sn);
        return td;
      }).collect(Collectors.toList());
      wf8266.setDetails(details);
    }
    return wf8266;
  }

}