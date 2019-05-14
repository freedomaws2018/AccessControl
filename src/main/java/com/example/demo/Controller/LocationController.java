package com.example.demo.Controller;

import java.util.List;

import javax.validation.Valid;

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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Controller.FormEntity.FormLocation;
import com.example.demo.DataBase.Entity.Location;
import com.example.demo.DataBase.Service.LocationService;

@Controller
@RequestMapping(value = "/location")
public class LocationController {

	@Autowired
	private LocationService locationService;

	@GetMapping(value = "/list")
	private ModelAndView list(ModelAndView model,
			@PageableDefault(page = 0, size = 10, sort = { "id" }, direction = Direction.ASC) Pageable pageable) {
		model = new ModelAndView("layout/location/l_location");
		Page<Location> locations = this.locationService.getAll(pageable);
		model.addObject("locations", locations);
		return model;
	}

	@GetMapping(value = "/add")
	private ModelAndView add(ModelAndView model) {
		model = new ModelAndView("layout/location/u_location");
		model.addObject("funcType", "edit");
//		Location location = this.locationService.getById(id);
		model.addObject("location", new Location());

		return model;
	}

	@GetMapping(value = "/{funcType:view|edit}/{id}")
	private ModelAndView edit(ModelAndView model, @PathVariable String funcType, @PathVariable Long id) {
		model = new ModelAndView("layout/location/u_location");

		Location location = this.locationService.getById(id);
		model.addObject("location", location);
		model.addObject("funcType", funcType);
		return model;
	}

	@PostMapping(value = "/save")
	private ModelAndView save(ModelAndView model, RedirectAttributes attr, @Valid FormLocation form,
			BindingResult result) {

		if (result.hasErrors()) {
			List<FieldError> fieldErrors = result.getFieldErrors();
			for (FieldError error : fieldErrors) {
				System.err.printf("%s:%s:%s\n", error.getField(), error.getDefaultMessage(), error.getCode());
			}
			return new ModelAndView("redirect:/location/edit/" + form.getId());
		}

		System.err.println(form);
		Location location = form.toEntity();
		System.err.println(location);

		location = this.locationService.save(location);
		System.err.println(location);

		return new ModelAndView("redirect:/location/view/" + location.getId());
	}

	@DeleteMapping(value = "/delete/{id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	private ResponseEntity<Object> delete(@PathVariable Long id) {
		Location location = this.locationService.getById(id);
		if (location != null) {
			this.locationService.delete(location);
		}
		return new ResponseEntity<>(null, HttpStatus.OK);
	}

	/** autocomplete **/
	@PostMapping(value = "/autocomplete/getAll", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	private ResponseEntity<Object> getAll(String term) {
		List<Location> location = this.locationService.getByNameLike(term);

		return new ResponseEntity<>(location, HttpStatus.OK);
	}

}
