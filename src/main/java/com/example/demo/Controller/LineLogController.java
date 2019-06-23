package com.example.demo.Controller;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.DataBase.Entity.Location;
import com.example.demo.DataBase.Entity.Member;
import com.example.demo.DataBase.Entity.Log.LogIot;
import com.example.demo.DataBase.Repository.LogIotRepository;
import com.example.demo.DataBase.Service.LocationService;
import com.example.demo.DataBase.Service.MemberService;

@Controller
@RequestMapping(value = "/line/log")
public class LineLogController {

  @Autowired
  private LogIotRepository logIotRepository;

  @Autowired
  private MemberService memberService;

  @Autowired
  private LocationService locationService;

  @GetMapping(value = "/list")
  public ModelAndView list(ModelAndView model) {
    model = new ModelAndView("layout/line/l_line_log");
    List<LogIot> logIots = logIotRepository.findAllInThreeMonth();
    model.addObject("logIots", logIots);

    List<Long> memberIds = logIots.stream().map(LogIot::getMemberId).distinct().collect(Collectors.toList());
    Map<Long, Member> mapMember = memberService.getByIds(memberIds).stream()
        .collect(Collectors.toMap(Member::getId, Function.identity()));
    model.addObject("mapMember", mapMember);

    List<Long> locationIds = logIots.stream().map(LogIot::getLocationId).distinct().collect(Collectors.toList());
    Map<Long, Location> mapLocation = locationService.getByIds(locationIds).stream()
        .collect(Collectors.toMap(Location::getId, Function.identity()));
    model.addObject("mapLocation", mapLocation);

    return model;
  }

}
