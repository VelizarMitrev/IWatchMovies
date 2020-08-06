package com.example.demo.user;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController {
	
	@Autowired
	UserRepository userRepository;
    
	@GetMapping("/signup")
    public ModelAndView showSignUpForm(ModelAndView modelAndView, User user) {
		modelAndView.setViewName("add-user");
        return modelAndView;
    }
	
	@GetMapping("/index")
	public ModelAndView showIndexPage(ModelAndView modelAndView, User user) {
		modelAndView.setViewName("index");
		return modelAndView;
	}
    
    @PostMapping("/adduser")
    public ModelAndView addUser(ModelAndView modelAndView, @Valid User user, BindingResult result) {
    	modelAndView.setViewName("index");
        if (result.hasErrors()) {
            return modelAndView;
        }
        
        userRepository.save(user);
        modelAndView.addObject("users", userRepository.findAll());
        return modelAndView;
    }
    
    @GetMapping("/edit/{id}")
    public ModelAndView showUpdateForm(ModelAndView modelAndView, @PathVariable("id") long id, Model model) {
        User user = userRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        
        modelAndView.addObject("user", user);
        return modelAndView;
    }
    
    @PostMapping("/update/{id}")
    public ModelAndView updateUser(ModelAndView modelAndView, @PathVariable("id") long id, @Valid User user, 
      BindingResult result, Model model) {
    	modelAndView.setViewName("update-user");
        if (result.hasErrors()) {
            user.setId(id);
            return modelAndView;
        }
            
        userRepository.save(user);
        modelAndView.addObject("users", userRepository.findAll());
        return modelAndView;
    }
        
    @GetMapping("/delete/{id}")
    public ModelAndView deleteUser(ModelAndView modelAndView, @PathVariable("id") long id, Model model) {
    	modelAndView.setViewName("index");
        User user = userRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        modelAndView.addObject("users", userRepository.findAll());
        return modelAndView;
    }
 
}