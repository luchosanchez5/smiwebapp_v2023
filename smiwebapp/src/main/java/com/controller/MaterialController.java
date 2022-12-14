package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Material;
import com.repository.IndustryRep;
import com.repository.MaterialRep;

@Controller
public class MaterialController {

	
	@Autowired
	private MaterialRep materialRep;
	
	@RequestMapping("list-materials")
	@ResponseBody
	private List<Material> findAllMaterials(){
		List<Material> lisMat = materialRep.findAllByStatReg("0");
		System.out.println("MATERIALS : " + lisMat.size());
		return lisMat;
		
	}
}
