package com.example.demo.DataBase.Service;

import org.springframework.stereotype.Service;

@Service
public class X_LogWf8266Service {

//	@Autowired
//	private JdbcTemplate jdbcTemplate;
//
//	@Autowired
//	private LineUserRepository lineUserRepository;
//
//	@Autowired
//	private Wf8266Repository wf8266Repository;
//
//	@Autowired
//	private Wf8266DetailRepository wf8266DetailRepository;
//
//	@Autowired
//	private LogWf8266Repository logwf8266Repostory;
//
//	public List<LogWf8266> getAll() {
//		return this.logwf8266Repostory.findAll();
//	}
//
//	public Page<LogWf8266> findAll(Pageable pageable) {
//		return this.logwf8266Repostory.findAll(pageable);
//	}
//
//	public Page<LogWf8266> getLineUserAndWf8266Detail(Page<LogWf8266> logWf8266s){
////		List<String> userIds = logWf8266s.stream().map(LogWf8266::getUserId).distinct().collect(Collectors.toList());
////		Map<String,LineUser> mappingLineUser = this.lineUserRepository.getByUserIdIn(userIds).stream().collect(Collectors.toMap(LineUser::getUserId, Function.identity()));
////
////		List<String> wf8266Ids = logWf8266s.stream().map(log -> log.getWf8266Trigger()).distinct().collect(Collectors.toList());
////		Map<String,Wf8266Detail> mappingWf8266Detail = this.wf8266DetailRepository.getByTriggerTextIn(wf8266Ids).stream().collect(Collectors.toMap(Wf8266Detail::getTriggerText, Function.identity()));
//
////		logWf8266s.stream().forEach( entity -> {
////			String userId = entity.getUserId();
////			if(mappingLineUser.containsKey(userId)){
////				entity.setLineUser(mappingLineUser.get(userId));
////			}
////			String triggerText = entity.getWf8266Trigger();
////			if(mappingWf8266Detail.containsKey(triggerText)) {
////				entity.setWf8266Detail(mappingWf8266Detail.get(triggerText));
////			}
////		});
//		return null;
//	}

}
