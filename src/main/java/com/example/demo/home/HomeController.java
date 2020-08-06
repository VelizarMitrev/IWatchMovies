package com.example.demo.home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@GetMapping("/home")
	public ModelAndView showMovies(ModelAndView modelAndView) {
		modelAndView.setViewName("home");
		return modelAndView;
	}
}
