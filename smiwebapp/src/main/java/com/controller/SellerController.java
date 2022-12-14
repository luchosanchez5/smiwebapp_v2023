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
import com.repository.QuoteRep;
import com.repository.SellerRep;
import com.repository.UserRep;


@Controller
public class SellerController {
	
	@Autowired
	private SellerRep sellerRep;
	
	@Autowired
	private UserRep userRep;
	
	@Autowired
	private QuoteRep quoteRep;
	
	@RequestMapping("list-sellers")
	@ResponseBody
	private List<Seller> findAllSellersbyStatReg(){
		
		List<Seller> listSel = sellerRep.findAllByStatSellerAndStatReg("0", "0");
		return listSel;
	}
	
	@RequestMapping(value = "seller/new", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Seller> new_customer(@RequestBody Seller s){
		s.setStatReg("0");
		s.setStatSeller("0");
		s.setCreatedDate(LocalDateTime.now());
		sellerRep.save(s);
		return new ResponseEntity<Seller>(s, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "seller/delete/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Seller> delete_customer(@PathVariable("id") long id){
		Seller currentCust = sellerRep.findOneById(id);
		currentCust.setStatReg("1");
		sellerRep.save(currentCust);
		return new ResponseEntity<Seller>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(value = "seller/edit/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Seller> edit_customer(@PathVariable("id") int id,@RequestBody Seller c){
		
		Seller currentCust = sellerRep.findOneById(id);
		currentCust = c;
		sellerRep.save(currentCust);
		System.out.println("Seller : " + c.getSureName());
		return new ResponseEntity<Seller>(c, HttpStatus.OK);
	}	
	
	@RequestMapping("sellers/indicators")
	@ResponseBody	
	private List<Long> indicatorsSellers(String user){
		
		
		Users us = userRep.findByUsername(user);
		Seller e = sellerRep.findOneByUser(us);
		
		List<Long> listI = new ArrayList<Long>();
		long totQ = sellerRep.findTotalQuotesEstimator(BigInteger.valueOf(e.getId()));
		listI.add(totQ);
		long totI = sellerRep.findTotalItemsEstimator(e.getId());
		listI.add(totI);
		long totPen = sellerRep.findTotalItemsPending(e.getId());
		listI.add(totPen);
		long totOr = sellerRep.findTotalItemsOrder(e.getId());
		listI.add(totOr);
		
		long amountOrd = sellerRep.findTotalAmountOrder(e.getId());
		listI.add(amountOrd);
		
		return listI;		
			
	}
	
	@RequestMapping(value="sellers/lastQuotes")
	@ResponseBody
	private List<Quote> findAllQuotesByEstimator(String user){
		
		
		Users us = userRep.findByUsername(user);
   	    Seller e = sellerRep.findOneByUser(us);		
		List<Quote> listQ = quoteRep.findAllByStatRegAndSellerOrderByCreatedDateDesc("0",e);
		
		return listQ;
	}	
	
	@RequestMapping(value="sellers/graphicQuotesPerDayEstimator")
	@ResponseBody	
	public List<QueryQuotes> graphicQuotesPerDayEstimator(String user){
		List<QueryQuotes> lista = new ArrayList<QueryQuotes>();
		Users us = userRep.findByUsername(user);
   	    Seller e = sellerRep.findOneByUser(us);
		List<Object[]> resulLeads = sellerRep.graphicQuotesPerDaySeller(e.getId());
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
		
		backColor.add("#33FFFF");
		backColor.add("#9B59B6");
		backColor.add("#E74C3C");
		backColor.add("#26B99A");
		backColor.add("#3498DB");
		backColor.add("#33CCFF");
		
		backColor.add("#BDC3C7");
		
		
		hoverColor.add("#CCFF33");
		hoverColor.add("#B370CF");
		hoverColor.add("#E95E4F");
		hoverColor.add("#36CAAB");
		hoverColor.add("#49A9EA");
		hoverColor.add("#CCCC00");
		
		hoverColor.add("#CFD4D8");
		

		QueryQuotes qq = new QueryQuotes();
		qq.setBackColor(backColor);
		qq.setHover(hoverColor);
		
		lista.add(qq);
		
		return lista;
	}
	
	
	@RequestMapping(value="sellers/donutchartseller")
	@ResponseBody
	public QueryQuotes donutchartindustryestimator(String user, String typeGraph){
		
		List<QueryQuotes> lista = new ArrayList<QueryQuotes>();
		Users us = userRep.findByUsername(user);
   	    Seller e = sellerRep.findOneByUser(us);
   	    List<Object[]> resulLeads = new ArrayList<Object[]>();
   	    
   	    if (typeGraph.equals("1")) {
   	    	resulLeads = sellerRep.donutChartIndustryEstimator(e.getId());
   	    }else if (typeGraph.equals("2")) {
   	    	resulLeads = sellerRep.donutChartItemtypeEstimator(e.getId());
   	    }else if (typeGraph.equals("3")) {
   	    	resulLeads = sellerRep.donutChartQItemtypeEstimator(e.getId());
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
	
	
	@RequestMapping(value="sellers/sparklinesseller")
	@ResponseBody
	public QueryQuotes sparklinesestimator(String user, String typeGraph){
		QueryQuotes qSel = new QueryQuotes();
		List<Object[]> resulQ = new ArrayList<Object[]>();
		Users us = userRep.findByUsername(user);
   	    Seller e = sellerRep.findOneByUser(us);
   	    long tot = 0;
   	    
		if (typeGraph.equals("1")) {
		    resulQ = sellerRep.sparkline1Estimator(e.getId()); 
	        tot    = sellerRep.totalSparkline1Estimator(e.getId()); 		
		}else if (typeGraph.equals("2")) {
			resulQ = sellerRep.sparkline2Estimator(e.getId());
			tot    = sellerRep.totalSparkline2Estimator(e.getId());
		}else if (typeGraph.equals("3")) {
		    resulQ = sellerRep.sparkline3Estimator(e.getId()); 
	        tot    = sellerRep.totalSparkline3Estimator(e.getId()); 			
    	}else if (typeGraph.equals("4")) {
			resulQ = sellerRep.sparkline4Estimator(e.getId());
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
	
	
	
	@RequestMapping(value="sellers/percentitemsseller")
	@ResponseBody	
	public List<QueryQuotes> percentitemsseller(long idSeller){
		
		List<QueryQuotes> lista = new ArrayList<QueryQuotes>();
		List<Object[]> resulLeads = sellerRep.percentitemseller(idSeller);
	    Iterator vLista = resulLeads.iterator();
	    
	    for (int j = 0; j < resulLeads.size(); j++){
	    	QueryQuotes qSel = new QueryQuotes();
         	String desc = (String) resulLeads.get(j)[0];
	    	BigDecimal tot = (BigDecimal) resulLeads.get(j)[1];
	    	
	    	qSel.setName(desc);
	    	qSel.setTotal(tot.longValue());

	    	lista.add(qSel);
	    }
	    
	    		
		return lista;
	}
	
	
	@RequestMapping(value="sellers/activities")
	@ResponseBody	
	public List<QueryQuotes> activitiesseller(long idSeller){
		
		List<QueryQuotes> lista = new ArrayList<QueryQuotes>();
		List<Object[]> resulLeads = sellerRep.sellerActivities(idSeller);
	    Iterator vLista = resulLeads.iterator();
	    
	    for (int j = 0; j < resulLeads.size(); j++){
	    	QueryQuotes qSel = new QueryQuotes();

	    	
	    	String d = (String) resulLeads.get(j)[1];
	    	String itNum = (String) resulLeads.get(j)[2];
	    	String image = (String) resulLeads.get(j)[3];
	    	Date dItem = (Date) resulLeads.get(j)[4];
	    	
	    	d = "Comment on Item # : " + d;
	    	
	    	
	    	qSel.setName(d);
	    	qSel.setStatus(image);
	    	qSel.setDay(dItem);

	    	lista.add(qSel);
	    }
	    
	    		
		return lista;
	}	
	
	
	@RequestMapping(value="sellers/customers")
	@ResponseBody	
	public List<QueryQuotes> customersSeller(String username){
		
		Users us = userRep.findByUsername(username);
		Seller sel = sellerRep.findOneByIdUser(us.getId().toString());	
		List<QueryQuotes> lista = new ArrayList<QueryQuotes>();
		List<Object[]> resulLeads = sellerRep.findAllCustomersSellers(sel.getId());
	    Iterator vLista = resulLeads.iterator();
	    
	    for (int j = 0; j < resulLeads.size(); j++){
	    	QueryQuotes qSel = new QueryQuotes();

	    	BigInteger id = (BigInteger) resulLeads.get(j)[0];
	    	String contactEmail = (String) resulLeads.get(j)[2];
	    	String contactName = (String) resulLeads.get(j)[3];
	    	String contactPhone = (String) resulLeads.get(j)[4];
	    	String customerName = (String) resulLeads.get(j)[5];
	    	BigInteger tot = (BigInteger) resulLeads.get(j)[7];

	    	
	    	qSel.setIdCustomer(id.longValue()); 
	    	qSel.setCustomerName(customerName);
	    	qSel.setContactName(contactName);
	    	qSel.setContactEmail(contactEmail);
	    	qSel.setContactPhone(contactPhone);
	    	qSel.setTotal(tot.longValue());

	    	lista.add(qSel);
	    }
	    
	    		
		return lista;
	}	
	
	
	
	@RequestMapping(value="sellers/sellergrprofile")
	@ResponseBody	
	public List<QueryQuotes> sellergrprofile(long idSeller){
		
		List<QueryQuotes> lista = new ArrayList<QueryQuotes>();
		List<Object[]> resulLeads = sellerRep.graphicprofileseller(idSeller);
	    Iterator vLista = resulLeads.iterator();
	    
	    for (int j = 0; j < resulLeads.size(); j++){
	    	QueryQuotes qSel = new QueryQuotes();
	    	
	    	BigInteger tot = (BigInteger) resulLeads.get(j)[0];
	    	String month = (String) resulLeads.get(j)[1];
	    	
    	
	    	qSel.setName(month);
	    	qSel.setTotal(tot.longValue());
	    	

	    	lista.add(qSel);
	    }
	    
	    		
		return lista;
	}
	
	@RequestMapping("findOneSellerUser")
	@ResponseBody
	public Seller findOnebyuser(String username){
		
		Users user = userRep.findByUsername(username);
		Seller c = sellerRep.findOneByUser(user);
		return c;
	}
	
	

}
