package com.expandable.controller;


import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.expandable.model.Xxftm;
import com.expandable.repository.XxftmRep;
import com.model.Customer;
import com.model.QueryExp;
import com.model.Terms;
import com.repository.TermsRep;

@Controller
public class XftmController {
	
	@Autowired
	private TermsRep termsRep;
	
	@Autowired
	private XxftmRep xxftmRep;
	
	
	
	@RequestMapping("list-terms-exp")
	@ResponseBody
	private List<Xxftm> findAllTermsExp(){
		
		List<Xxftm> listTerms = xxftmRep.findAll();
		return listTerms;
	}
	
	
	@RequestMapping("list-terms-expView")
	@ResponseBody
	private List<QueryExp> findAllTermsExpView(){
		
		List<QueryExp> listerm = new ArrayList<QueryExp>();
    	List<Object[]> lis = xxftmRep.findAllTermsExp();
    	
    	for (int j = 0; j < lis.size(); j++) {
    		
    		QueryExp q = new QueryExp();
    		
    		q.setTermsCode((String) lis.get(j)[1]);
    		q.setTermsDesc((String) lis.get(j)[2]);
    		q.setDiscountDays(((BigInteger) lis.get(j)[6]).intValue());
    		q.setDueDays(((BigInteger) lis.get(j)[7]).intValue());
    		
    		
    		
    	}
		
		
		
		return listerm;
		
		
	}
	
	
	@RequestMapping(value = "terms-exp/new", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Xxftm> new_term_exp(@RequestBody Xxftm term){
		
		Xxftm aux = new Xxftm(term.getTermsCode(),term.getTermsDesc(),"N",0.00,"G",term.getDiscountDays(),
				term.getDueDays(), 0,"CASH","DEMO",LocalDateTime.now(),"DEMO",LocalDateTime.now(),LocalDateTime.now(),"");
		
		/*
        
		term.setDiscountType("N");
		term.setDiscountPct(0.00);
		term.setDiscountCalc("G");
		
		term.setDayOfMonth(0);
		term.setUserfield4("NOTHING");
		term.setCreatedby("DEMO");
		term.setDateCreated(LocalDateTime.now());
		term.setModifiedBy("DEMO");
		term.setDateModified(LocalDateTime.now());
		term.setDateLastUpdate(LocalDateTime.now());
		term.setDeleteFlag("0");*/
		
		xxftmRep.saveAndFlush(aux);	
	     
		
		return new ResponseEntity<Xxftm>(aux, HttpStatus.OK);
		
	}

}
