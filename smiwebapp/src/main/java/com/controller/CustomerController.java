package com.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Customer;
import com.model.Estimator;
import com.repository.CustomerRep;

@Controller
public class CustomerController {
	
	@Autowired
	private CustomerRep customerRep;
	
	@RequestMapping("list-customers")
	@ResponseBody
	
    private List<Customer> findAllCustomersByStatCompanyandStatReg() {
		
		//List<Customer> lisCus = customerRep.findAllByStatCompanyAndStatReg("0", "0");
		List<Customer> lisCus = customerRep.findAllByStatReg("0");
		return lisCus;
	}
	
	//@RequestMapping("new-customer")
	@RequestMapping(value = "customer/new", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Customer> new_customer(@RequestBody Customer c){
        
		c.setCreatedDate(LocalDateTime.now());
		c.setStatCompany("0");
		c.setStatCustomer("0");
		c.setStatReg("0");
		customerRep.saveAndFlush(c);
		
		return new ResponseEntity<Customer>(c, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "customer/edit", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Customer> edit_customer(@RequestBody Customer c){
		
		customerRep.saveAndFlush(c);
		return new ResponseEntity<Customer>(c, HttpStatus.OK);
	}
	
	
	@RequestMapping("findOneCustomer")
	@ResponseBody
	public Customer findOneCustomer(String companyId){
		Customer c = customerRep.findOneByIdCompany(companyId);
		return c;
	}	
	
	
	@RequestMapping("findCustomerById")
	@ResponseBody
	public Customer findOneCustomerById(long companyId){
		Customer c = customerRep.findOneById(companyId);
		return c;
	}		

}
