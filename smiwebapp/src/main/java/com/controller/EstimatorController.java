package com.controller;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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
import com.model.QueryQuotes;
import com.model.Quote;
import com.model.Seller;
import com.model.Users;
import com.repository.EstimatorRep;
import com.repository.QuoteRep;
import com.repository.UserRep;

@Controller
public class EstimatorController {
	
	@Autowired
	private EstimatorRep estimatorRep;
	
	@Autowired
	private UserRep userRep;
	
	@Autowired
	private QuoteRep quoteRep;	
	
	
	@RequestMapping("findOneEstimatorUser")
	@ResponseBody
	public Estimator findOnebyuser(String username){
		
		Users user = userRep.findByUsername(username);
		Estimator c = estimatorRep.findOneByUser(user);
		return c;
	}
	
	
	@RequestMapping("list-estimators")
	@ResponseBody
	private List<Estimator> findAllEstimatorByStatReg(){
		
		List<Estimator> listEst = estimatorRep.findAllByStatReg("0");
		return listEst;
	}
	
	
	@RequestMapping(value = "estimator/new", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Estimator> new_estimator(@RequestBody Estimator s){
		s.setStatReg("0");
		s.setStatEstimator("0");
		s.setCreatedDate(LocalDateTime.now());
		estimatorRep.save(s);
		return new ResponseEntity<Estimator>(s, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "estimator/delete/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Estimator> delete_estimator(@PathVariable("id") long id){
		Estimator currentCust = estimatorRep.findOneById(id);
		currentCust.setStatReg("1");
		estimatorRep.save(currentCust);
		return new ResponseEntity<Estimator>(HttpStatus.NO_CONTENT);
	}
	
	
	
	@RequestMapping(value = "estimator/edit/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Estimator> edit_estimator(@PathVariable("id") int id,@RequestBody Estimator c){
		
		Estimator currentCust = estimatorRep.findOneById(id);
		currentCust = c;
		estimatorRep.save(currentCust);
		System.out.println("Seller : " + c.getSureName());
		return new ResponseEntity<Estimator>(c, HttpStatus.OK);
	}		
	
	
	@RequestMapping("estimators/indicators")
	@ResponseBody	
	private List<Long> indicatorsEstimators(String user){
		
		//Users us = userRep.findUserById(user);
		Users us = userRep.findByUsername(user);
		
		Estimator e = estimatorRep.findOneByUser(us);
		
		List<Long> listI = new ArrayList<Long>();
		long totQ = estimatorRep.findTotalQuotesEstimator(BigInteger.valueOf(e.getId()));
		listI.add(totQ);
		long totI = estimatorRep.findTotalItemsEstimator(e.getId());
		listI.add(totI);
		long totPen = estimatorRep.findTotalItemsPending(e.getId());
		listI.add(totPen);
		long totOr = estimatorRep.findTotalItemsOrder(e.getId());
		listI.add(totOr);
		
		long totQPend = estimatorRep.findTotalQuotesPendingEstimator(BigInteger.valueOf(e.getId()));
		listI.add(totQPend);
		
		long totOrd = estimatorRep.findTotalAmountOrder(e.getId());
		listI.add(totOrd);
		
		long totQHold = estimatorRep.findTotalQuotesOnHoldEstimator(BigInteger.valueOf(e.getId()));
		listI.add(totQHold);
		
		long totOrdered = estimatorRep.findTotalQuotesOrderedEstimator(BigInteger.valueOf(e.getId()));
		listI.add(totOrdered);
		
		
		return listI;
		
	}
	
	@RequestMapping("estimators/indicatorsYear")
	@ResponseBody	
	private List<Long> indicatorsEstimatorsYear(String user, long year){
		
		//Users us = userRep.findUserById(user);
		Users us = userRep.findByUsername(user);
		Estimator e = estimatorRep.findOneByUser(us);
		List<Long> listI = new ArrayList<Long>();
		long totQ = estimatorRep.findTotalQuotesEstimatorYear(BigInteger.valueOf(e.getId()), year);
		listI.add(totQ);
		long totI = estimatorRep.findTotalItemsEstimatorYear(e.getId(), year);
		listI.add(totI);
		long totPen = estimatorRep.findTotalItemsPendingYear(e.getId(),year);
		listI.add(totPen);
		long totOr = estimatorRep.findTotalItemsOrderYear(e.getId(),year);
		listI.add(totOr);
		
		long totQPend = estimatorRep.findTotalQuotesPendingEstimatorYear(BigInteger.valueOf(e.getId()), year);
		listI.add(totQPend);
		
		long totOrd = estimatorRep.findTotalAmountOrderYear(e.getId(),year);
		listI.add(totOrd);
		
		long totQHold = estimatorRep.findTotalQuotesOnHoldEstimatorYear(BigInteger.valueOf(e.getId()), year);
		listI.add(totQHold);
		
		long totOrdered = estimatorRep.findTotalQuotesOrderedEstimatorYear(BigInteger.valueOf(e.getId()), year);
		listI.add(totOrdered);
		
		
		return listI;
		
	}
	
	
	@RequestMapping(value="estimators/lastQuotes")
	@ResponseBody
	private List<Quote> findAllQuotesByEstimator(String user){
		
		
		Users us = userRep.findByUsername(user);
   	    Estimator e = estimatorRep.findOneByUser(us);		
		List<Quote> listQ = quoteRep.findAllByStatRegAndEstimatorOrderByCreatedDateDesc("0",e);
		
		return listQ;
	}
	
	
	@RequestMapping(value="estimators/graphicQuotesPerDayEstimator")
	@ResponseBody	
	public List<QueryQuotes> graphicQuotesPerDayEstimator(String user){
		List<QueryQuotes> lista = new ArrayList<QueryQuotes>();
		Users us = userRep.findByUsername(user);
   	    Estimator e = estimatorRep.findOneByUser(us);
		List<Object[]> resulLeads = estimatorRep.graphicQuotesPerDayEstimator(e.getId());
	    Iterator vLista = resulLeads.iterator();
	    
	    for (int j = 0; j < resulLeads.size(); j++){
	    	QueryQuotes qSel = new QueryQuotes();
	    	Date qDate = (Date) resulLeads.get(j)[0];
	    	BigInteger tot = (BigInteger) resulLeads.get(j)[1];
	    	
	    	qSel.setDay(qDate);
	    	qSel.setTotQuotes(tot.intValue());
	    	lista.add(qSel);
	    }
	    
	    		
		return lista;
	}
	
	public List<QueryQuotes> colorDonut(){
		
		List<QueryQuotes> lista = new ArrayList<QueryQuotes>();
		List<String> backColor =  new ArrayList<String>();
		List<String> hoverColor =  new ArrayList<String>();
		
		backColor.add("#BDC3C7");
		backColor.add("#9B59B6");
		backColor.add("#A74C3C");
		backColor.add("#26B99A");
		backColor.add("#3498DB");
		backColor.add("#33CCFF");
		backColor.add("#33FFFF");
		backColor.add("#33FFFF");
		
		hoverColor.add("#CFD4D8");
		hoverColor.add("#B370CF");
		hoverColor.add("#E95E4F");
		hoverColor.add("#36CAAB");
		hoverColor.add("#49A9EA");
		hoverColor.add("#CCCC00");
		hoverColor.add("#CCFF33");
		hoverColor.add("#CCFF33");
		

		QueryQuotes qq = new QueryQuotes();
		qq.setBackColor(backColor);
		qq.setHover(hoverColor);
		
		lista.add(qq);
		
		return lista;
	}
	
	
	@RequestMapping(value="estimators/donutchartestimator")
	@ResponseBody
	public QueryQuotes donutchartindustryestimator(String user, String typeGraph){
		
		List<QueryQuotes> lista = new ArrayList<QueryQuotes>();
		Users us = userRep.findByUsername(user);
   	    Estimator e = estimatorRep.findOneByUser(us);
   	    List<Object[]> resulLeads = new ArrayList<Object[]>();
   	    
   	    if (typeGraph.equals("1")) {
   	    	resulLeads = estimatorRep.donutChartIndustryEstimator(e.getId());
   	    }else if (typeGraph.equals("2")) {
   	    	resulLeads = estimatorRep.donutChartItemtypeEstimator(e.getId());
   	    }else if (typeGraph.equals("3")) {
   	    	resulLeads = estimatorRep.donutChartQItemtypeEstimator(e.getId());
   	    }
   	    	
	    Iterator vLista = resulLeads.iterator();
	    List<QueryQuotes> colors = colorDonut();
	    List<Integer> totals = new ArrayList<Integer>();
	    List<String> labels =  new ArrayList<String>();
		List<String> backColor =  new ArrayList<String>();
		List<String> hoverColor =  new ArrayList<String>();	    
	    
		QueryQuotes qSel = new QueryQuotes();
	    for (int j = 0; j < resulLeads.size(); j++){
	    	
	    	
	    	BigInteger tot = (BigInteger) resulLeads.get(j)[0];
	    	totals.add(tot.intValue());
	    	String label   = (String) resulLeads.get(j)[1];
	    	labels.add(label);
	    	
	    	backColor.add(colors.get(0).getBackColor().get(j));
	    	hoverColor.add(colors.get(0).getHover().get(j));
	    	
	    }	
	    
    	qSel.setTotals(totals);
    	qSel.setLabels(labels);
    	qSel.setBackColor(backColor);
    	qSel.setHover(hoverColor);
    	
    		
		
		return qSel;
	}
	
	
	@RequestMapping(value="estimators/sparklinesestimator")
	@ResponseBody
	public QueryQuotes sparklinesestimator(String user, String typeGraph){
		QueryQuotes qSel = new QueryQuotes();
		List<Object[]> resulQ = new ArrayList<Object[]>();
		Users us = userRep.findByUsername(user);
   	    Estimator e = estimatorRep.findOneByUser(us);
   	    long tot = 0;
   	    
		if (typeGraph.equals("1")) {
		    resulQ = estimatorRep.sparkline1Estimator(e.getId()); 
	        tot    = estimatorRep.totalSparkline1Estimator(e.getId()); 		
		}else if (typeGraph.equals("2")) {
			resulQ = estimatorRep.sparkline2Estimator(e.getId());
			tot    = estimatorRep.totalSparkline2Estimator(e.getId());
		}else if (typeGraph.equals("3")) {
		    resulQ = estimatorRep.sparkline3Estimator(e.getId()); 
	        tot    = estimatorRep.totalSparkline3Estimator(e.getId()); 			
    	}else if (typeGraph.equals("4")) {
			resulQ = estimatorRep.sparkline4Estimator(e.getId());
		}
		
		List<Integer> totals = new ArrayList<Integer>();
		List<Double> totalsDbl = new ArrayList<Double>();
	    for (int j = 0; j < resulQ.size(); j++){
	    	
	    	
	    	
	    	if (typeGraph.equals("1") || typeGraph.equals("3") || typeGraph.equals("4")) {
	    		BigInteger totq = (BigInteger) resulQ.get(j)[0];
	    		totals.add(totq.intValue());
	    	}else {
	    		double totq  = (double) resulQ.get(j)[0];
	    		totalsDbl.add(totq);
	    	}
	    	
	    }
	    
	    qSel.setTotals(totals);
	    qSel.setTotSpark(tot);
	    qSel.setTotalsDbl(totalsDbl);
	    qSel.setTotSparkDbl(tot);
	    
		return qSel;
	}
	
			
}
