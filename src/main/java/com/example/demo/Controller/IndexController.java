package com.example.demo.Controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	private ModelAndView model;

	@GetMapping(value = "/index")
	public ModelAndView index() {
		model = new ModelAndView("comm/index");
		Map<String,String> map = new HashMap<>();
		map.put("name", "超級使用者");
		map.put("data", "測試用");
		model.addObject("headerInfo", map);
		return model;
	}

	@GetMapping(value = "/login")
	public ModelAndView login() {
		model = new ModelAndView("comm/login");
		return model;
	}

	/** Redirect **/
	@GetMapping(value = "/")
	public ModelAndView root() {
		model = new ModelAndView("redirect:/index");
		return model;
	}
}
