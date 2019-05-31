package com.example.demo.DataBase.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.example.demo.Common.FunctionUtils;
import com.example.demo.DataBase.Entity.Wf8266;
import com.example.demo.DataBase.Repository.Wf8266DetailRepository;
import com.example.demo.DataBase.Repository.Wf8266Repository;

@Service
public class Wf8266Service {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  @Autowired
  private Wf8266Repository wf8266Repository;

  @Autowired
  private Wf8266DetailRepository wf8266DetailRepository;

  public List<Wf8266> getWf8266All() {
    return this.wf8266Repository.findAll();
  }

  public Page<Wf8266> getWf8266All(Pageable pageable) {
    List<Wf8266> wf8266s = this.getWf8266All();
    wf8266s = wf8266s.stream().filter(FunctionUtils.distinctByKey(Wf8266::getSn)).collect(Collectors.toList());
    int start = (int) pageable.getOffset();
    int end = start + pageable.getPageSize() > wf8266s.size() ? wf8266s.size() : (start + pageable.getPageSize());
    return new PageImpl<>(wf8266s.subList(start, end), pageable, wf8266s.size());
  }

  public Wf8266 getBySn(String sn) {
    return this.wf8266Repository.getBySnOrderByLocationIdAsc(sn).orElse(null);
  }

  public void deleteAllDetailBySn(String wf8266Sn) {
    wf8266DetailRepository.deleteDetailByWf8266Sn(wf8266Sn);
  }

  public Wf8266 save(Wf8266 wf8266) {
    return wf8266Repository.saveAndFlush(wf8266);
  }

  public List<Map<String, Object>> getByLocationIdAndNameLikeOrderBySnAndRelayAscLimit10(Long locationId, String name) {
    StringBuffer sb = new StringBuffer();
    List<Object> attr = new ArrayList<>();
    sb.append(" SELECT detail.* ");
    sb.append(" FROM tbl_wf8266 wf8266 , tbl_wf8266_detail detail , tbl_location location ");
    sb.append(" WHERE 1 = 1 ");
    sb.append(" AND wf8266.location_id = location.id ");
    sb.append(" AND wf8266.sn = detail.wf8266_sn ");
    if (locationId != null) {
      sb.append(" AND wf8266.location_id = ? ");
      attr.add(locationId);
    }

    sb.append(" AND detail.name LIKE ? ");
    attr.add("%" + name + "%");

    sb.append(" ORDER BY wf8266.sn ASC , relay ASC ");
    sb.append(" LIMIT 10; ");

    return this.jdbcTemplate.queryForList(sb.toString(), attr.toArray());
  }

}
