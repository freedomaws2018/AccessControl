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

import com.example.demo.Controller.FormEntity.FormDataTables;
import com.example.demo.Controller.FormEntity.FormPermission;
import com.example.demo.DataBase.Entity.Permission;
import com.example.demo.DataBase.Service.PermissionService;

@Controller
@RequestMapping(value = "/permission")
public class PermissionController {

  @Autowired
  private PermissionService permissionService;

  @GetMapping(value = "/list")
  private ModelAndView list(ModelAndView model,
      @PageableDefault(page = 0, size = 10, sort = { "menuName" }, direction = Direction.ASC) Pageable pageable) {
    model = new ModelAndView("layout/permission/l_permission");
    Page<Permission> permissions = this.permissionService.getAllPermission(pageable);
    model.addObject("permissions", permissions);
    return model;
  }

  @GetMapping(value = "/list2")
  private ModelAndView list2(ModelAndView model) {
    model = new ModelAndView("layout/permission/l_permission2");
    return model;
  }
  @PostMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  private ResponseEntity<Object> listWithDataTables(FormDataTables form){
//    System.err.println(form);
    Map<String, Object> result = new HashMap<>();
    result.put("draw", 0); // 我也不知道這是啥
    List<Permission> permissions = permissionService.getAllPermission();
    result.put("recordsTotal", permissions.size()); // 總筆數
    result.put("recordsFiltered", permissions.size()); // 過濾後筆數
    result.put("data", permissions); // 數據
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @GetMapping(value = "/add")
  public ModelAndView add(ModelAndView model, RedirectAttributes attr) {
    model = new ModelAndView("layout/permission/u_permission");
    model.addObject("funcType", "add");

    return model;
  }

  @GetMapping(value = "/{funcType:view|edit}/{permissionId}")
  public ModelAndView viewAndEdit(ModelAndView model, RedirectAttributes attr, @PathVariable String funcType,
      @PathVariable Long permissionId) {
    model = new ModelAndView("layout/permission/u_permission");
    model.addObject("funcType", funcType);
    Permission permission = this.permissionService.getPermissionById(permissionId);
    model.addObject("permission", permission);
    return model;
  }

  @DeleteMapping(value = "/delete/{permissionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> delete(@PathVariable Long permissionId) {
    Map<String, Object> result = new HashMap<>();
    permissionService.delete(permissionId);
    result.put("status", "success");
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PostMapping(value = "/save")
  public ResponseEntity<Object> save(FormPermission form) {
    Map<String, Object> result = new HashMap<>();
    Permission permission;
    if (form.getId() == null ) {
      permission = new Permission();
    } else {
      permission = permissionService.getPermissionById(form.getId());
    }
    permission = permissionService.save(form.getPermission(permission));
    permissionService.deleteAllDetailByPId(permission.getId());
    permissionService.saveAllPermissionDetail(form.getPermissionDetail(permission));
    permission = permissionService.getPermissionById(permission.getId());

    result.put("status", "success");
    result.put("data", permission);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  /** Redirect **/
}
