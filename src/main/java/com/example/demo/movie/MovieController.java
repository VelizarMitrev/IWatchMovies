package com.example.demo.movie;

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
public class MovieController {
	@Autowired
	MovieRepository movieRepository;
	
	@GetMapping("/movies")
	public ModelAndView showMovies(ModelAndView modelAndView) {
		modelAndView.addObject("movies", movieRepository.findAll());
		modelAndView.setViewName("movies");	
		return modelAndView;
	}
	
	@GetMapping("/add-movie")
	public ModelAndView showAddMovieForm(ModelAndView modelAndView, Movie movie, BindingResult result) {
		modelAndView.setViewName("add-movie");
		
		return modelAndView;
	}
	
	@PostMapping("/add-movie")
	public ModelAndView addMovie(ModelAndView modelAndView, @Valid Movie movie, BindingResult result) {
		modelAndView.setViewName("add-movie");	
        if (result.hasErrors()) {
        	System.out.println("Movie not added! " + result.getErrorCount());
            return modelAndView;
        }
        modelAndView.setViewName("redirect:/movies");	
        movieRepository.save(movie);
        System.out.println(movie.getTitle() + " " + movie.getReleaseDate());
		return modelAndView;
	}
	
	@GetMapping("/movie/{title}")
    public ModelAndView showUpdateForm(ModelAndView modelAndView, @PathVariable("id") long id, Model model) {
        Movie movie = movieRepository.findById(id)
          .orElseThrow(() -> new IllegalArgumentException("Invalid movie Id:" + id));
        
        modelAndView.addObject("movie", movie);     
        return modelAndView;
    }
}
