package com.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Machine;
import com.repository.MachineRep;
import com.repository.ToolRep;

@Controller
public class MachineController {
	
	@Autowired
	private MachineRep machineRep;
	
	
	@RequestMapping("list-machines")
	@ResponseBody	
	public List<Machine> findAllMachionesbystat(){
		
		List<Machine> lMachine = machineRep.findAllByStatReg("0");
		return lMachine;
		
	}
}
