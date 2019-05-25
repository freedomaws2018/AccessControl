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

import com.example.demo.DataBase.Entity.Employee;
import com.example.demo.DataBase.Service.EmployeeService;

@Controller
@RequestMapping(value = "/employee")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

	@GetMapping(value = "/list")
	private ModelAndView list(ModelAndView model,
			@PageableDefault(page = 0, size = 10, sort = { "id" }, direction = Direction.ASC) Pageable pageable) {
		model = new ModelAndView("layout/employee/l_employee");
		Page<Employee> employees = employeeService.getAll(pageable);
		model.addObject("employees", employees);
		return model;
	}



}
