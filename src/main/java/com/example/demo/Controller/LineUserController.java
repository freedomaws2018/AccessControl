package com.example.demo.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Controller.FormEntity.FormLineUser;
import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Entity.MappingWf8266DetailAndUser;
import com.example.demo.DataBase.Repository.LineUserRepository;
import com.example.demo.DataBase.Repository.MappingWf8266DetailAndUserRepository;
import com.example.demo.DataBase.Repository.Wf8266DetailRepository;
import com.example.demo.DataBase.Service.LineRichMenuService;
import com.example.demo.LineModel.RichMenu.RichMenu;
import com.example.demo.LineModel.RichMenu.RichMenuResponse;

@Controller
@RequestMapping(value = "/line/user")
public class LineUserController {

	private ModelAndView model;

	@Autowired
	private LineUserRepository lineUserRepository;

	@Autowired
	private Wf8266DetailRepository wf8266DetailRepository;

	@Autowired
	private MappingWf8266DetailAndUserRepository mappingWf8266DetailAndUserRepository;

	@Autowired
	private LineRichMenuService lineRichMenuService;

	@GetMapping(value = "/list/{page:[0-9]+}")
	public ModelAndView list(@PathVariable int page) {
		model = new ModelAndView("layout/line/l_line_user");
		Pageable pageable = PageRequest.of(page - 1, 20, Sort.by(Order.asc("createDate")));
		Page<LineUser> users = lineUserRepository.findAll(pageable);
		model.addObject("Users", users);
		return model;
	}

	@GetMapping(value = "/{funcType:view|edit}/{userId}")
	public ModelAndView viewAndEdit(@PathVariable String funcType, @PathVariable String userId) {
		model = new ModelAndView("layout/line/u_line_user");

		List<String> allTriggerTexts = mappingWf8266DetailAndUserRepository.getByUserId(userId).stream()
		    .filter(MappingWf8266DetailAndUser::getIsUse).map(MappingWf8266DetailAndUser::getTriggerText)
		    .collect(Collectors.toList());

		model.addObject("funcType", funcType);
		model.addObject("User", lineUserRepository.findById(userId).orElse(null));
		model.addObject("groupBySnDetails", //
		    wf8266DetailRepository.findAll().stream().map(detail -> {
			    detail.setIsUse(allTriggerTexts.contains(detail.getTriggerText()));
			    return detail;
		    }).collect(Collectors.groupingBy(detail -> detail.getSn())) //
		);

		try {
			String richMenuId = lineRichMenuService.getRichMenuIdLinkToUser(userId);
			RichMenu richMenu = lineRichMenuService.getRichMenu(richMenuId);
			model.addObject("richMenu", richMenu);
		} catch (Exception ex) {
			model.addObject("richMenu", new RichMenu());
		}

		if ("edit".equals(funcType)) {
			RichMenuResponse response = lineRichMenuService.getRichMenuList();
			List<RichMenu> allRichMenu = response.getRichmenus();
			model.addObject("allRichMenu", allRichMenu);

		}

		return model;
	}

	@DeleteMapping(value = "/delete/{lineUserId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> delete(@PathVariable String lineUserId) {
		lineUserRepository.deleteById(lineUserId);

		List<MappingWf8266DetailAndUser> allMapping = mappingWf8266DetailAndUserRepository.getByUserId(lineUserId);
		if (allMapping != null && !allMapping.isEmpty())
			mappingWf8266DetailAndUserRepository.deleteAll(allMapping);

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	/** Redirect **/
	@GetMapping(value = "/list")
	public ModelAndView redirectList() {
		model = new ModelAndView("redirect:/line/user/list/1");
		return model;
	}

	@RequestMapping(value = "/save")
	public ModelAndView save(FormLineUser form, RedirectAttributes attr) {
		model = new ModelAndView(String.format("redirect:/line/user/view/%s", form.getUserId()));

		try {
			LineUser lineUser = lineUserRepository.save(form.toLineUser());
			mappingWf8266DetailAndUserRepository.updateAllIsUseFalseByUserId(form.getUserId());
			mappingWf8266DetailAndUserRepository.saveAll(form.toMappingWf8266DetailAndUser());

			if (StringUtils.isNotBlank(form.getRichMenuId())) {
				lineRichMenuService.linkRichMenuToUser(form.getUserId(), form.getRichMenuId());
			} else {
				lineRichMenuService.unlinkRichMenuToUser(form.getUserId());
			}

			attr.addFlashAttribute("info_status", String.format("%s 的資料異動成功", lineUser.getUserName()));
		} catch (RuntimeException ex) {
			attr.addFlashAttribute("error_status", String.format("資料異動時發生錯誤 : %s", ex.getMessage()));
		}
		return model;
	}

	@GetMapping(value = "/{funcType:[Vv][Ii][Ee][Ww]|[Ee][Dd][Ii][Tt]/{userId}")
	public ModelAndView redirectViewAndEdit(@PathVariable String funcType, @PathVariable String userId) {
		model = new ModelAndView(String.format("redirect:/line/user/%s/%s", funcType.toLowerCase(), userId));
		return model;
	}

}
