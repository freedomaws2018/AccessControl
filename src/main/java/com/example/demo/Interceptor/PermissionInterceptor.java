package com.example.demo.Interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.demo.DataBase.Entity.Employee;
import com.example.demo.DataBase.Entity.Menu;
import com.example.demo.DataBase.Entity.Permission;
import com.example.demo.DataBase.Service.EmployeeService;
import com.example.demo.DataBase.Service.MenuService;
import com.example.demo.DataBase.Service.PermissionService;

@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

  @Autowired
  private PermissionService permissionService;

  @Autowired
  private MenuService menuService;

  @Autowired
  private EmployeeService employeeService;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//    System.err.println("=================== preHandle =================== ");

    Employee employee = (Employee) request.getSession().getAttribute("employee");
    if (employee != null && "admin".equals(employee.getAccount())) {
      request.getSession().setAttribute("isAdmin", true);
    } else {
      request.getSession().setAttribute("isAdmin", false);
    }
    String vPassword = (String) request.getSession().getAttribute("password");
    if (employee != null && employee.getPassword().equals(vPassword)) {
      // 選單 (長出側邊選單用)
      List<Menu> menuWithPermissions = menuService.getByEmployeeId(employee.getId());
      request.getSession().setAttribute("menu", menuWithPermissions);
      // 權限 (功能內權限顯示用)
      String uri = request.getRequestURI();
      Menu menu = menuService.getOneByUrl(uri);
      if (menu != null) {
        request.getSession().setAttribute("currentMenu", menu);
        request.getSession().setAttribute("URI", uri);
        Permission permission = permissionService.getByMenuName(menu.getMenuName());
        List<String> currentPermissions = employeeService.getMappingEPPByEmployeeAndPermission(employee, permission);
        request.getSession().setAttribute("currentPermissions", currentPermissions);
      }

    } else {
      response.sendRedirect("/login");
      return false;
    }
    return true;
  }

  @Value("${server.user_login_timeout}")
  private Integer userLoginTimeout;

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
//    System.err.println("=================== postHandle =================== ");
    Long creationTime = request.getSession().getCreationTime();
    Long lastAccessedTime = request.getSession().getLastAccessedTime();
//    int maxInactiveInterval = request.getSession().getMaxInactiveInterval();
    request.getSession().setAttribute("creationTime", creationTime);
    request.getSession().setAttribute("lastAccessedTime", lastAccessedTime);
    request.getSession().setAttribute("maxInactiveInterval", userLoginTimeout);

    super.postHandle(request, response, handler, modelAndView);
  }

//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		System.err.println("=================== afterCompletion =================== ");
//		super.afterCompletion(request, response, handler, ex);
//	}

}
