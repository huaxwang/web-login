package com.soga.code.core.action;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class TestMechantController extends BaseController{
	
	@RequestMapping(value="/a", method=RequestMethod.GET)
	public ModelAndView home(HttpServletRequest request, HttpServletResponse response,ModelMap model)throws  Exception{
		System.out.print("aaaa======================");
		return new ModelAndView("redirect:/login/merchant/login");

	}
}