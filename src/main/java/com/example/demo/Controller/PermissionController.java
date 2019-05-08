package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin/permission")
public class PermissionController {

	private ModelAndView model;

	@GetMapping(value = "/list/{page:[0-9]+}")
	private ModelAndView list(@PathVariable int page) {
		this.model = new ModelAndView("layout/admin/permission/l_permission");
		return this.model;
	}

	/** Redirect **/
	@GetMapping(value = "/list")
	public ModelAndView redirectList() {
		return new ModelAndView("redirect:/admin/permission/list/1");
	}
}
