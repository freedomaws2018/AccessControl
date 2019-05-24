package com.example.demo.Interceptor;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private PermissionInterceptor permissionInterceptor;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		List<String> interceptPathList = new ArrayList<>();
		interceptPathList.add("/**");

		List<String> excludePathList = new ArrayList<>();
		excludePathList.add("/css/**");
		excludePathList.add("/js/**");
		excludePathList.add("/images/**");
		excludePathList.add("/register");
		excludePathList.add("/login");

		// 調用 攔截器
		registry.addInterceptor(this.permissionInterceptor)
		// 設定攔截對象
		.addPathPatterns(interceptPathList)
		// 忽略 攔截器
		.excludePathPatterns(excludePathList);

		WebMvcConfigurer.super.addInterceptors(registry);
	}

}
