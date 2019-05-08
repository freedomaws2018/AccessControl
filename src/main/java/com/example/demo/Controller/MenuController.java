package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin/menu")
public class MenuController {

	private ModelAndView model;

	@GetMapping(value = "/edit")
	private ModelAndView edit() {
		this.model = new ModelAndView("layout/admin/menu/u_menu");
		return this.model;
	}

}
