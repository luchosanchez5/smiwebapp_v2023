package com.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Terms;
import com.repository.TermsRep;


@Controller
public class TermController {
	
	@Autowired
	private TermsRep termsRep;
	
	@RequestMapping("list-terms")
	@ResponseBody
	private List<Terms> findAllTerms(){
		
		List<Terms> listTerms = termsRep.findAllByStatus("0");
		return listTerms;
	}

}
