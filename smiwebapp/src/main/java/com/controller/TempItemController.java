package com.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.ResponseBody;

import com.model.ItemStatus;
import com.model.Material;
import com.model.Part;
import com.model.TempItem;
import com.model.Users;
import com.repository.ItemStatusRep;
import com.repository.MaterialRep;
import com.repository.PartRep;
import com.repository.TempitemRep;
import com.repository.UserRep;


@Controller
public class TempItemController {
	
	@Autowired
	private TempitemRep tempRep;
	
	@Autowired
	private ItemStatusRep iStatRep;
	
	@Autowired
	private MaterialRep matRep;
	
	@Autowired
	private UserRep userRep;
	
	@Autowired
	private PartRep partRep;
	
	@RequestMapping("list-tempitems")
	@ResponseBody
	private List<TempItem> findtempitems(String username, String sessionId){
		
		Users user = userRep.findByUsername(username);
		//List<TempItem> listTemp = tempRep.findAllByUsersAndSessionId(user, sessionId);
		List<TempItem> listTemp = tempRep.findAllBySessionIdAndStatReg(sessionId, "0");
		return listTemp;
	}
	
	
	@RequestMapping(value = "tempitem", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Users> new_tempItem(@RequestBody TempItem t) {
		int status = 0; // To New Item
		ItemStatus itStat = iStatRep.findOneById(status);
		
		//System.out.println(t.getSugMaterials().toArray().);

		
        if (t.getItemType().getId() == 4) {
        	
        	
        	boolean pAux = partRep.existsByPartNumber(t.getPart().getPartNumber());
        	Part p = new Part();
        	
        	if (pAux == false) {
        	
        	
        	
        	p = t.getPart();
        	p.setCreatedDate(LocalDateTime.now());
        	p.setStatReg("0");
        	partRep.saveAndFlush(p);
        	t.setPart(p);
        	
        	}
        	else
        	{
        	    p = partRep.findOneByPartNumber(t.getPart().getPartNumber());
        	    t.setPart(p);
        	    
        	}
        	
        	
        }
		
        
        if (t.getSugMaterials() != null) {
        
	        Iterator iMaterials =  t.getSugMaterials().iterator();   
	        ArrayList<Material> lis = new ArrayList<Material>();
	        
	              
	        while (iMaterials.hasNext()){
	        	Material currMat = (Material) iMaterials.next();
	        	//String idClient = (String) iClients.next();
	        	long materialInt = currMat.getIdMaterial();
	        	//System.out.println("Seller Code :" + idSeller);
	        	Material mat = matRep.getOne(materialInt);
	        	lis.add(mat); 
	        	
	        }
	        
	        
			t.setSugMaterials(new HashSet<Material>(lis) );
		
        }
		
		
		
		//System.out.println("SIZE MAT : " + lis.size());
	    t.setItemStatus(itStat);
		t.setCreatedDate(LocalDateTime.now());
		t.setStatReg("0");
	    t.setCavities(0);
		tempRep.save(t);
		
		TempItem iAux = tempRep.getOne(t.getId());
		Users u = t.getUsers();
		
		
		
		return new ResponseEntity<Users>(u, HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "copyitem", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Users> copytempItem(@RequestBody TempItem t) {
		int status = 0; // To New Item
		ItemStatus itStat = iStatRep.findOneById(status);
		
		//System.out.println(t.getSugMaterials().toArray().);

		
		TempItem tNew = new TempItem();
		//tNew = t;
		
		tNew.setSessionId(t.getSessionId());
		tNew.setItemType(t.getItemType());
		tNew.setIndustry(t.getIndustry());
		tNew.setItemStatus(t.getItemStatus());
		tNew.setTeslaModel(t.getTeslaModel());
		tNew.setCreatedDate(LocalDateTime.now());
		tNew.setPart(t.getPart());
		tNew.setDrawingAva(t.getDrawingAva());
		tNew.setNewPart(t.getNewPart());
		tNew.setSampleAva(t.getSampleAva());
		tNew.setConcernsCurr(t.getConcernsCurr());
		tNew.setCadfileAva(t.getCadfileAva());
		tNew.setPackageReq(t.getPackageReq());
		tNew.setPartKissCut(t.getPartKissCut());
		tNew.setCavities(t.getCavities());
		tNew.setQitemType(t.getQitemType());
		tNew.setAnualUsage(t.getAnualUsage());
		tNew.setQuantity(t.getQuantity());
		tNew.setSuggestMat(t.getSuggestMat());
		tNew.setSuggestVend(t.getSuggestVend());
		tNew.setTargetPrice(t.getTargetPrice());
		tNew.setFob(t.getFob());
		tNew.setUsers(t.getUsers());
		tNew.setRfqdueDate(t.getRfqdueDate());
		tNew.setStatReg(t.getStatReg());
		tNew.setNewPartName(t.getNewPartName());
		tNew.setNote(t.getNote());
		tNew.setMeasureUnit(t.getMeasureUnit());
		tNew.setIncoTerms(t.getIncoTerms());
		
		
        if (t.getItemType().getId() == 4) {
        	
        	
        	boolean pAux = partRep.existsByPartNumber(t.getPart().getPartNumber());
        	Part p = new Part();
        	
        	if (pAux == false) {
        	
        	
        	
        	p = t.getPart();
        	p.setCreatedDate(LocalDateTime.now());
        	p.setStatReg("0");
        	partRep.saveAndFlush(p);
        	tNew.setPart(p);
        	
        	}
        	else
        	{
        	    p = partRep.findOneByPartNumber(t.getPart().getPartNumber());
        	    tNew.setPart(p);
        	    
        	}
        	
        	
        }
		
	
		
		//System.out.println("SIZE MAT : " + lis.size());
	    tNew.setItemStatus(itStat);
		tNew.setCreatedDate(LocalDateTime.now());
		tNew.setStatReg("0");
	    tNew.setCavities(0);
		tempRep.save(tNew);
		
		
		
		TempItem iAux = tempRep.getOne(t.getId());
		Users u = t.getUsers();
		
		
		
		return new ResponseEntity<Users>(u, HttpStatus.OK);
	}
		
	
	
	@RequestMapping(value = "tempItem/delete", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<TempItem> removeTempItem(@RequestBody TempItem t){
		
	
		TempItem tAux = tempRep.findOneById(t.getId());
		tAux.setStatReg("1");
		tempRep.save(tAux);
		
		return new ResponseEntity<TempItem>(tAux, HttpStatus.OK);
	}	
	
	
	@RequestMapping(value = "tempItem/update", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<TempItem> updateTempItem(@RequestBody TempItem t){
		

        if (t.getItemType().getId() == 4) {
        	
        	
        	boolean pAux = partRep.existsByPartNumber(t.getPart().getPartNumber());
        	Part p = new Part();
        	
        	if (pAux == false) {
        	
        	
        	
        	p = t.getPart();
        	p.setCreatedDate(LocalDateTime.now());
        	p.setStatReg("0");
        	partRep.saveAndFlush(p);
        	t.setPart(p);
        	
        	}
        	else
        	{
        	    p = partRep.findOneByPartNumber(t.getPart().getPartNumber());
        	    t.setPart(p);
        	    
        	}
        	
        	
        }
        
        ItemStatus itS = iStatRep.findOneById(t.getItemStatus().getId());
		
		
		TempItem tAux = tempRep.findOneById(t.getId());
		
		tAux = t;		
		tAux.setItemStatus(itS);
    	tempRep.saveAndFlush(tAux);
		
		return new ResponseEntity<TempItem>(tAux, HttpStatus.OK);
	}	
		

}
