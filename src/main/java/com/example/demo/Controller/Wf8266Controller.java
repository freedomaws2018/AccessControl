package com.example.demo.Controller;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
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
import com.example.demo.DataBase.Repository.Wf8266Repository;

@Controller
@RequestMapping(value = "/wf8266")
public class Wf8266Controller {

	@Autowired
	private Wf8266Repository wf8266Repository;

	@GetMapping("/list")
	public ModelAndView list(ModelAndView model, @PageableDefault(page = 0, size = 10, sort = {
			"createDate" }, direction = Direction.ASC) Pageable pageable) {
		model = new ModelAndView("layout/wf8266/l_wf8266");
		List<Wf8266> wf8266s = this.wf8266Repository.findAll();
		wf8266s = wf8266s.stream().filter(distinctByKey(Wf8266::getSn)).collect(Collectors.toList());
		int start = (int) pageable.getOffset();
		int end = start + pageable.getPageSize() > wf8266s.size() ? wf8266s.size() : (start + pageable.getPageSize());
		Page<Wf8266> wf8266stoPage = new PageImpl<>(wf8266s.subList(start, end), pageable, wf8266s.size());

		model.addObject("Wf8266s", wf8266stoPage);
		return model;
	}

	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> seen = new ConcurrentHashMap<>();
		return object -> seen.putIfAbsent(keyExtractor.apply(object), Boolean.TRUE) == null;
	}

	@GetMapping("/add")
	public ModelAndView add(ModelAndView model) {
		model = new ModelAndView("layout/wf8266/u_wf8266");

		model.addObject("funcType", "add");
		return model;
	}

	@GetMapping("/{funcType:view|edit}/{sn}")
	public ModelAndView viewAndEdit(ModelAndView model, @PathVariable String funcType, @PathVariable String sn) {
		model = new ModelAndView("layout/wf8266/u_wf8266");
		List<Wf8266> wf8266s = this.wf8266Repository.getBySn(sn);

		model.addObject("wf8266s", wf8266s);
		model.addObject("funcType", funcType);
		return model;
	}

	/** Redirect **/

	@RequestMapping("/save")
	public ModelAndView save(ModelAndView model, FormWf8266 form, RedirectAttributes attr) {

		// 刪除
		List<Wf8266> deleteWf8266 =form.toDeleteEntity();
		if (deleteWf8266 != null && !deleteWf8266.isEmpty()) {
			this.wf8266Repository.deleteAll(deleteWf8266);
		}

		// 更新
		List<Wf8266> updateWf8266 = form.toEntity();
		if (updateWf8266 != null && !updateWf8266.isEmpty()) {
			this.wf8266Repository.saveAll(updateWf8266);
		}

		return new ModelAndView(String.format("redirect:/wf8266/view/%s", form.getSn()));
	}

	/** Ajax **/
	@GetMapping(value = "/ajax/getAllWf8266", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<Object> getAllWf8266Detail() {
		List<Wf8266> wf8266s = this.wf8266Repository.findAll();
		if (wf8266s != null) {
			return new ResponseEntity<>(wf8266s, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
		}
	}

}