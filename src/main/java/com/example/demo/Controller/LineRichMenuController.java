package com.example.demo.Controller;

import java.util.List;
import java.util.stream.Collectors;

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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.Controller.FormEntity.FormRichMenuTemplate;
import com.example.demo.DataBase.Entity.RichMenu;
import com.example.demo.DataBase.Entity.RichMenuTemplate;
import com.example.demo.DataBase.Repository.LineUserRepository;
import com.example.demo.DataBase.Repository.RichMenuRepository;
import com.example.demo.DataBase.Repository.RichMenuTemplateRepository;
import com.example.demo.DataBase.Service.LineRichMenuService;
import com.linecorp.bot.model.richmenu.RichMenuListResponse;
import com.linecorp.bot.model.richmenu.RichMenuResponse;

@Controller
@RequestMapping(value = "/line/rich_menu")
public class LineRichMenuController {

  @Autowired
  private LineUserRepository lineUserRepository;

  @Autowired
  private LineRichMenuService lineRichMenuService;

  @Autowired
  private RichMenuRepository richMenuRepository;

  @Autowired
  private RichMenuTemplateRepository richMenuTemplateRepository;

  @GetMapping(value = "/list")
  public ModelAndView list(ModelAndView model,
      @PageableDefault(page = 0, size = 10, sort = { "createDate" }, direction = Direction.ASC) Pageable pageable) {
    model = new ModelAndView("layout/line/l_rich_menu");
    Page<RichMenu> richMenus = this.richMenuRepository.findAll(pageable);
    model.addObject("richMenus", richMenus);

    return model;
  }

