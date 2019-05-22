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

import com.example.demo.Controller.FormEntity.FromLogWf8266;
import com.example.demo.DataBase.Entity.Log.LogWf8266;
import com.example.demo.DataBase.Service.LogWf8266Service;

@Controller
@RequestMapping(value = "/line/log")
public class LineLogController {

	@Autowired
	private LogWf8266Service logWf8266Service;


	@GetMapping(value = "/list")
	public ModelAndView list(FromLogWf8266 form ,ModelAndView model,
			@PageableDefault(page = 0, size = 10, sort = { "createDate" }, direction = Direction.DESC) Pageable pageable) {
		model = new ModelAndView("layout/line/l_line_log");
		System.err.println(form);
		Page<LogWf8266> logWf8266s = this.logWf8266Service.findAll(pageable);

		if(!logWf8266s.isEmpty()) {
			logWf8266s = this.logWf8266Service.getLineUserAndWf8266Detail(logWf8266s);
		}

		model.addObject("logWf8266s", logWf8266s);
		return model;
	}

}
