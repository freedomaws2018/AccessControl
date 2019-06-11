package com.example.demo.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

import com.example.demo.Controller.FormEntity.FormMember;
import com.example.demo.DataBase.Entity.LineUser;
import com.example.demo.DataBase.Entity.Member;
import com.example.demo.DataBase.Service.LineUserService;
import com.example.demo.DataBase.Service.MemberService;

@Controller
@RequestMapping(value = "/member")
public class MemberController {

  @Autowired
  private MemberService memberService;

  @Autowired
  private LineUserService lineUserService;

  @GetMapping(value = "/list")
  private ModelAndView list(ModelAndView model,
      @PageableDefault(page = 0, size = 10, sort = { "id" }) Pageable pageable) {
    model = new ModelAndView("layout/member/l_member");
    Page<Member> members = memberService.getAll(pageable);
    model.addObject("members", members);
    return model;
  }

  @GetMapping(value = "/add")
  public ModelAndView add(ModelAndView model) {
    model = new ModelAndView("layout/member/u_member");
    model.addObject("funcType", "add");
    List<LineUser> lineUsers = lineUserService.getAll();
    model.addObject("lineusers", lineUsers);
    return model;
  }

  @GetMapping(value = "/{funcType:view|edit}/{memberId}")
  public ModelAndView viewAndEdit(ModelAndView model, @PathVariable String funcType, @PathVariable Long memberId) {
    model = new ModelAndView("layout/member/u_member");
    model.addObject("funcType", funcType);
    Member member = memberService.getById(memberId);
    model.addObject("member", member);
    List<LineUser> lineUsers = lineUserService.getAll();
    model.addObject("lineusers", lineUsers);

    return model;
  }

  @PostMapping(value = "/save", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> save(FormMember form) {
    Map<String, Object> result = new HashMap<>();

    Member member = memberService.getById(form.getId());
    member = form.toMember(member);
    member = memberService.save(member);

    result.put("status", "success");
    result.put("data", member);
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

  @DeleteMapping(value = "/delete/{memberId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
  public ResponseEntity<Object> delete(@PathVariable Long memberId) {
    Map<String, Object> result = new HashMap<>();
    memberService.deleteById(memberId);
    result.put("status", "success");
    return new ResponseEntity<>(result, HttpStatus.OK);
  }

}
