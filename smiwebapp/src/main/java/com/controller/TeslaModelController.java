package com.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.TeslaModel;
import com.repository.TeslaModelRep;


@Controller
public class TeslaModelController {
	
	@Autowired
	private TeslaModelRep teslaRep;
	
	
	@RequestMapping("list-tesla-models")
	@ResponseBody	
	public List<TeslaModel> findAllByStatREg(){
		
		List<TeslaModel> lTes = teslaRep.findAllByStatReg("0");
		return lTes;
		
	}
	
	
	
	
	

}
