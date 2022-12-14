package com.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.QitemType;
import com.repository.PartRep;
import com.repository.QitemTypeRep;

@Controller
public class QitemtypeController {
	
	@Autowired
	private QitemTypeRep partRep;
	
	@RequestMapping("list-qitemstype")
	@ResponseBody
	private List<QitemType> findAllQItems(){
		List<QitemType>  lisIt = partRep.findAllByStatReg("0");
		return lisIt;
	}

}
