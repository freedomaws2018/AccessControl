package com.example.demo.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import com.example.demo.DataBase.Entity.Mapping.MappingWf8266AndLineUser;
import com.example.demo.DataBase.Repository.LineUserRepository;
import com.example.demo.DataBase.Repository.MappingWf8266AndLineUserRepository;
import com.example.demo.DataBase.Repository.Wf8266Repository;
import com.example.demo.DataBase.Service.LineRichMenuService;
import com.example.demo.LineModel.RichMenu.LineRichMenu;
import com.example.demo.LineModel.RichMenu.LineRichMenuResponse;

@Controller
@RequestMapping(value = "/line/user")
public class LineUserController {

	@Autowired
	private LineUserRepository lineUserRepository;

	@Autowired
	private Wf8266Repository wf8266Repository;

	@Autowired
	private MappingWf8266AndLineUserRepository mappingWf8266AndLineUserRepository;

	@Autowired
	private LineRichMenuService lineRichMenuService;

	@GetMapping(value = "/list")
	public ModelAndView list(ModelAndView model, @PageableDefault(page = 0, size = 10, sort = {
			"createDate" }, direction = Direction.ASC) Pageable pageable) {
		model = new ModelAndView("layout/line/l_line_user");
		Page<LineUser> users = this.lineUserRepository.findAll(pageable);
		model.addObject("Users", users);
		return model;
	}

	@GetMapping(value = "/{funcType:view|edit}/{userId}")
	public ModelAndView viewAndEdit(ModelAndView model, @PathVariable String funcType, @PathVariable String userId) {
		model = new ModelAndView("layout/line/u_line_user");

		List<String> allTriggerTexts = this.mappingWf8266AndLineUserRepository.getByLineUserId(userId).stream()
				.filter(MappingWf8266AndLineUser::getIsUse).map(MappingWf8266AndLineUser::getWf8266Id)
				.collect(Collectors.toList());

		model.addObject("funcType", funcType);
		model.addObject("User", this.lineUserRepository.findById(userId).orElse(null));
		model.addObject("groupBySnDetails", //
				this.wf8266Repository.findAll().stream().map(detail -> {
					detail.setIsUse(allTriggerTexts.contains(detail.getTriggerText()));
					return detail;
				}).collect(Collectors.groupingBy(detail -> detail.getSn())) //
		);

		try {
			String richMenuId = this.lineRichMenuService.getRichMenuIdLinkToUser(userId);
			LineRichMenu richMenu = this.lineRichMenuService.getRichMenu(richMenuId);
			model.addObject("richMenu", richMenu);
		} catch (Exception ex) {
			model.addObject("richMenu", new LineRichMenu());
		}

		if ("edit".equals(funcType)) {
			LineRichMenuResponse response = this.lineRichMenuService.getRichMenuList();
			List<LineRichMenu> allRichMenu = response.getRichmenus();
			model.addObject("allRichMenu", allRichMenu);

		}

		return model;
	}

	@DeleteMapping(value = "/delete/{lineUserId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> delete(@PathVariable String lineUserId) {
		this.lineUserRepository.deleteById(lineUserId);

		List<MappingWf8266AndLineUser> allMapping = this.mappingWf8266AndLineUserRepository.getByLineUserId(lineUserId);
		if (allMapping != null && !allMapping.isEmpty()) {
			this.mappingWf8266AndLineUserRepository.deleteAll(allMapping);
		}

		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	/** Redirect **/

	@RequestMapping(value = "/save")
	public ModelAndView save(ModelAndView model, FormLineUser form, RedirectAttributes attr) {
		model = new ModelAndView(String.format("redirect:/line/user/view/%s", form.getUserId()));

		try {
			LineUser lineUser = this.lineUserRepository.save(form.toLineUser());
			this.mappingWf8266AndLineUserRepository.updateAllIsUseFalseByLineUserId(form.getUserId());
			this.mappingWf8266AndLineUserRepository.saveAll(form.toMappingWf8266DetailAndUser());

			if (StringUtils.isNotBlank(form.getRichMenuId())) {
				this.lineRichMenuService.linkRichMenuToUser(form.getUserId(), form.getRichMenuId());
			} else {
				this.lineRichMenuService.unlinkRichMenuToUser(form.getUserId());
			}

			attr.addFlashAttribute("info_status", String.format("%s 的資料異動成功", lineUser.getUserName()));
		} catch (RuntimeException ex) {
			attr.addFlashAttribute("error_status", String.format("資料異動時發生錯誤 : %s", ex.getMessage()));
		}
		return model;
	}

}
