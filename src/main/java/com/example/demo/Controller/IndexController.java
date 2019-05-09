package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.DataBase.Repository.EmployeeRepository;

@Controller
public class IndexController {

	@Autowired
	private EmployeeRepository employeeRepository;

	@GetMapping(value = "/index")
	public ModelAndView index(ModelAndView model) {
		model = new ModelAndView("comm/index");
		System.err.println("=================== index ===================");
		return model;
	}

	@GetMapping(value = "/login")
	public ModelAndView login(ModelAndView model) {
		model = new ModelAndView("comm/login");
		return model;
	}

	@GetMapping(value = "/register")
	public ModelAndView adminRegister(ModelAndView model) {
		Long empCount = this.employeeRepository.count();
		if (empCount == 0) {
			model = new ModelAndView("comm/admin_register");
		} else {
			model = new ModelAndView("redirect:/index");
		}
		return model;
	}

	@GetMapping(value = "/404")
	public ModelAndView v404(ModelAndView model) {
		model = new ModelAndView("error/404");
		return model;
	}

	@GetMapping(value = "/500")
	public ModelAndView v500(ModelAndView model) {
		model = new ModelAndView("error/500");
		return model;
	}

	/** Redirect **/
	@GetMapping(value = "/")
	public ModelAndView root(ModelAndView model) {
		model = new ModelAndView("redirect:/index");
		return model;
	}
}
