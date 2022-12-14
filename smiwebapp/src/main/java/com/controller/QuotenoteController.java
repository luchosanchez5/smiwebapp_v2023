package com.controller;

import java.time.LocalDateTime;
import java.util.List;

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

import com.model.Item;
import com.model.ItemActivity;
import com.model.Quote;
import com.model.QuoteNote;
import com.model.Users;
import com.repository.ItemactivityRep;
import com.repository.QuoteNoteRep;
import com.repository.QuoteRep;

@Controller
public class QuotenoteController {
	
	
	@Autowired
	private QuoteNoteRep quotenoteRep;
	
	@Autowired
	private QuoteRep quoteRep;
	
	@Autowired
	private ItemactivityRep iactRep;
	
	
	
	@RequestMapping("list-quotesnotes")
	@ResponseBody
	private List<QuoteNote> findAllByQuote(long id){
		
		Quote q = quoteRep.findById(id);
		List<QuoteNote> listN = quotenoteRep.findAllByStatRegAndQuote("0", q);
		return listN;
		
	}
	
	@RequestMapping("list-quotesnotesbytype")
	@ResponseBody
	private List<QuoteNote> findAllByQuoteByType(long id, String type){
		
		Quote q = quoteRep.findById(id);
		List<QuoteNote> listN = quotenoteRep.findAllByStatRegAndQuoteAndTypeNoteOrderByCreatedDateDesc("0", q,type);
		System.out.println("TOTAL NOTES  : " + listN.size()  );
		return listN;
		
	}	
	
	
	@RequestMapping(value = "quote/note/add", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Quote> addquoteNote(@RequestParam("quoteId") long quoteId, @RequestParam("message") String message, @RequestParam("typeNote") String typeNote, @RequestBody Users user){
		
		Quote q = quoteRep.findById(quoteId);
        
    	QuoteNote qn = new QuoteNote();
    	qn.setComment(message);
    	qn.setQuote(q);
    	qn.setStatReg("0");
    	qn.setUser(user);
    	qn.setTypeNote("F");
    	qn.setCreatedDate(LocalDateTime.now());
    	quotenoteRep.save(qn);
    	
    	q.setFeedbackUser(user);
    	q.setLastfeedbackDate(LocalDateTime.now());
	
		
		return new ResponseEntity<Quote>(q, HttpStatus.OK);
	}	
	
	
	@RequestMapping(value = "quote/note/markread", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Quote> markasread(@RequestParam("idNote") long id, @RequestParam("type") String type, @RequestBody Users user ){
		
		
		Quote q = null;
		if (type.equals("item")) {
				
			ItemActivity it = iactRep.findOneByIdItemActivity(id);
			it.setStat_read_estimator("1");
			iactRep.save(it); 
			
		}else
		{
			
			QuoteNote qn = quotenoteRep.findOneById(id);
			qn.setStat_read_estimator("1");
			quotenoteRep.save(qn);
			
		}
		
		

		
		return new ResponseEntity<Quote>(q, HttpStatus.OK);
	}	
	

}
