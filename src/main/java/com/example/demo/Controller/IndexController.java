package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class IndexController {

	private ModelAndView model;

	@GetMapping(value = "/index")
	public ModelAndView index() {
		this.model = new ModelAndView("comm/index");
		return this.model;
	}

	@GetMapping(value = "/login")
	public ModelAndView login() {
		this.model = new ModelAndView("comm/login");
		return this.model;
	}

	/** Redirect **/
	@GetMapping(value = "/")
	public ModelAndView root() {
		this.model = new ModelAndView("redirect:/index");
		return this.model;
	}
}
