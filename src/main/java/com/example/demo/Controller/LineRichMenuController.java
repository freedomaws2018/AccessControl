package com.example.demo.Controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Controller.FormEntity.FormRichMenu;
import com.example.demo.Controller.FormEntity.FormRichMenuAction;
import com.example.demo.Controller.FormEntity.FormRichMenuTemplate;
import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Entity.RichMenu;
import com.example.demo.DataBase.Entity.RichMenuTemplate;
import com.example.demo.DataBase.Repository.LineUserRepository;
import com.example.demo.DataBase.Repository.RichMenuRepository;
import com.example.demo.DataBase.Repository.RichMenuTemplateRepository;
import com.example.demo.DataBase.Service.LineRichMenuService;
import com.example.demo.LineModel.RichMenu.RichMenuArea;
import com.example.demo.LineModel.RichMenu.RichMenuResponse;
import com.google.gson.Gson;

@Controller
@RequestMapping(value = "/line/rich_menu")
public class LineRichMenuController {

	private ModelAndView model;

	@Autowired
	private LineUserRepository lineUserRepository;

	@Autowired
	private LineRichMenuService lineRichMenuService;

	@Autowired
	private RichMenuRepository richMenuRepository;

	@Autowired
	private RichMenuTemplateRepository richMenuTemplateRepository;

//	@Autowired
//	private MappingRichMenuRepository mappingRichMenuRepository;

	@GetMapping(value = "/list/{page}")
	public ModelAndView list(@PathVariable int page) throws Exception {
		model = new ModelAndView("layout/line/l_rich_menu");
		Pageable pageable = PageRequest.of(page - 1, 20, Sort.by(Order.asc("createDate")));
		Page<RichMenu> richMenus = richMenuRepository.findAll(pageable);
		model.addObject("richMenus", richMenus);

		return model;
	}

