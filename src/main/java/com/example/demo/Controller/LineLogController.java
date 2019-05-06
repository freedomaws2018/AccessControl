package com.example.demo.Controller;

import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Entity.LogWf8266;
import com.example.demo.DataBase.Entity.Wf8266Detail;
import com.example.demo.DataBase.Repository.LineUserRepository;
import com.example.demo.DataBase.Repository.LogWf8266Repository;
import com.example.demo.DataBase.Repository.Wf8266DetailRepository;

@Controller
@RequestMapping(value = "/line/log")
public class LineLogController {

	private ModelAndView model;

	@Autowired
	private LineUserRepository lineUserRepository;

	@Autowired
	private Wf8266DetailRepository wf8266DetailRepository;

	@Autowired
	private LogWf8266Repository logwf8266Repostory;

	@GetMapping(value = "/list/{page:[0-9]+}")
	public ModelAndView list(@PathVariable int page) {
		model = new ModelAndView("layout/line/l_line_log");
		Pageable pageable = PageRequest.of(page - 1, 20, Sort.by(Direction.DESC, "createDate"));
		Page<LogWf8266> logWf8266s = logwf8266Repostory.findAll(pageable);
		Map<String, String> userMap = lineUserRepository.findAll().stream()
		    .collect(Collectors.toMap(LineUser::getUserId, LineUser::getUserName));
		Map<String, String> wdMap = wf8266DetailRepository.findAll().stream()
		    .collect(Collectors.toMap(d -> "##" + d.getTriggerText(), Wf8266Detail::getName));

		model.addObject("logWf8266s", logWf8266s);
		model.addObject("userMap", userMap);
		model.addObject("wf8266DetailMap", wdMap);
		return model;
	}

	/** Redirect **/
	@GetMapping(value = "/list")
	public ModelAndView redirectList() {
		return new ModelAndView("redirect:/line/log/list/1");
	}

}
