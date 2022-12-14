package com.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Tool;
import com.repository.ToolRep;


@Controller
public class ToolController {
	
	@Autowired
	private ToolRep toolRep;
	
	
	@RequestMapping("list-tools")
	@ResponseBody	
	public List<Tool> findltoolsbyStatReg(){
		
		List<Tool> lTool = toolRep.findAllByStatReg("0");
		return lTool;
	}

}