	@DeleteMapping(value = "/delete/{richMenuId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> delete(@PathVariable String richMenuId) {
		lineRichMenuService.deleteRichMenu(richMenuId);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	@GetMapping("/add")
	public ModelAndView add() {
		model = new ModelAndView("layout/line/u_rich_menu");
		model.addObject("funcType", "add");
		List<RichMenuTemplate> templates = richMenuTemplateRepository.findAll();
		model.addObject("templates", templates);
		return model;
	}

	@GetMapping("/edit/{richMenuId}")
	public ModelAndView edit(@PathVariable String richMenuId) {
		model = new ModelAndView("layout/line/u_rich_menu");
		model.addObject("funcType", "edit");

		RichMenu richMenu = richMenuRepository.findById(richMenuId).orElse(null);
		model.addObject("richMenu", richMenu);

		List<RichMenuTemplate> templates = richMenuTemplateRepository.findAll();
		model.addObject("templates", templates);
		return model;
	}

	@PostMapping("/save")
	public ModelAndView save(@Valid FormRichMenu form, BindingResult result) throws IOException {
		model = new ModelAndView("redirect:/line/rich_menu/list/1");

		/** 0. 獲取存檔所需資料 **/
		// 0.1 確認驗證結果
		if (result.hasErrors()) {
			List<FieldError> fieldErrors = result.getFieldErrors();
			for (FieldError error : fieldErrors) {
				System.err.printf("%s:%s:%s\n", error.getField(), error.getDefaultMessage(), error.getCode());
			}
			return new ModelAndView("/add");
		}

		// 0.2 獲取所有 form 表單的訊息
		String name = form.getName();
//		String chatBarText = form.getChatBarText();
		String oldRichMenuId = form.getOldRichMenuId();
		Long templateId = form.getTemplateId();
		MultipartFile image = form.getImage();
		List<FormRichMenuAction> actions = form.getActions();

		// 0.2 透過 TemplateId 取出樣式
		RichMenuTemplate template = richMenuTemplateRepository.findById(templateId).orElse(null);
		String templateJson = template.getTemplate();

		/** 1. 發送資料至 LINE Service 建立 RichMenu **/
		// 1.1 寫入名稱
		templateJson = templateJson.replace(":name", name);
		templateJson = templateJson.replace(":chatBarText", name);

		// 1.2 寫入按鈕功能
		for (int i = 0; i < actions.size(); i++) {
			FormRichMenuAction action = actions.get(i);
			String type = action.getType() != null ? action.getType() : "";
			String data = action.getData() != null ? action.getData() : "";
			String text = action.getText() != null ? action.getText() : "";
			switch (type) {
				case "change_view":
					templateJson = templateJson.replace(String.format(":data%02d", i + 1), "#>" + data);
					templateJson = templateJson.replace(String.format(":text%02d", i + 1), text);
					break;
				case "trigger_iot":
					templateJson = templateJson.replace(String.format(":data%02d", i + 1), "##" + data);
					templateJson = templateJson.replace(String.format(":text%02d", i + 1), text);
					break;
				default:
					templateJson = templateJson.replace(String.format(":data%02d", i + 1), "");
					templateJson = templateJson.replace(String.format(":text%02d", i + 1), "");
			}
		}

		RichMenu richMenu = new Gson().fromJson(templateJson, RichMenu.class);
		List<RichMenuArea> richMenuAreas = richMenu.getAreas().stream()
		    .filter(area -> StringUtils.isNotBlank(area.getAction().getData())).collect(Collectors.toList());
		richMenu.setAreas(richMenuAreas);
		templateJson = new Gson().toJson(richMenu);

		// 1.3 建立新的選單
		String richMenuId = lineRichMenuService.createRichMenu(templateJson);
		// 1.4 上傳新選單圖片
		lineRichMenuService.uploadRichMenuImage(richMenuId, image.getBytes());

		// 2. 存入 RichMenu 資料表
		richMenu.setRichMenuId(richMenuId);
		richMenu.setImage(lineRichMenuService.downloadImage(richMenu.getRichMenuId()));
		richMenu = richMenuRepository.save(richMenu);

		// 3. 是否有舊版型
		if (StringUtils.isNotBlank(oldRichMenuId)) {
			// 3.1 刪除 已被覆蓋的模型
			lineRichMenuService.deleteRichMenu(oldRichMenuId);
			// 3.2 重新推送 更新後的版型給 當前使用中的所有人 (Thread)
			new Thread(new Runnable() {
				@Override
				public void run() {
					List<LineUser> users = lineUserRepository.findAll();
					users.stream().forEach(user -> {
						String userRichMenuId = lineRichMenuService.getRichMenuIdLinkToUser(user.getUserId());
						if (userRichMenuId.equals(oldRichMenuId)) {
							lineRichMenuService.linkRichMenuToUser(user.getUserId(), richMenuId);
						}
					});
				}
			}).start();
		}

		return model;
	}

	/** Tempalte **/
	@GetMapping(value = "/template/list/{page}")
	public ModelAndView templateList(@PathVariable int page) throws Exception {
		model = new ModelAndView("layout/line/l_rich_menu_template");
		Pageable pageable = PageRequest.of(page - 1, 20, Sort.by(Order.asc("createDate")));
		Page<RichMenuTemplate> richMenuTemplates = richMenuTemplateRepository.findAll(pageable);
		model.addObject("templateList", richMenuTemplates);

		return model;
	}

	@GetMapping("/template/add")
	public ModelAndView tempalteAdd() {
		model = new ModelAndView("layout/line/u_rich_menu_template");
		model.addObject("funcType", "add");

		return model;
	}

	@PostMapping("/template/save")
	public ModelAndView templateSave(FormRichMenuTemplate form) {
		model = new ModelAndView("redirect:/line/rich_menu/template/list/1");
		RichMenuTemplate template = form.toRichMenuTemplate();
		richMenuTemplateRepository.save(template);
		return model;
	}

	@DeleteMapping("/template/delete/{id}")
	public ResponseEntity<Object> templateDelete(@PathVariable Long id) {
		richMenuTemplateRepository.deleteById(id);
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	/** Redirect **/
	@GetMapping("/template/list")
	public ModelAndView redirectTemplateList() {
		model = new ModelAndView("redirect:/line/rich_menu/template/list/1");
		return model;
	}

	@GetMapping("/list")
	public ModelAndView redirectList() {
		model = new ModelAndView("redirect:/line/rich_menu/list/1");
		return model;
	}

	/** ajax **/
	/** getRichMenuList & downloadImage 寫入資料庫 **/
	@PostMapping(value = "/ajax/getRichMenuDataOnLineServer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> ajaxGetRichMenuDataOnLineServer() {
		RichMenuResponse response1 = lineRichMenuService.getRichMenuList();
		List<RichMenu> richMenus = response1.getRichmenus().stream().map(rm -> {
			RichMenu richMenu = new RichMenu(rm);
			richMenu.setImage(lineRichMenuService.downloadImage(richMenu.getRichMenuId()));
			return richMenu;
		}).collect(Collectors.toList());
		richMenus = richMenuRepository.saveAll(richMenus);
		return new ResponseEntity<>(richMenus, HttpStatus.OK);
	}

	/** autocomplete **/
	@GetMapping(value = "/autocomplete/getRichMenu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> getRichMenuList(@RequestParam("term") String term) {
		List<RichMenu> richMenus = richMenuRepository.getByNameLike(term);
//		List<String> rmNames = richMenus.stream().map(RichMenu::getName).collect(Collectors.toList());
		return new ResponseEntity<>(richMenus, HttpStatus.OK);
	}

}
