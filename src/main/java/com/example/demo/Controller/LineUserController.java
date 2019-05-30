package com.example.demo.Controller;

import java.util.List;
import java.util.concurrent.ExecutionException;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Controller.FormEntity.FormLineUser;
import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Entity.Mapping.MappingLineuserWf8266;
import com.example.demo.DataBase.Repository.LineUserRepository;
import com.example.demo.DataBase.Repository.MappingLineuserWf8266Repository;
import com.example.demo.DataBase.Repository.Wf8266Repository;
import com.example.demo.DataBase.Service.LineRichMenuService;
import com.linecorp.bot.model.richmenu.RichMenuListResponse;
import com.linecorp.bot.model.richmenu.RichMenuResponse;

@Controller
@RequestMapping(value = "/line/user")
public class LineUserController {

  @Autowired
  private LineUserRepository lineUserRepository;

  @Autowired
  private Wf8266Repository wf8266Repository;

  @Autowired
  private MappingLineuserWf8266Repository mappingLineuserWf8266Repository;

  @Autowired
  private LineRichMenuService lineRichMenuService;

  @GetMapping(value = "/list")
  public ModelAndView list(ModelAndView model,
      @PageableDefault(page = 0, size = 10, sort = { "createDate" }, direction = Direction.ASC) Pageable pageable) {
    model = new ModelAndView("layout/line/l_line_user");
    Page<LineUser> users = this.lineUserRepository.findAll(pageable);
    model.addObject("users", users);
    return model;
  }

  @GetMapping(value = "/add")
  public ModelAndView add(ModelAndView model) {
    model = new ModelAndView("layout/line/u_line_user");
    model.addObject("funcType", "add");
    return model;
  }

  @GetMapping(value = "/{funcType:view|edit}/{userId}")
  public ModelAndView viewAndEdit(ModelAndView model, RedirectAttributes attr, @PathVariable String funcType,
      @PathVariable String userId) throws InterruptedException, ExecutionException {
    model = new ModelAndView("layout/line/u_line_user");

    List<String> allTriggerTexts = mappingLineuserWf8266Repository.getByLineUserId(userId).stream()
        .filter(MappingLineuserWf8266::getIsUse).map(MappingLineuserWf8266::getWf8266Id).collect(Collectors.toList());

    LineUser user = this.lineUserRepository.findById(userId).orElse(null);
    if (user == null) {
      this.lineUserRepository.findById(userId).orElse(null);
    }

    model.addObject("funcType", funcType);
    model.addObject("user", user);

//    /** 詢問當前使用者對應的頁面 **/
//    RichMenuIdResponse richMenuIdResponse = lineRichMenuService.getRichMenuIdLinkToUser(userId);
//    RichMenuResponse richMenu = lineRichMenuService.getRichMenu(richMenuIdResponse.getRichMenuId());
//    model.addObject("richMenu", richMenu);

    /** 修改時 取得所有選單 **/
    RichMenuListResponse response = lineRichMenuService.getRichMenuList();
    List<RichMenuResponse> RichMenuResponses = response.getRichMenus();
    model.addObject("allRichMenu", RichMenuResponses);

    return model;
  }

  @DeleteMapping(value = "/delete/{lineUserId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> delete(@PathVariable String lineUserId) {
    this.lineUserRepository.deleteById(lineUserId);

    List<MappingLineuserWf8266> allMapping = mappingLineuserWf8266Repository.getByLineUserId(lineUserId);
    if (allMapping != null && !allMapping.isEmpty()) {
      mappingLineuserWf8266Repository.deleteAll(allMapping);
    }

    return new ResponseEntity<>(null, HttpStatus.OK);
  }

  /** Redirect **/
  @RequestMapping(value = "/save")
  public ModelAndView save(ModelAndView model, RedirectAttributes attr, FormLineUser form, BindingResult result) {

    String lineUserId = form.getUserId();
    try {
      LineUser lineUser = lineUserRepository.save(form.toLineUser());
//			this.mappingWf8266AndLineUserRepository.updateAllIsUseFalseByLineUserId(form.getUserId());
//			this.mappingWf8266AndLineUserRepository.saveAll(form.toMappingWf8266DetailAndUser());

      if (StringUtils.isNotBlank(form.getRichMenuId())) {
        this.lineRichMenuService.linkRichMenuToUser(form.getUserId(), form.getRichMenuId());
      } else {
        this.lineRichMenuService.unlinkRichMenuToUser(form.getUserId());
      }

      attr.addFlashAttribute("info_status", String.format("%s 的資料異動成功", lineUser.getUserName()));
      return new ModelAndView(String.format("redirect:/line/user/view/%s", lineUserId));
    } catch (Exception ex) {
      attr.addFlashAttribute("error_status", String.format("資料異動時發生錯誤 : %s", ex.getMessage()));
      return new ModelAndView(String.format("redirect:/line/user/edit/%s", lineUserId));
    }
  }

  /** autocomplete **/
  @PostMapping(value = "/autocomplete/getAll", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  private ResponseEntity<Object> getAll(String term) {
    List<LineUser> lineUsers = lineUserRepository.getByUserNameLike("%" + term + "%");
    return new ResponseEntity<>(lineUsers, HttpStatus.OK);
  }

}