  @DeleteMapping(value = "/delete/{richMenuId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> delete(@PathVariable String richMenuId) {
    this.lineRichMenuService.deleteRichMenu(richMenuId);
    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  @GetMapping("/add")
  public ModelAndView add(ModelAndView model) {
    model = new ModelAndView("layout/line/u_rich_menu");
    model.addObject("funcType", "add");
    List<RichMenuTemplate> templates = richMenuTemplateRepository.findAll();
    model.addObject("templates", templates);
    return model;
  }

  @GetMapping("/edit/{richMenuId}")
  public ModelAndView edit(ModelAndView model, @PathVariable String richMenuId) {
    model = new ModelAndView("layout/line/u_rich_menu");
    model.addObject("funcType", "edit");

    RichMenu richMenu = this.richMenuRepository.findById(richMenuId).orElse(null);
    model.addObject("richMenu", richMenu);

    List<RichMenuTemplate> templates = this.richMenuTemplateRepository.findAll();
    model.addObject("templates", templates);
    return model;
  }

//  @PostMapping("/save")
//  public ModelAndView save(ModelAndView model, @Valid FormRichMenu form, BindingResult result) throws IOException {
//    model = new ModelAndView("redirect:/line/rich_menu/list");
//
//    /** 0. 獲取存檔所需資料 **/
//    // 0.1 確認驗證結果
//    if (result.hasErrors()) {
//      List<FieldError> fieldErrors = result.getFieldErrors();
//      for (FieldError error : fieldErrors) {
//        System.err.printf("%s:%s:%s\n", error.getField(), error.getDefaultMessage(), error.getCode());
//      }
//      return new ModelAndView("/add");
//    }
//
//    // 0.2 獲取所有 form 表單的訊息
//    String name = form.getName();
////		String chatBarText = form.getChatBarText();
//    String oldRichMenuId = form.getOldRichMenuId();
//    Long templateId = form.getTemplateId();
//    MultipartFile image = form.getImage();
//    List<FormRichMenuAction> actions = form.getActions();
//
//    // 0.2 透過 TemplateId 取出樣式
//    RichMenuTemplate template = this.richMenuTemplateRepository.findById(templateId).orElse(null);
//    String templateJson = template.getTemplateJson();
//
//    /** 1. 發送資料至 LINE Service 建立 RichMenu **/
//    // 1.1 寫入名稱
//    templateJson = templateJson.replace(":name", name);
//    templateJson = templateJson.replace(":chatBarText", name);
//
//    // 1.2 寫入按鈕功能
//    for (int i = 0; i < actions.size(); i++) {
//      FormRichMenuAction action = actions.get(i);
//      String type = action.getType() != null ? action.getType() : "";
//      String data = action.getData() != null ? action.getData() : "";
//      String text = action.getText() != null ? action.getText() : "";
//      switch (type) {
//      case "change_view":
//        templateJson = templateJson.replace(String.format(":data%02d", i + 1), "#>" + data);
//        templateJson = templateJson.replace(String.format(":text%02d", i + 1), text);
//        break;
//      case "trigger_iot":
//        templateJson = templateJson.replace(String.format(":data%02d", i + 1), "##" + data);
//        templateJson = templateJson.replace(String.format(":text%02d", i + 1), text);
//        break;
//      default:
//        templateJson = templateJson.replace(String.format(":data%02d", i + 1), "");
//        templateJson = templateJson.replace(String.format(":text%02d", i + 1), "");
//      }
//    }
//
//    RichMenu richMenu = new Gson().fromJson(templateJson, RichMenu.class);
//    List<LineRichMenuArea> richMenuAreas = richMenu.getAreas().stream()
//        .filter(area -> StringUtils.isNotBlank(area.getAction().getData())).collect(Collectors.toList());
//    richMenu.setAreas(richMenuAreas);
//    templateJson = new Gson().toJson(richMenu);
//
//    // 1.3 建立新的選單
//    String richMenuId = this.lineRichMenuService.createRichMenu(templateJson);
//    // 1.4 上傳新選單圖片
//    this.lineRichMenuService.uploadRichMenuImage(richMenuId, image.getBytes());
//
//    // 2. 存入 RichMenu 資料表
//    richMenu.setRichMenuId(richMenuId);
//    richMenu.setImage(this.lineRichMenuService.downloadImage(richMenu.getRichMenuId()));
//    richMenu = this.richMenuRepository.save(richMenu);
//
//    // 3. 是否有舊版型
//    if (StringUtils.isNotBlank(oldRichMenuId)) {
//      // 3.1 刪除 已被覆蓋的模型
//      this.lineRichMenuService.deleteRichMenu(oldRichMenuId);
//      // 3.2 重新推送 更新後的版型給 當前使用中的所有人 (Thread)
//      new Thread(() -> {
//        List<LineUser> users = LineRichMenuController.this.lineUserRepository.findAll();
//        users.stream().forEach(user -> {
//          String userRichMenuId = LineRichMenuController.this.lineRichMenuService
//              .getRichMenuIdLinkToUser(user.getUserId());
//          if (userRichMenuId.equals(oldRichMenuId)) {
//            LineRichMenuController.this.lineRichMenuService.linkRichMenuToUser(user.getUserId(), richMenuId);
//          }
//        });
//      }).start();
//    }
//
//    return model;
//  }

  /** Tempalte **/
  @GetMapping(value = "/template/list")
  public ModelAndView templateList(ModelAndView model,
      @PageableDefault(page = 0, size = 10, sort = { "createDate" }, direction = Direction.ASC) Pageable pageable) {
    model = new ModelAndView("layout/line/l_rich_menu_template");
    Page<RichMenuTemplate> richMenuTemplates = richMenuTemplateRepository.findAll(pageable);
    model.addObject("templateList", richMenuTemplates);

    return model;
  }

  @GetMapping("/template/add")
  public ModelAndView tempalteAdd(ModelAndView model) {
    model = new ModelAndView("layout/line/u_rich_menu_template");
    model.addObject("funcType", "add");

    return model;
  }

  @GetMapping("/template/{funcType:view|edit}/{id}")
  public ModelAndView tempalteAdd(ModelAndView model, @PathVariable String funcType, @PathVariable("id") Long id) {
    model = new ModelAndView("layout/line/u_rich_menu_template");
    model.addObject("funcType", funcType);
    RichMenuTemplate rmt = richMenuTemplateRepository.findById(id).orElse(null);
    model.addObject("rmt", rmt);
    return model;
  }

  @PostMapping("/template/save")
  public ModelAndView templateSave(ModelAndView model, FormRichMenuTemplate form) {
    model = new ModelAndView("redirect:/line/rich_menu/template/list");
    RichMenuTemplate template = form.toRichMenuTemplate();
    this.richMenuTemplateRepository.save(template);
    return model;
  }

  @DeleteMapping("/template/delete/{id}")
  public ResponseEntity<Object> templateDelete(@PathVariable Long id) {
    this.richMenuTemplateRepository.deleteById(id);
    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  /** Redirect **/

  /** ajax **/
  /** getRichMenuList & downloadImage 寫入資料庫 **/
  @PostMapping(value = "/ajax/getRichMenuDataOnLineServer", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> ajaxGetRichMenuDataOnLineServer() {
    RichMenuListResponse response = lineRichMenuService.getRichMenuList();
    List<RichMenuResponse> richMenuResponses = response.getRichMenus();
    List<RichMenu> richMenus = richMenuResponses.stream().map(richMenu -> {
      RichMenu menu = new RichMenu();
      menu.setRichMenuId(richMenu.getRichMenuId());
      menu.setRichMenuResponse(richMenu);
      menu.setImage(lineRichMenuService.downloadImage(richMenu.getRichMenuId()));
      return menu;
    }).collect(Collectors.toList());
    lineRichMenuService.save(richMenus);

    return new ResponseEntity<>(richMenus, HttpStatus.OK);
  }

//  /** autocomplete **/
//  @GetMapping(value = "/autocomplete/getRichMenu", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//  public ResponseEntity<Object> getRichMenuList(@RequestParam("term") String term) {
//    List<RichMenu> richMenus = this.richMenuRepository.getByNameLike(term);
//    return new ResponseEntity<>(richMenus, HttpStatus.OK);
//  }

}
