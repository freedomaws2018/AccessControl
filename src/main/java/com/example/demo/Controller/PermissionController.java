package com.example.demo.Controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/admin/permission")
public class PermissionController {

	@GetMapping(value = "/list")
	private ModelAndView list(ModelAndView model,
			@PageableDefault(page = 0, size = 10, sort = { "id" }, direction = Direction.ASC) Pageable pageable) {
		model = new ModelAndView("layout/admin/permission/l_permission");
		return model;
	}

	/** Redirect **/
}
