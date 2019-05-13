package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin/menu")
public class MenuController {

	@GetMapping(value = "/edit")
	private ModelAndView edit(ModelAndView model) {
		model = new ModelAndView("layout/admin/menu/u_menu");
		return model;
	}

}
