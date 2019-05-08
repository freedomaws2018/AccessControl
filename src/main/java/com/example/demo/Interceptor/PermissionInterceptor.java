package com.example.demo.Interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.example.demo.DataBase.Repository.EmployeeRepository;

@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

//	@Autowired
//	private JdbcTemplate jdbcTemplate;

	@Autowired
	private EmployeeRepository employeeRepository;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		System.err.println(request.getSession());

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.err.println("=================== postHandle =================== ");

		// 首次登入系統 驗證
		Long empCount = this.employeeRepository.count();
		if(empCount == 0) {
			response.sendRedirect("/register");
		}

		// 登入驗證
		Boolean is_login = false;
		if(!is_login) {
			response.sendRedirect("/login");
		}

		modelAndView.addObject("employee", null);

		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.err.println("=================== afterCompletion =================== ");
		super.afterCompletion(request, response, handler, ex);
	}

}
