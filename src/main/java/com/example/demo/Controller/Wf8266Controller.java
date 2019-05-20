package com.example.demo.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Controller.FormEntity.FormWf8266;
import com.example.demo.DataBase.Entity.Wf8266;
import com.example.demo.DataBase.Entity.Wf8266Detail;
import com.example.demo.DataBase.Service.LocationService;
import com.example.demo.DataBase.Service.Wf8266Service;

@Controller
@RequestMapping(value = "/wf8266")
public class Wf8266Controller {

  @Autowired
  private Wf8266Service wf8266Service;

  @Autowired
  private LocationService locationService;

  @GetMapping("/list")
  public ModelAndView list(ModelAndView model, @PageableDefault(page = 0, size = 10) Pageable pageable) {
    Page<Wf8266> wf8266s = this.wf8266Service.getAll(pageable);
    wf8266s.stream().forEach(wf8266 -> {
      if (wf8266.getLocationId() != null) {
        wf8266.setLocation(this.locationService.getById(wf8266.getLocationId()));
      }
    });
    return new ModelAndView("layout/wf8266/l_wf8266").addObject("wf8266s", wf8266s);
  }

  @GetMapping("/add")
  public ModelAndView add(ModelAndView model) {
    return new ModelAndView("layout/wf8266/u_wf8266").addObject("funcType", "add");
  }

  @GetMapping("/{funcType:view|edit}/{sn}")
  public ModelAndView viewAndEdit(ModelAndView model, RedirectAttributes attr, @PathVariable String funcType,
      @PathVariable String sn) {
    Wf8266 wf8266 = this.wf8266Service.getBySn(sn);
    if (wf8266 != null) {
      if (wf8266.getLocationId() != null) {
        wf8266.setLocation(this.locationService.getById(wf8266.getLocationId()));
      }
    } else {
      attr.addAttribute("error_msg", "找不到對應的物聯網晶片設定");
      return new ModelAndView("redirect:/wf8266/list");
    }
    return new ModelAndView("layout/wf8266/u_wf8266").addObject("funcType", funcType).addObject("wf8266", wf8266);
  }

  /** Redirect **/
  @RequestMapping("/save")
  public ModelAndView save(ModelAndView model, FormWf8266 form, RedirectAttributes attr) {

    // 刪除
    List<Wf8266Detail> deleteWf8266Detail = form.toDeleteEntity();
    if (deleteWf8266Detail != null && !deleteWf8266Detail.isEmpty()) {
      this.wf8266Service.deleteDetails(deleteWf8266Detail);
    }

    // 更新
    Wf8266 updateWf8266 = form.toEntity();
    if (updateWf8266 != null) {
      this.wf8266Service.save(updateWf8266);
    }

    return new ModelAndView(String.format("redirect:/wf8266/view/%s", form.getSn()));
  }

  /** Autocomplete **/
  @PostMapping(value = "/autocomplete/getAllWf8266DetailWithLocationIdAndName", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> getAllWf8266DetailWithLocationIdAndName(Long locationId, String name) {
    List<Map<String, Object>> wf8266detail = this.wf8266Service
        .getByLocationIdAndNameLikeOrderBySnAndRelayAscLimit10(locationId, name);

    return new ResponseEntity<>(wf8266detail, HttpStatus.OK);
  }

}