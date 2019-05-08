package com.example.demo.Interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class PermissionInterceptor extends HandlerInterceptorAdapter {

//	@Autowired
//	private JdbcTemplate jdbcTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.err.println(request.getSession());

		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		System.err.println("=================== postHandle =================== ");

		Map<String,String> map = new HashMap<>();
		map.put("name", "超級使用者");
		map.put("data", "測試用");
		modelAndView.addObject("headerInfo", map);

		super.postHandle(request, response, handler, modelAndView);
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.err.println("=================== afterCompletion =================== ");
		super.afterCompletion(request, response, handler, ex);
	}

}
