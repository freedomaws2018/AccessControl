package com.example.demo.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Controller.FormEntity.FormPosition;
import com.example.demo.DataBase.Entity.Permission;
import com.example.demo.DataBase.Entity.Position;
import com.example.demo.DataBase.Service.PermissionService;
import com.example.demo.DataBase.Service.PositionService;

@Controller
@RequestMapping(value = "/position")
public class PositionController {

  @Autowired
  private PositionService positionService;

  @Autowired
  private PermissionService permissionService;

  @GetMapping(value = "/list")
  private ModelAndView list(ModelAndView model,
      @PageableDefault(page = 0, size = 10, sort = { "id" }, direction = Direction.ASC) Pageable pageable) {
    model = new ModelAndView("layout/position/l_position");
    Page<Position> positions = positionService.getAll(pageable);
    model.addObject("positions", positions);
    return model;
  }

  @GetMapping(value = "/add")
  public ModelAndView add(ModelAndView model, RedirectAttributes attr) {
    model = new ModelAndView("layout/position/u_position");
    model.addObject("funcType", "add");
    List<Permission> permissions = permissionService.getAllPermission();
    model.addObject("permissions", permissions);
    return model;
  }

  @GetMapping(value = "/{funcType:view|edit}/{positionId}")
  public ModelAndView viewAndEdit(ModelAndView model, RedirectAttributes attr, @PathVariable String funcType,
      @PathVariable Long positionId) {
    model = new ModelAndView("layout/position/u_position");
    model.addObject("funcType", funcType);
    Position position = positionService.getById(positionId);
    model.addObject("position", position);
    List<Permission> permissions = permissionService.getAllPermission();
    model.addObject("permissions", permissions);
    List<String> mappingDetail = positionService.findMappingPPPByPositionIdAndIsUseTrue(positionId);
    model.addObject("mappingDetail", mappingDetail);
    return model;
  }

  @PostMapping("/save")
  public ResponseEntity<Object> save(FormPosition form) {
    Map<String, Object> result = new HashMap<>();
    if (form.getId() == null && positionService.existByName(form.getName())) {
      result.put("status", "error");
      result.put("msg", "職稱已存在");
      return new ResponseEntity<>(result, HttpStatus.OK);
    } else {
      positionService.updateAllIsUseFalseWithPositionId(form.getId());
      Position position = positionService.save(form.getPosition());
      positionService.saveAllMappingPPP(form.getMappingPPP(form.getId()));

      result.put("status", "success");
      result.put("data", position);
      return new ResponseEntity<>(result, HttpStatus.OK);
    }
  }

  @DeleteMapping("/delete/{positionId}")
  public ResponseEntity<Object> delete(@PathVariable Long positionId) {
    Map<String, Object> result = new HashMap<>();
    result.put("status", "success");
    positionService.deleteById(positionId);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PostMapping(value = "/getPositionById", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Position> getPositionById(Long positionId) {
    Position position = positionService.getById(positionId);
    return new ResponseEntity<>(position, HttpStatus.OK);
  }

}
