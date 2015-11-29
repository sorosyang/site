package controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping("/hotel")
@Controller
public class HotelController {
	
	@RequestMapping("/index")
	public ModelAndView hello()
	{
		ModelAndView mv=new ModelAndView("hotel/index");
		System.out.print("hello2");
		mv.addObject("name", "123");
		return mv;
	}
}
