package com.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Estimator;
import com.model.Incoterms;
import com.model.Item;
import com.model.ItemStatus;
import com.model.MatDetails;
import com.model.Material;
import com.model.Part;
import com.model.Quote;
import com.model.QuoteNote;
import com.model.Seller;
import com.model.TempItem;
import com.model.Users;
import com.repository.EstimatorRep;
import com.repository.IncotermsRep;
import com.repository.ItemRep;
import com.repository.ItemStatusRep;
import com.repository.MatDetailRep;
import com.repository.MaterialRep;
import com.repository.PartRep;
import com.repository.QuoteNoteRep;
import com.repository.QuoteRep;
import com.repository.SellerRep;
import com.repository.UserRep;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class ItemController {
	
	@Autowired
	private ItemRep itemRep;
	
	@Autowired
	private QuoteRep quoteRep;
	
	@Autowired
	private MatDetailRep matDetailRep;
	
	@Autowired
	private ItemStatusRep iStatRep; 
	
	@Autowired
	private QuoteNoteRep quotenoteRep;	
	
	@Autowired
	private PartRep partRep;
	
	@Autowired
	private MaterialRep matRep;
	
	@Autowired
	private SellerRep sellerRep;
	
	@Autowired
	private UserRep userRep;
	
	@Autowired
	private EstimatorRep estimatorRep;	
	
	@Autowired
	private IncotermsRep incotermsRep;
	


	
	@RequestMapping("list-sessionitems")
	@ResponseBody
	private List<Item> findAllByStatReg(String session){
		List<Item> listItem = itemRep.findAll();
		return listItem;
	}
	
	@RequestMapping("list-itemstatus")
	@ResponseBody
	private List<ItemStatus> findAllStatusbyAction(String act){
		List<ItemStatus> listItem = iStatRep.findAllByAction(act);
		return listItem;
	}
	
	@RequestMapping("list-incoterms")
	@ResponseBody
	private List<Incoterms> findAllIncotermsByStatReg(){
		List<Incoterms> listItem = incotermsRep.findAllByStatus("0");
		return listItem;
	}
	

	
	@RequestMapping("list-itemsquote")
	@ResponseBody	
	private List<Item> findAllByStatRegAndQuote(long id){
		
		List<Item> lisItem = null;
		Quote q = quoteRep.findById(id);
		if (q.getSetorderItems().equals("0")){
			 lisItem = itemRep.findAllByStatRegAndQuoteOrderByIdItem("0", q);
		}
		
		if (q.getSetorderItems().equals("1")){
			 lisItem = itemRep.findAllByStatRegAndQuoteOrderByNewPartNameAscQuantityAsc("0", q);
		}
		
		return lisItem;
		
		
	}
	
	@RequestMapping("list-pendingitems")
	@ResponseBody	
	private List<Item> findAllByStatRegAndSeller(long idSeller){
		
		int id = 0;
	    Seller sel = sellerRep.findOneById(idSeller);	
	    ItemStatus it = iStatRep.findOneById(id);
	    
		List<Item> lisItem = itemRep.findAllByStatRegAndItemStatusOrderByQuote("0", it);
		return lisItem;
		
		
	}
	
	@RequestMapping("list-pendingitemsEst")
	@ResponseBody	
	private List<Item> findAllByStatRegAndEst(){
		
		int id = 0;
	    ItemStatus it = iStatRep.findOneById(id);
	    
	    List<ItemStatus> sList = iStatRep.findAllByPending("Y");
	    
		List<Item> lisItem = itemRep.findAllByStatRegAndItemStatusInOrderByQuote("0", sList);
		return lisItem;
		
		
	}
	
	
	@RequestMapping("list-pendingitemsEst-year")
	@ResponseBody	
	private List<Item> findAllByStatRegAndEstYear(long year){
		
		int id = 0;
	    ItemStatus it = iStatRep.findOneById(id);
	    
	    List<ItemStatus> sList = iStatRep.findAllByPending("Y");
	    
		List<Item> lisItem = itemRep.findAllByStatRegAndItemStatusInOrderByQuote("0", sList);
		List<Item> listYear = lisItem
				.stream()
				.filter(c -> c.getCreatedDate().getYear() == year)
				.collect(Collectors.toList());
		return listYear;
		
		
	}
	
	
	@RequestMapping("list-ordereditems")
	@ResponseBody	
	private List<Item> findAllByOrderedItems(){
		
		int id = 0;
	    ItemStatus it = iStatRep.findOneById(id);
	    
	    List<ItemStatus> sList = iStatRep.findAllByOrdened("Y");
	    
		List<Item> lisItem = itemRep.findAllByStatRegAndItemStatusInOrderByQuote("0", sList);
		return lisItem;
		
		
	}
	
	@RequestMapping("list-ordereditems-year")
	@ResponseBody	
	private List<Item> findAllByOrderedItemsYyear(long year){
		
		int id = 0;
	    ItemStatus it = iStatRep.findOneById(id);
	    
	    List<ItemStatus> sList = iStatRep.findAllByOrdened("Y");
	    
		List<Item> lisItem = itemRep.findAllByStatRegAndItemStatusInOrderByQuote("0", sList);
		List<Item> listYear = lisItem
				.stream()
				.filter(c -> c.getCreatedDate().getYear() == year)
				.collect(Collectors.toList());
		
		return listYear;
		
		
	}
	
	
	
	
	
	
	@RequestMapping("list-itemsall")
	@ResponseBody	
	private List<Item> findAllByStatReg(){

		List<Item> lisItem = itemRep.findAllByStatReg("0");
		return lisItem;

	}
	
	@RequestMapping("list-itemsByKeyword")
	@ResponseBody	
	private List<Item> findAllByStatRegAndKeyword(String keyword){

		List<Item> lisItem = itemRep.findByStatRegAndNewPartNameContainsIgnoreCase("0", keyword);
		return lisItem;

	}
	


	@RequestMapping("list-matdetailsitem")
	@ResponseBody
	private List<MatDetails> finddetailsitemAllByStatReg(long idItem){
		Item it = itemRep.findOneByIdItem(idItem);
		List<MatDetails> listItem = matDetailRep.findAllByItemAndStatRegAndTypeDetailOrderById(it, "0","1");
		return listItem;
	}	
	
	
	@RequestMapping("list-newmaterial")
	@ResponseBody
	private List<Material> listnewmaterial(){

		List<Material> listMat = matRep.findAllByTypeM("1");
		return listMat;
	}	
		
	
	@RequestMapping("list-labdetailsitem")
	@ResponseBody
	private List<MatDetails> findlabdetailsitemAllByStatReg(long idItem){
		Item it = itemRep.findOneByIdItem(idItem);
		List<MatDetails> listItem = matDetailRep.findAllByItemAndStatRegAndTypeDetailOrderById(it, "0","2");
		return listItem;
	}
	
	
	@RequestMapping(value = "matdetail", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<MatDetails> new_matDetail(@RequestBody MatDetails md) {
		
		md.setStatReg("0");
		md.setCreateDate(LocalDateTime.now());
		
		// GET IF NEW PART OR EXISTING    ---------
		
	/*	if (md.getTypeMaterial().equals("0") ) {
			
			null;
		} */
		
		
		
		
		if (md.getTypeDetail().equals("1")) {   //Material
		    
			md.setTool(null); 
			md.setMachine(null);
			md.setStepDescription(null);
			md.setQtyHour(0);
			md.setLaborCost(0);
			md.setLaborRate(0);
			
			
			if (md.getTypeMaterial().equals("0")) {
				boolean enc = false;
				enc = matRep.existsByPartNumber(md.getMaterial().getPartNumber());
				
				if (enc) {
					
					Material matExis = matRep.findOneByPartNumber(md.getMaterial().getPartNumber());
					md.setMaterial(matExis);
				}else {
					
					Material newMat = new Material();
					newMat.setPartNumber(md.getMaterial().getPartNumber());
					newMat.setDescMaterial(md.getMaterial().getDescMaterial());
					newMat.setUnitCost(md.getMaterial().getUnitCost());
					newMat.setUser(md.getUser());
					newMat.setStatReg("0");
					newMat.setCreatedDate(LocalDateTime.now());
					matRep.saveAndFlush(newMat);
					md.setMaterial(newMat);
					
				}
				
				
			}
			
			
			
			if (md.getMaterial() != null) {
				Quote q = quoteRep.findById(md.getItem().getQuote().getId());
				if (q.getPrintMaterials() == null)
					q.setPrintMaterials(md.getMaterial().getDescMaterial());
				else {
					
				  if (q.getPrintMaterials().length() < 200)	 {
					q.setPrintMaterials(q.getPrintMaterials() + ", " + md.getMaterial().getDescMaterial());
				  }	
				}	
			}
		}
		
		if (md.getTypeDetail().equals("2")) { //Labor
			
			md.setMatWidht(0);
			md.setMeasureUnit(null);
			md.setMaterial(null);
			md.setCostUm(0);
			md.setLf(0);
			md.setProgression(0);
			md.setSlitWidth(0);
			md.setNroRolls(0);
			md.setYield(0);
			md.setPriceEach(0);
			md.setFreight(0);
			md.setTotalCost(0);
			
		}
		
		matDetailRep.save(md);
		
		

		
		return new ResponseEntity<MatDetails>(md, HttpStatus.OK);
	     
	}
	
	
	@RequestMapping(value = "updateItem", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Item> updateItem(@RequestBody Item it) {	
		
		Item item = itemRep.findOneByIdItem(it.getIdItem());
		item.setMaterialCost(it.getMaterialCost());
		item.setPackagingCost(it.getPackagingCost());
		item.setLaborCost(it.getLaborCost());
		item.setScrapRate(it.getScrapRate());
		item.setMargin(it.getMargin());
		item.setToolingCost(it.getToolingCost());
		item.setSmiTotalCost(it.getSmiTotalCost());
		item.setSmiSaleCost(it.getSmiSaleCost());
		item.setCavities(it.getCavities());
		
		if (it.getItemStatus().getId() == 0) {
			int status = 1; // To New Item
			ItemStatus itStat = iStatRep.findOneById(status);
			item.setItemStatus(itStat);
			item.setLastUpdate(LocalDateTime.now());
			
		}
	
		
		itemRep.save(item);		
		updateAutmaticStatus(item.getQuote());
		return new ResponseEntity<Item>(it, HttpStatus.OK);
	}
	
	@RequestMapping(value = "matdetail/update", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<MatDetails> editMatDetail(@RequestBody MatDetails md){
		

		
		MatDetails mdActual = matDetailRep.findOneById(md.getId());
		mdActual.setCostUm(md.getCostUm());
		mdActual.setFreight(md.getFreight());
		mdActual.setLaborCost(md.getLaborCost());
		mdActual.setLaborRate(md.getLaborRate());
		mdActual.setLf(md.getLf());
		mdActual.setMachine(md.getMachine());
		//mdActual.setMaterial(md.getMaterial());
		
		//
		mdActual.setMatWidht(md.getMatWidht());
		mdActual.setMeasureUnit(md.getMeasureUnit());
		mdActual.setNroRolls(md.getNroRolls());
		mdActual.setPriceEach(md.getPriceEach());
		mdActual.setProgression(md.getProgression());
		mdActual.setQtyHour(md.getQtyHour());
		mdActual.setSlitWidth(md.getSlitWidth());
		mdActual.setStepDescription(md.getStepDescription());
		mdActual.setTool(md.getTool());
		mdActual.setTotalCost(md.getTotalCost());
		mdActual.setYield(md.getYield());
		mdActual.setTypeMaterial(md.getTypeMaterial());
		
		mdActual.setNotesMat(md.getNotesMat());
		mdActual.setFreightCost(md.getFreightCost());
        
		
		if (md.getTypeDetail().equals("1")) {
		
			if (md.getTypeMaterial().equals("0")) {
				boolean enc = false;
				enc = matRep.existsByPartNumber(md.getMaterial().getPartNumber());
				
				if (enc) {
					
					Material matExis = matRep.findOneByPartNumber(md.getMaterial().getPartNumber());
					mdActual.setMaterial(matExis);
				}else {
					
					Material newMat = new Material();
					newMat.setPartNumber(md.getMaterial().getPartNumber());
					newMat.setDescMaterial(md.getMaterial().getDescMaterial());
					newMat.setUnitCost(md.getMaterial().getUnitCost());
					newMat.setUser(md.getUser());
					newMat.setStatReg("0");
					newMat.setCreatedDate(LocalDateTime.now());
					matRep.saveAndFlush(newMat);
					mdActual.setMaterial(newMat);
					
				}
				
				
			}
	   
		}	
		
		
	
		matDetailRep.save(mdActual);
		

		
		return new ResponseEntity<MatDetails>(mdActual, HttpStatus.OK);
	}

	
	@RequestMapping(value = "matdetail/remove", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<MatDetails> removematDetail(@RequestBody MatDetails md){	
	    
		MatDetails mdActual = matDetailRep.findOneById(md.getId());
		mdActual.setStatReg("1");
		mdActual.setDeleteDate(LocalDateTime.now());
		mdActual.setDeleteUser(md.getDeleteUser());
		matDetailRep.save(mdActual);
		
		
		
		return new ResponseEntity<MatDetails>(mdActual, HttpStatus.OK);
	}
	
	
	public void updateAutmaticStatus(Quote q) {
		
		int totItems = 0;
		int totProgress =0;
		int totCancel = 0;
		int statusProgress = 1;
		String newStaQuote = q.getStatQuote();
		int totComplete = 0;
		int totOrdened = 0;
		int totonHold = 0;
	    int totFeedback = 0;
		int statusComplete = 2;
		int statusOrdened = 4;
		int statusfeedBackPending = 5;
		int statusonHold = 3;
		int statusCancel = 7;
		
		System.out.print("*** 1 -  AUTOMATIC STATUS ");
		
		ItemStatus itStat = iStatRep.findOneById(statusCancel);
		totCancel = itemRep.countByQuoteAndStatRegAndItemStatus(q, "0", itStat);
		
		totItems = itemRep.countByQuoteAndStatReg(q, "0");
		
		totItems = totItems - totCancel;
		
        itStat = iStatRep.findOneById(statusProgress);
		totProgress = itemRep.countByQuoteAndStatRegAndItemStatus(q, "0", itStat);
		
		if (totProgress > 0) {
			newStaQuote = "1";
		}
		
		itStat = iStatRep.findOneById(statusComplete);
		totComplete = itemRep.countByQuoteAndStatRegAndItemStatus(q, "0", itStat);
		
		if (totComplete == totItems) {
			//newStaQuote = "4";	complete estimate	
			newStaQuote = "5";	//feedback pending	
		}
		
		if ((totComplete > 0) && (totComplete < totItems)){
			
			newStaQuote = "3";
			
		}
		
		//------------------------------------------------------------------------
		
		itStat = iStatRep.findOneById(statusonHold);
		totonHold = itemRep.countByQuoteAndStatRegAndItemStatus(q, "0", itStat);	
		
		if (totonHold == totItems) {
			newStaQuote = "7";        // Quote to Hold
		}
		
		
		itStat = iStatRep.findOneById(statusfeedBackPending);
		totFeedback = itemRep.countByQuoteAndStatRegAndItemStatus(q, "0", itStat);	
		
		if (totFeedback > 0) {
			newStaQuote = "5";        // Quote to FeedBackPending
		}		
		
		//----------------------------------------------------------------------------
		
		itStat = iStatRep.findOneById(statusOrdened);
		totOrdened = itemRep.countByQuoteAndStatRegAndItemStatus(q, "0", itStat);	
		
		
		
		if (totOrdened > 0) {
			newStaQuote = "6";
			q.setDateorderReceived(LocalDateTime.now());
		}
		
		if ((q.getStatQuote().equals("5")) || (q.getStatQuote().equals("7"))){
			newStaQuote = q.getStatQuote();
		}
		
		
		
		q.setLastStatus(q.getStatQuote());
		q.setStatQuote(newStaQuote);
		quoteRep.saveAndFlush(q);
		
		
	
		
	}
	
	
	@RequestMapping(value = "updateitemStatus", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Item> updateitemStatus(@RequestParam("newStat") int newStat, @RequestBody Item it) {	
		
		int status = newStat; // To New Item
		ItemStatus itStat = iStatRep.findOneById(status);
		Item item = itemRep.findOneByIdItem(it.getIdItem());
		item.setItemStatus(itStat);
		item.setLastUpdate(LocalDateTime.now());	
		
		if (itStat.getOrdened().equals("Y")) {
			item.setDateOrdered(LocalDateTime.now());
			item.setPoAmount(it.getPoAmount());
			item.setDateRegisterOrdered(it.getDateRegisterOrdered());
			item.setPoNumber(it.getPoNumber());
		}	

		itemRep.saveAndFlush(item);	
		if (itStat.getId() == 4) {
			Quote q1 = item.getQuote();
			q1.setStatQuote("6");
			quoteRep.saveAndFlush(q1);
		}

		updateAutmaticStatus(item.getQuote());
		return new ResponseEntity<Item>(it, HttpStatus.OK);
	}
	
	@RequestMapping(value = "item/delete", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Item> removeTempItem(@RequestBody Item t){
		
	
		Item tAux = itemRep.findOneByIdItem(t.getIdItem());
		tAux.setStatReg("1");
		tAux.setDateDeleted(LocalDateTime.now());
		tAux.setUserDeleted(t.getUserDeleted());
		itemRep.save(tAux);
		
		/*
		
    	QuoteNote qn = new QuoteNote();
    	qn.setComment("New Quote Created");
    	qn.setQuote(t.getQuote());
    	qn.setStatReg("0");
    	qn.setUser(t.getUserDeleted());
    	qn.setCreatedDate(LocalDateTime.now());
    	quotenoteRep.save(qn); */
		
		return new ResponseEntity<Item>(tAux, HttpStatus.OK);
	}
	
	@RequestMapping(value = "item/update", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Item> updateItemDet(@RequestBody Item t){
		

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
        	
        	
        }else {
        	
            if (t.getPart() == null) {
        	    
            	
		        	List<Part> lPart = partRep.findAll();
		    		Part newPart = new Part();
		    		newPart.setCreatedDate(LocalDateTime.now());
		    		newPart.setPartDesc(t.getNewPartName());
		    		newPart.setPartNumber("NEW" + Integer.toString(lPart.size()));
		    		newPart.setStatReg("0");
		    		partRep.saveAndFlush(newPart);
		    		
		    		t.setPart(newPart);
            	
            }else {
            	
            	    Part p1 = new Part();
            	    p1 = t.getPart();
            	    p1.setPartDesc(t.getNewPartName());
            	    partRep.saveAndFlush(p1);
            }	
        }
		
		
		Item tAux = itemRep.findOneByIdItem(t.getIdItem());
		tAux = t;		
		
		ItemStatus itStat = iStatRep.findOneById(t.getItemStatus().getId());
		tAux.setItemStatus(itStat);
		
    	itemRep.saveAndFlush(tAux);
		
		return new ResponseEntity<Item>(tAux, HttpStatus.OK);
		
		
		
	}	
	
	
	@RequestMapping(value = "item/insert", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Item> insertItem(@RequestBody Item t){
		

       
		
		if (t.getItemType().getId() == 4) {  // Parts from expandable
        	
        	
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
        	
        	
        }else {
        	
        	
        	if (t.getPart() == null) {
        	
	        	List<Part> lPart = partRep.findAll();
	    		Part newPart = new Part();
	    		newPart.setCreatedDate(LocalDateTime.now());
	    		newPart.setPartDesc(t.getNewPartName());
	    		newPart.setPartNumber("NEW" + Integer.toString(lPart.size()));
	    		partRep.saveAndFlush(newPart);
	    		
	    		t.setPart(newPart);
    		
        	}
        }
		
		Item iAux = new Item();
		
		
        List<Item> lItem = itemRep.findAll();
        String nroItem = "0";
        		
        if  (lItem.size() > 99){
                	nroItem = "A000" + Integer.toString(lItem.size());
           	
        }       		
        
        if (lItem.size() > 999){
        	nroItem = "A00" + Integer.toString(lItem.size());
   	
        }
    	    	
    	Item newItem = new Item();
    	
    	
    	
    	newItem.setAnualUsage(t.getAnualUsage());
    	newItem.setCadfileAva(t.getCadfileAva());
    	newItem.setConcernsCurr(t.getConcernsCurr());
    	newItem.setCreatedDate(t.getCreatedDate());
    	newItem.setDrawingAva(t.getDrawingAva());
    	newItem.setFob(t.getFob());
    	newItem.setIndustry(t.getIndustry());
    	newItem.setItemNumber(nroItem);
    	newItem.setItemStatus(t.getItemStatus());
    	newItem.setItemType(t.getItemType());
    	newItem.setStatReg("0");
    	newItem.setQuantity(t.getQuantity());
    	newItem.setTargetPrice(t.getTargetPrice());
    	newItem.setRfqdueDate(t.getRfqdueDate());
    	newItem.setQitemType(t.getQitemType());
    	newItem.setPart(t.getPart());
    	newItem.setNewPartName(t.getNewPartName());
    	newItem.setMeasureUnit(t.getMeasureUnit());
    	newItem.setNote(t.getNote());
    	newItem.setTeslaModel(t.getTeslaModel());
    	newItem.setSuggestMat(t.getSuggestMat());
    	newItem.setSuggestVend(t.getSuggestVend());
    	newItem.setNewPart(t.getNewPart());
    	newItem.setPartKissCut(t.getPartKissCut());
    	newItem.setQuote(t.getQuote());
    	
    	
    	newItem.setCreatedDate(LocalDateTime.now());
    	newItem.setUsers(t.getUsers());

        
    	//newItem.setQuote(q);
    	
	
		
		
    	//newItem = t;
    	
		int status = 0; // To New Item
		ItemStatus itStat = iStatRep.findOneById(status);

		newItem.setItemStatus(itStat);
		
		itemRep.saveAndFlush(newItem);
		
		QuoteNote qn = new QuoteNote();
		qn.setComment("Comment on Item " + newItem.getItemNumber() + " : New Item has been added");
		qn.setCreatedDate(LocalDateTime.now());
		qn.setQuote(newItem.getQuote());
		qn.setStatReg("0");
		qn.setUser(newItem.getUsers());
		
		quotenoteRep.save(qn);
				
		
		return new ResponseEntity<Item>(newItem, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(value = "copyMatdetail", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Item> copy_matDetail(@RequestParam("itCopy") int itCopy, @RequestBody Item itNew ) {
		
		
	  Item itAux = itemRep.findOneByIdItem(itCopy);
	  List<MatDetails> listMd = matDetailRep.findAllByItemAndStatRegOrderById(itAux, "0");
      Iterator iMaterials =  listMd.iterator();
      
	  int status = 1; // To New Item
	  ItemStatus itStat = iStatRep.findOneById(status);
      
	  itNew.setItemStatus(itStat);
      itNew.setCavities(itAux.getCavities());
      itNew.setMargin(itAux.getMargin());
      itNew.setPackagingCost(itAux.getPackagingCost());
      itNew.setScrapRate(itAux.getScrapRate());
      itNew.setToolingCost(itAux.getToolingCost());
      itemRep.saveAndFlush(itNew);
          
            
      while (iMaterials.hasNext()){
      	//Material currMat = (Material) iMaterials.next();
       	  
      	MatDetails mat = (MatDetails) iMaterials.next();
      	
      	MatDetails newMat = new MatDetails();
      	newMat.setCostUm(mat.getCostUm());
      	newMat.setCreateDate(LocalDateTime.now());
      	newMat.setFreight(mat.getFreight());
      	newMat.setItem(itNew);
      	newMat.setLaborCost(mat.getLaborCost());
      	newMat.setLaborRate(mat.getLaborRate());
      	newMat.setLf(mat.getLf());
      	newMat.setMachine(mat.getMachine());
      	newMat.setMatDescription(mat.getMatDescription());
      	newMat.setMaterial(mat.getMaterial());
      	newMat.setMatWidht(mat.getMatWidht());
      	newMat.setMeasureUnit(mat.getMeasureUnit());
      	newMat.setNotesMat(mat.getNotesMat());
      	newMat.setNroRolls(mat.getNroRolls());
      	newMat.setPriceEach(mat.getPriceEach());
      	newMat.setProgression(mat.getProgression());
      	newMat.setQtyHour(mat.getQtyHour());
      	newMat.setSlitWidth(mat.getSlitWidth());
      	newMat.setStatReg("0");
      	newMat.setStepDescription(mat.getStepDescription());
      	newMat.setTool(mat.getTool());
      	newMat.setTotalCost(mat.getTotalCost());
      	newMat.setTypeDetail(mat.getTypeDetail());
      	newMat.setUser(mat.getUser());
      	newMat.setYield(mat.getYield());
      	newMat.setTypeMaterial(mat.getTypeMaterial());

      	newMat.setNotesMat(mat.getNotesMat());
      	newMat.setFreightCost(mat.getFreightCost());
      	

      	matDetailRep.saveAndFlush(newMat);     	

      }
      
      
     
      return new ResponseEntity<Item>(itNew, HttpStatus.OK);
	     
	}	
	
	
	@RequestMapping("getItem")
	@ResponseBody
	public Item findOne(long id){
		Item q = itemRep.findOneByIdItem(id);
		System.out.println("item actual" + id);
				
		return q;
	}
	
	
	@RequestMapping(value = "item/new", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Item> insertNewItem(@RequestBody Item t){
		

		Item newItem = new Item();
		
		if (t.getItemType().getId() == 4) {  // Parts from expandable
        	
        	
        	boolean pAux = partRep.existsByPartNumber(t.getPart().getPartNumber());
        	Part p = new Part();
        	
        	if (pAux == false) {
        	
        	
        	
        	p = t.getPart();
        	p.setCreatedDate(LocalDateTime.now());
        	p.setStatReg("0");
        	partRep.saveAndFlush(p);
        	newItem.setPart(p);
        	
        	}
        	else
        	{
        	    p = partRep.findOneByPartNumber(t.getPart().getPartNumber());
        	    newItem.setPart(p);
        	    
        	}
        	
        	
        }else {
        	
        	
        	if (t.getPart() == null) {
        	
	        	List<Part> lPart = partRep.findAll();
	    		Part newPart = new Part();
	    		newPart.setCreatedDate(LocalDateTime.now());
	    		newPart.setPartDesc(t.getNewPartName());
	    		newPart.setPartNumber("NEW" + Integer.toString(lPart.size()));
	    		partRep.saveAndFlush(newPart);
	    		
	    		newItem.setPart(newPart);
    		
        	}
        }
		
		Item iAux = new Item();
		
		
        List<Item> lItem = itemRep.findAll();
        String nroItem = "0";
        		
        if  (lItem.size() > 99){
                	nroItem = "A000" + Integer.toString(lItem.size());
           	
        }       		
        
        if (lItem.size() > 999){
        	nroItem = "A00" + Integer.toString(lItem.size());
   	
        }
    	    	
    	
    	
    	
    	
    	newItem.setAnualUsage(t.getAnualUsage());
    	newItem.setCadfileAva(t.getCadfileAva());
    	newItem.setConcernsCurr(t.getConcernsCurr());
    	newItem.setCreatedDate(t.getCreatedDate());
    	newItem.setDrawingAva(t.getDrawingAva());
    	newItem.setFob(t.getFob());
    	newItem.setIndustry(t.getIndustry());
    	newItem.setItemNumber(nroItem);
    	newItem.setItemStatus(t.getItemStatus());
    	newItem.setItemType(t.getItemType());
    	newItem.setStatReg("0");
    	newItem.setQuantity(t.getQuantity());
    	newItem.setTargetPrice(t.getTargetPrice());
    	newItem.setRfqdueDate(t.getRfqdueDate());
    	newItem.setQitemType(t.getQitemType());
    	newItem.setPart(t.getPart());
    	newItem.setNewPartName(t.getNewPartName());
    	newItem.setMeasureUnit(t.getMeasureUnit());
    	newItem.setNote(t.getNote());
    	newItem.setTeslaModel(t.getTeslaModel());
    	newItem.setSuggestMat(t.getSuggestMat());
    	newItem.setSuggestVend(t.getSuggestVend());
    	newItem.setNewPart(t.getNewPart());
    	newItem.setPartKissCut(t.getPartKissCut());
    	newItem.setQuote(t.getQuote());
    	
    	
    	newItem.setCreatedDate(LocalDateTime.now());
    	newItem.setUsers(t.getUsers());

        
    	//newItem.setQuote(q);
    	
	
		
		
    	//newItem = t;
    	
		int status = 0; // To New Item
		ItemStatus itStat = iStatRep.findOneById(status);

		newItem.setItemStatus(itStat);
		
		itemRep.saveAndFlush(newItem);
		
		QuoteNote qn = new QuoteNote();
		qn.setComment("Comment on Item " + newItem.getItemNumber() + " : New Item has been added");
		qn.setCreatedDate(LocalDateTime.now());
		qn.setQuote(newItem.getQuote());
		qn.setStatReg("0");
		qn.setUser(newItem.getUsers());
		
		quotenoteRep.save(qn);
				
		
		return new ResponseEntity<Item>(newItem, HttpStatus.OK);
		
	}
	
	


}
