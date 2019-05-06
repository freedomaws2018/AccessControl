package com.example.demo.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Controller.FormEntity.FormWf8266;
import com.example.demo.DataBase.Entity.Wf8266;
import com.example.demo.DataBase.Entity.Wf8266Detail;
import com.example.demo.DataBase.Repository.Wf8266DetailRepository;
import com.example.demo.DataBase.Repository.Wf8266Repository;

@Controller
@RequestMapping(value = "/wf8266")
public class Wf8266Controller {

	private ModelAndView model;

	@Autowired
	private Wf8266Repository wf8266Repository;

	@Autowired
	private Wf8266DetailRepository wf8266DetailRepository;

	@GetMapping("/list/{page:[0-9]+}")
	public ModelAndView list(@PathVariable int page) {
		model = new ModelAndView("layout/wf8266/l_wf8266");
		Pageable pageable = PageRequest.of(page - 1, 20, Sort.by(Order.asc("createDate")));
		Page<Wf8266> wf8266s = wf8266Repository.findAll(pageable);

		model.addObject("Wf8266s", wf8266s);
		return model;
	}

	@GetMapping("/add")
	public ModelAndView add() {
		model = new ModelAndView("layout/wf8266/u_wf8266");

		model.addObject("wf8266", new Wf8266());
		model.addObject("wf8266Details", null);
		model.addObject("funcType", "add");
		return model;
	}

	@GetMapping("/{funcType:add|view|edit}/{sn}")
	public ModelAndView viewAndEdit(@PathVariable String funcType, @PathVariable String sn) {
		model = new ModelAndView("layout/wf8266/u_wf8266");
		Wf8266 wf8266 = wf8266Repository.getBySn(sn).orElse(null);
		List<Wf8266Detail> details = wf8266DetailRepository.getBySn(sn, Sort.by(Direction.ASC, "triggerText"));

		model.addObject("wf8266", wf8266);
		model.addObject("wf8266Details", details);
		model.addObject("funcType", funcType);
		return model;
	}

	/** Redirect **/
	@GetMapping("/list")
	public ModelAndView redirectList() {
		return new ModelAndView("redirect:/wf8266/list/1");
	}

	@RequestMapping("/save")
	public ModelAndView save(FormWf8266 wf8266, RedirectAttributes attr) {
		model = new ModelAndView(String.format("redirect:/wf8266/view/%s", wf8266.getSn()));
		// Wf8266
		wf8266Repository.save(wf8266.toWf8266());
		// 刪除所有 Detail

		// 刪除的
		List<String> DDetailId = wf8266.toWf8266DetailsDelete();
		if (DDetailId != null && !DDetailId.isEmpty()) {
			DDetailId.forEach(wdId -> wf8266DetailRepository.deleteById(wdId));
		}

		List<Wf8266Detail> SUDetailList = wf8266.toWf8266DetailsSaveOrUpdate();
		if (SUDetailList != null && !SUDetailList.isEmpty()) {
			// 添加新增 Detail
			wf8266DetailRepository.saveAll(SUDetailList);
		}
		return model;
	}

	/** Ajax **/
	@GetMapping(value = "/ajax/getAllWf8266Detail", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> getAllWf8266Detail() {
		List<Wf8266Detail> wdList = wf8266DetailRepository.findAll();
		if (wdList != null)
			return new ResponseEntity<>(wdList, HttpStatus.OK);
		else
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
	}

}
