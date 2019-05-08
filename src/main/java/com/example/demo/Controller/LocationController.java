package com.example.demo.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/location")
public class LocationController {

	private ModelAndView model;

	@GetMapping(value = "/list/{page:[0-9]+}")
	private ModelAndView list(@PathVariable int page) {
		this.model = new ModelAndView("layout/location/l_location");
		return this.model;
	}

	/** Redirect **/
	@GetMapping(value = "/list")
	public ModelAndView redirectList() {
		return new ModelAndView("redirect:/location/list/1");
	}
}
