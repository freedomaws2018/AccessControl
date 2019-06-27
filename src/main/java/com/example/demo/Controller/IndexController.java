package com.example.demo.Controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.DataBase.Entity.Employee;
import com.example.demo.DataBase.Service.EmployeeService;

@Controller
public class IndexController {

  @Autowired
  private EmployeeService employeeService;

  @GetMapping(value = "/index")
  public ModelAndView index(ModelAndView model) {
    model = new ModelAndView("comm/index");
    return model;
  }

  @GetMapping(value = "/login")
  public ModelAndView login(ModelAndView model, HttpSession session) {
    model = new ModelAndView("comm/login");
    session.removeAttribute("employee");
    return model;
  }

  @GetMapping(value = "/logout")
  public ModelAndView logout(ModelAndView model, HttpSession session) {
    model = new ModelAndView("comm/login");
    session.removeAttribute("employee");
    return model;
  }

  /** 登入驗證 **/
  @PostMapping(value = "/login")
  public ModelAndView loginVerification(@Param(value = "account") String account, @Param("password") String passwd,
      ModelAndView model, RedirectAttributes attr, HttpSession session) {
    Employee employee = null;
    try {
      employee = employeeService.loginVerification(account, passwd);

      session.setAttribute("employee", employee);
      session.setAttribute("password", employee.getPassword());

      return new ModelAndView(new RedirectView("/index"));
    } catch (RuntimeException re) {
      attr.addFlashAttribute("err_msg", re.getMessage());
      return new ModelAndView(new RedirectView("/login"));
    }
  }

//  @GetMapping(value = "/registered")
//  public ModelAndView reg(ModelAndView model, HttpSession session) {
//    model = new ModelAndView("comm/registered");
//    session.removeAttribute("employee");
//    return model;
//  }
//
//  /** 首次登入 **/
//  @PostMapping(value = "/registered")
//  public ModelAndView registered(@RequestParam String account, @RequestParam String passwd, ModelAndView model,
//      RedirectAttributes attr, HttpSession session) {
//    try {
//      Employee employee = employeeService.getAccount(account);
//
//      if (employee.hashPassword("").equals(employee.getPassword())) {
//        employee.setPassword(employee.hashPassword(account, passwd));
//        employee = employeeService.save(employee);
//
//        session.setAttribute("employee", employee);
//        session.setAttribute("password", employee.getPassword());
//      } else {
//        attr.addFlashAttribute("err_msg", "非首次登入");
//        return new ModelAndView(new RedirectView("/login"));
//      }
//
//      return new ModelAndView(new RedirectView("/index"));
//    } catch (RuntimeException re) {
//      attr.addFlashAttribute("err_msg", re.getMessage());
//      return new ModelAndView(new RedirectView("/registered"));
//    }
//
//  }

  @GetMapping(value = "/404")
  public ModelAndView v404(ModelAndView model) {
    model = new ModelAndView("error/404");
    return model;
  }

  @GetMapping(value = "/500")
  public ModelAndView v500(ModelAndView model) {
    model = new ModelAndView("error/500");
    return model;
  }

  @GetMapping(value = "/demo")
  public ModelAndView demo(ModelAndView model) {
    model = new ModelAndView("comm/demo");
    return model;
  }

  /** Redirect **/
  @GetMapping(value = "/")
  public ModelAndView root(ModelAndView model) {
    model = new ModelAndView("redirect:/index");
    return model;
  }
}
