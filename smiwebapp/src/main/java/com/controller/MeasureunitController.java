package com.controller;

import java.time.LocalDateTime;
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

import com.model.Estimator;
import com.model.MeasureUnit;
import com.repository.MeasureUnitRep;

@Controller
public class MeasureunitController {
	
	@Autowired
	private MeasureUnitRep measureRep;
	
	@RequestMapping("list-measureunits")
	@ResponseBody	
	private List<MeasureUnit> findAllmeasuresbystatreg(){
		
		List<MeasureUnit> me = measureRep.findAllByStatReg("0");
		return me;
		
	}
	

	@RequestMapping(value = "measure/new", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<MeasureUnit> new_measure(@RequestBody MeasureUnit s){
		s.setStatReg("0");
		measureRep.save(s);
		return new ResponseEntity<MeasureUnit>(s, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "measure/delete/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<MeasureUnit> delete_measure(@PathVariable("id") long id){
		MeasureUnit currentCust = measureRep.findOneByIdMeasureunit(id);
		currentCust.setStatReg("1");
		measureRep.save(currentCust);
		return new ResponseEntity<MeasureUnit>(HttpStatus.NO_CONTENT);
	}
	
	
	
	@RequestMapping(value = "measure/edit/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<MeasureUnit> edit_measure(@PathVariable("id") int id,@RequestBody MeasureUnit c){
		
		MeasureUnit currentCust = measureRep.findOneByIdMeasureunit(id);
		currentCust = c;
		measureRep.save(currentCust);
		return new ResponseEntity<MeasureUnit>(c, HttpStatus.OK);
	}		
	

}
