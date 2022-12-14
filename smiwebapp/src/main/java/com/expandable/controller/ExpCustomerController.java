package com.expandable.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.expandable.model.ExpCustomer;
import com.expandable.model.Xxftm;
import com.expandable.repository.CustomerShipRep;
import com.expandable.repository.ExpCustomerRep;

@Controller
public class ExpCustomerController {
	
	@Autowired
	private ExpCustomerRep custRep;
	
	@Autowired
	private CustomerShipRep custShipRep;
	
	@RequestMapping("list-customers-exp")
	@ResponseBody
	private List<ExpCustomer> findCustomersExp(){
		
		List<ExpCustomer> listTerms = custRep.findAll();
		return listTerms;
	}
	
	@RequestMapping("list-customers-shipping")
	@ResponseBody
	private List<ExpCustomer> findCustomersShip(){
		
		List<ExpCustomer> listTerms = custRep.findAll();
		return listTerms;
	}
	
	

}
