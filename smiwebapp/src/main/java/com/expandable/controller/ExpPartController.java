package com.expandable.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.expandable.model.ExpPart;
import com.expandable.repository.ExpPartRep;

@Controller
public class ExpPartController {
	
	@Autowired
	private ExpPartRep partRep;
	
	@RequestMapping("list-part-exp-all")
	@ResponseBody
	private List<ExpPart> findAllpartsExp(){
		
		List<ExpPart> listParts = partRep.findAll();
		return listParts;
	}
	
	@RequestMapping("list-part-exp-type")
	@ResponseBody
	private List<ExpPart> findpartsExpByType(String type){
		
		List<ExpPart> listParts = partRep.findAllByPartType(type);
		return listParts;
	}
	
	
	@RequestMapping("list-part-cust-exp")
	@ResponseBody
	private List<ExpPart> findpartsCustExp(String custId){
		
		List<ExpPart> listParts = partRep.findAllByCustomerId(custId);
		return listParts;
	}
	
	
	@RequestMapping("findOnePartExp")
	@ResponseBody
	private ExpPart findOnePartExp(String partId){
		
		ExpPart partExp = partRep.findOneByPartId(partId);
		System.out.println("PART : ---- " + partExp.getPartDesc());
		return partExp;
	}
	

}
