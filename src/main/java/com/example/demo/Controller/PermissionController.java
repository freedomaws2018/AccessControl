package com.example.demo.Controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.DataBase.Entity.Permission;
import com.example.demo.DataBase.Service.PermissionService;

@Controller
@RequestMapping(value = "/permission")
public class PermissionController {

  @Autowired
  private PermissionService permissionService;

  @GetMapping(value = "/list")
  private ModelAndView list(ModelAndView model,
      @PageableDefault(page = 0, size = 10, sort = { "id" }, direction = Direction.ASC) Pageable pageable) {
    model = new ModelAndView("layout/permission/l_permission");
    Page<Permission> permissions = this.permissionService.getAllPermission(pageable);
    model.addObject("permissions", permissions);
    return model;
  }

  @GetMapping(value = "/add")
  public ModelAndView add(ModelAndView model, RedirectAttributes attr) {
    model = new ModelAndView("layout/permission/u_permission");

    return model;
  }

  @GetMapping(value = "/{funcType:view|edit}/{permissionId}")
  public ModelAndView viewAndEdit(ModelAndView model, RedirectAttributes attr, @PathVariable String funcType,
      @PathVariable Long permissionId) {
    model = new ModelAndView("layout/permission/u_permission");
    Permission permission = this.permissionService.getPermissionById(permissionId);
    model.addObject("permission", permission);
    model.addObject("funcType", funcType);
    return model;
  }

  @DeleteMapping(value = "/delete/{permissionId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> delete(@PathVariable String permissionId) {

    return new ResponseEntity(null, HttpStatus.OK);
  }

  /** Redirect **/
}
