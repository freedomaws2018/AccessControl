package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.DataBase.Entity.Location;
import com.example.demo.DataBase.Service.LocationService;

@Controller
@RequestMapping(value = "/location")
public class LocationController {

	@Autowired
	private LocationService locationService;

	@GetMapping(value = "/list")
	private ModelAndView list(ModelAndView model,
			@PageableDefault(page = 0, size = 10, sort = { "id" }, direction = Direction.ASC) Pageable pageable) {
		model = new ModelAndView("layout/location/l_location");
		Page<Location> locations = this.locationService.getAll(pageable);
		model.addObject("locations", locations);
		return model;
	}

}
