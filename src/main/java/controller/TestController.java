package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Person;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.eclipsesource.v8.V8;
import com.eclipsesource.v8.V8Array;

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
	
	@SuppressWarnings("deprecation")
	@RequestMapping("/j2v8")
	public ModelAndView j2v8(HttpServletRequest request,HttpServletResponse response) throws IOException, ScriptException
	{	
		System.out.print(request.getRealPath("./WEB-INF/res/script/handlebars-v4.0.2.js"));
		request.getServletPath();
		ModelAndView mv=new ModelAndView("hello");
		FileReader fr=new FileReader("./WEB-INF/res/script/handlebars-v4.0.2.js");
	    ScriptEngineManager manager = new ScriptEngineManager();
	    ScriptEngine engine = manager.getEngineByName("javascript");
	    engine.eval(fr);
	    engine.eval("var t=Handlebars.compile('<h1>{{name}}</h1><h1>{{age}}</h1>');");
	    Person p=new Person();
	    p.setName("Soros");
	    p.setAge(34);
	    engine.put("viewmodel", p);
	    engine.eval("var html=t(viewmodel);");	    
	    Object o=engine.get("html");	    
		mv.addObject("name", o.toString());		
		return mv;
	}
}