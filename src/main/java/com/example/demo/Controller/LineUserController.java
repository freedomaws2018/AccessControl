package com.example.demo.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
    model.addObject("funcType", funcType);

    LineUser user = lineUserRepository.findById(userId).orElse(null);

    model.addObject("user", user);
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
  @RequestMapping(value = "/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> save(FormLineUser form, BindingResult bindingResult) {
    Map<String, Object> result = new HashMap<>();
    LineUser lineUser = lineUserRepository.save(form.toLineUser());
    result.put("status", "success");
    result.put("data", lineUser);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  /** autocomplete **/
  @PostMapping(value = "/autocomplete/getAll", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  private ResponseEntity<Object> getAll(String term) {
    List<LineUser> lineUsers = lineUserRepository.getByUserNameLike("%" + term + "%");
    return new ResponseEntity<>(lineUsers, HttpStatus.OK);
  }

}
