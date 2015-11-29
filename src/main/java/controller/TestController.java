package controller;


import java.io.IOException;

import model.Person;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/test")
@Controller
public class TestController {	
	
	@RequestMapping("/hello")
	public ModelAndView hello()
	{
		ModelAndView mv=new ModelAndView("hello");
		System.out.print("hello2");
		mv.addObject("name", "123");
		return mv;
	}
	
	@RequestMapping("/handlebars")
	public ModelAndView handlebars() throws IOException{
		ModelAndView mv=new ModelAndView("testhbs");
		Person p=new Person();
		p.setName("Soros");
		p.setAge(34);		
		mv.addObject("p", p);
		return mv;
	}
}