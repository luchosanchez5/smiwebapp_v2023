package com.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Industry;
import com.repository.IndustryRep;


@Controller
public class IndustryController {
	
	@Autowired
	private IndustryRep industryRep;
	
	@RequestMapping("list-industries")
	@ResponseBody
	private List<Industry> findAllByStatReg(){
		
		List<Industry> lisInd = industryRep.findAllByStatReg("0");
		return lisInd;
	}

}
