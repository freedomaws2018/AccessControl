package com.example.demo.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.DataBase.Repository.LineUserRepository;
import com.example.demo.DataBase.Repository.LogWf8266Repository;
import com.example.demo.DataBase.Repository.Wf8266Repository;

@Controller
@RequestMapping(value = "/line/log")
public class LineLogController {

	@Autowired
	private LineUserRepository lineUserRepository;

	@Autowired
	private Wf8266Repository wf8266Repository;

	@Autowired
	private LogWf8266Repository logwf8266Repostory;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@GetMapping(value = "/list")
	public ModelAndView list(ModelAndView model,
			@PageableDefault(page = 0, size = 10, sort = { "createDate" }, direction = Direction.DESC) Pageable pageable) {
		model = new ModelAndView("layout/line/l_line_log");

//		Page<LogWf8266> logWf8266s = this.logwf8266Repostory.findAll(pageable);
//
//		if(!logWf8266s.isEmpty()) {
//			List<String> userIds = logWf8266s.getContent().stream().map(LogWf8266::getUserId).distinct().collect(Collectors.toList());
//			Map<String,LineUser> mappingLineUser = this.lineUserRepository.getByUserIdIn(userIds).stream().collect(Collectors.toMap(LineUser::getUserId, Function.identity()));
//
//			List<String> wf8266Ids = logWf8266s.getContent().stream().map(log -> log.getWf8266Trigger().substring(2)).distinct().collect(Collectors.toList());
//
//			Map<String,Wf8266> mappingWf8266 = this.wf8266Repository.getByTriggerTextIn(wf8266Ids).stream().collect(Collectors.toMap(Wf8266::getTriggerText, Function.identity()));
//
//			logWf8266s.stream().forEach( entity -> {
//				String userId = entity.getUserId();
//				String triggerText = entity.getWf8266Trigger().substring(2);
//				if(mappingLineUser.containsKey(userId)){
//					entity.setLineUser(mappingLineUser.get(userId));
//				}
//				if(mappingWf8266.containsKey(triggerText)) {
//					entity.setWf8266(mappingWf8266.get(triggerText));
//				}
//			});
//		}

//		Map<String, String> userMap = this.lineUserRepository.findAll().stream()
//		    .collect(Collectors.toMap(LineUser::getUserId, LineUser::getUserName));
//		Map<String, String> wdMap = this.wf8266Repository.findAll().stream()
//		    .collect(Collectors.toMap(d -> "##" + d.getTriggerText(), Wf8266::getName));

//		model.addObject("logWf8266s", logWf8266s);
//		model.addObject("userMap", userMap);
//		model.addObject("wf8266DetailMap", wdMap);
		return model;
	}

	@GetMapping(value = "/search")
	public ModelAndView search(ModelAndView model,
			@PageableDefault(page = 0, size = 10, sort = { "createDate" }, direction = Direction.DESC) Pageable pageable) {

//		Page<LogWf8266> logWf8266s = this.logwf8266Repostory.findAll(pageable);
//		Map<String, String> userMap = this.lineUserRepository.findAll().stream()
//		    .collect(Collectors.toMap(LineUser::getUserId, LineUser::getUserName));
//		Map<String, String> wdMap = this.wf8266Repository.findAll().stream()
//		    .collect(Collectors.toMap(d -> "##" + d.getTriggerText(), Wf8266::getName));
//
//		model.addObject("logWf8266s", logWf8266s);
//		model.addObject("userMap", userMap);
//		model.addObject("wf8266DetailMap", wdMap);
		return model;
	}

}
