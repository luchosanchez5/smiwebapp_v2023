package com.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	  
	  @CrossOrigin(origins = "https://www.shipengine.com")
	  @RequestMapping(value = "/sealhome")
	  public String index() {
	    return "principal.html";
	  }

}
