package com.expandable.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.expandable.model.Sofst;
import com.expandable.repository.SofstRep;

@Controller
public class SofstController {
	
	@Autowired
	private SofstRep sofstRep;
	
	private Boolean updateTracking(String soId, Double batchNumber, String tracking) {
		
		List<Sofst> listPack = sofstRep.findAllBySoIdAndBatchNumber(soId, batchNumber);
		listPack.stream().forEach((c) -> { c.setBillofLanding(tracking);  sofstRep.save(c);});
		
		
		return false;
	}

}
