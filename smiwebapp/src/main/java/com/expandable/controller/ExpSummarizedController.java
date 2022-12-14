package com.expandable.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.expandable.model.ExpSummarized;
import com.expandable.repository.ExpSummarizedRep;

@Controller
public class ExpSummarizedController {
	
	@Autowired
	private ExpSummarizedRep custRep;
	
	@RequestMapping("list-summarized-exp")
	@ResponseBody
	private List<ExpSummarized> findSummarizedExp(String partId){
		
		List<ExpSummarized> listTerms = custRep.findAllByPartId(partId);
		System.out.println("SIZE SUMMARIZED : " + partId + " ==> " + listTerms.size());
		System.out.println("SIZE SUMMARIZED : " + listTerms);
		
		return listTerms;
	}
	

}
