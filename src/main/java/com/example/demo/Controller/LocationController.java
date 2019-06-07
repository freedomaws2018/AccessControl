package com.example.demo.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Controller.FormEntity.FormLocation;
import com.example.demo.DataBase.Entity.Employee;
import com.example.demo.DataBase.Entity.Location;
import com.example.demo.DataBase.Entity.LocationDetail;
import com.example.demo.DataBase.Entity.RichMenu;
import com.example.demo.DataBase.Service.EmployeeService;
import com.example.demo.DataBase.Service.LocationService;
import com.example.demo.DataBase.Service.RichMenuService;
import com.google.common.base.Functions;

@Controller
@RequestMapping(value = "/location")
public class LocationController {

  @Autowired
  private LocationService locationService;

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private RichMenuService richMenuService;

  @GetMapping(value = "/list")
  private ModelAndView list(ModelAndView model,
      @PageableDefault(page = 0, size = 10, sort = { "id" }, direction = Direction.ASC) Pageable pageable) {
    model = new ModelAndView("layout/location/l_location");
    Page<Location> locations = this.locationService.getAll(pageable);
    model.addObject("locations", locations);
    return model;
  }

  @GetMapping(value = "/list2")
  private ModelAndView list2(ModelAndView model) {
    model = new ModelAndView("layout/location/l_location2");
    List<Location> locations = locationService.getAll();
    model.addObject("locations", locations);
    return model;
  }

  @GetMapping(value = "/add")
  private ModelAndView add(ModelAndView model) {
    model = new ModelAndView("layout/location/u_location");
    model.addObject("funcType", "add");
    Map<Integer, Employee> empMap = employeeService.getAll().stream()
        .collect(Collectors.toMap(e -> Math.toIntExact(e.getId()), Functions.identity()));
    model.addObject("mappingEmp", empMap);
    List<RichMenu> richMenus = richMenuService.getAllRichMenu();
    model.addObject("richMenus", richMenus);
    return model;
  }

  @GetMapping(value = "/{funcType:view|edit}/{id}")
  private ModelAndView edit(ModelAndView model, @PathVariable String funcType, @PathVariable Long id) {
    model = new ModelAndView("layout/location/u_location");

    Location location = this.locationService.getById(id);
    model.addObject("location", location);
    model.addObject("funcType", funcType);
    Map<Integer, Employee> empMap = employeeService.getAll().stream()
        .collect(Collectors.toMap(e -> Math.toIntExact(e.getId()), Functions.identity()));
    model.addObject("mappingEmp", empMap);
    List<RichMenu> richMenus = richMenuService.getAllRichMenu();
    model.addObject("richMenus", richMenus);

    return model;
  }

  @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  private ResponseEntity<Object> save(ModelAndView model, @Valid FormLocation form, BindingResult bindingResult) {
    Map<String, Object> result = new HashMap<>();

//  if (bindingResult.hasErrors()) {
//    List<FieldError> fieldErrors = bindingResult.getFieldErrors();
//    for (FieldError error : fieldErrors) {
//      System.err.printf("%s:%s:%s\n", error.getField(), error.getDefaultMessage(), error.getCode());
//    }
//  }

    Location location = null;
    if (form.getId() == null) {
      location = locationService.save(form.getLocaiton());
    } else {
      locationService.updateAllIsUseFalseByLoctionId(form.getId());
      locationService.deleteAlLocationDetailByLocationId(form.getId());
      location = locationService.save(form.getLocaitonWithId());
      locationService.saveMappingEL(form.getMappingEL(location));
      locationService.saveLocationDetails(form.getLocationDetails(location));
    }

    result.put("status", "success");
    result.put("data", location);

    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  private ResponseEntity<Object> delete(@PathVariable Long id) {
    Location location = this.locationService.getById(id);
    if (location != null) {
      this.locationService.delete(location);
    }
    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  /** autocomplete **/
  @PostMapping(value = "/autocomplete/getAll", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  private ResponseEntity<Object> getAll(String term) {
    List<Location> locations;
    if (StringUtils.isNotBlank(term)) {
      locations = locationService.getByNameLike(term);
    } else {
      locations = locationService.getByNameLike("");
    }
    return new ResponseEntity<>(locations, HttpStatus.OK);
  }

  @PostMapping(value = "/getLocationDetailByLocationId", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  private ResponseEntity<Object> getLocationDetailByLocationId(Long locationId){
    List<LocationDetail> details = locationService.getLocationDetailByLocationId(locationId);
    return new ResponseEntity<>(details, HttpStatus.OK);
  }

}
