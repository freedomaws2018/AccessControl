package com.example.demo.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.demo.DataBase.Entity.Employee;

@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

//	@Autowired
//	private JdbcTemplate jdbcTemplate;

//	@Autowired
//	private EmployeeRepository employeeRepository;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//    System.err.println("=================== preHandle =================== ");
    request.getSession().setAttribute("URI", request.getRequestURI());
    Employee employee = (Employee) request.getSession().getAttribute("employee");
    if (employee == null) {
      response.sendRedirect("/login");
      return false;
    }
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
      ModelAndView modelAndView) throws Exception {
//    System.err.println("=================== postHandle =================== ");
    Long creationTime = request.getSession().getCreationTime();
    Long lastAccessedTime = request.getSession().getLastAccessedTime();
    int maxInactiveInterval = request.getSession().getMaxInactiveInterval();
    request.getSession().setAttribute("creationTime", creationTime);
    request.getSession().setAttribute("lastAccessedTime", lastAccessedTime);
    request.getSession().setAttribute("maxInactiveInterval", maxInactiveInterval);
    super.postHandle(request, response, handler, modelAndView);
  }

//	@Override
//	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
//			throws Exception {
//		System.err.println("=================== afterCompletion =================== ");
//		super.afterCompletion(request, response, handler, ex);
//	}

}
