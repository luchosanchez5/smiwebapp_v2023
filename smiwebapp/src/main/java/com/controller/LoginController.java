package com.controller;

import java.util.Iterator;
import java.util.List;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.ModelAndView;


import com.model.Role;
import com.model.SessionUser;
import com.model.Users;
import com.service.UserService;

@Controller
public class LoginController {
	
	@Autowired
	private UserService userService;
	


	/*@RequestMapping(value={"/login"}, method = RequestMethod.GET)
	public ModelAndView login(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("login");
		return modelAndView;
	}*/
	
	@RequestMapping(value={"/"}, method = RequestMethod.GET)
	public String login(){
		
		return "login.html";	
		
	}
	
	@RequestMapping(value={"/loginini"}, method = RequestMethod.GET)
	public String prueba(){
		
		return "login.html";
	}
	
	
	@RequestMapping(value="/welcome", method = RequestMethod.GET)
	@ResponseBody
	public SessionUser home(){
		SessionUser currentSes = new SessionUser();
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String sesionId = RequestContextHolder.currentRequestAttributes().getSessionId();
		
		
		
		Object au = auth.getDetails();
		
		
		
		Users user = userService.findUserByusername(auth.getName());
		//modelAndView.addAttribute("userName", "Welcome " + user.getName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
		//modelAndView.addAttribute("adminMessage","Content Available Only for Users with Admin Role");
		//modelAndView.addAttribute("name", user.getName() + " " + user.getLastName());
		currentSes.setUserName(user.getEmail());
		currentSes.setCompleteName(user.getName() + " " + user.getLastName());
		currentSes.setImageUrl(user.getImageUrl());
		currentSes.setIdSesion(sesionId);
		currentSes.setUsers(user);
		
		
		System.out.println("Session : " + currentSes.getIdSesion() + "Type  : " + user.getUserType());

		
		Iterator vRoles = user.getRoles().iterator();
		//Role curRol = (Role) vRoles.next();		
		//currentSes.setRole("Administrator");
		currentSes.setRole(user.getUserType());

		return currentSes;
	}
	
	/*@RequestMapping(value="/registration", method = RequestMethod.GET)
	public ModelAndView registration(){
		ModelAndView modelAndView = new ModelAndView();
		Userr user = new Userr();
		modelAndView.addObject("user", user);
		modelAndView.setViewName("registration");
		return modelAndView;
	}*/
	

	

}
