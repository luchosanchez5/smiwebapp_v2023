package com.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.ItemType;
import com.repository.ItemTypeRep;

@Controller
public class ItemtypeController {
	
	@Autowired
	private ItemTypeRep itemtypeRep;
	
	
	@RequestMapping("list-TypeItems")
	@ResponseBody
	private List<ItemType> findAllItemsTypeByStatReg(){
		
		List<ItemType> listItems =  itemtypeRep.findAllByStatReg("0");
		return listItems;
		
		
	}

}
