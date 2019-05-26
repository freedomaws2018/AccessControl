package com.example.demo.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Controller.FormEntity.FormEmployeeChangePassword;
import com.example.demo.DataBase.Entity.Employee;
import com.example.demo.DataBase.Service.EmployeeService;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  @GetMapping(value = "/list")
  private ModelAndView list(ModelAndView model,
      @PageableDefault(page = 0, size = 10, sort = { "id" }, direction = Direction.ASC) Pageable pageable) {
    model = new ModelAndView("layout/employee/l_employee");
    Page<Employee> employees = employeeService.getAll(pageable);
    model.addObject("employees", employees);
    return model;
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
//    model.addObject("form", form);

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

    employee.setPassword(pw1);
    employeeService.save(employee);

    model = new ModelAndView("redirect:/login");
    return model;
  }

}
