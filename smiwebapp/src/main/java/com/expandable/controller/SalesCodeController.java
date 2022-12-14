package com.expandable.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.expandable.model.ExpCustomer;
import com.expandable.model.SalesCode;
import com.expandable.repository.SalesCodeRep;


@Controller
public class SalesCodeController {
	
	@Autowired
	private SalesCodeRep salesRep;
	
	
	@RequestMapping("list-salescode-exp")
	@ResponseBody
	private List<SalesCode> findSalesCodeExp(){
		
		List<SalesCode> listCodes = salesRep.findAll();
		return listCodes;
	}
	
	
	
	

}
