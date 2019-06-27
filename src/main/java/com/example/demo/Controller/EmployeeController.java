package com.example.demo.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Controller.FormEntity.FormEmployee;
import com.example.demo.Controller.FormEntity.FormEmployeeChangePassword;
import com.example.demo.DataBase.Entity.Employee;
import com.example.demo.DataBase.Entity.Permission;
import com.example.demo.DataBase.Entity.Position;
import com.example.demo.DataBase.Entity.Mapping.MappingEmployeeMenu;
import com.example.demo.DataBase.Entity.Mapping.MappingPositionPermissionPermissiondetail;
import com.example.demo.DataBase.Repository.MappingEmployeeMenuRepository;
import com.example.demo.DataBase.Repository.MappingEmployeePermissonPositionRepository;
import com.example.demo.DataBase.Service.EmployeeService;
import com.example.demo.DataBase.Service.PermissionService;
import com.example.demo.DataBase.Service.PositionService;
import com.google.common.base.Functions;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @Autowired
  private PositionService positionService;

  @Autowired
  private PermissionService permissionService;

  @Autowired
  private MappingEmployeePermissonPositionRepository mappingEmployeePermissonPositionRepository;

  @Autowired
  private MappingEmployeeMenuRepository mappingEmployeeMenuRepository;

  @GetMapping(value = "/list")
  private ModelAndView list(ModelAndView model,
      @PageableDefault(page = 0, size = 10, sort = { "id" }, direction = Direction.ASC) Pageable pageable) {
    model = new ModelAndView("layout/employee/l_employee");
    Page<Employee> employees = employeeService.getAll(pageable);
    model.addObject("employees", employees);
    Map<Long, Position> mPosition = positionService.getAll().stream()
        .collect(Collectors.toMap(Position::getId, Functions.identity()));
    model.addObject("mPosition", mPosition);
    return model;
  }

  @GetMapping(value = "/add")
  private ModelAndView add(ModelAndView model) {
    model = new ModelAndView("layout/employee/u_employee");
    model.addObject("funcType", "add");
    List<Position> positions = positionService.getAll();
    model.addObject("positions", positions);
    List<Permission> permissions = permissionService.getAllPermission();
    model.addObject("permissions", permissions);
    return model;
  }

  @GetMapping(value = "/{funcType:view|edit}/{employeeId}")
  public ModelAndView viewAndEdit(ModelAndView model, @PathVariable String funcType, @PathVariable Long employeeId) {
    model = new ModelAndView("layout/employee/u_employee");
    model.addObject("funcType", funcType);
    Employee employee = employeeService.getById(employeeId);
    model.addObject("employee", employee);
    List<Position> positions = positionService.getAll();
    model.addObject("positions", positions);
    List<Permission> permissions = permissionService.getAllPermission();
    model.addObject("permissions", permissions);
    List<String> mappingPermissions = mappingEmployeePermissonPositionRepository
        .findByEmployeeIdAndPositionIdAndIsUseTrue(employee.getId(), employee.getPositionId()).stream()
        .map(m -> m.getPermissionKey() + ":" + m.getPermissionDetailType()).collect(Collectors.toList());
    model.addObject("mappingPermissions", mappingPermissions);
    return model;
  }

  @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> save(FormEmployee form) {
    String funcType = form.getFuncType();
    Map<String, Object> map = new HashMap<>();
    try {
      if ("add".equals(funcType) && employeeService.hasAccount(form.getAccount())) {
        throw new RuntimeException("帳號存在");
      }

      Employee employee = employeeService.save(form.getEmployee());

      // 權限設定
      mappingEmployeePermissonPositionRepository.updateAllIsUseFalseWithEmployeeId(employee.getId());
      mappingEmployeePermissonPositionRepository.saveAll(form.getMappingEPP(employee));

      // 選單設定
      mappingEmployeeMenuRepository.updateAllIsUseFalseByEmployeeId(employee.getId());
      if (!form.getPermissionDetailType().isEmpty()) {
        List<Permission> permission = permissionService
            .getPermissionByPermissionIdAndPermissionDetailType(form.getPermissionDetailType());
        List<String> menuName = permission.stream().map(Permission::getMenuName).collect(Collectors.toList());
        List<MappingEmployeeMenu> mems = menuName.stream().map(mn -> {
          MappingEmployeeMenu mem = new MappingEmployeeMenu();
          mem.setEmployeeId(employee.getId());
          mem.setMenuName(mn);
          mem.setIsUse(true);
          return mem;
        }).collect(Collectors.toList());
        mappingEmployeeMenuRepository.saveAll(mems);
      }
      map.put("status", "success");
      map.put("data", employee);
    } catch (Exception ex) {
      map.put("status", "error");
      map.put("msg", ex.getMessage());
      map.put("stackTrace", ex.getStackTrace());
      // ex.printStackTrace();
    }
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete/{id}")
  public ResponseEntity<Object> delect(@PathVariable Long id) {
    Map<String, Object> map = new HashMap<>();
    employeeService.delete(id);
    map.put("status", "success");
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @GetMapping(value = "/changePassword")
  public ModelAndView getChangePassword(ModelAndView model, HttpSession session) {
    model = new ModelAndView("layout/employee/u_change_password");
    Employee employee = (Employee) session.getAttribute("employee");
    model.addObject("employee", employee);
    return model;
  }

  @PostMapping("/changePassword")
  public ModelAndView postChangePassword(FormEmployeeChangePassword form, ModelAndView model, HttpSession session) {
    model = new ModelAndView("layout/employee/u_change_password");
    Employee employee = (Employee) session.getAttribute("employee");
    // 原始密碼輸入必須正確
    if (!employee.getPassword().equals(employee.hashPassword(form.getOriPassword()))) {
      model.addObject("error_status", "原密碼輸入錯誤");
      return model;
    }
    String pw1 = form.getNewPassword1();
    String pw2 = form.getNewPassword2();
    if (!pw1.equals(pw2)) {
      model.addObject("error_status", "新密碼與確認密碼 不相同");
      return model;
    }
    employee.setPassword(pw1, true);
    employeeService.save(employee);
    model = new ModelAndView("redirect:/login");
    return model;
  }

  @PostMapping("/resetPassword")
  public ResponseEntity<Object> resetPasswrod(@RequestParam Long id, @RequestParam String account) {
    Map<String, Object> result = new HashMap<>();
    Employee employee = employeeService.getByIdAndAccount(id, account);
    if (employee != null) {
      employee.setPassword(account, true);
      employeeService.save(employee);
      result.put("status", "success");
    } else {
      result.put("status", "error");
    }
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @PostMapping(value = "/getMappingPPPWithPositionId", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> getMappingPPPWithPersitionId(@RequestParam Long positionId) {
    Map<String, Object> result = new HashMap<>();
    List<MappingPositionPermissionPermissiondetail> mappingPPPs = positionService
        .getMappingPPPWithPersitionId(positionId);
    if (mappingPPPs != null && !mappingPPPs.isEmpty()) {
      result.put("status", "success");
      result.put("data", mappingPPPs);
    } else {
      result.put("status", "error");
    }
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}
