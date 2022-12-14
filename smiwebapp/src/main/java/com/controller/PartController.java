package com.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Part;
import com.repository.PartRep;

@Controller
public class PartController {
	
	@Autowired
	private PartRep partRep;
	
	@RequestMapping("list-parts")
	@ResponseBody
	private List<Part> findAllPartsByStatReg(){
		
		List<Part> listParts = partRep.findAllByStatReg("0");
		return listParts;
		
	}
	
}
