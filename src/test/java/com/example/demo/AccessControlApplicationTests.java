package com.example.demo;

//@RunWith(SpringRunner.class)
//@SpringBootTest
public class AccessControlApplicationTests {

//	@Autowired
//	private LineUserRepository userRepository;
//
//	@Autowired
//	private Wf8266Repository wf8266Repository;
//
//	@Autowired
//	private Wf8266DetailRepository wf8266DetailRepository;
//
//	@Autowired
//	private MappingWf8266DetailAndUserRepository mappingWf8266DetailAndUserRepository;
//
//	@Test
//	public void testAddUser() {
//		LineUser lineUser = new LineUser();
//		lineUser.setUserId("U8072500ba156381f46d7422f254d8cb5");
//		lineUser.setUserName("張軒豪");
//		lineUser.setIsUse(true);
//		lineUser.setBegDt(LocalDateTime.now());
//		lineUser.setEndDt(LocalDateTime.now().plusYears(1));
//		lineUser = userRepository.saveAndFlush(lineUser);
//	}
//
//	@Test
//	public void testAddUser2() {
//		String[] userId = { "U5583a84d61b413e68f088dd10e97bb56", "U404f06e683f83748356794a9d837d9fd",
//		    "U81e5daaa1df5578b6787b7c7914a43a5", "Ufa1582570732df3f7a6be055184f3f0b", "U8072500ba156381f46d7422f254d8cb5",
//		    "U51ab6d969d7266e3986b992d047b927a" };
//		String[] userName = { "莊竣逸", "Fran", "茜茜Eve", "蚊子", "張軒豪", "Alex MJ Hsieh" };
//
//		for (int i = 0; i < userId.length; i++) {
//			LineUser lineUser = new LineUser();
//			lineUser.setUserId(userId[i]);
//			lineUser.setUserName(userName[i]);
//			lineUser.setIsUse(true);
//			lineUser.setBegDt(LocalDateTime.now());
//			lineUser.setEndDt(LocalDateTime.now().plusYears(1));
//			lineUser = userRepository.saveAndFlush(lineUser);
//		}
//	}
//
//	@Test
//	public void testAddWf82661() {
//		String SN = "15738184";
//		String CMD = "GPIO";
//		String KEY = "xoeExDUjSkPG2GMBDCAqfgPYl8v2";
//		String[] DATA1 = { "12", "4", "13", "5", "5" };
//		String[] DATA2 = { "4", "4", "4", "0", "1" };
//		String[] Names = { "229 一樓大門", "229 2F-3號門", "229-A室開門", "229-A室開燈", "229-A室關燈" };
//		String[] triggerTexts = { "229_1F", "229_2F_3", "229_2F_3_A_D0", "229_2F_3_A_LO0", "229_2F_3_A_LC0" };
//		String[] replys = { "229一樓大門開啟", "229二樓大門開啟", "229-A門禁開啟", "229-A開燈", "229-A關燈" };
//
//		Wf8266 wf8266 = new Wf8266();
//		wf8266.setSn(SN);
//		wf8266.setKey(KEY);
//		wf8266.setIsUse(true);
//		wf8266.setIsMessageEvent(true);
//		wf8266.setIsPostbackEvent(true);
//
//		List<Wf8266Detail> details = new ArrayList<>();
//		for (int i = 0; i < Names.length; i++) {
//			Wf8266Detail detail = new Wf8266Detail();
//			detail.setSn(SN);
//			detail.setCmd(CMD);
//			detail.setData1(DATA1[i]);
//			detail.setData2(DATA2[i]);
//			detail.setName(Names[i]);
//			detail.setTriggerText(triggerTexts[i]);
//			detail.setReply(replys[i]);
//			detail.setIsUse(true);
//			details.add(detail);
//		}
//
//		wf8266Repository.saveAndFlush(wf8266);
//		wf8266DetailRepository.saveAll(details);
//	}
//
//	@Test
//	public void testAddWf82662() {
//		String SN = "13716528";
//		String CMD = "GPIO";
//		String KEY = "xoeExDUjSkPG2GMBDCAqfgPYl8v2";
//		String[] DATA1 = "3,4,5,12,13,14,15,16".split(",");
//		String[] DATA2 = "2,2,2,2,2,2,2,2,2".split(",");
//
//		Wf8266 wf8266 = new Wf8266();
//		wf8266.setSn(SN);
//		wf8266.setKey(KEY);
//		wf8266.setIsUse(true);
//		wf8266.setIsMessageEvent(true);
//		wf8266.setIsPostbackEvent(true);
//
//		List<Wf8266Detail> details = new ArrayList<>();
//		for (int i = 0; i < DATA1.length; i++) {
//			Wf8266Detail detail = new Wf8266Detail();
//			detail.setSn(SN);
//			detail.setCmd(CMD);
//			detail.setData1(DATA1[i]);
//			detail.setData2(DATA2[i]);
//			detail.setName(String.format("Test%02d", i));
//			detail.setTriggerText(String.format("Test%02d", i));
//			detail.setReply(String.format("觸發%02d", i));
//			detail.setIsUse(true);
//			details.add(detail);
//		}
//
//		wf8266Repository.saveAndFlush(wf8266);
//		wf8266DetailRepository.saveAll(details);
//	}
//
//	@Test
//	public void testAddOneUserAllPermission() {
//		LineUser lineUser = userRepository.getOne("U8072500ba156381f46d7422f254d8cb5");
//		List<Wf8266Detail> wf8266Details = wf8266DetailRepository.findAll(Sort.by(Order.asc("createDate")));
//		for (Wf8266Detail wf8266Detail : wf8266Details) {
//			MappingWf8266DetailAndUser mappingWdU = new MappingWf8266DetailAndUser();
//			mappingWdU.setUserId(lineUser.getUserId());
//			mappingWdU.setTriggerText(wf8266Detail.getTriggerText());
//			mappingWdU.setIsUse(true);
//			mappingWf8266DetailAndUserRepository.save(mappingWdU);
//		}
//	}

}
