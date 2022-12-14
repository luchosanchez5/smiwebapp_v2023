package com.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Customer;
import com.model.Estimator;
import com.model.FileUpload;
import com.model.FilterStatus;
import com.model.Item;
import com.model.ItemStatus;
import com.model.MatDetails;
import com.model.Material;
import com.model.QueryQuotes;
import com.model.Quote;
import com.model.QuoteNote;
import com.model.QuoteSubStatus;
import com.model.Seller;
import com.model.TempItem;
import com.model.Users;
import com.model.VGraph;
import com.repository.CustomerRep;
import com.repository.EstimatorRep;
import com.repository.ItemRep;
import com.repository.ItemStatusRep;
import com.repository.MatDetailRep;
import com.repository.MaterialRep;
import com.repository.QuoteNoteRep;
import com.repository.QuoteRep;
import com.repository.QuoteSubStatusRep;
import com.repository.SellerRep;
import com.repository.TempitemRep;
import com.repository.UserRep;
import com.service.EmailService;
import com.service.FileUploadService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class QuoteController {
    
	
	@Autowired
	private TempitemRep tempRep;
	
	@Autowired
	private QuoteRep quoteRep;
	
	@Autowired
	private ItemRep itemRep;
	
	@Autowired
	private QuoteNoteRep quotenoteRep;
	
	@Autowired
	private UserRep userRep;
	
	@Autowired
	private SellerRep sellerRep;
	
	@Autowired
	private EstimatorRep estimatorRep;	
	
	
	@Autowired
	private ApplicationContext appContext;
	
    @Autowired
    FileUploadService fileUploadService;
    
	@Autowired
	private CustomerRep customerRep;
	
	@Autowired
	private MaterialRep matRep;
	
    @Autowired
    EmailService emailService;	
    
    @Autowired
    private MatDetailRep matDetRep;
    
	@Autowired
	private ItemStatusRep iStatRep;
		
	@Autowired
	private QuoteSubStatusRep substatusRep;	
	
	@RequestMapping("quotes/indicatorsHistory")
	@ResponseBody	
	private List<Long> indicatorsQuotesHistory(){
		
		
		
		List<Long> listI = new ArrayList<Long>();
		long totQ = quoteRep.findTotalQuotesHistory();
		listI.add(totQ);
		
	    
		long totI = quoteRep.findTotalItemsHistory();
		listI.add(totI);
				
		long totPen = quoteRep.findTotalItemsPendingHistory();
		listI.add(totPen);
		
		
		BigInteger totOr = quoteRep.findTotalItemsOrdenedHistory();
		listI.add(totOr.longValue());
		
		long totPendQ = quoteRep.findTotalPendQuotesHistory();
		listI.add(totPendQ);
		
		long totAmountO = quoteRep.findTotalAmountOrderHistory();
		listI.add(totAmountO);
		
		
		return listI;		

	}
	
	
	@RequestMapping("quotes/indicatorsYear")
	@ResponseBody	
	private List<Long> indicatorsQuotesYear(long year){
		
		
		
		List<Long> listI = new ArrayList<Long>();
		long totQ = quoteRep.findTotalQuotesYear(year);
		listI.add(totQ);
		
	    
		long totI = quoteRep.findTotalItemsYear(year);
		listI.add(totI);
				
		long totPen = quoteRep.findTotalItemsPendingYear(year);
		listI.add(totPen);
		
		
		BigInteger totOr = quoteRep.findTotalItemsOrdenedYear(year);
		listI.add(totOr.longValue());
		
		long totPendQ = quoteRep.findTotalPendQuotes(year);
		listI.add(totPendQ);
		
		long totAmountO = quoteRep.findTotalAmountOrder(year);
		listI.add(totAmountO);
		
		
		return listI;		

	}	
	

	@RequestMapping(value="quotes/temps/delete")
	//@ResponseBody
	//@DeleteMapping(value="quotes/temps/delete")
	
	public @ResponseBody ResponseEntity<TempItem> quotesessionstemps(@RequestParam("sessionId") String sessionId) {
		 
		 TempItem temp = new TempItem();
		 List<TempItem> lisItem = tempRep.findAllBySessionIdAndStatReg(sessionId, "0");
         Iterator iTemItems = lisItem.iterator();
	     ArrayList<TempItem> lis = new ArrayList<TempItem>();

	        while (iTemItems.hasNext()) {
	        	
	        	temp = (TempItem) iTemItems.next();
	        	temp.setStatReg("1");
	        	tempRep.save(temp);
	        }
		
    	fileUploadService.delete_trashfiles(sessionId);
		//tempRep.deleteAllBySessionId(sessionId);
		return new ResponseEntity<TempItem>(temp, HttpStatus.OK);
	}
	
	
	
	@RequestMapping(value="quotes/topSellersDashYear")
	@ResponseBody	
	public List<QueryQuotes> topSellersDashYear(String typeGraph){
		List<QueryQuotes> qSel = new ArrayList<QueryQuotes>();
		List<Object[]> resulQ = quoteRep.topSellersDashYear();
		
		for (int j = 0; j < resulQ.size(); j++) {
			
			QueryQuotes q = new QueryQuotes();
			String name = (String) resulQ.get(j)[0];
						
			q.setName((String) resulQ.get(j)[0]);
			q.setLastName((String) resulQ.get(j)[1]);
			q.setTotal(((BigInteger) resulQ.get(j)[2]).longValue());
			q.setItems(((BigInteger) resulQ.get(j)[3]).longValue());
			q.setItemsOrd(((BigInteger) resulQ.get(j)[4]).longValue());
			q.setProficit(((BigInteger) resulQ.get(j)[5]).doubleValue());
						
			Seller sel = sellerRep.findOneById(((BigInteger) resulQ.get(j)[6]).longValue());
			q.setSeller(sel);
			
			qSel.add(q);
			
		}
		
		
		
		return qSel;
	}
	
	@RequestMapping(value="quotes/grahpicMainDash")
	@ResponseBody	
	public List<QueryQuotes> graphicMainDash(String typeGraph){
		List<Object[]> qSel1 = new ArrayList<Object[]>();
		List<QueryQuotes> qSel = new ArrayList<QueryQuotes>();
		
		
		if (typeGraph.equals("1"))
			qSel1 = quoteRep.graphicMainDashDaily();
		else if (typeGraph.equals("2"))
			qSel1 = quoteRep.graphicMainDashMonthly();
		    
		for (int j = 0; j < qSel1.size(); j++) {
			Date qDate = null;
			String qMonth = null;
			QueryQuotes q = new QueryQuotes();
			if (typeGraph.equals("1"))
				qDate = (Date) qSel1.get(j)[0];
			else if (typeGraph.equals("2"))
				qMonth = (String) qSel1.get(j)[0];
	    	BigInteger tot = (BigInteger) qSel1.get(j)[1];
	    	q.setDay(qDate);
	    	q.setName(qMonth);  //Name of the Month
	    	q.setTotQuotes(tot.intValue());
	    	qSel.add(q);
			
		}
		
			
		return qSel;
		
	}

	
	@RequestMapping(value="quotes/sparklinesYear")
	@ResponseBody
	public QueryQuotes sparklinesestimator(String typeGraph, long year){
		QueryQuotes qSel = new QueryQuotes();
		List<Object[]> resulQ = new ArrayList<Object[]>();
   	    long tot = 0;

   	    if (typeGraph.equals("1")) {
			resulQ = quoteRep.sparkline1mainDashYear(year);
 		
		}else if (typeGraph.equals("2")) {
            resulQ = quoteRep.sparkline2mainDashYear(year);

		}else if (typeGraph.equals("3")) {
			resulQ = quoteRep.sparkline3mainDash();
			
			
    	}else if (typeGraph.equals("4")) {
    		resulQ = quoteRep.sparkline4mainDash();
    		
		}else if (typeGraph.equals("5")) {
			resulQ = quoteRep.sparkline5maindash();
		}
		
		List<Integer> totals = new ArrayList<Integer>();
		List<Double> totalsDbl = new ArrayList<Double>();
	    for (int j = 0; j < resulQ.size(); j++){
	    	
	    	
	    	
	    	if (typeGraph.equals("1") || typeGraph.equals("2") || typeGraph.equals("3") || typeGraph.equals("4") || typeGraph.equals("5")) {
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
	
	
	
	
	
	@RequestMapping(value="quotes/sparklines")
	@ResponseBody
	public QueryQuotes sparklinesestimator(String typeGraph){
		QueryQuotes qSel = new QueryQuotes();
		List<Object[]> resulQ = new ArrayList<Object[]>();
   	    long tot = 0;

   	    if (typeGraph.equals("1")) {
			resulQ = quoteRep.sparkline1mainDash();
 		
		}else if (typeGraph.equals("2")) {
            resulQ = quoteRep.sparkline2mainDash();

		}else if (typeGraph.equals("3")) {
			resulQ = quoteRep.sparkline3mainDash();
			
			
    	}else if (typeGraph.equals("4")) {
    		resulQ = quoteRep.sparkline4mainDash();
    		
		}else if (typeGraph.equals("5")) {
			resulQ = quoteRep.sparkline5maindash();
		}
		
		List<Integer> totals = new ArrayList<Integer>();
		List<Double> totalsDbl = new ArrayList<Double>();
	    for (int j = 0; j < resulQ.size(); j++){
	    	
	    	
	    	
	    	if (typeGraph.equals("1") || typeGraph.equals("2") || typeGraph.equals("3") || typeGraph.equals("4") || typeGraph.equals("5")) {
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
	
	public List<QueryQuotes> colorDonut(){
		
		List<QueryQuotes> lista = new ArrayList<QueryQuotes>();
		List<String> backColor =  new ArrayList<String>();
		List<String> hoverColor =  new ArrayList<String>();
		
		backColor.add("#BDC3C7");
		backColor.add("#9B59B6");
		backColor.add("#E74C3C");
		backColor.add("#26B99A");
		backColor.add("#3498DB");
		backColor.add("#33CCFF");
		backColor.add("#33FFFF");
		
		backColor.add("#BDC3C7");
		backColor.add("#9B59B6");
		
		hoverColor.add("#CFD4D8");
		hoverColor.add("#B370CF");
		hoverColor.add("#E95E4F");
		hoverColor.add("#36CAAB");
		hoverColor.add("#49A9EA");
		hoverColor.add("#CCCC00");
		hoverColor.add("#CCFF33");
		
		hoverColor.add("#CFD4D8");
		hoverColor.add("#B370CF");
		

		QueryQuotes qq = new QueryQuotes();
		qq.setBackColor(backColor);
		qq.setHover(hoverColor);
		
		lista.add(qq);
		
		return lista;
	}
	
	
	@RequestMapping(value="quotes/donutcharts")
	@ResponseBody
	public QueryQuotes donutchartindustry(String typeGraph){
		
		List<QueryQuotes> lista = new ArrayList<QueryQuotes>();
   	    List<Object[]> resulLeads = new ArrayList<Object[]>();
   	    
   	    if (typeGraph.equals("1")) {
   	    	resulLeads = quoteRep.donutchartIndustry();
   	    }else if (typeGraph.equals("2")) {
   	    	resulLeads = quoteRep.donutchartType();
   	    }else if (typeGraph.equals("3")) {
   	    	resulLeads = quoteRep.donutchartqitemType();
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
	
	
	@RequestMapping(value="quotes/montlyGraph")
	@ResponseBody
	public VGraph monthlygraph(long year){
		
		List<QueryQuotes> lista = new ArrayList<QueryQuotes>();
   	    List<Object[]> resulQ = new ArrayList<Object[]>();
   	    List<Object[]> resulOrder = new ArrayList<Object[]>();
   	    VGraph resul = new VGraph();
   	    
   	    
   	    resulQ = quoteRep.findcountquotesyearmonth(year);
   	    

	    Iterator vLista = resulQ.iterator();

	    List<Long> countAll = new ArrayList<Long>();
	    List<Long> countOrder = new ArrayList<Long>();
	    List<Double> totPoAmount = new ArrayList<Double>();
	    List<String> months =  new ArrayList<String>();

	    for (int j = 0; j < resulQ.size(); j++){
	    	
	    	
	    		    	
	    	BigInteger tot = (BigInteger) resulQ.get(j)[0];
	    	countAll.add(tot.longValue());
	    		
	    	
	    	String label   = (String) resulQ.get(j)[1];
	    	months.add(label);
	    	
	    }	
	    
	    resulOrder = quoteRep.findcountorderquotesyearmonth(year);
        for (int j = 0; j < resulOrder.size(); j++){
	    	BigInteger tot = (BigInteger) resulOrder.get(j)[0];
	    	Double totPo = (Double) resulOrder.get(j)[3];
	    	countOrder.add(tot.longValue());	    	
	    	totPoAmount.add(totPo);
	    }
        
        resul.setList_str_month(months);
        resul.setList_tot_quotes(countAll);
        resul.setList_tot_quotes_ord(countOrder);
        resul.setList_po_amount(totPoAmount);
	    
		
		return resul;
	}	
	
	@RequestMapping("qQuotesCustomers")
	@ResponseBody	private List<Quote> findAllQuotes(Date inicio, Date fin, long idCustomer){
		
		Customer cus = customerRep.findOneById(idCustomer);
		
		System.out.println("Ini : " + inicio);
		System.out.println("Fin : " + fin);
		//List<Quote> lisq = quoteRep.findAllByStatRegOrderByCreatedDateDesc("0");
		
		LocalDateTime startD = inicio.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		LocalDateTime endD = fin.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
		
		
		//List<Quote> lisq = quoteRep.findByCreatedDateBetween(startD, endD);
		List<Quote> lisq = quoteRep.findByCustomerAndCreatedDateBetween(cus, startD, endD);
		System.out.println("QUERY 4 : " + lisq.size());
		return lisq;
	}
	
	@RequestMapping("list-quotes")
	@ResponseBody	private List<Quote> findAllQuotes(){
		List<Quote> lisq = quoteRep.findAllByStatRegOrderByCreatedDateDesc("0");
		return lisq;
	}
	
	
	@RequestMapping("list-quotes-status")
	@ResponseBody	private List<Quote> findAllQuotesbystatus(String stat){
		List<Quote> lisq = quoteRep.findAllByStatRegAndStatQuote("0",stat);
		return lisq;
	}
	
	@RequestMapping("list-quotes-substatus")
	@ResponseBody	private List<QuoteSubStatus> findAllSubStatus(String quotestatus){
		List<QuoteSubStatus> lisq = substatusRep.findAllByStatRegAndQuoteStatus("0",quotestatus);
		return lisq;
	}
	
	@RequestMapping(value="quotes/pendingItems")
	@ResponseBody	
	public Long countPendingItemsByQuote(long idQuote) {
		long tot = 0;
		tot = quoteRep.countPendingItemsByQuote(idQuote);		
		return 	tot;
	}
	
	
	@RequestMapping("list-quoteestimator")
	@ResponseBody
	private List<Quote> findAllQuotesseller(String userId){
		
		System.out.println("USE LIST ID" + userId);
		Users user = userRep.findByUsername(userId);
		System.out.println("USE LIST" + user.getLastName());
		Estimator est = estimatorRep.findOneByUser(user);
		List<Quote> lisq = quoteRep.findAllByStatRegAndEstimatorOrderByCreatedDateDesc("0",est);
		return lisq;
	}
	
	
	
	@RequestMapping("list-quotesseller")
	@ResponseBody
	private List<Quote> findAllQuoteestimator(String userId){
		
		Users user = userRep.findByUsername(userId);
		System.out.println("ACTUAL SALES ==> " + user.getId());
		Seller sel = sellerRep.findOneByIdUser(user.getId().toString());
		System.out.println("ACTUAL SALES ==> " + sel.getLastName());
		List<Quote> lisq = quoteRep.findAllSalesByStatRegAndSellerOrSeller2OrderByCreatedDateDesc("0",sel,sel);
		System.out.println("ACTUAL SALES ARRAY SIZE ==> " + lisq.size());
		return lisq;
	}
	
	
	@RequestMapping("list-sharedquotesseller")
	@ResponseBody
	private List<Quote> findAllQuoteSharedSeller(String userId){
		
		Users user = userRep.findByUsername(userId);
		System.out.println("ACTUAL SALES ==> " + user.getId());
		Seller sel = sellerRep.findOneByIdUser(user.getId().toString());
		System.out.println("ACTUAL SALES ==> " + sel.getLastName());
		List<Quote> lisq = quoteRep.findAllSalesByStatRegAndSharedQuoteAndSeller2OrderByCreatedDateDesc("0", "1", sel);
		//List<Quote> lisq = quoteRep.findAllSalesByStatRegAndSellerOrderByCreatedDateDesc("0",sel);
		System.out.println("ACTUAL SALES ARRAY SIZE ==> " + lisq.size());
		return lisq;
	}	
	
	
	@RequestMapping("list-filter-quotes-est")
	@ResponseBody
	private List<Quote> findFilterQuotes(String s0, String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8, String userId ){
		List<Quote> lisq = null;
		List<String> listStat = new ArrayList<String>();
		Users user = userRep.findByUsername(userId);
		System.out.println("USE LIST" + user.getLastName());
		Estimator est = estimatorRep.findOneByUser(user);
		
		if (!s0.equals("X"))
			  listStat.add(s0);
		
		if (!s1.equals("X") != false)
			  listStat.add(s1);
		
		if (!s2.equals("X") != false)
			  listStat.add(s2);
		
		if (!s3.equals("X") != false)
			  listStat.add(s3);
		
		if (!s4.equals("X") != false)
			  listStat.add(s4);
		
		if (!s5.equals("X") != false)
			  listStat.add(s5);
		
		if (!s6.equals("X") != false)
			  listStat.add(s6);
		
		if (!s7.equals("X") != false)
			  listStat.add(s7);
		
		if (!s8.equals("X") != false)
			  listStat.add(s8);			 

			lisq = quoteRep.findAllByStatRegAndEstimatorAndStatQuoteIn("0", est, listStat);
		//List<Quote> lisq = quoteRep.findAllByStatRegAndStatQuote("0", s0);
	
		
		return lisq;
	}	
	
	
	@RequestMapping("list-filter-quotes-est-year")
	@ResponseBody
	private List<Quote> findFilterQuotesYear(String s0, String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8, String userId , long year){
		List<Quote> lisq = null;
		List<String> listStat = new ArrayList<String>();
		Users user = userRep.findByUsername(userId);
		System.out.println("USE LIST" + user.getLastName());
		Estimator est = estimatorRep.findOneByUser(user);
		
		if (!s0.equals("X"))
			  listStat.add(s0);
		
		if (!s1.equals("X") != false)
			  listStat.add(s1);
		
		if (!s2.equals("X") != false)
			  listStat.add(s2);
		
		if (!s3.equals("X") != false)
			  listStat.add(s3);
		
		if (!s4.equals("X") != false)
			  listStat.add(s4);
		
		if (!s5.equals("X") != false)
			  listStat.add(s5);
		
		if (!s6.equals("X") != false)
			  listStat.add(s6);
		
		if (!s7.equals("X") != false)
			  listStat.add(s7);
		
		if (!s8.equals("X") != false)
			  listStat.add(s8);			 

		lisq = quoteRep.findAllByStatRegAndEstimatorAndStatQuoteIn("0", est, listStat);
		List<Quote> listqYear = lisq
				.stream()
				.filter(c -> c.getCreatedDate().getYear() == year )
				.collect(Collectors.toList());
		//List<Quote> lisq = quoteRep.findAllByStatRegAndStatQuote("0", s0);
	
		
		return listqYear;
	}		
	
	
	
	@RequestMapping("list-filter-quotes-sel")
	@ResponseBody
	private List<Quote> findFilterQuotesSel(String s0, String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8, String userId ){
		List<Quote> lisq = null;
		List<String> listStat = new ArrayList<String>();
		Users user = userRep.findByUsername(userId);
		System.out.println("USE LIST" + user.getLastName());
		Seller sel = sellerRep.findOneByIdUser(user.getId().toString());
		
		if (!s0.equals("X"))
			  listStat.add(s0);
		
		if (!s1.equals("X") != false)
			  listStat.add(s1);
		
		if (!s2.equals("X") != false)
			  listStat.add(s2);
		
		if (!s3.equals("X") != false)
			  listStat.add(s3);
		
		if (!s4.equals("X") != false)
			  listStat.add(s4);
		
		if (!s5.equals("X") != false)
			  listStat.add(s5);
		
		if (!s6.equals("X") != false)
			  listStat.add(s6);
		
		if (!s7.equals("X") != false)
			  listStat.add(s7);
		
		if (!s8.equals("X") != false)
			  listStat.add(s8);			 

			lisq = quoteRep.findAllByStatRegAndSellerAndStatQuoteIn("0", sel, listStat);
		//List<Quote> lisq = quoteRep.findAllByStatRegAndStatQuote("0", s0);
	
		
		return lisq;
	}		
	
	@RequestMapping("list-filter-estimator")
	@ResponseBody
	private List<Quote> findFilterQuotesEstimator(int opt, String s0, String s1,String s2,String s3,String s4,String s5,String s6,String s7,String s8,String est ){
		List<Quote> lisq = null;
		System.out.println("SO : " + s0);
		System.out.println("S1 : " + s1);
		System.out.println("S2 : " + s2);
		System.out.println("OPTION : " + opt);
        
		if (opt == 1)
			lisq = quoteRep.findAllByStatRegAndStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuote("0", s0,s1, s2,s3,s4,s5,s6,s7,s8);
		//List<Quote> lisq = quoteRep.findAllByStatRegAndStatQuote("0", s0);
		
		if (opt == 2) {
			System.out.println("ESTIMA : " + est);
		     long estLong =Long.parseLong(est);
		     System.out.println("OPTION : " + opt);
		     
		     Estimator estAux = estimatorRep.findOneById(estLong);
		     lisq = quoteRep.findAllByEstimatorAndStatRegAndStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuote(estAux, "0", s0,s1, s2,s3,s4,s5,s6,s7,s8);

		}
		
		
		return lisq;
	}		
	
	
	@RequestMapping("list-quotessellerbyid")
	@ResponseBody
	private List<Quote> findAllQuoteestimator(long idSel){
		
		//Users user = userRep.findByUsername(username);
		Seller sel = sellerRep.findOneById(idSel);
		List<Quote> lisq = quoteRep.findAllSalesByStatRegAndSellerOrSeller2OrderByCreatedDateDesc("0",sel,sel);
		return lisq;
	}
	
	@RequestMapping("list-sharedquotessellerbyid")
	@ResponseBody
	private List<Quote> findAllSharedSeller(long idSel){
		
		//Users user = userRep.findByUsername(username);
		Seller sel = sellerRep.findOneById(idSel);
		List<Quote> lisq = quoteRep.findAllSalesByStatRegAndSharedQuoteAndSeller2OrderByCreatedDateDesc("0", "1", sel);
		return lisq;
	}
	
	
	
	@RequestMapping("list-quotefiles")
	@ResponseBody
	private List<FileUpload> findAllQuoteFiles(long idQuote){
		
		Quote q = quoteRep.findById(idQuote);
		List<FileUpload> ls = fileUploadService.findAllByquoteAndStatFile(q, "1");

		return ls;
	}
	
	@RequestMapping(value = "quote", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Quote> new_quote(@RequestParam("sessionId") String sessionId, @RequestParam("newC") String newC,  @RequestBody Quote q) throws IOException {
		
        String rfq = "";
        Customer c = new Customer();
        
        c = q.getCustomer();
        
        System.out.println("NEW C VALUE : " +  newC);
        
        
        if (newC.equals("YN")) {  //New Customer
        	    
        	    c = q.getCustomer();
               
        		c.setCreatedDate(LocalDateTime.now());
        		c.setStatCompany("0");
        		c.setStatCustomer("0");
        		c.setStatReg("0");
        		customerRep.saveAndFlush(c);
        		q.setCustomer(c);

        	
        }
         
        if (newC.equals("YY"))  // Customer existing in quoting app, Not in Expandable
        	   	c = customerRep.findOneById(q.getCustomer().getId());
        		q.setCustomer(c);
        
         
        if (newC.equals("N")) {   //Customer come from expandable
        	   
    	  //  c = q.getCustomer();
        	
        	// Look customer in exiting from the app by company id, if does not exits , add it  ** PENDING **
        	
        	boolean cAux = customerRep.existsByIdCompany(c.getIdCompany());
        	
        	if (cAux == false) {
            
    		c.setCreatedDate(LocalDateTime.now());
    		c.setStatCompany("1");
    		c.setStatCustomer("0");
    		c.setStatReg("0");
    		customerRep.saveAndFlush(c);
    		q.setCustomer(c);
    		
        	}else
        	{
        		 c = customerRep.findOneByIdCompany(q.getCustomer().getIdCompany());
        		 q.setCustomer(c);
        	}
        }
        
        
        List<Quote> listQ = quoteRep.findAll();
        
        String strIni = "2022-01-01 00:01"; 
        String strFin = "2022-12-31 23:59";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"); 
        LocalDateTime initDate = LocalDateTime.parse(strIni, formatter);
        LocalDateTime endDate = LocalDateTime.parse(strFin, formatter);
        List<Quote> listSize = quoteRep.findAllByCreatedDateBetween(initDate,endDate);
        
        
        
        int sizeLis = listSize.size() + 1;
        
        if (sizeLis < 10) {
        	rfq = "22-0000" + Integer.toString(sizeLis);
        }
        
        if (sizeLis > 9) {
        	rfq = "22-000" + Integer.toString(sizeLis);
        }
        
        if (sizeLis > 99) {
        	rfq = "22-00" + Integer.toString(sizeLis);
        }
        
        if (sizeLis > 999) {
        	rfq = "22-0" + Integer.toString(sizeLis);
        }
        
        if (sizeLis > 9999) {
        	rfq = "22-" + Integer.toString(sizeLis);
        }        

        q.setNroRfq(rfq);
        
        
        List<TempItem> lisItem = tempRep.findAllBySessionIdAndStatReg(sessionId, "0");
        q.setTotItems(lisItem.size());
        q.setCreatedDate(LocalDateTime.now());
        q.setStatQuote("0");
        q.setStatReg("0");
        q.setSetorderItems("0");
        q.setRequoteTotal(0);
        //q.setExpirationDays("45");
        
        
        
        quoteRep.save(q);
        
        
        Iterator iTemItems = lisItem.iterator();
        ArrayList<TempItem> lis = new ArrayList<TempItem>();

        while (iTemItems.hasNext()) {
            List<Item> lItem = itemRep.findAll();
            //String nroItem = "A000" + Integer.toString(lItem.size());
            
            String nroItem = "0";
    		
            if  (lItem.size() > 99){
                    	nroItem = "A000" + Integer.toString(lItem.size());
               	
            }       		
            
            if (lItem.size() > 999){
            	nroItem = "A00" + Integer.toString(lItem.size());
       	
            }
            
            if (lItem.size() > 9999){
            	nroItem = "A0" + Integer.toString(lItem.size());
       	
            }
            
                 
            
            
        	TempItem temp = (TempItem) iTemItems.next();
        	
        	Item newItem = new Item();
        	newItem.setAnualUsage(temp.getAnualUsage());
        	newItem.setCadfileAva(temp.getCadfileAva());
        	newItem.setConcernsCurr(temp.getConcernsCurr());
        	newItem.setCreatedDate(temp.getCreatedDate());
        	newItem.setDrawingAva(temp.getDrawingAva());
        	newItem.setFob(temp.getFob());
        	newItem.setIndustry(temp.getIndustry());
        	newItem.setItemNumber(nroItem);
        	newItem.setItemStatus(temp.getItemStatus());
        	newItem.setItemType(temp.getItemType());
        	newItem.setStatReg("0");
        	newItem.setQuantity(temp.getQuantity());
        	newItem.setTargetPrice(temp.getTargetPrice());
        	newItem.setRfqdueDate(temp.getRfqdueDate());
        	newItem.setQitemType(temp.getQitemType());
        	newItem.setPart(temp.getPart());
        	newItem.setNewPartName(temp.getNewPartName());
        	newItem.setMeasureUnit(temp.getMeasureUnit());
        	newItem.setNote(temp.getNote());
        	newItem.setTeslaModel(temp.getTeslaModel());
        	newItem.setSuggestMat(temp.getSuggestMat());
        	newItem.setSuggestVend(temp.getSuggestVend());
        	newItem.setNewPart(temp.getNewPart());
        	newItem.setPartKissCut(temp.getPartKissCut());
        	newItem.setPackageReq(temp.getPackageReq());
        	

            
        	newItem.setQuote(q);
        	
        	temp.setStatReg("1");
        	itemRep.save(newItem);
        	tempRep.save(temp);
        	
        	// Add Materials --------------------------------------------
        	//newItem.setSugMaterials(temp.getSugMaterials());
        	
            Iterator iMaterials = temp.getSugMaterials().iterator();   
            ArrayList<Material> lisMat = new ArrayList<Material>();
            
            
            while (iMaterials.hasNext()){
            	Material currMat = (Material) iMaterials.next();
            	//String idClient = (String) iClients.next();
            	long materialInt = currMat.getIdMaterial();
            	//System.out.println("Seller Code :" + idSeller);
            	Material mat = matRep.getOne(materialInt);
            	lisMat.add(mat); 
            	
            }
            
            
            newItem.setSugMaterials(new HashSet<Material>(lisMat) );
            itemRep.save(newItem);
            
        	
        	// End Add Materilas ---------------------------------------------

        	
        	

        	
        	q.setPrintFOB(newItem.getFob());
        	
        	
        	
        	
        }
        
    	QuoteNote qn = new QuoteNote();
    	qn.setComment("New Quote Created");
    	qn.setQuote(q);
    	qn.setStatReg("0");
    	qn.setUser(q.getUser());
    	qn.setCreatedDate(LocalDateTime.now());
    	quotenoteRep.save(qn);
        
    	List<FileUpload> listFiles = (List<FileUpload>) fileUploadService.findAllBySessionAndStat(sessionId, "0");
        Iterator iFiles = listFiles.iterator();
        ArrayList<FileUpload> lisF = new ArrayList<FileUpload>(); 
        
        while (iFiles.hasNext()) {
        	
        	FileUpload f = (FileUpload) iFiles.next();
        	f.setQuote(q);
        	f.setUsers(q.getUser());
        	fileUploadService.updatefiledetails(f, q, q.getUser(), sessionId);
        	
        }
        
		
		
		return new ResponseEntity<Quote>(q, HttpStatus.OK);
	}
	
	
	@RequestMapping("quDetail")
	@ResponseBody
	public Quote findOne(long id){
		Quote q = quoteRep.findById(id);
		
				
		return q;
	}
	
	
	@RequestMapping(value = "quote/requote", method = RequestMethod.POST)
	public @ResponseBody ResponseEntity<Quote> generateRequote(@RequestBody Quote pQuote){
		
		Quote reQ = new Quote();
		//reQ = pQuote;
        reQ.setRequoteDate(LocalDateTime.now()); 
        reQ.setRequoteStatus("Y");
        
        
        reQ.setCreatedDate(pQuote.getCreatedDate());
        reQ.setCustomer(pQuote.getCustomer());
        reQ.setDateorderReceived(pQuote.getDateorderReceived());
        reQ.setDeletedDate(pQuote.getDeletedDate());
        reQ.setEstimator(pQuote.getEstimator());
        reQ.setLastStatus(pQuote.getLastStatus());
        reQ.setLastupdateDate(pQuote.getLastupdateDate());
        reQ.setLastupdateUser(pQuote.getLastupdateUser());
        reQ.setNewCustomer(pQuote.getNewCustomer());
        reQ.setNoteObtain(pQuote.getNoteObtain());
        reQ.setNroRfq(pQuote.getNroRfq());
        reQ.setOrderReceived(pQuote.getOrderReceived());
        reQ.setPrintFOB(pQuote.getPrintFOB());
        reQ.setPrintLeadTime(pQuote.getPrintLeadTime());
        reQ.setPrintMaterials(pQuote.getPrintMaterials());
        reQ.setPrintTolerance(pQuote.getPrintTolerance());
        reQ.setQuoteNotes(pQuote.getQuoteNotes());
        reQ.setReferral(pQuote.getReferral());
        reQ.setSeller(pQuote.getSeller());
        reQ.setStatQuote("1");
        reQ.setStatReg("0");
        reQ.setTerms(pQuote.getTerms());
        reQ.setTotItems(pQuote.getTotItems());
        reQ.setUser(pQuote.getUser());
        reQ.setExpirationDays(pQuote.getExpirationDays());
        
        //Check to add the new fields in Quote Model
        reQ.setSetorderItems(pQuote.getSetorderItems());
        
        
        
        if (pQuote.getRequoteTotal() == 0) {
        	reQ.setRequoteRevision("A");
        }
        
        if (pQuote.getRequoteTotal() == 1) {
        	reQ.setRequoteRevision("B");
        }
        
        if (pQuote.getRequoteTotal() == 2) {
        	reQ.setRequoteRevision("C");
        }
        
        if (pQuote.getRequoteTotal() == 3) {
        	reQ.setRequoteRevision("D");
        }
        
        if (pQuote.getRequoteTotal() == 4) {
        	reQ.setRequoteRevision("E");
        }
        
       
        quoteRep.saveAndFlush(reQ);
        pQuote.setRequoteTotal(pQuote.getRequoteTotal()+1);
        quoteRep.saveAndFlush(pQuote);
        
        //----------  GET ITEMS  -----------------------------------------------------------//
		int status = 1; // To New Item
		ItemStatus itStat = iStatRep.findOneById(status);
        
        List<Item> lisItem = itemRep.findAllByStatRegAndQuoteOrderByIdItem("0", pQuote);
        
      
        Iterator iTemItems = lisItem.iterator();
        ArrayList<Item> lis = new ArrayList<Item>();

        while (iTemItems.hasNext()) {
            List<Item> lItem = itemRep.findAll();
            String nroItem = "A000" + Integer.toString(lItem.size());
        	Item temp = (Item) iTemItems.next();
        	
        	if (temp.getItemStatus().getId() == 2) {
        	
        	Item newItem = new Item();
        	newItem.setAnualUsage(temp.getAnualUsage());
        	newItem.setCadfileAva(temp.getCadfileAva());
        	newItem.setConcernsCurr(temp.getConcernsCurr());
        	newItem.setCreatedDate(temp.getCreatedDate());
        	newItem.setDrawingAva(temp.getDrawingAva());
        	newItem.setFob(temp.getFob());
        	newItem.setIndustry(temp.getIndustry());
        	newItem.setItemNumber(nroItem);
        	newItem.setItemStatus(itStat);
        	newItem.setItemType(temp.getItemType());
        	newItem.setStatReg("0");
        	newItem.setQuantity(temp.getQuantity());
        	newItem.setTargetPrice(temp.getTargetPrice());
        	newItem.setRfqdueDate(temp.getRfqdueDate());
        	newItem.setQitemType(temp.getQitemType());
        	newItem.setPart(temp.getPart());
        	newItem.setNewPartName(temp.getNewPartName());
        	newItem.setMeasureUnit(temp.getMeasureUnit());
        	newItem.setNote(temp.getNote());
        	newItem.setTeslaModel(temp.getTeslaModel());
        	newItem.setSuggestMat(temp.getSuggestMat());
        	newItem.setSuggestVend(temp.getSuggestVend());
        	newItem.setNewPart(temp.getNewPart());
        	newItem.setPartKissCut(temp.getPartKissCut());
        	newItem.setDateDeleted(temp.getDateDeleted());
        	newItem.setDateOrdered(temp.getDateOrdered());
        	newItem.setLaborCost(temp.getLaborCost());
        	newItem.setLastUpdate(temp.getLastUpdate());
        	newItem.setMargin(temp.getMargin());
        	newItem.setMaterialCost(temp.getMaterialCost());
        	newItem.setNewPartName(temp.getNewPartName());
        	newItem.setPackageReq(temp.getPackageReq());
        	newItem.setPackagingCost(temp.getPackagingCost());
        	newItem.setPart(temp.getPart());
        	newItem.setQuote(reQ);
        	newItem.setRfqdueDate(temp.getRfqdueDate());
        	newItem.setSampleAva(temp.getSampleAva());
        	newItem.setScrapRate(temp.getScrapRate());
        	newItem.setSmiSaleCost(temp.getSmiSaleCost());
        	newItem.setSmiTotalCost(temp.getSmiTotalCost());
        	newItem.setStartEstimate(temp.getStartEstimate());
        	newItem.setStartestimateDate(temp.getStartestimateDate());
        	newItem.setStatReg("0");
        	newItem.setTargetPrice(temp.getTargetPrice());
        	newItem.setToolingCost(temp.getToolingCost());
        	
        	itemRep.save(newItem);
        	
        	//----------  GET SHEET COST  -----------------------------------------------------------//
        	
        	
        	List<MatDetails> listMat = matDetRep.findAllByItemAndStatRegOrderById(temp, "0");
            
            Iterator iTemMats = listMat.iterator();
            
            while (iTemMats.hasNext()) {

            	MatDetails matDet = (MatDetails) iTemMats.next();
            	MatDetails newMat = new MatDetails();
            	
            	newMat.setCostUm(matDet.getCostUm());
            	newMat.setCreateDate(matDet.getCreateDate());
            	newMat.setFreight(matDet.getFreight());
            	newMat.setItem(newItem);
            	newMat.setLaborCost(matDet.getLaborCost());
            	newMat.setLaborRate(matDet.getLaborRate());
            	newMat.setLf(matDet.getLf());
            	newMat.setMachine(matDet.getMachine());
            	newMat.setMaterial(matDet.getMaterial());
            	newMat.setMatWidht(matDet.getMatWidht());
            	newMat.setMeasureUnit(matDet.getMeasureUnit());
            	newMat.setNroRolls(matDet.getNroRolls());
            	newMat.setPriceEach(matDet.getPriceEach());
            	newMat.setProgression(matDet.getProgression());
            	newMat.setQtyHour(matDet.getQtyHour());
            	newMat.setSlitWidth(matDet.getSlitWidth());
            	newMat.setStatReg("0");
            	newMat.setStepDescription(matDet.getStepDescription());
            	newMat.setTool(matDet.getTool());
            	newMat.setTotalCost(matDet.getTotalCost());
            	newMat.setTypeDetail(matDet.getTypeDetail());
            	newMat.setTypeMaterial(matDet.getTypeMaterial());
            	newMat.setUser(matDet.getUser());
            	newMat.setYield(matDet.getYield());
            	
            	matDetRep.saveAndFlush(newMat);
            	
            	}  // END CONDITION FILTER JUST ITEMS COMPLETES
            	
            	
            	
            }

        	// ---------------------------------------------------------------------------------------//

        } 	

      //----------  GET QUOTES FILES  -----------------------------------------------------------//
              
        
             List<FileUpload> lisFiles = fileUploadService.findAllByquoteAndStatFile(pQuote, "0");
             Iterator files = lisFiles.iterator();
             
             while (files.hasNext()) {
            	 FileUpload newFile = new FileUpload();
            	 FileUpload actualFile = (FileUpload) files.next();
            	 
            	 newFile.setCreatedDate(actualFile.getCreatedDate());
            	 newFile.setFile(actualFile.getFile());
            	 newFile.setFilename(actualFile.getFilename());
            	 newFile.setMimeType(actualFile.getMimeType());
            	 newFile.setQuote(reQ);
            	 newFile.setSessionId(actualFile.getSessionId());
            	 newFile.setStatFile(actualFile.getStatFile());
            	 newFile.setUsers(actualFile.getUsers());
            	 
            	 fileUploadService.uploadFile(newFile);
            	 
            	 
             }
		
     //--------------------------------------------------------------------------------------------
        
         	QuoteNote qn = new QuoteNote();
        	qn.setComment("Re-Quote Created ,  Revision : " + reQ.getRequoteRevision() );
        	qn.setQuote(pQuote);
        	qn.setStatReg("0");
        	qn.setUser(pQuote.getRequoteUser());
        	qn.setCreatedDate(LocalDateTime.now());
        	quotenoteRep.save(qn);    
        
         	QuoteNote qn2 = new QuoteNote();
        	qn2.setComment("Re-Quote Created ,  Revision : " + reQ.getRequoteRevision() );
        	qn2.setQuote(reQ);
        	qn2.setStatReg("0");
        	qn2.setUser(pQuote.getRequoteUser());
        	qn2.setCreatedDate(LocalDateTime.now());
        	quotenoteRep.save(qn2);         
			
		
		return new ResponseEntity<Quote>(reQ, HttpStatus.OK);
	}
	
	
	
	
	@RequestMapping(value = "quote/update", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Quote> editMatDetail(@RequestBody Quote paramQuote){
		
		
		//System.out.println(paramQuote.get);
		Quote q = quoteRep.findById(paramQuote.getId());
        q = paramQuote;
        q.setLastupdateDate(LocalDateTime.now());
        quoteRep.save(q);
        
		int status = 7; // To New Item
		ItemStatus itStat = iStatRep.findOneById(status);
        
        if (q.getStatQuote().equals("2")) {
        
        	  List<Item>  listI = itemRep.findAllByStatRegAndQuoteOrderByIdItem("0",q);
              Iterator iItems =  listI.iterator();
              
              while (iItems.hasNext()){
            	  Item it = (Item) iItems.next();
            	  it.setItemStatus(itStat);
            	  itemRep.save(it);
              }
        	
        }
        
        if (q.getStatQuote().equals("8")) {
        	
      	  List<Item>  listI = itemRep.findAllByStatRegAndQuoteOrderByIdItem("0",q);
          Iterator iItems =  listI.iterator();
          
          while (iItems.hasNext()){
        	  Item it = (Item) iItems.next();
        	  if (it.getItemStatus().getPending().equals("Y")) {
            	  it.setItemStatus(itStat);
            	  itemRep.save(it); 
        	  }

          }        	
        	  
        	
        }
			
		
		return new ResponseEntity<Quote>(q, HttpStatus.OK);
	}	
	
	
	@RequestMapping(value = "quote/updateStatus", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Quote> quoteUpdateStatus(@RequestBody Quote paramQuote){
		
		Quote q = quoteRep.findById(paramQuote.getId());
        q.setLastupdateDate(LocalDateTime.now());
        q.setLastupdateUser(paramQuote.getLastupdateUser());
        q.setStatQuote(paramQuote.getStatQuote());

        quoteRep.save(q);
			
		
		return new ResponseEntity<Quote>(q, HttpStatus.OK);
	}		
	
	
	
	@RequestMapping(value = "quote/notify", method = RequestMethod.POST)
	public  @ResponseBody ResponseEntity<String> new_notify(@RequestBody Quote q) throws IOException{
		
	        
		try {
			DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
			
			String textEmail =  "<!DOCTYPE HTML>\r\n" + 
					"<!-- saved from url=(0104)mhtml:file://C:\\Users\\luiss\\AppData\\Local\\Microsoft\\Windows\\INetCache\\Content.Outlook\\H0SG760V\\email.mht -->\r\n" + 
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><!--[if IE]><html xmlns=\"http://www.w3.org/1999/xhtml\" class=\"ie\"><![endif]--><!--[if !IE]><!--><HTML \r\n" + 
					"style=\"margin: 0px; padding: 0px;\" xmlns=\"http://www.w3.org/1999/xhtml\"><!--<![endif]--><HEAD><META \r\n" + 
					"content=\"IE=11.0000\" http-equiv=\"X-UA-Compatible\">\r\n" + 
					" \r\n" + 
					"<META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">     \r\n" + 
					"<TITLE></TITLE>     <!--[if !mso]><!-->\r\n" + 
					"<META http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->     \r\n" + 
					"<META name=\"viewport\" content=\"width=device-width\">\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"@media only screen and (min-width: 620px){.wrapper{min-width:600px !important}.wrapper h1{}.wrapper h1{font-size:26px !important;line-height:34px !important}.wrapper h2{}.wrapper h3{}.column{}.wrapper .size-8{font-size:8px !important;line-height:14px !important}.wrapper .size-9{font-size:9px !important;line-height:16px !important}.wrapper .size-10{font-size:10px !important;line-height:18px !important}.wrapper .size-11{font-size:11px !important;line-height:19px !important}.wrapper .size-12{font-size:12px !important;line-height:19px !important}.wrapper .size-13{font-size:13px !important;line-height:21px !important}.wrapper .size-14{font-size:14px !important;line-height:21px !important}.wrapper .size-15{font-size:15px !important;line-height:23px !important}.wrapper .size-16{font-size:16px !important;line-height:24px !important}.wrapper .size-17{font-size:17px !important;line-height:26px \r\n" + 
					"!important}.wrapper .size-18{font-size:18px !important;line-height:26px !important}.wrapper .size-20{font-size:20px !important;line-height:28px !important}.wrapper .size-22{font-size:22px !important;line-height:31px !important}.wrapper .size-24{font-size:24px !important;line-height:32px !important}.wrapper .size-26{font-size:26px !important;line-height:34px !important}.wrapper .size-28{font-size:28px !important;line-height:36px !important}.wrapper .size-30{font-size:30px !important;line-height:38px !important}.wrapper .size-32{font-size:32px !important;line-height:40px !important}.wrapper .size-34{font-size:34px !important;line-height:43px !important}.wrapper .size-36{font-size:36px !important;line-height:43px !important}.wrapper .size-40{font-size:40px !important;line-height:47px !important}.wrapper .size-44{font-size:44px !important;line-height:50px !important}.wrapper \r\n" + 
					".size-48{font-size:48px !important;line-height:54px !important}.wrapper .size-56{font-size:56px !important;line-height:60px !important}.wrapper .size-64{font-size:64px !important;line-height:63px !important}}\r\n" + 
					"</STYLE>\r\n" + 
					"     \r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"body {\r\n" + 
					"  margin: 0;\r\n" + 
					"  padding: 0;\r\n" + 
					"}\r\n" + 
					"table {\r\n" + 
					"  border-collapse: collapse;\r\n" + 
					"  table-layout: fixed;\r\n" + 
					"}\r\n" + 
					"* {\r\n" + 
					"  line-height: inherit;\r\n" + 
					"}\r\n" + 
					"[x-apple-data-detectors],\r\n" + 
					"[href^=\"tel\"],\r\n" + 
					"[href^=\"sms\"] {\r\n" + 
					"  color: inherit !important;\r\n" + 
					"  text-decoration: none !important;\r\n" + 
					"}\r\n" + 
					".wrapper .footer__share-button a:hover,\r\n" + 
					".wrapper .footer__share-button a:focus {\r\n" + 
					"  color: #ffffff !important;\r\n" + 
					"}\r\n" + 
					".btn a:hover,\r\n" + 
					".btn a:focus,\r\n" + 
					".footer__share-button a:hover,\r\n" + 
					".footer__share-button a:focus,\r\n" + 
					".email-footer__links a:hover,\r\n" + 
					".email-footer__links a:focus {\r\n" + 
					"  opacity: 0.8;\r\n" + 
					"}\r\n" + 
					".preheader,\r\n" + 
					".header,\r\n" + 
					".layout,\r\n" + 
					".column {\r\n" + 
					"  transition: width 0.25s ease-in-out, max-width 0.25s ease-in-out;\r\n" + 
					"}\r\n" + 
					".preheader td {\r\n" + 
					"  padding-bottom: 8px;\r\n" + 
					"}\r\n" + 
					".layout,\r\n" + 
					"div.header {\r\n" + 
					"  max-width: 400px !important;\r\n" + 
					"  -fallback-width: 95% !important;\r\n" + 
					"  width: calc(100% - 20px) !important;\r\n" + 
					"}\r\n" + 
					"div.preheader {\r\n" + 
					"  max-width: 360px !important;\r\n" + 
					"  -fallback-width: 90% !important;\r\n" + 
					"  width: calc(100% - 60px) !important;\r\n" + 
					"}\r\n" + 
					".snippet,\r\n" + 
					".webversion {\r\n" + 
					"  Float: none !important;\r\n" + 
					"}\r\n" + 
					".column {\r\n" + 
					"  max-width: 400px !important;\r\n" + 
					"  width: 100% !important;\r\n" + 
					"}\r\n" + 
					".fixed-width.has-border {\r\n" + 
					"  max-width: 402px !important;\r\n" + 
					"}\r\n" + 
					".fixed-width.has-border .layout__inner {\r\n" + 
					"  box-sizing: border-box;\r\n" + 
					"}\r\n" + 
					".snippet,\r\n" + 
					".webversion {\r\n" + 
					"  width: 50% !important;\r\n" + 
					"}\r\n" + 
					".ie .btn {\r\n" + 
					"  width: 100%;\r\n" + 
					"}\r\n" + 
					"[owa] .column div,\r\n" + 
					"[owa] .column button {\r\n" + 
					"  display: block !important;\r\n" + 
					"}\r\n" + 
					"[owa] .wrapper > tbody > tr > td {\r\n" + 
					"  overflow-x: auto;\r\n" + 
					"  overflow-y: hidden;\r\n" + 
					"}\r\n" + 
					"[owa] .wrapper > tbody > tr > td > div {\r\n" + 
					"  min-width: 600px;\r\n" + 
					"}\r\n" + 
					".ie .column,\r\n" + 
					"[owa] .column,\r\n" + 
					".ie .gutter,\r\n" + 
					"[owa] .gutter {\r\n" + 
					"  display: table-cell;\r\n" + 
					"  float: none !important;\r\n" + 
					"  vertical-align: top;\r\n" + 
					"}\r\n" + 
					".ie div.preheader,\r\n" + 
					"[owa] div.preheader,\r\n" + 
					".ie .email-footer,\r\n" + 
					"[owa] .email-footer {\r\n" + 
					"  max-width: 560px !important;\r\n" + 
					"  width: 560px !important;\r\n" + 
					"}\r\n" + 
					".ie .snippet,\r\n" + 
					"[owa] .snippet,\r\n" + 
					".ie .webversion,\r\n" + 
					"[owa] .webversion {\r\n" + 
					"  width: 280px !important;\r\n" + 
					"}\r\n" + 
					".ie div.header,\r\n" + 
					"[owa] div.header,\r\n" + 
					".ie .layout,\r\n" + 
					"[owa] .layout,\r\n" + 
					".ie .one-col .column,\r\n" + 
					"[owa] .one-col .column {\r\n" + 
					"  max-width: 600px !important;\r\n" + 
					"  width: 600px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col .column,\r\n" + 
					"[owa] .two-col .column {\r\n" + 
					"  max-width: 300px !important;\r\n" + 
					"  width: 300px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col .column,\r\n" + 
					"[owa] .three-col .column,\r\n" + 
					".ie .narrow,\r\n" + 
					"[owa] .narrow {\r\n" + 
					"  max-width: 200px !important;\r\n" + 
					"  width: 200px !important;\r\n" + 
					"}\r\n" + 
					".ie .wide,\r\n" + 
					"[owa] .wide {\r\n" + 
					"  width: 400px !important;\r\n" + 
					"}\r\n" + 
					".ie .fixed-width.has-border,\r\n" + 
					"[owa] .fixed-width.x_has-border,\r\n" + 
					".ie .has-gutter.has-border,\r\n" + 
					"[owa] .has-gutter.x_has-border {\r\n" + 
					"  max-width: 602px !important;\r\n" + 
					"  width: 602px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col.has-gutter .column,\r\n" + 
					"[owa] .two-col.x_has-gutter .column {\r\n" + 
					"  max-width: 290px !important;\r\n" + 
					"  width: 290px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col.has-gutter .column,\r\n" + 
					"[owa] .three-col.x_has-gutter .column,\r\n" + 
					".ie .has-gutter .narrow,\r\n" + 
					"[owa] .has-gutter .narrow {\r\n" + 
					"  max-width: 188px !important;\r\n" + 
					"  width: 188px !important;\r\n" + 
					"}\r\n" + 
					".ie .has-gutter .wide,\r\n" + 
					"[owa] .has-gutter .wide {\r\n" + 
					"  max-width: 394px !important;\r\n" + 
					"  width: 394px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col.has-gutter.has-border .column,\r\n" + 
					"[owa] .two-col.x_has-gutter.x_has-border .column {\r\n" + 
					"  max-width: 292px !important;\r\n" + 
					"  width: 292px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col.has-gutter.has-border .column,\r\n" + 
					"[owa] .three-col.x_has-gutter.x_has-border .column,\r\n" + 
					".ie .has-gutter.has-border .narrow,\r\n" + 
					"[owa] .has-gutter.x_has-border .narrow {\r\n" + 
					"  max-width: 190px !important;\r\n" + 
					"  width: 190px !important;\r\n" + 
					"}\r\n" + 
					".ie .has-gutter.has-border .wide,\r\n" + 
					"[owa] .has-gutter.x_has-border .wide {\r\n" + 
					"  max-width: 396px !important;\r\n" + 
					"  width: 396px !important;\r\n" + 
					"}\r\n" + 
					".ie .fixed-width .layout__inner {\r\n" + 
					"  border-left: 0 none white !important;\r\n" + 
					"  border-right: 0 none white !important;\r\n" + 
					"}\r\n" + 
					".ie .layout__edges {\r\n" + 
					"  display: none;\r\n" + 
					"}\r\n" + 
					".mso .layout__edges {\r\n" + 
					"  font-size: 0;\r\n" + 
					"}\r\n" + 
					".layout-fixed-width,\r\n" + 
					".mso .layout-full-width {\r\n" + 
					"  background-color: #ffffff;\r\n" + 
					"}\r\n" + 
					"@media only screen and (min-width: 620px) {\r\n" + 
					"  .column,\r\n" + 
					"  .gutter {\r\n" + 
					"    display: table-cell;\r\n" + 
					"    Float: none !important;\r\n" + 
					"    vertical-align: top;\r\n" + 
					"  }\r\n" + 
					"  div.preheader,\r\n" + 
					"  .email-footer {\r\n" + 
					"    max-width: 560px !important;\r\n" + 
					"    width: 560px !important;\r\n" + 
					"  }\r\n" + 
					"  .snippet,\r\n" + 
					"  .webversion {\r\n" + 
					"    width: 280px !important;\r\n" + 
					"  }\r\n" + 
					"  div.header,\r\n" + 
					"  .layout,\r\n" + 
					"  .one-col .column {\r\n" + 
					"    max-width: 600px !important;\r\n" + 
					"    width: 600px !important;\r\n" + 
					"  }\r\n" + 
					"  .fixed-width.has-border,\r\n" + 
					"  .fixed-width.ecxhas-border,\r\n" + 
					"  .has-gutter.has-border,\r\n" + 
					"  .has-gutter.ecxhas-border {\r\n" + 
					"    max-width: 602px !important;\r\n" + 
					"    width: 602px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col .column {\r\n" + 
					"    max-width: 300px !important;\r\n" + 
					"    width: 300px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col .column,\r\n" + 
					"  .column.narrow {\r\n" + 
					"    max-width: 200px !important;\r\n" + 
					"    width: 200px !important;\r\n" + 
					"  }\r\n" + 
					"  .column.wide {\r\n" + 
					"    width: 400px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col.has-gutter .column,\r\n" + 
					"  .two-col.ecxhas-gutter .column {\r\n" + 
					"    max-width: 290px !important;\r\n" + 
					"    width: 290px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col.has-gutter .column,\r\n" + 
					"  .three-col.ecxhas-gutter .column,\r\n" + 
					"  .has-gutter .narrow {\r\n" + 
					"    max-width: 188px !important;\r\n" + 
					"    width: 188px !important;\r\n" + 
					"  }\r\n" + 
					"  .has-gutter .wide {\r\n" + 
					"    max-width: 394px !important;\r\n" + 
					"    width: 394px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col.has-gutter.has-border .column,\r\n" + 
					"  .two-col.ecxhas-gutter.ecxhas-border .column {\r\n" + 
					"    max-width: 292px !important;\r\n" + 
					"    width: 292px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col.has-gutter.has-border .column,\r\n" + 
					"  .three-col.ecxhas-gutter.ecxhas-border .column,\r\n" + 
					"  .has-gutter.has-border .narrow,\r\n" + 
					"  .has-gutter.ecxhas-border .narrow {\r\n" + 
					"    max-width: 190px !important;\r\n" + 
					"    width: 190px !important;\r\n" + 
					"  }\r\n" + 
					"  .has-gutter.has-border .wide,\r\n" + 
					"  .has-gutter.ecxhas-border .wide {\r\n" + 
					"    max-width: 396px !important;\r\n" + 
					"    width: 396px !important;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					"@media only screen and (-webkit-min-device-pixel-ratio: 2), only screen and (min--moz-device-pixel-ratio: 2), only screen and (-o-min-device-pixel-ratio: 2/1), only screen and (min-device-pixel-ratio: 2), only screen and (min-resolution: 192dpi), only screen and (min-resolution: 2dppx) {\r\n" + 
					"  .fblike {\r\n" + 
					"    background-image: url(http://i7.cmail19.com/static/eb/master/13-the-blueprint-3/images/fblike@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .tweet {\r\n" + 
					"    background-image: url(http://i8.cmail19.com/static/eb/master/13-the-blueprint-3/images/tweet@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .linkedinshare {\r\n" + 
					"    background-image: url(http://i9.cmail19.com/static/eb/master/13-the-blueprint-3/images/lishare@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .forwardtoafriend {\r\n" + 
					"    background-image: url(http://i10.cmail19.com/static/eb/master/13-the-blueprint-3/images/forward@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					"@media (max-width: 321px) {\r\n" + 
					"  .fixed-width.has-border .layout__inner {\r\n" + 
					"    border-width: 1px 0 !important;\r\n" + 
					"  }\r\n" + 
					"  .layout,\r\n" + 
					"  .column {\r\n" + 
					"    min-width: 320px !important;\r\n" + 
					"    width: 320px !important;\r\n" + 
					"  }\r\n" + 
					"  .border {\r\n" + 
					"    display: none;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					".mso div {\r\n" + 
					"  border: 0 none white !important;\r\n" + 
					"}\r\n" + 
					".mso .w560 .divider {\r\n" + 
					"  Margin-left: 260px !important;\r\n" + 
					"  Margin-right: 260px !important;\r\n" + 
					"}\r\n" + 
					".mso .w360 .divider {\r\n" + 
					"  Margin-left: 160px !important;\r\n" + 
					"  Margin-right: 160px !important;\r\n" + 
					"}\r\n" + 
					".mso .w260 .divider {\r\n" + 
					"  Margin-left: 110px !important;\r\n" + 
					"  Margin-right: 110px !important;\r\n" + 
					"}\r\n" + 
					".mso .w160 .divider {\r\n" + 
					"  Margin-left: 60px !important;\r\n" + 
					"  Margin-right: 60px !important;\r\n" + 
					"}\r\n" + 
					".mso .w354 .divider {\r\n" + 
					"  Margin-left: 157px !important;\r\n" + 
					"  Margin-right: 157px !important;\r\n" + 
					"}\r\n" + 
					".mso .w250 .divider {\r\n" + 
					"  Margin-left: 105px !important;\r\n" + 
					"  Margin-right: 105px !important;\r\n" + 
					"}\r\n" + 
					".mso .w148 .divider {\r\n" + 
					"  Margin-left: 54px !important;\r\n" + 
					"  Margin-right: 54px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-8,\r\n" + 
					".ie .size-8 {\r\n" + 
					"  font-size: 8px !important;\r\n" + 
					"  line-height: 14px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-9,\r\n" + 
					".ie .size-9 {\r\n" + 
					"  font-size: 9px !important;\r\n" + 
					"  line-height: 16px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-10,\r\n" + 
					".ie .size-10 {\r\n" + 
					"  font-size: 10px !important;\r\n" + 
					"  line-height: 18px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-11,\r\n" + 
					".ie .size-11 {\r\n" + 
					"  font-size: 11px !important;\r\n" + 
					"  line-height: 19px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-12,\r\n" + 
					".ie .size-12 {\r\n" + 
					"  font-size: 12px !important;\r\n" + 
					"  line-height: 19px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-13,\r\n" + 
					".ie .size-13 {\r\n" + 
					"  font-size: 13px !important;\r\n" + 
					"  line-height: 21px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-14,\r\n" + 
					".ie .size-14 {\r\n" + 
					"  font-size: 14px !important;\r\n" + 
					"  line-height: 21px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-15,\r\n" + 
					".ie .size-15 {\r\n" + 
					"  font-size: 15px !important;\r\n" + 
					"  line-height: 23px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-16,\r\n" + 
					".ie .size-16 {\r\n" + 
					"  font-size: 16px !important;\r\n" + 
					"  line-height: 24px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-17,\r\n" + 
					".ie .size-17 {\r\n" + 
					"  font-size: 17px !important;\r\n" + 
					"  line-height: 26px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-18,\r\n" + 
					".ie .size-18 {\r\n" + 
					"  font-size: 18px !important;\r\n" + 
					"  line-height: 26px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-20,\r\n" + 
					".ie .size-20 {\r\n" + 
					"  font-size: 20px !important;\r\n" + 
					"  line-height: 28px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-22,\r\n" + 
					".ie .size-22 {\r\n" + 
					"  font-size: 22px !important;\r\n" + 
					"  line-height: 31px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-24,\r\n" + 
					".ie .size-24 {\r\n" + 
					"  font-size: 24px !important;\r\n" + 
					"  line-height: 32px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-26,\r\n" + 
					".ie .size-26 {\r\n" + 
					"  font-size: 26px !important;\r\n" + 
					"  line-height: 34px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-28,\r\n" + 
					".ie .size-28 {\r\n" + 
					"  font-size: 28px !important;\r\n" + 
					"  line-height: 36px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-30,\r\n" + 
					".ie .size-30 {\r\n" + 
					"  font-size: 30px !important;\r\n" + 
					"  line-height: 38px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-32,\r\n" + 
					".ie .size-32 {\r\n" + 
					"  font-size: 32px !important;\r\n" + 
					"  line-height: 40px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-34,\r\n" + 
					".ie .size-34 {\r\n" + 
					"  font-size: 34px !important;\r\n" + 
					"  line-height: 43px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-36,\r\n" + 
					".ie .size-36 {\r\n" + 
					"  font-size: 36px !important;\r\n" + 
					"  line-height: 43px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-40,\r\n" + 
					".ie .size-40 {\r\n" + 
					"  font-size: 40px !important;\r\n" + 
					"  line-height: 47px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-44,\r\n" + 
					".ie .size-44 {\r\n" + 
					"  font-size: 44px !important;\r\n" + 
					"  line-height: 50px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-48,\r\n" + 
					".ie .size-48 {\r\n" + 
					"  font-size: 48px !important;\r\n" + 
					"  line-height: 54px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-56,\r\n" + 
					".ie .size-56 {\r\n" + 
					"  font-size: 56px !important;\r\n" + 
					"  line-height: 60px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-64,\r\n" + 
					".ie .size-64 {\r\n" + 
					"  font-size: 64px !important;\r\n" + 
					"  line-height: 63px !important;\r\n" + 
					"}\r\n" + 
					"</STYLE>\r\n" + 
					"       <!--[if !mso]><!-->\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"@import url(https://fonts.googleapis.com/css?family=PT+Serif:400,700,400italic,700italic|Ubuntu:400,700,400italic,700italic);\r\n" + 
					"</STYLE>\r\n" + 
					"<LINK href=\"email_files/css.css\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"body{background-color:#ededf1}.logo a:hover,.logo a:focus{color:#859bb1 !important}.mso .layout-has-border{border-top:1px solid #b4b4c4;border-bottom:1px solid #b4b4c4}.mso .layout-has-bottom-border{border-bottom:1px solid #b4b4c4}.mso .border,.ie .border{background-color:#b4b4c4}.mso h1,.ie h1{}.mso h1,.ie h1{font-size:26px !important;line-height:34px !important}.mso h2,.ie h2{}.mso h3,.ie h3{}.mso .layout__inner,.ie .layout__inner{}.mso .footer__share-button p{}.mso .footer__share-button p{font-family:Ubuntu,sans-serif}\r\n" + 
					"</STYLE>\r\n" + 
					"\r\n" + 
					"<META name=\"GENERATOR\" content=\"MSHTML 11.00.10570.1001\"></HEAD> <!--[if mso]>\r\n" + 
					"  <body class=\"mso\">\r\n" + 
					"<![endif]--> <!--[if !mso]><!--> \r\n" + 
					"  \r\n" + 
					"<BODY class=\"full-padding\" style=\"margin: 0px; padding: 0px; -webkit-text-size-adjust: 100%;\"><!--<![endif]--> \r\n" + 
					"    \r\n" + 
					"<TABLE class=\"wrapper\" role=\"presentation\" style=\"width: 100%; border-collapse: collapse; table-layout: fixed; min-width: 320px; background-color: rgb(237, 237, 241);\" \r\n" + 
					"cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
					"  <TBODY>\r\n" + 
					"  <TR>\r\n" + 
					"    <TD>\r\n" + 
					"      <DIV role=\"banner\">\r\n" + 
					"      <DIV class=\"preheader\" style=\"margin: 0px auto; width: calc(28000% - 167440px); min-width: 280px; max-width: 560px;\">\r\n" + 
					"      <DIV style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" class=\"preheader\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 280px\" valign=\"top\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"snippet\" style=\"padding: 10px 0px 5px; width: calc(14000% - 78120px); color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; display: table-cell; min-width: 140px; max-width: 280px;\"></DIV><!--[if (mso)|(IE)]></td><td style=\"width: 280px\" valign=\"top\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"webversion\" style=\"padding: 10px 0px 5px; width: calc(14100% - 78680px); text-align: right; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; display: table-cell; min-width: 139px; max-width: 280px;\"></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV>\r\n" + 
					"      <DIV class=\"header\" id=\"emb-email-header-container\" style=\"margin: 0px auto; width: calc(28000% - 167400px); min-width: 320px; max-width: 600px;\"><!--[if (mso)|(IE)]><table align=\"center\" class=\"header\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 600px\"><![endif]--> \r\n" + 
					"                \r\n" + 
					"      <DIV align=\"center\" class=\"logo emb-logo-margin-box\" style=\"margin: 6px 20px 20px; color: rgb(195, 206, 217); line-height: 32px; font-family: Roboto,Tahoma,sans-serif; font-size: 26px;\">\r\n" + 
					"      <DIV align=\"center\" class=\"logo-center\" id=\"emb-email-header\"><IMG width=\"261\" \r\n" + 
					"      style=\"border: 0px currentColor; border-image: none; width: 100%; height: auto; display: block; max-width: 261px;\" \r\n" + 
					"      alt=\"\" src=\"http://www.westerntape.com/WT_images/logo-smi-new.png\"></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"              </DIV></DIV>\r\n" + 
					"      <DIV>\r\n" + 
					"      <DIV class=\"layout one-col fixed-width\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse; background-color: rgb(255, 255, 255);\" \r\n" + 
					"      emb-background-style=\"\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-fixed-width\" emb-background-style><td style=\"width: 600px\" class=\"w560\"><![endif]--> \r\n" + 
					"                \r\n" + 
					"      <DIV class=\"column\" style=\"width: calc(28000% - 167400px); text-align: left; color: rgb(124, 126, 127); line-height: 21px; font-family: PT Serif,Georgia,serif; font-size: 14px; min-width: 320px; max-width: 600px;\">\r\n" + 
					"      <DIV style=\"margin-top: 24px; margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 10px; font-size: 1px; mso-line-height-rule: exactly;\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <H3 style=\"text-align: center; color: rgb(120, 137, 145); line-height: 24px; font-size: 16px; font-style: normal; font-weight: normal; margin-top: 0px; margin-bottom: 0px;\">QUOTE \r\n" + 
					"      REQUEST</H3>\r\n" + 
					"      <H1 style=\"text-align: left; color: rgb(62, 71, 81); line-height: 31px; font-family: Ubuntu,sans-serif; font-size: 22px; font-style: normal; font-weight: normal; margin-top: 12px; margin-bottom: 0px;\">Dear \r\n" + 
					"      " + q.getEstimator().getSureName() + "  " + q.getEstimator().getLastName() + ",</H1>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 20px;\">A new quote was assigned \r\n" + 
					"      to you.</P></DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV class=\"divider\" style=\"width: 40px; line-height: 2px; font-size: 2px; margin-right: auto; margin-bottom: 20px; margin-left: auto; display: block; background-color: rgb(180, 180, 196);\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 5px; font-size: 1px; mso-line-height-rule: exactly;\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <H2 style=\"color: rgb(62, 71, 81); line-height: 24px; font-family: Ubuntu,sans-serif; font-size: 16px; font-style: normal; font-weight: normal; margin-top: 0px; margin-bottom: 0px;\"><STRONG>Quote \r\n" + 
					"      Information</STRONG></H2>\r\n" + 
					"      <P style=\"margin-top: 16px; margin-bottom: 0px;\"><STRONG>RFQ </STRONG>: \r\n" + 
					"      " + q.getNroRfq() + "</P>\r\n" + 
					"      <P style=\"margin-top: 16px; margin-bottom: 0px;\"><STRONG>Customer   </STRONG>: \r\n" + 
					"      " + q.getCustomer().getCustomerName() + "</P>\r\n" + 
			
					"      <P style=\"margin-top: 20px; margin-bottom: 0px;\"><STRONG>Date:&nbsp; \r\n" + 
					"      " + formatter.format(q.getCreatedDate()) + "</STRONG><BR>Contact Name :&nbsp;<STRONG><STRONG>\r\n" + 
					"      " + q.getCustomer().getContactName() + " -- Phone: " + q.getCustomer().getContactPhone() + " &nbsp;</STRONG></STRONG><BR>Email \r\n" + 
					"      :&nbsp;<STRONG><A style=\"transition:opacity 0.1s ease-in; color: rgb(78, 170, 204); text-decoration: underline;\" \r\n" + 
					"      href=\"mailto: " + q.getCustomer().getContactEmail() + " \">" + q.getCustomer().getContactEmail() + "</A>&nbsp;</STRONG><BR>\r\n" + 
					"      Observations :&nbsp;<STRONG> " + q.getQuoteNotes() + "</STRONG></P>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 20px;\">Please do not reply this \r\n" + 
					"      message, all you need is go to smi quoting web app and then go to your \r\n" + 
					"      pipeline, for more information contact \r\n" + 
					"      questions@sealmethodsinc.com</P></DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV class=\"btn btn--shadow btn--large\" style=\"text-align: center; margin-bottom: 20px;\"><!--[if !mso]--><A \r\n" + 
					"      style=\"padding: 12px 24px 13px; border-radius: 4px; transition:opacity 0.1s ease-in; text-align: center; color: rgb(255, 255, 255) !important; line-height: 24px; font-family: PT Serif, Georgia, serif; font-size: 14px; font-weight: bold; text-decoration: none !important; display: inline-block; box-shadow: inset 0px -2px 0px 0px rgba(0,0,0,0.2); background-color: rgb(78, 170, 204);\" \r\n" + 
					"      href=\"http://quoting.westerntape.com\">GO TO \r\n" + 
					"PIPELINE</A><!--[endif]-->       <!--[if mso]><p style=\"line-height:0;margin:0;\">&nbsp;</p><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" href=\"http://192.168.1.4:8080/smiwebapp/\" style=\"width:160px\" arcsize=\"9%\" fillcolor=\"#4EAACC\" stroke=\"f\"><v:shadow on=\"t\" color=\"#3E88A3\" offset=\"0,2px\"></v:shadow><v:textbox style=\"mso-fit-shape-to-text:t\" inset=\"0px,11px,0px,10px\"><center style=\"font-size:14px;line-height:24px;color:#FFFFFF;font-family:PT Serif,Georgia,serif;font-weight:bold;mso-line-height-rule:exactly;mso-text-raise:4px\">GO TO PIPELINE</center></v:textbox></v:roundrect><![endif]--></DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-bottom: 24px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <P style=\"margin-top: 0px; margin-bottom: 0px;\">Kind regards,<BR>Seal \r\n" + 
					"      Methods Planners Team</P></DIV></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"              </DIV></DIV>\r\n" + 
					"      <DIV style=\"line-height: 20px; font-size: 20px;\">&nbsp;</DIV>\r\n" + 
					"      <DIV role=\"contentinfo\">\r\n" + 
					"      <DIV class=\"layout email-footer\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 400px;\" valign=\"top\" class=\"w360\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column wide\" style=\"width: calc(8000% - 47600px); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; min-width: 320px; max-width: 400px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px;\">\r\n" + 
					"      <DIV>Seal Methods Inc. USA<BR>11915 Shoemaker Ave Santa Fe Springs, CA \r\n" + 
					"      90670<BR>Phone:&nbsp;800.423.4777&nbsp;&nbsp; &nbsp; \r\n" + 
					"      Fax:&nbsp;562.946.9439&nbsp; &nbsp; \r\n" + 
					"      Email:&nbsp;sales@sealmethodsinc.com</DIV></DIV>\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px; margin-top: 18px;\"></DIV><!--[if mso]>&nbsp;<![endif]--> \r\n" + 
					"                    </DIV></DIV><!--[if (mso)|(IE)]></td><td style=\"width: 200px;\" valign=\"top\" class=\"w160\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column narrow\" style=\"width: calc(72200px - 12000%); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; min-width: 200px; max-width: 320px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\"></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV>\r\n" + 
					"      <DIV class=\"layout one-col email-footer\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 600px;\" class=\"w560\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column\" style=\"width: calc(28000% - 167400px); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; min-width: 320px; max-width: 600px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px;\"><A style=\"transition:opacity 0.1s ease-in; color: rgb(124, 126, 127); text-decoration: underline;\" \r\n" + 
					"      href=\"\"></A>   \r\n" + 
					"                    </DIV></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV></DIV>\r\n" + 
					"      <DIV \r\n" + 
					"  style=\"line-height: 40px; font-size: 40px;\">&nbsp;</DIV></DIV></TD></TR></TBODY></TABLE><IMG \r\n" + 
					"width=\"1\" height=\"1\" style=\"margin: 0px !important; padding: 0px !important; border: 0px currentColor !important; border-image: none !important; width: 1px !important; height: 1px !important; overflow: hidden; display: block !important; visibility: hidden !important; position: fixed;\" \r\n" + 
					"alt=\"\" src=\"email_files/o.gif\" border=\"0\"></BODY></HTML>\r\n" + 
					"" ;
			
			
		    DateFormat dateF = new SimpleDateFormat("MM/dd/yyyy");
			String text1 = "<table cellspacing=0 cellpadding=0> " 
    				+ "<tr align=left>"
    				+ "  <td align=left valign=top><table> "
    				+ "    <tbody> "
    				+ "      <tr align=left> "
    				+ "        <td align=left><table> "
    				+ "          <tbody> "
    				+ "            <tr align=left> "
    				+ "              <td align=left valign=top><p> Dear " + q.getEstimator().getSureName() + " " + q.getEstimator().getSureName() + ",</p> "
    				+ "                <p>A new quote was assigned to you.</p> "
    				+ "                <p>Best Regards,&nbsp;<br /> "
    				+ "                  Seal Methods Planners Team </p> "
    				+ "                <table> "
    				+ "                  <tbody> "
    				+ "                    <tr align=left> "
    				+ "                      <td width=724 align=left valign=top><table width=608> "
    				+ "                        <tbody> "
    				+ "                          <tr align=left> "
    				+ "                            <td width=719 align=left valign=top bgcolor=#ECF8FF><strong>APPOIMENT INFORMATION</strong> "
    				+ "                                <div>Date :&nbsp; <strong><span class=Estilo1>" + "#RFQ : " + q.getNroRfq() + "   Date:" + q.getCreatedDate() +  "</span>&nbsp;</strong><br /> "
    				+ "                                   Contact Name :&nbsp;<strong><br /> "
    				+ "                                    " + q.getCustomer().getContactName() + " -- Phone: " + q.getCustomer().getContactPhone() +  "&nbsp;<br /> "
    				+ "                                    </strong>Property Address:&nbsp;<strong>" + q.getCustomer().getContactEmail() + "&nbsp;</strong><br /> "
    				+ "                                    Observations "
    				+ "                                  :&nbsp;<strong> " + q.getQuoteNotes() + "</strong></div></td> "
    				+ "                            </tr> "
    				+ "                        </tbody> "
    				+ "                      </table></td> "
    				+ "                    </tr> "
    				+ "                  </tbody> "
    				+ "                </table> "
    				+ "                <table> "
    				+ "                  <tbody> "
    				+ "                    <tr align=left> "
    				+ "                      <td align=left bgcolor=#ebebeb valign=top><table> "
    				+ "                        <tbody> "
    				+ "                          <tr align=left> "
    				+ "                            <td align=left valign=top><h4 align=center><img src=https://cdn.supplyfx.com/images/7021231449058346550.JPG width=110 height=115 /></h4> "
    				+ "                                <div> "
    				+ "                                  <p><strong>Santa Fe Springs, CA</strong><br /> "
    				+ "                                    Monday-Friday: 9:00am-5:30pm<br /> "
    				+ "                                  </p> "
    				+ "                                </div></td> "
    				+ "                            <td align=left valign=top></td> "
    				+ "                          </tr> "
    				+ "                        </tbody> "
    				+ "                      </table></td> "
    				+ "                      <td align=left bgcolor=#ebebeb valign=top><table> "
    				+ "                        <tbody> "
    				+ "                          <tr align=left> "
    				+ "                           <td align=left valign=top><h4 align=left>FOR MORE INFORMATION PLEASE CHECK OUT OUR WEBSITE</h4> "
    				+ "                                <div><strong><span id=et-info-phone>323-736-1949 - </span><a href=https://www.sealmethodsinc.com/>www.sealmethodsinc.com/</a></strong></div> "
    				+ "                                <br /> "
    				+ "                              <div> "
    				+ "                                <h4 align=left>CONNECT WITH US</h4> "
    				+ "                                <strong>sales@sealmethodsinc.com</strong><br /> "
    				+ "                               <a href=https://www.facebook.com/Seal-Methods-133774880008750/><img src=http://significadodesimbolos.com/wp-content/uploads/2014/09/simbolos-facebook-300x300.png width=41 height=39 border=0 /></a>&nbsp;<a href=https://twitter.com/SealMethodsInc><img src=http://www.avenueroadadvertising.com/wp-content/uploads/2015/05/twitter-logo.png width=41 height=39 border=0 /></a>&nbsp;<a href=https://www.youtube.com/channel/UCI5FZLsoHd0oNSDS9ZWf4Ng target=_blank><img src=http://pngimg.com/uploads/instagram/instagram_PNG14.png width=40 height=38 border=0 /></a></div></td> "
    				+ "                           <td align=left valign=top></td> "
    				+ "                          </tr> "
    				+ "                        </tbody> "
    				+ "                      </table></td> "
    				+ "                    </tr> "
    				+ "                  </tbody> "
    				+ "                </table></td> "
    				+ "            </tr> "
    				+ "          </tbody> "
    				+ "        </table></td> "
    				+ "      </tr> "
    				+ "    </tbody> "
    				+ "  </table></td> "
    				+ " </tr> "
    				+ "</table>"; 
			System.out.println("SENDING EMAIL TO....." +  q.getEstimator().getEmailContact());
			
			
    		emailService.sendHTMlEmail(q.getEstimator().getEmailContact(), "New Quote ", textEmail);
    		System.out.println("EMAIl sent to :");
    		
    		
		   }
		catch (Exception e) {
			System.out.println("Error : sending email.." +  q.getEstimator().getEmailContact());
		}
            


		return new ResponseEntity<String>("0", HttpStatus.OK);
		
		

	}	
	
	
	
	@RequestMapping(value = "quote/notifyMessage", method = RequestMethod.POST)
	public  @ResponseBody ResponseEntity<String> new_notifyMessage(@RequestParam("message")  String message, @RequestParam("idQuote")  long idQuote, @RequestParam("username")  String username) throws IOException{
		
		Quote q = quoteRep.findById(idQuote);    
		try {
			DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
			String url_est = "http://192.168.1.4:8080/smiwebapp/sealhome#/quotenote-detail?quoteID="+q.getId();
			System.out.print("Message :  " + message );
			//String url_est = "http://localhost:8080/smiwebapp/sealhome#/quotenote-detail?quoteID="+q.getId();
			String url_sales = "http://69.178.147.146:8080/smiwebapp/sealhome#/quotenote-detail?quoteID="+q.getId();
			
			
			String textEmail =  "<!DOCTYPE HTML>\r\n" + 
					"<!-- saved from url=(0104)mhtml:file://C:\\Users\\luiss\\AppData\\Local\\Microsoft\\Windows\\INetCache\\Content.Outlook\\H0SG760V\\email.mht -->\r\n" + 
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><!--[if IE]><html xmlns=\"http://www.w3.org/1999/xhtml\" class=\"ie\"><![endif]--><!--[if !IE]><!--><HTML \r\n" + 
					"style=\"margin: 0px; padding: 0px;\" xmlns=\"http://www.w3.org/1999/xhtml\"><!--<![endif]--><HEAD><META \r\n" + 
					"content=\"IE=11.0000\" http-equiv=\"X-UA-Compatible\">\r\n" + 
					" \r\n" + 
					"<META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">     \r\n" + 
					"<TITLE></TITLE>     <!--[if !mso]><!-->\r\n" + 
					"<META http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->     \r\n" + 
					"<META name=\"viewport\" content=\"width=device-width\">\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"@media only screen and (min-width: 620px){.wrapper{min-width:600px !important}.wrapper h1{}.wrapper h1{font-size:26px !important;line-height:34px !important}.wrapper h2{}.wrapper h3{}.column{}.wrapper .size-8{font-size:8px !important;line-height:14px !important}.wrapper .size-9{font-size:9px !important;line-height:16px !important}.wrapper .size-10{font-size:10px !important;line-height:18px !important}.wrapper .size-11{font-size:11px !important;line-height:19px !important}.wrapper .size-12{font-size:12px !important;line-height:19px !important}.wrapper .size-13{font-size:13px !important;line-height:21px !important}.wrapper .size-14{font-size:14px !important;line-height:21px !important}.wrapper .size-15{font-size:15px !important;line-height:23px !important}.wrapper .size-16{font-size:16px !important;line-height:24px !important}.wrapper .size-17{font-size:17px !important;line-height:26px \r\n" + 
					"!important}.wrapper .size-18{font-size:18px !important;line-height:26px !important}.wrapper .size-20{font-size:20px !important;line-height:28px !important}.wrapper .size-22{font-size:22px !important;line-height:31px !important}.wrapper .size-24{font-size:24px !important;line-height:32px !important}.wrapper .size-26{font-size:26px !important;line-height:34px !important}.wrapper .size-28{font-size:28px !important;line-height:36px !important}.wrapper .size-30{font-size:30px !important;line-height:38px !important}.wrapper .size-32{font-size:32px !important;line-height:40px !important}.wrapper .size-34{font-size:34px !important;line-height:43px !important}.wrapper .size-36{font-size:36px !important;line-height:43px !important}.wrapper .size-40{font-size:40px !important;line-height:47px !important}.wrapper .size-44{font-size:44px !important;line-height:50px !important}.wrapper \r\n" + 
					".size-48{font-size:48px !important;line-height:54px !important}.wrapper .size-56{font-size:56px !important;line-height:60px !important}.wrapper .size-64{font-size:64px !important;line-height:63px !important}}\r\n" + 
					"</STYLE>\r\n" + 
					"     \r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"body {\r\n" + 
					"  margin: 0;\r\n" + 
					"  padding: 0;\r\n" + 
					"}\r\n" + 
					"table {\r\n" + 
					"  border-collapse: collapse;\r\n" + 
					"  table-layout: fixed;\r\n" + 
					"}\r\n" + 
					"* {\r\n" + 
					"  line-height: inherit;\r\n" + 
					"}\r\n" + 
					"[x-apple-data-detectors],\r\n" + 
					"[href^=\"tel\"],\r\n" + 
					"[href^=\"sms\"] {\r\n" + 
					"  color: inherit !important;\r\n" + 
					"  text-decoration: none !important;\r\n" + 
					"}\r\n" + 
					".wrapper .footer__share-button a:hover,\r\n" + 
					".wrapper .footer__share-button a:focus {\r\n" + 
					"  color: #ffffff !important;\r\n" + 
					"}\r\n" + 
					".btn a:hover,\r\n" + 
					".btn a:focus,\r\n" + 
					".footer__share-button a:hover,\r\n" + 
					".footer__share-button a:focus,\r\n" + 
					".email-footer__links a:hover,\r\n" + 
					".email-footer__links a:focus {\r\n" + 
					"  opacity: 0.8;\r\n" + 
					"}\r\n" + 
					".preheader,\r\n" + 
					".header,\r\n" + 
					".layout,\r\n" + 
					".column {\r\n" + 
					"  transition: width 0.25s ease-in-out, max-width 0.25s ease-in-out;\r\n" + 
					"}\r\n" + 
					".preheader td {\r\n" + 
					"  padding-bottom: 8px;\r\n" + 
					"}\r\n" + 
					".layout,\r\n" + 
					"div.header {\r\n" + 
					"  max-width: 400px !important;\r\n" + 
					"  -fallback-width: 95% !important;\r\n" + 
					"  width: calc(100% - 20px) !important;\r\n" + 
					"}\r\n" + 
					"div.preheader {\r\n" + 
					"  max-width: 360px !important;\r\n" + 
					"  -fallback-width: 90% !important;\r\n" + 
					"  width: calc(100% - 60px) !important;\r\n" + 
					"}\r\n" + 
					".snippet,\r\n" + 
					".webversion {\r\n" + 
					"  Float: none !important;\r\n" + 
					"}\r\n" + 
					".column {\r\n" + 
					"  max-width: 400px !important;\r\n" + 
					"  width: 100% !important;\r\n" + 
					"}\r\n" + 
					".fixed-width.has-border {\r\n" + 
					"  max-width: 402px !important;\r\n" + 
					"}\r\n" + 
					".fixed-width.has-border .layout__inner {\r\n" + 
					"  box-sizing: border-box;\r\n" + 
					"}\r\n" + 
					".snippet,\r\n" + 
					".webversion {\r\n" + 
					"  width: 50% !important;\r\n" + 
					"}\r\n" + 
					".ie .btn {\r\n" + 
					"  width: 100%;\r\n" + 
					"}\r\n" + 
					"[owa] .column div,\r\n" + 
					"[owa] .column button {\r\n" + 
					"  display: block !important;\r\n" + 
					"}\r\n" + 
					"[owa] .wrapper > tbody > tr > td {\r\n" + 
					"  overflow-x: auto;\r\n" + 
					"  overflow-y: hidden;\r\n" + 
					"}\r\n" + 
					"[owa] .wrapper > tbody > tr > td > div {\r\n" + 
					"  min-width: 600px;\r\n" + 
					"}\r\n" + 
					".ie .column,\r\n" + 
					"[owa] .column,\r\n" + 
					".ie .gutter,\r\n" + 
					"[owa] .gutter {\r\n" + 
					"  display: table-cell;\r\n" + 
					"  float: none !important;\r\n" + 
					"  vertical-align: top;\r\n" + 
					"}\r\n" + 
					".ie div.preheader,\r\n" + 
					"[owa] div.preheader,\r\n" + 
					".ie .email-footer,\r\n" + 
					"[owa] .email-footer {\r\n" + 
					"  max-width: 560px !important;\r\n" + 
					"  width: 560px !important;\r\n" + 
					"}\r\n" + 
					".ie .snippet,\r\n" + 
					"[owa] .snippet,\r\n" + 
					".ie .webversion,\r\n" + 
					"[owa] .webversion {\r\n" + 
					"  width: 280px !important;\r\n" + 
					"}\r\n" + 
					".ie div.header,\r\n" + 
					"[owa] div.header,\r\n" + 
					".ie .layout,\r\n" + 
					"[owa] .layout,\r\n" + 
					".ie .one-col .column,\r\n" + 
					"[owa] .one-col .column {\r\n" + 
					"  max-width: 600px !important;\r\n" + 
					"  width: 600px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col .column,\r\n" + 
					"[owa] .two-col .column {\r\n" + 
					"  max-width: 300px !important;\r\n" + 
					"  width: 300px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col .column,\r\n" + 
					"[owa] .three-col .column,\r\n" + 
					".ie .narrow,\r\n" + 
					"[owa] .narrow {\r\n" + 
					"  max-width: 200px !important;\r\n" + 
					"  width: 200px !important;\r\n" + 
					"}\r\n" + 
					".ie .wide,\r\n" + 
					"[owa] .wide {\r\n" + 
					"  width: 400px !important;\r\n" + 
					"}\r\n" + 
					".ie .fixed-width.has-border,\r\n" + 
					"[owa] .fixed-width.x_has-border,\r\n" + 
					".ie .has-gutter.has-border,\r\n" + 
					"[owa] .has-gutter.x_has-border {\r\n" + 
					"  max-width: 602px !important;\r\n" + 
					"  width: 602px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col.has-gutter .column,\r\n" + 
					"[owa] .two-col.x_has-gutter .column {\r\n" + 
					"  max-width: 290px !important;\r\n" + 
					"  width: 290px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col.has-gutter .column,\r\n" + 
					"[owa] .three-col.x_has-gutter .column,\r\n" + 
					".ie .has-gutter .narrow,\r\n" + 
					"[owa] .has-gutter .narrow {\r\n" + 
					"  max-width: 188px !important;\r\n" + 
					"  width: 188px !important;\r\n" + 
					"}\r\n" + 
					".ie .has-gutter .wide,\r\n" + 
					"[owa] .has-gutter .wide {\r\n" + 
					"  max-width: 394px !important;\r\n" + 
					"  width: 394px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col.has-gutter.has-border .column,\r\n" + 
					"[owa] .two-col.x_has-gutter.x_has-border .column {\r\n" + 
					"  max-width: 292px !important;\r\n" + 
					"  width: 292px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col.has-gutter.has-border .column,\r\n" + 
					"[owa] .three-col.x_has-gutter.x_has-border .column,\r\n" + 
					".ie .has-gutter.has-border .narrow,\r\n" + 
					"[owa] .has-gutter.x_has-border .narrow {\r\n" + 
					"  max-width: 190px !important;\r\n" + 
					"  width: 190px !important;\r\n" + 
					"}\r\n" + 
					".ie .has-gutter.has-border .wide,\r\n" + 
					"[owa] .has-gutter.x_has-border .wide {\r\n" + 
					"  max-width: 396px !important;\r\n" + 
					"  width: 396px !important;\r\n" + 
					"}\r\n" + 
					".ie .fixed-width .layout__inner {\r\n" + 
					"  border-left: 0 none white !important;\r\n" + 
					"  border-right: 0 none white !important;\r\n" + 
					"}\r\n" + 
					".ie .layout__edges {\r\n" + 
					"  display: none;\r\n" + 
					"}\r\n" + 
					".mso .layout__edges {\r\n" + 
					"  font-size: 0;\r\n" + 
					"}\r\n" + 
					".layout-fixed-width,\r\n" + 
					".mso .layout-full-width {\r\n" + 
					"  background-color: #ffffff;\r\n" + 
					"}\r\n" + 
					"@media only screen and (min-width: 620px) {\r\n" + 
					"  .column,\r\n" + 
					"  .gutter {\r\n" + 
					"    display: table-cell;\r\n" + 
					"    Float: none !important;\r\n" + 
					"    vertical-align: top;\r\n" + 
					"  }\r\n" + 
					"  div.preheader,\r\n" + 
					"  .email-footer {\r\n" + 
					"    max-width: 560px !important;\r\n" + 
					"    width: 560px !important;\r\n" + 
					"  }\r\n" + 
					"  .snippet,\r\n" + 
					"  .webversion {\r\n" + 
					"    width: 280px !important;\r\n" + 
					"  }\r\n" + 
					"  div.header,\r\n" + 
					"  .layout,\r\n" + 
					"  .one-col .column {\r\n" + 
					"    max-width: 600px !important;\r\n" + 
					"    width: 600px !important;\r\n" + 
					"  }\r\n" + 
					"  .fixed-width.has-border,\r\n" + 
					"  .fixed-width.ecxhas-border,\r\n" + 
					"  .has-gutter.has-border,\r\n" + 
					"  .has-gutter.ecxhas-border {\r\n" + 
					"    max-width: 602px !important;\r\n" + 
					"    width: 602px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col .column {\r\n" + 
					"    max-width: 300px !important;\r\n" + 
					"    width: 300px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col .column,\r\n" + 
					"  .column.narrow {\r\n" + 
					"    max-width: 200px !important;\r\n" + 
					"    width: 200px !important;\r\n" + 
					"  }\r\n" + 
					"  .column.wide {\r\n" + 
					"    width: 400px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col.has-gutter .column,\r\n" + 
					"  .two-col.ecxhas-gutter .column {\r\n" + 
					"    max-width: 290px !important;\r\n" + 
					"    width: 290px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col.has-gutter .column,\r\n" + 
					"  .three-col.ecxhas-gutter .column,\r\n" + 
					"  .has-gutter .narrow {\r\n" + 
					"    max-width: 188px !important;\r\n" + 
					"    width: 188px !important;\r\n" + 
					"  }\r\n" + 
					"  .has-gutter .wide {\r\n" + 
					"    max-width: 394px !important;\r\n" + 
					"    width: 394px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col.has-gutter.has-border .column,\r\n" + 
					"  .two-col.ecxhas-gutter.ecxhas-border .column {\r\n" + 
					"    max-width: 292px !important;\r\n" + 
					"    width: 292px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col.has-gutter.has-border .column,\r\n" + 
					"  .three-col.ecxhas-gutter.ecxhas-border .column,\r\n" + 
					"  .has-gutter.has-border .narrow,\r\n" + 
					"  .has-gutter.ecxhas-border .narrow {\r\n" + 
					"    max-width: 190px !important;\r\n" + 
					"    width: 190px !important;\r\n" + 
					"  }\r\n" + 
					"  .has-gutter.has-border .wide,\r\n" + 
					"  .has-gutter.ecxhas-border .wide {\r\n" + 
					"    max-width: 396px !important;\r\n" + 
					"    width: 396px !important;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					"@media only screen and (-webkit-min-device-pixel-ratio: 2), only screen and (min--moz-device-pixel-ratio: 2), only screen and (-o-min-device-pixel-ratio: 2/1), only screen and (min-device-pixel-ratio: 2), only screen and (min-resolution: 192dpi), only screen and (min-resolution: 2dppx) {\r\n" + 
					"  .fblike {\r\n" + 
					"    background-image: url(http://i7.cmail19.com/static/eb/master/13-the-blueprint-3/images/fblike@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .tweet {\r\n" + 
					"    background-image: url(http://i8.cmail19.com/static/eb/master/13-the-blueprint-3/images/tweet@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .linkedinshare {\r\n" + 
					"    background-image: url(http://i9.cmail19.com/static/eb/master/13-the-blueprint-3/images/lishare@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .forwardtoafriend {\r\n" + 
					"    background-image: url(http://i10.cmail19.com/static/eb/master/13-the-blueprint-3/images/forward@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					"@media (max-width: 321px) {\r\n" + 
					"  .fixed-width.has-border .layout__inner {\r\n" + 
					"    border-width: 1px 0 !important;\r\n" + 
					"  }\r\n" + 
					"  .layout,\r\n" + 
					"  .column {\r\n" + 
					"    min-width: 320px !important;\r\n" + 
					"    width: 320px !important;\r\n" + 
					"  }\r\n" + 
					"  .border {\r\n" + 
					"    display: none;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					".mso div {\r\n" + 
					"  border: 0 none white !important;\r\n" + 
					"}\r\n" + 
					".mso .w560 .divider {\r\n" + 
					"  Margin-left: 260px !important;\r\n" + 
					"  Margin-right: 260px !important;\r\n" + 
					"}\r\n" + 
					".mso .w360 .divider {\r\n" + 
					"  Margin-left: 160px !important;\r\n" + 
					"  Margin-right: 160px !important;\r\n" + 
					"}\r\n" + 
					".mso .w260 .divider {\r\n" + 
					"  Margin-left: 110px !important;\r\n" + 
					"  Margin-right: 110px !important;\r\n" + 
					"}\r\n" + 
					".mso .w160 .divider {\r\n" + 
					"  Margin-left: 60px !important;\r\n" + 
					"  Margin-right: 60px !important;\r\n" + 
					"}\r\n" + 
					".mso .w354 .divider {\r\n" + 
					"  Margin-left: 157px !important;\r\n" + 
					"  Margin-right: 157px !important;\r\n" + 
					"}\r\n" + 
					".mso .w250 .divider {\r\n" + 
					"  Margin-left: 105px !important;\r\n" + 
					"  Margin-right: 105px !important;\r\n" + 
					"}\r\n" + 
					".mso .w148 .divider {\r\n" + 
					"  Margin-left: 54px !important;\r\n" + 
					"  Margin-right: 54px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-8,\r\n" + 
					".ie .size-8 {\r\n" + 
					"  font-size: 8px !important;\r\n" + 
					"  line-height: 14px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-9,\r\n" + 
					".ie .size-9 {\r\n" + 
					"  font-size: 9px !important;\r\n" + 
					"  line-height: 16px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-10,\r\n" + 
					".ie .size-10 {\r\n" + 
					"  font-size: 10px !important;\r\n" + 
					"  line-height: 18px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-11,\r\n" + 
					".ie .size-11 {\r\n" + 
					"  font-size: 11px !important;\r\n" + 
					"  line-height: 19px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-12,\r\n" + 
					".ie .size-12 {\r\n" + 
					"  font-size: 12px !important;\r\n" + 
					"  line-height: 19px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-13,\r\n" + 
					".ie .size-13 {\r\n" + 
					"  font-size: 13px !important;\r\n" + 
					"  line-height: 21px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-14,\r\n" + 
					".ie .size-14 {\r\n" + 
					"  font-size: 14px !important;\r\n" + 
					"  line-height: 21px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-15,\r\n" + 
					".ie .size-15 {\r\n" + 
					"  font-size: 15px !important;\r\n" + 
					"  line-height: 23px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-16,\r\n" + 
					".ie .size-16 {\r\n" + 
					"  font-size: 16px !important;\r\n" + 
					"  line-height: 24px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-17,\r\n" + 
					".ie .size-17 {\r\n" + 
					"  font-size: 17px !important;\r\n" + 
					"  line-height: 26px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-18,\r\n" + 
					".ie .size-18 {\r\n" + 
					"  font-size: 18px !important;\r\n" + 
					"  line-height: 26px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-20,\r\n" + 
					".ie .size-20 {\r\n" + 
					"  font-size: 20px !important;\r\n" + 
					"  line-height: 28px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-22,\r\n" + 
					".ie .size-22 {\r\n" + 
					"  font-size: 22px !important;\r\n" + 
					"  line-height: 31px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-24,\r\n" + 
					".ie .size-24 {\r\n" + 
					"  font-size: 24px !important;\r\n" + 
					"  line-height: 32px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-26,\r\n" + 
					".ie .size-26 {\r\n" + 
					"  font-size: 26px !important;\r\n" + 
					"  line-height: 34px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-28,\r\n" + 
					".ie .size-28 {\r\n" + 
					"  font-size: 28px !important;\r\n" + 
					"  line-height: 36px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-30,\r\n" + 
					".ie .size-30 {\r\n" + 
					"  font-size: 30px !important;\r\n" + 
					"  line-height: 38px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-32,\r\n" + 
					".ie .size-32 {\r\n" + 
					"  font-size: 32px !important;\r\n" + 
					"  line-height: 40px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-34,\r\n" + 
					".ie .size-34 {\r\n" + 
					"  font-size: 34px !important;\r\n" + 
					"  line-height: 43px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-36,\r\n" + 
					".ie .size-36 {\r\n" + 
					"  font-size: 36px !important;\r\n" + 
					"  line-height: 43px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-40,\r\n" + 
					".ie .size-40 {\r\n" + 
					"  font-size: 40px !important;\r\n" + 
					"  line-height: 47px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-44,\r\n" + 
					".ie .size-44 {\r\n" + 
					"  font-size: 44px !important;\r\n" + 
					"  line-height: 50px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-48,\r\n" + 
					".ie .size-48 {\r\n" + 
					"  font-size: 48px !important;\r\n" + 
					"  line-height: 54px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-56,\r\n" + 
					".ie .size-56 {\r\n" + 
					"  font-size: 56px !important;\r\n" + 
					"  line-height: 60px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-64,\r\n" + 
					".ie .size-64 {\r\n" + 
					"  font-size: 64px !important;\r\n" + 
					"  line-height: 63px !important;\r\n" + 
					"}\r\n" + 
					"</STYLE>\r\n" + 
					"       <!--[if !mso]><!-->\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"@import url(https://fonts.googleapis.com/css?family=PT+Serif:400,700,400italic,700italic|Ubuntu:400,700,400italic,700italic);\r\n" + 
					"</STYLE>\r\n" + 
					"<LINK href=\"email_files/css.css\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"body{background-color:#ededf1}.logo a:hover,.logo a:focus{color:#859bb1 !important}.mso .layout-has-border{border-top:1px solid #b4b4c4;border-bottom:1px solid #b4b4c4}.mso .layout-has-bottom-border{border-bottom:1px solid #b4b4c4}.mso .border,.ie .border{background-color:#b4b4c4}.mso h1,.ie h1{}.mso h1,.ie h1{font-size:26px !important;line-height:34px !important}.mso h2,.ie h2{}.mso h3,.ie h3{}.mso .layout__inner,.ie .layout__inner{}.mso .footer__share-button p{}.mso .footer__share-button p{font-family:Ubuntu,sans-serif}\r\n" + 
					"</STYLE>\r\n" + 
					"\r\n" + 
					"<META name=\"GENERATOR\" content=\"MSHTML 11.00.10570.1001\"></HEAD> <!--[if mso]>\r\n" + 
					"  <body class=\"mso\">\r\n" + 
					"<![endif]--> <!--[if !mso]><!--> \r\n" + 
					"  \r\n" + 
					"<BODY class=\"full-padding\" style=\"margin: 0px; padding: 0px; -webkit-text-size-adjust: 100%;\"><!--<![endif]--> \r\n" + 
					"    \r\n" + 
					"<TABLE class=\"wrapper\" role=\"presentation\" style=\"width: 100%; border-collapse: collapse; table-layout: fixed; min-width: 320px; background-color: rgb(237, 237, 241);\" \r\n" + 
					"cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
					"  <TBODY>\r\n" + 
					"  <TR>\r\n" + 
					"    <TD>\r\n" + 
					"      <DIV role=\"banner\">\r\n" + 
					"      <DIV class=\"preheader\" style=\"margin: 0px auto; width: calc(28000% - 167440px); min-width: 280px; max-width: 560px;\">\r\n" + 
					"      <DIV style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" class=\"preheader\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 280px\" valign=\"top\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"snippet\" style=\"padding: 10px 0px 5px; width: calc(14000% - 78120px); color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; display: table-cell; min-width: 140px; max-width: 280px;\"></DIV><!--[if (mso)|(IE)]></td><td style=\"width: 280px\" valign=\"top\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"webversion\" style=\"padding: 10px 0px 5px; width: calc(14100% - 78680px); text-align: right; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; display: table-cell; min-width: 139px; max-width: 280px;\"></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV>\r\n" + 
					"      <DIV class=\"header\" id=\"emb-email-header-container\" style=\"margin: 0px auto; width: calc(28000% - 167400px); min-width: 320px; max-width: 600px;\"><!--[if (mso)|(IE)]><table align=\"center\" class=\"header\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 600px\"><![endif]--> \r\n" + 
					"                \r\n" + 
					"      <DIV align=\"center\" class=\"logo emb-logo-margin-box\" style=\"margin: 6px 20px 20px; color: rgb(195, 206, 217); line-height: 32px; font-family: Roboto,Tahoma,sans-serif; font-size: 26px;\">\r\n" + 
					"      <DIV align=\"center\" class=\"logo-center\" id=\"emb-email-header\"><IMG width=\"261\" \r\n" + 
					"      style=\"border: 0px currentColor; border-image: none; width: 100%; height: auto; display: block; max-width: 261px;\" \r\n" + 
					"      alt=\"\" src=\"http://www.westerntape.com/WT_images/logo-smi-new.png\"></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"              </DIV></DIV>\r\n" + 
					"      <DIV>\r\n" + 
					"      <DIV class=\"layout one-col fixed-width\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse; background-color: rgb(255, 255, 255);\" \r\n" + 
					"      emb-background-style=\"\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-fixed-width\" emb-background-style><td style=\"width: 600px\" class=\"w560\"><![endif]--> \r\n" + 
					"                \r\n" + 
					"      <DIV class=\"column\" style=\"width: calc(28000% - 167400px); text-align: left; color: rgb(124, 126, 127); line-height: 21px; font-family: PT Serif,Georgia,serif; font-size: 14px; min-width: 320px; max-width: 600px;\">\r\n" + 
					"      <DIV style=\"margin-top: 24px; margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 10px; font-size: 1px; mso-line-height-rule: exactly;\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <H3 style=\"text-align: center; color: rgb(120, 137, 145); line-height: 24px; font-size: 16px; font-style: normal; font-weight: normal; margin-top: 0px; margin-bottom: 0px;\">\r\n" + 
					"      MESSAGE</H3>\r\n" + 
					"      <H1 style=\"text-align: left; color: rgb(62, 71, 81); line-height: 31px; font-family: Ubuntu,sans-serif; font-size: 22px; font-style: normal; font-weight: normal; margin-top: 12px; margin-bottom: 0px;\">Dear \r\n" + 
					"      " + q.getEstimator().getSureName() + "  " + q.getEstimator().getLastName() + ",</H1>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 20px;\">New activity was added to your Quote \r\n" + 
					"      .</P></DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV class=\"divider\" style=\"width: 40px; line-height: 2px; font-size: 2px; margin-right: auto; margin-bottom: 20px; margin-left: auto; display: block; background-color: rgb(180, 180, 196);\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 5px; font-size: 1px; mso-line-height-rule: exactly;\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <H2 style=\"color: rgb(62, 71, 81); line-height: 24px; font-family: Ubuntu,sans-serif; font-size: 16px; font-style: normal; font-weight: normal; margin-top: 0px; margin-bottom: 0px;\"><STRONG>Quote \r\n" + 
					"      Information</STRONG></H2>\r\n" + 
					"      <P style=\"margin-top: 16px; margin-bottom: 0px;\"><STRONG>RFQ </STRONG>: \r\n" + 
					"      " + q.getNroRfq() + "</P>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 0px;\"><STRONG>Date:&nbsp; \r\n" + 
					"      " + formatter.format(q.getCreatedDate()) + "</STRONG><BR>Contact Name :&nbsp;<STRONG><STRONG>\r\n" + 
					"      " + q.getContactName() + " -- Phone: " + q.getContactPhone() + " &nbsp;</STRONG></STRONG><BR>Email \r\n" + 
					"      :&nbsp;<STRONG><A style=\"transition:opacity 0.1s ease-in; color: rgb(78, 170, 204); text-decoration: underline;\" \r\n" + 
					"      href=\"mailto: " + q.getContactEmail() + " \">" + q.getContactEmail() + "</A>&nbsp;</STRONG><BR>\r\n" + 
					"      Observations :&nbsp;<STRONG> </STRONG></P>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 20px;\"> " + message + "\r\n" + 
					"       \r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV class=\"btn btn--shadow btn--large\" style=\"text-align: center; margin-bottom: 20px;\"><!--[if !mso]--><A \r\n" + 
					"      style=\"padding: 12px 24px 13px; border-radius: 4px; transition:opacity 0.1s ease-in; text-align: center; color: rgb(255, 255, 255) !important; line-height: 24px; font-family: PT Serif, Georgia, serif; font-size: 14px; font-weight: bold; text-decoration: none !important; display: inline-block; box-shadow: inset 0px -2px 0px 0px rgba(0,0,0,0.2); background-color: rgb(78, 170, 204);\" \r\n" + 
					"      href=\"http://192.168.1.4:8080/smiwebapp/\">GO TO \r\n" + 
					"PIPELINE</A><!--[endif]-->       <!--[if mso]><p style=\"line-height:0;margin:0;\">&nbsp;</p><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" href=\"" + url_est + "\" style=\"width:160px\" arcsize=\"9%\" fillcolor=\"#4EAACC\" stroke=\"f\"><v:shadow on=\"t\" color=\"#3E88A3\" offset=\"0,2px\"></v:shadow><v:textbox style=\"mso-fit-shape-to-text:t\" inset=\"0px,11px,0px,10px\"><center style=\"font-size:14px;line-height:24px;color:#FFFFFF;font-family:PT Serif,Georgia,serif;font-weight:bold;mso-line-height-rule:exactly;mso-text-raise:4px\">GO TO PIPELINE</center></v:textbox></v:roundrect><![endif]--></DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-bottom: 24px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <P style=\"margin-top: 0px; margin-bottom: 0px;\">Kind regards,<BR>Seal \r\n" + 
					"      Methods Planners Team</P></DIV></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"              </DIV></DIV>\r\n" + 
					"      <DIV style=\"line-height: 20px; font-size: 20px;\">&nbsp;</DIV>\r\n" + 
					"      <DIV role=\"contentinfo\">\r\n" + 
					"      <DIV class=\"layout email-footer\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 400px;\" valign=\"top\" class=\"w360\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column wide\" style=\"width: calc(8000% - 47600px); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; min-width: 320px; max-width: 400px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px;\">\r\n" + 
					"      <DIV>Seal Methods Inc. USA<BR>11915 Shoemaker Ave Santa Fe Springs, CA \r\n" + 
					"      90670<BR>Phone:&nbsp;800.423.4777&nbsp;&nbsp; &nbsp; \r\n" + 
					"      Fax:&nbsp;562.946.9439&nbsp; &nbsp; \r\n" + 
					"      Email:&nbsp;sales@sealmethodsinc.com</DIV></DIV>\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px; margin-top: 18px;\"></DIV><!--[if mso]>&nbsp;<![endif]--> \r\n" + 
					"                    </DIV></DIV><!--[if (mso)|(IE)]></td><td style=\"width: 200px;\" valign=\"top\" class=\"w160\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column narrow\" style=\"width: calc(72200px - 12000%); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; min-width: 200px; max-width: 320px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\"></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV>\r\n" + 
					"      <DIV class=\"layout one-col email-footer\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 600px;\" class=\"w560\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column\" style=\"width: calc(28000% - 167400px); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; min-width: 320px; max-width: 600px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px;\"><A style=\"transition:opacity 0.1s ease-in; color: rgb(124, 126, 127); text-decoration: underline;\" \r\n" + 
					"      href=\"\"></A>   \r\n" + 
					"                    </DIV></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV></DIV>\r\n" + 
					"      <DIV \r\n" + 
					"  style=\"line-height: 40px; font-size: 40px;\">&nbsp;</DIV></DIV></TD></TR></TBODY></TABLE><IMG \r\n" + 
					"width=\"1\" height=\"1\" style=\"margin: 0px !important; padding: 0px !important; border: 0px currentColor !important; border-image: none !important; width: 1px !important; height: 1px !important; overflow: hidden; display: block !important; visibility: hidden !important; position: fixed;\" \r\n" + 
					"alt=\"\" src=\"email_files/o.gif\" border=\"0\"></BODY></HTML>\r\n" + 
					"" ;
			
			
			String textEmailSeller =  "<!DOCTYPE HTML>\r\n" + 
					"<!-- saved from url=(0104)mhtml:file://C:\\Users\\luiss\\AppData\\Local\\Microsoft\\Windows\\INetCache\\Content.Outlook\\H0SG760V\\email.mht -->\r\n" + 
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><!--[if IE]><html xmlns=\"http://www.w3.org/1999/xhtml\" class=\"ie\"><![endif]--><!--[if !IE]><!--><HTML \r\n" + 
					"style=\"margin: 0px; padding: 0px;\" xmlns=\"http://www.w3.org/1999/xhtml\"><!--<![endif]--><HEAD><META \r\n" + 
					"content=\"IE=11.0000\" http-equiv=\"X-UA-Compatible\">\r\n" + 
					" \r\n" + 
					"<META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">     \r\n" + 
					"<TITLE></TITLE>     <!--[if !mso]><!-->\r\n" + 
					"<META http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->     \r\n" + 
					"<META name=\"viewport\" content=\"width=device-width\">\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"@media only screen and (min-width: 620px){.wrapper{min-width:600px !important}.wrapper h1{}.wrapper h1{font-size:26px !important;line-height:34px !important}.wrapper h2{}.wrapper h3{}.column{}.wrapper .size-8{font-size:8px !important;line-height:14px !important}.wrapper .size-9{font-size:9px !important;line-height:16px !important}.wrapper .size-10{font-size:10px !important;line-height:18px !important}.wrapper .size-11{font-size:11px !important;line-height:19px !important}.wrapper .size-12{font-size:12px !important;line-height:19px !important}.wrapper .size-13{font-size:13px !important;line-height:21px !important}.wrapper .size-14{font-size:14px !important;line-height:21px !important}.wrapper .size-15{font-size:15px !important;line-height:23px !important}.wrapper .size-16{font-size:16px !important;line-height:24px !important}.wrapper .size-17{font-size:17px !important;line-height:26px \r\n" + 
					"!important}.wrapper .size-18{font-size:18px !important;line-height:26px !important}.wrapper .size-20{font-size:20px !important;line-height:28px !important}.wrapper .size-22{font-size:22px !important;line-height:31px !important}.wrapper .size-24{font-size:24px !important;line-height:32px !important}.wrapper .size-26{font-size:26px !important;line-height:34px !important}.wrapper .size-28{font-size:28px !important;line-height:36px !important}.wrapper .size-30{font-size:30px !important;line-height:38px !important}.wrapper .size-32{font-size:32px !important;line-height:40px !important}.wrapper .size-34{font-size:34px !important;line-height:43px !important}.wrapper .size-36{font-size:36px !important;line-height:43px !important}.wrapper .size-40{font-size:40px !important;line-height:47px !important}.wrapper .size-44{font-size:44px !important;line-height:50px !important}.wrapper \r\n" + 
					".size-48{font-size:48px !important;line-height:54px !important}.wrapper .size-56{font-size:56px !important;line-height:60px !important}.wrapper .size-64{font-size:64px !important;line-height:63px !important}}\r\n" + 
					"</STYLE>\r\n" + 
					"     \r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"body {\r\n" + 
					"  margin: 0;\r\n" + 
					"  padding: 0;\r\n" + 
					"}\r\n" + 
					"table {\r\n" + 
					"  border-collapse: collapse;\r\n" + 
					"  table-layout: fixed;\r\n" + 
					"}\r\n" + 
					"* {\r\n" + 
					"  line-height: inherit;\r\n" + 
					"}\r\n" + 
					"[x-apple-data-detectors],\r\n" + 
					"[href^=\"tel\"],\r\n" + 
					"[href^=\"sms\"] {\r\n" + 
					"  color: inherit !important;\r\n" + 
					"  text-decoration: none !important;\r\n" + 
					"}\r\n" + 
					".wrapper .footer__share-button a:hover,\r\n" + 
					".wrapper .footer__share-button a:focus {\r\n" + 
					"  color: #ffffff !important;\r\n" + 
					"}\r\n" + 
					".btn a:hover,\r\n" + 
					".btn a:focus,\r\n" + 
					".footer__share-button a:hover,\r\n" + 
					".footer__share-button a:focus,\r\n" + 
					".email-footer__links a:hover,\r\n" + 
					".email-footer__links a:focus {\r\n" + 
					"  opacity: 0.8;\r\n" + 
					"}\r\n" + 
					".preheader,\r\n" + 
					".header,\r\n" + 
					".layout,\r\n" + 
					".column {\r\n" + 
					"  transition: width 0.25s ease-in-out, max-width 0.25s ease-in-out;\r\n" + 
					"}\r\n" + 
					".preheader td {\r\n" + 
					"  padding-bottom: 8px;\r\n" + 
					"}\r\n" + 
					".layout,\r\n" + 
					"div.header {\r\n" + 
					"  max-width: 400px !important;\r\n" + 
					"  -fallback-width: 95% !important;\r\n" + 
					"  width: calc(100% - 20px) !important;\r\n" + 
					"}\r\n" + 
					"div.preheader {\r\n" + 
					"  max-width: 360px !important;\r\n" + 
					"  -fallback-width: 90% !important;\r\n" + 
					"  width: calc(100% - 60px) !important;\r\n" + 
					"}\r\n" + 
					".snippet,\r\n" + 
					".webversion {\r\n" + 
					"  Float: none !important;\r\n" + 
					"}\r\n" + 
					".column {\r\n" + 
					"  max-width: 400px !important;\r\n" + 
					"  width: 100% !important;\r\n" + 
					"}\r\n" + 
					".fixed-width.has-border {\r\n" + 
					"  max-width: 402px !important;\r\n" + 
					"}\r\n" + 
					".fixed-width.has-border .layout__inner {\r\n" + 
					"  box-sizing: border-box;\r\n" + 
					"}\r\n" + 
					".snippet,\r\n" + 
					".webversion {\r\n" + 
					"  width: 50% !important;\r\n" + 
					"}\r\n" + 
					".ie .btn {\r\n" + 
					"  width: 100%;\r\n" + 
					"}\r\n" + 
					"[owa] .column div,\r\n" + 
					"[owa] .column button {\r\n" + 
					"  display: block !important;\r\n" + 
					"}\r\n" + 
					"[owa] .wrapper > tbody > tr > td {\r\n" + 
					"  overflow-x: auto;\r\n" + 
					"  overflow-y: hidden;\r\n" + 
					"}\r\n" + 
					"[owa] .wrapper > tbody > tr > td > div {\r\n" + 
					"  min-width: 600px;\r\n" + 
					"}\r\n" + 
					".ie .column,\r\n" + 
					"[owa] .column,\r\n" + 
					".ie .gutter,\r\n" + 
					"[owa] .gutter {\r\n" + 
					"  display: table-cell;\r\n" + 
					"  float: none !important;\r\n" + 
					"  vertical-align: top;\r\n" + 
					"}\r\n" + 
					".ie div.preheader,\r\n" + 
					"[owa] div.preheader,\r\n" + 
					".ie .email-footer,\r\n" + 
					"[owa] .email-footer {\r\n" + 
					"  max-width: 560px !important;\r\n" + 
					"  width: 560px !important;\r\n" + 
					"}\r\n" + 
					".ie .snippet,\r\n" + 
					"[owa] .snippet,\r\n" + 
					".ie .webversion,\r\n" + 
					"[owa] .webversion {\r\n" + 
					"  width: 280px !important;\r\n" + 
					"}\r\n" + 
					".ie div.header,\r\n" + 
					"[owa] div.header,\r\n" + 
					".ie .layout,\r\n" + 
					"[owa] .layout,\r\n" + 
					".ie .one-col .column,\r\n" + 
					"[owa] .one-col .column {\r\n" + 
					"  max-width: 600px !important;\r\n" + 
					"  width: 600px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col .column,\r\n" + 
					"[owa] .two-col .column {\r\n" + 
					"  max-width: 300px !important;\r\n" + 
					"  width: 300px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col .column,\r\n" + 
					"[owa] .three-col .column,\r\n" + 
					".ie .narrow,\r\n" + 
					"[owa] .narrow {\r\n" + 
					"  max-width: 200px !important;\r\n" + 
					"  width: 200px !important;\r\n" + 
					"}\r\n" + 
					".ie .wide,\r\n" + 
					"[owa] .wide {\r\n" + 
					"  width: 400px !important;\r\n" + 
					"}\r\n" + 
					".ie .fixed-width.has-border,\r\n" + 
					"[owa] .fixed-width.x_has-border,\r\n" + 
					".ie .has-gutter.has-border,\r\n" + 
					"[owa] .has-gutter.x_has-border {\r\n" + 
					"  max-width: 602px !important;\r\n" + 
					"  width: 602px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col.has-gutter .column,\r\n" + 
					"[owa] .two-col.x_has-gutter .column {\r\n" + 
					"  max-width: 290px !important;\r\n" + 
					"  width: 290px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col.has-gutter .column,\r\n" + 
					"[owa] .three-col.x_has-gutter .column,\r\n" + 
					".ie .has-gutter .narrow,\r\n" + 
					"[owa] .has-gutter .narrow {\r\n" + 
					"  max-width: 188px !important;\r\n" + 
					"  width: 188px !important;\r\n" + 
					"}\r\n" + 
					".ie .has-gutter .wide,\r\n" + 
					"[owa] .has-gutter .wide {\r\n" + 
					"  max-width: 394px !important;\r\n" + 
					"  width: 394px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col.has-gutter.has-border .column,\r\n" + 
					"[owa] .two-col.x_has-gutter.x_has-border .column {\r\n" + 
					"  max-width: 292px !important;\r\n" + 
					"  width: 292px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col.has-gutter.has-border .column,\r\n" + 
					"[owa] .three-col.x_has-gutter.x_has-border .column,\r\n" + 
					".ie .has-gutter.has-border .narrow,\r\n" + 
					"[owa] .has-gutter.x_has-border .narrow {\r\n" + 
					"  max-width: 190px !important;\r\n" + 
					"  width: 190px !important;\r\n" + 
					"}\r\n" + 
					".ie .has-gutter.has-border .wide,\r\n" + 
					"[owa] .has-gutter.x_has-border .wide {\r\n" + 
					"  max-width: 396px !important;\r\n" + 
					"  width: 396px !important;\r\n" + 
					"}\r\n" + 
					".ie .fixed-width .layout__inner {\r\n" + 
					"  border-left: 0 none white !important;\r\n" + 
					"  border-right: 0 none white !important;\r\n" + 
					"}\r\n" + 
					".ie .layout__edges {\r\n" + 
					"  display: none;\r\n" + 
					"}\r\n" + 
					".mso .layout__edges {\r\n" + 
					"  font-size: 0;\r\n" + 
					"}\r\n" + 
					".layout-fixed-width,\r\n" + 
					".mso .layout-full-width {\r\n" + 
					"  background-color: #ffffff;\r\n" + 
					"}\r\n" + 
					"@media only screen and (min-width: 620px) {\r\n" + 
					"  .column,\r\n" + 
					"  .gutter {\r\n" + 
					"    display: table-cell;\r\n" + 
					"    Float: none !important;\r\n" + 
					"    vertical-align: top;\r\n" + 
					"  }\r\n" + 
					"  div.preheader,\r\n" + 
					"  .email-footer {\r\n" + 
					"    max-width: 560px !important;\r\n" + 
					"    width: 560px !important;\r\n" + 
					"  }\r\n" + 
					"  .snippet,\r\n" + 
					"  .webversion {\r\n" + 
					"    width: 280px !important;\r\n" + 
					"  }\r\n" + 
					"  div.header,\r\n" + 
					"  .layout,\r\n" + 
					"  .one-col .column {\r\n" + 
					"    max-width: 600px !important;\r\n" + 
					"    width: 600px !important;\r\n" + 
					"  }\r\n" + 
					"  .fixed-width.has-border,\r\n" + 
					"  .fixed-width.ecxhas-border,\r\n" + 
					"  .has-gutter.has-border,\r\n" + 
					"  .has-gutter.ecxhas-border {\r\n" + 
					"    max-width: 602px !important;\r\n" + 
					"    width: 602px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col .column {\r\n" + 
					"    max-width: 300px !important;\r\n" + 
					"    width: 300px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col .column,\r\n" + 
					"  .column.narrow {\r\n" + 
					"    max-width: 200px !important;\r\n" + 
					"    width: 200px !important;\r\n" + 
					"  }\r\n" + 
					"  .column.wide {\r\n" + 
					"    width: 400px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col.has-gutter .column,\r\n" + 
					"  .two-col.ecxhas-gutter .column {\r\n" + 
					"    max-width: 290px !important;\r\n" + 
					"    width: 290px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col.has-gutter .column,\r\n" + 
					"  .three-col.ecxhas-gutter .column,\r\n" + 
					"  .has-gutter .narrow {\r\n" + 
					"    max-width: 188px !important;\r\n" + 
					"    width: 188px !important;\r\n" + 
					"  }\r\n" + 
					"  .has-gutter .wide {\r\n" + 
					"    max-width: 394px !important;\r\n" + 
					"    width: 394px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col.has-gutter.has-border .column,\r\n" + 
					"  .two-col.ecxhas-gutter.ecxhas-border .column {\r\n" + 
					"    max-width: 292px !important;\r\n" + 
					"    width: 292px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col.has-gutter.has-border .column,\r\n" + 
					"  .three-col.ecxhas-gutter.ecxhas-border .column,\r\n" + 
					"  .has-gutter.has-border .narrow,\r\n" + 
					"  .has-gutter.ecxhas-border .narrow {\r\n" + 
					"    max-width: 190px !important;\r\n" + 
					"    width: 190px !important;\r\n" + 
					"  }\r\n" + 
					"  .has-gutter.has-border .wide,\r\n" + 
					"  .has-gutter.ecxhas-border .wide {\r\n" + 
					"    max-width: 396px !important;\r\n" + 
					"    width: 396px !important;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					"@media only screen and (-webkit-min-device-pixel-ratio: 2), only screen and (min--moz-device-pixel-ratio: 2), only screen and (-o-min-device-pixel-ratio: 2/1), only screen and (min-device-pixel-ratio: 2), only screen and (min-resolution: 192dpi), only screen and (min-resolution: 2dppx) {\r\n" + 
					"  .fblike {\r\n" + 
					"    background-image: url(http://i7.cmail19.com/static/eb/master/13-the-blueprint-3/images/fblike@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .tweet {\r\n" + 
					"    background-image: url(http://i8.cmail19.com/static/eb/master/13-the-blueprint-3/images/tweet@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .linkedinshare {\r\n" + 
					"    background-image: url(http://i9.cmail19.com/static/eb/master/13-the-blueprint-3/images/lishare@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .forwardtoafriend {\r\n" + 
					"    background-image: url(http://i10.cmail19.com/static/eb/master/13-the-blueprint-3/images/forward@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					"@media (max-width: 321px) {\r\n" + 
					"  .fixed-width.has-border .layout__inner {\r\n" + 
					"    border-width: 1px 0 !important;\r\n" + 
					"  }\r\n" + 
					"  .layout,\r\n" + 
					"  .column {\r\n" + 
					"    min-width: 320px !important;\r\n" + 
					"    width: 320px !important;\r\n" + 
					"  }\r\n" + 
					"  .border {\r\n" + 
					"    display: none;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					".mso div {\r\n" + 
					"  border: 0 none white !important;\r\n" + 
					"}\r\n" + 
					".mso .w560 .divider {\r\n" + 
					"  Margin-left: 260px !important;\r\n" + 
					"  Margin-right: 260px !important;\r\n" + 
					"}\r\n" + 
					".mso .w360 .divider {\r\n" + 
					"  Margin-left: 160px !important;\r\n" + 
					"  Margin-right: 160px !important;\r\n" + 
					"}\r\n" + 
					".mso .w260 .divider {\r\n" + 
					"  Margin-left: 110px !important;\r\n" + 
					"  Margin-right: 110px !important;\r\n" + 
					"}\r\n" + 
					".mso .w160 .divider {\r\n" + 
					"  Margin-left: 60px !important;\r\n" + 
					"  Margin-right: 60px !important;\r\n" + 
					"}\r\n" + 
					".mso .w354 .divider {\r\n" + 
					"  Margin-left: 157px !important;\r\n" + 
					"  Margin-right: 157px !important;\r\n" + 
					"}\r\n" + 
					".mso .w250 .divider {\r\n" + 
					"  Margin-left: 105px !important;\r\n" + 
					"  Margin-right: 105px !important;\r\n" + 
					"}\r\n" + 
					".mso .w148 .divider {\r\n" + 
					"  Margin-left: 54px !important;\r\n" + 
					"  Margin-right: 54px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-8,\r\n" + 
					".ie .size-8 {\r\n" + 
					"  font-size: 8px !important;\r\n" + 
					"  line-height: 14px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-9,\r\n" + 
					".ie .size-9 {\r\n" + 
					"  font-size: 9px !important;\r\n" + 
					"  line-height: 16px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-10,\r\n" + 
					".ie .size-10 {\r\n" + 
					"  font-size: 10px !important;\r\n" + 
					"  line-height: 18px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-11,\r\n" + 
					".ie .size-11 {\r\n" + 
					"  font-size: 11px !important;\r\n" + 
					"  line-height: 19px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-12,\r\n" + 
					".ie .size-12 {\r\n" + 
					"  font-size: 12px !important;\r\n" + 
					"  line-height: 19px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-13,\r\n" + 
					".ie .size-13 {\r\n" + 
					"  font-size: 13px !important;\r\n" + 
					"  line-height: 21px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-14,\r\n" + 
					".ie .size-14 {\r\n" + 
					"  font-size: 14px !important;\r\n" + 
					"  line-height: 21px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-15,\r\n" + 
					".ie .size-15 {\r\n" + 
					"  font-size: 15px !important;\r\n" + 
					"  line-height: 23px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-16,\r\n" + 
					".ie .size-16 {\r\n" + 
					"  font-size: 16px !important;\r\n" + 
					"  line-height: 24px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-17,\r\n" + 
					".ie .size-17 {\r\n" + 
					"  font-size: 17px !important;\r\n" + 
					"  line-height: 26px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-18,\r\n" + 
					".ie .size-18 {\r\n" + 
					"  font-size: 18px !important;\r\n" + 
					"  line-height: 26px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-20,\r\n" + 
					".ie .size-20 {\r\n" + 
					"  font-size: 20px !important;\r\n" + 
					"  line-height: 28px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-22,\r\n" + 
					".ie .size-22 {\r\n" + 
					"  font-size: 22px !important;\r\n" + 
					"  line-height: 31px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-24,\r\n" + 
					".ie .size-24 {\r\n" + 
					"  font-size: 24px !important;\r\n" + 
					"  line-height: 32px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-26,\r\n" + 
					".ie .size-26 {\r\n" + 
					"  font-size: 26px !important;\r\n" + 
					"  line-height: 34px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-28,\r\n" + 
					".ie .size-28 {\r\n" + 
					"  font-size: 28px !important;\r\n" + 
					"  line-height: 36px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-30,\r\n" + 
					".ie .size-30 {\r\n" + 
					"  font-size: 30px !important;\r\n" + 
					"  line-height: 38px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-32,\r\n" + 
					".ie .size-32 {\r\n" + 
					"  font-size: 32px !important;\r\n" + 
					"  line-height: 40px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-34,\r\n" + 
					".ie .size-34 {\r\n" + 
					"  font-size: 34px !important;\r\n" + 
					"  line-height: 43px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-36,\r\n" + 
					".ie .size-36 {\r\n" + 
					"  font-size: 36px !important;\r\n" + 
					"  line-height: 43px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-40,\r\n" + 
					".ie .size-40 {\r\n" + 
					"  font-size: 40px !important;\r\n" + 
					"  line-height: 47px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-44,\r\n" + 
					".ie .size-44 {\r\n" + 
					"  font-size: 44px !important;\r\n" + 
					"  line-height: 50px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-48,\r\n" + 
					".ie .size-48 {\r\n" + 
					"  font-size: 48px !important;\r\n" + 
					"  line-height: 54px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-56,\r\n" + 
					".ie .size-56 {\r\n" + 
					"  font-size: 56px !important;\r\n" + 
					"  line-height: 60px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-64,\r\n" + 
					".ie .size-64 {\r\n" + 
					"  font-size: 64px !important;\r\n" + 
					"  line-height: 63px !important;\r\n" + 
					"}\r\n" + 
					"</STYLE>\r\n" + 
					"       <!--[if !mso]><!-->\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"@import url(https://fonts.googleapis.com/css?family=PT+Serif:400,700,400italic,700italic|Ubuntu:400,700,400italic,700italic);\r\n" + 
					"</STYLE>\r\n" + 
					"<LINK href=\"email_files/css.css\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"body{background-color:#ededf1}.logo a:hover,.logo a:focus{color:#859bb1 !important}.mso .layout-has-border{border-top:1px solid #b4b4c4;border-bottom:1px solid #b4b4c4}.mso .layout-has-bottom-border{border-bottom:1px solid #b4b4c4}.mso .border,.ie .border{background-color:#b4b4c4}.mso h1,.ie h1{}.mso h1,.ie h1{font-size:26px !important;line-height:34px !important}.mso h2,.ie h2{}.mso h3,.ie h3{}.mso .layout__inner,.ie .layout__inner{}.mso .footer__share-button p{}.mso .footer__share-button p{font-family:Ubuntu,sans-serif}\r\n" + 
					"</STYLE>\r\n" + 
					"\r\n" + 
					"<META name=\"GENERATOR\" content=\"MSHTML 11.00.10570.1001\"></HEAD> <!--[if mso]>\r\n" + 
					"  <body class=\"mso\">\r\n" + 
					"<![endif]--> <!--[if !mso]><!--> \r\n" + 
					"  \r\n" + 
					"<BODY class=\"full-padding\" style=\"margin: 0px; padding: 0px; -webkit-text-size-adjust: 100%;\"><!--<![endif]--> \r\n" + 
					"    \r\n" + 
					"<TABLE class=\"wrapper\" role=\"presentation\" style=\"width: 100%; border-collapse: collapse; table-layout: fixed; min-width: 320px; background-color: rgb(237, 237, 241);\" \r\n" + 
					"cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
					"  <TBODY>\r\n" + 
					"  <TR>\r\n" + 
					"    <TD>\r\n" + 
					"      <DIV role=\"banner\">\r\n" + 
					"      <DIV class=\"preheader\" style=\"margin: 0px auto; width: calc(28000% - 167440px); min-width: 280px; max-width: 560px;\">\r\n" + 
					"      <DIV style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" class=\"preheader\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 280px\" valign=\"top\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"snippet\" style=\"padding: 10px 0px 5px; width: calc(14000% - 78120px); color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; display: table-cell; min-width: 140px; max-width: 280px;\"></DIV><!--[if (mso)|(IE)]></td><td style=\"width: 280px\" valign=\"top\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"webversion\" style=\"padding: 10px 0px 5px; width: calc(14100% - 78680px); text-align: right; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; display: table-cell; min-width: 139px; max-width: 280px;\"></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV>\r\n" + 
					"      <DIV class=\"header\" id=\"emb-email-header-container\" style=\"margin: 0px auto; width: calc(28000% - 167400px); min-width: 320px; max-width: 600px;\"><!--[if (mso)|(IE)]><table align=\"center\" class=\"header\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 600px\"><![endif]--> \r\n" + 
					"                \r\n" + 
					"      <DIV align=\"center\" class=\"logo emb-logo-margin-box\" style=\"margin: 6px 20px 20px; color: rgb(195, 206, 217); line-height: 32px; font-family: Roboto,Tahoma,sans-serif; font-size: 26px;\">\r\n" + 
					"      <DIV align=\"center\" class=\"logo-center\" id=\"emb-email-header\"><IMG width=\"261\" \r\n" + 
					"      style=\"border: 0px currentColor; border-image: none; width: 100%; height: auto; display: block; max-width: 261px;\" \r\n" + 
					"      alt=\"\" src=\"http://www.westerntape.com/WT_images/logo-smi-new.png\"></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"              </DIV></DIV>\r\n" + 
					"      <DIV>\r\n" + 
					"      <DIV class=\"layout one-col fixed-width\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse; background-color: rgb(255, 255, 255);\" \r\n" + 
					"      emb-background-style=\"\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-fixed-width\" emb-background-style><td style=\"width: 600px\" class=\"w560\"><![endif]--> \r\n" + 
					"                \r\n" + 
					"      <DIV class=\"column\" style=\"width: calc(28000% - 167400px); text-align: left; color: rgb(124, 126, 127); line-height: 21px; font-family: PT Serif,Georgia,serif; font-size: 14px; min-width: 320px; max-width: 600px;\">\r\n" + 
					"      <DIV style=\"margin-top: 24px; margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 10px; font-size: 1px; mso-line-height-rule: exactly;\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <H3 style=\"text-align: center; color: rgb(120, 137, 145); line-height: 24px; font-size: 16px; font-style: normal; font-weight: normal; margin-top: 0px; margin-bottom: 0px;\">\r\n" + 
					"      MESSAGE</H3>\r\n" + 
					"      <H1 style=\"text-align: left; color: rgb(62, 71, 81); line-height: 31px; font-family: Ubuntu,sans-serif; font-size: 22px; font-style: normal; font-weight: normal; margin-top: 12px; margin-bottom: 0px;\">Dear \r\n" + 
					"      " + q.getSeller().getSureName() + "  " + q.getSeller().getLastName() + ",</H1>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 20px;\">New Activity was added to your quote \r\n" + 
					"      .</P></DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV class=\"divider\" style=\"width: 40px; line-height: 2px; font-size: 2px; margin-right: auto; margin-bottom: 20px; margin-left: auto; display: block; background-color: rgb(180, 180, 196);\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 5px; font-size: 1px; mso-line-height-rule: exactly;\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <H2 style=\"color: rgb(62, 71, 81); line-height: 24px; font-family: Ubuntu,sans-serif; font-size: 16px; font-style: normal; font-weight: normal; margin-top: 0px; margin-bottom: 0px;\"><STRONG>Quote \r\n" + 
					"      Information</STRONG></H2>\r\n" + 
					"      <P style=\"margin-top: 16px; margin-bottom: 0px;\"><STRONG>RFQ </STRONG>: \r\n" + 
					"      " + q.getNroRfq() + "</P>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 0px;\"><STRONG>Date:&nbsp; \r\n" + 
					"      " + formatter.format(q.getCreatedDate()) + "</STRONG><BR>Contact Name :&nbsp;<STRONG><STRONG>\r\n" + 
					"      " + q.getContactName() + " -- Phone: " + q.getContactPhone() + " &nbsp;</STRONG></STRONG><BR>Email \r\n" + 
					"      :&nbsp;<STRONG><A style=\"transition:opacity 0.1s ease-in; color: rgb(78, 170, 204); text-decoration: underline;\" \r\n" + 
					"      href=\"mailto: " + q.getContactEmail() + " \">" + q.getContactEmail() + "</A>&nbsp;</STRONG><BR>\r\n" + 
					"      Notes :&nbsp;<STRONG> </STRONG></P>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 20px;\">"+ message +  "\r\n" + 
					"       \r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV class=\"btn btn--shadow btn--large\" style=\"text-align: center; margin-bottom: 20px;\"><!--[if !mso]--><A \r\n" + 
					"      style=\"padding: 12px 24px 13px; border-radius: 4px; transition:opacity 0.1s ease-in; text-align: center; color: rgb(255, 255, 255) !important; line-height: 24px; font-family: PT Serif, Georgia, serif; font-size: 14px; font-weight: bold; text-decoration: none !important; display: inline-block; box-shadow: inset 0px -2px 0px 0px rgba(0,0,0,0.2); background-color: rgb(78, 170, 204);\" \r\n" + 
					"      href=\"http://quoting.westerntape.com\">GO TO \r\n" + 
					"PIPELINE</A><!--[endif]-->       <!--[if mso]><p style=\"line-height:0;margin:0;\">&nbsp;</p><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" href=\"" + url_sales + "\" style=\"width:160px\" arcsize=\"9%\" fillcolor=\"#4EAACC\" stroke=\"f\"><v:shadow on=\"t\" color=\"#3E88A3\" offset=\"0,2px\"></v:shadow><v:textbox style=\"mso-fit-shape-to-text:t\" inset=\"0px,11px,0px,10px\"><center style=\"font-size:14px;line-height:24px;color:#FFFFFF;font-family:PT Serif,Georgia,serif;font-weight:bold;mso-line-height-rule:exactly;mso-text-raise:4px\">GO TO PIPELINE</center></v:textbox></v:roundrect><![endif]--></DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-bottom: 24px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <P style=\"margin-top: 0px; margin-bottom: 0px;\">Kind regards,<BR>Seal \r\n" + 
					"      Methods Planners Team</P></DIV></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"              </DIV></DIV>\r\n" + 
					"      <DIV style=\"line-height: 20px; font-size: 20px;\">&nbsp;</DIV>\r\n" + 
					"      <DIV role=\"contentinfo\">\r\n" + 
					"      <DIV class=\"layout email-footer\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 400px;\" valign=\"top\" class=\"w360\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column wide\" style=\"width: calc(8000% - 47600px); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; min-width: 320px; max-width: 400px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px;\">\r\n" + 
					"      <DIV>Seal Methods Inc. USA<BR>11915 Shoemaker Ave Santa Fe Springs, CA \r\n" + 
					"      90670<BR>Phone:&nbsp;800.423.4777&nbsp;&nbsp; &nbsp; \r\n" + 
					"      Fax:&nbsp;562.946.9439&nbsp; &nbsp; \r\n" + 
					"      Email:&nbsp;sales@sealmethodsinc.com</DIV></DIV>\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px; margin-top: 18px;\"></DIV><!--[if mso]>&nbsp;<![endif]--> \r\n" + 
					"                    </DIV></DIV><!--[if (mso)|(IE)]></td><td style=\"width: 200px;\" valign=\"top\" class=\"w160\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column narrow\" style=\"width: calc(72200px - 12000%); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; min-width: 200px; max-width: 320px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\"></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV>\r\n" + 
					"      <DIV class=\"layout one-col email-footer\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 600px;\" class=\"w560\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column\" style=\"width: calc(28000% - 167400px); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; min-width: 320px; max-width: 600px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px;\"><A style=\"transition:opacity 0.1s ease-in; color: rgb(124, 126, 127); text-decoration: underline;\" \r\n" + 
					"      href=\"\"></A>   \r\n" + 
					"                    </DIV></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV></DIV>\r\n" + 
					"      <DIV \r\n" + 
					"  style=\"line-height: 40px; font-size: 40px;\">&nbsp;</DIV></DIV></TD></TR></TBODY></TABLE><IMG \r\n" + 
					"width=\"1\" height=\"1\" style=\"margin: 0px !important; padding: 0px !important; border: 0px currentColor !important; border-image: none !important; width: 1px !important; height: 1px !important; overflow: hidden; display: block !important; visibility: hidden !important; position: fixed;\" \r\n" + 
					"alt=\"\" src=\"email_files/o.gif\" border=\"0\"></BODY></HTML>\r\n" + 
					"" ;
			
			
		    DateFormat dateF = new SimpleDateFormat("MM/dd/yyyy");
			String text1 = "<table cellspacing=0 cellpadding=0> " 
    				+ "<tr align=left>"
    				+ "  <td align=left valign=top><table> "
    				+ "    <tbody> "
    				+ "      <tr align=left> "
    				+ "        <td align=left><table> "
    				+ "          <tbody> "
    				+ "            <tr align=left> "
    				+ "              <td align=left valign=top><p> Dear " + q.getEstimator().getSureName() + " " + q.getEstimator().getSureName() + ",</p> "
    				+ "                <p>A new quote was assigned to you.</p> "
    				+ "                <p>Best Regards,&nbsp;<br /> "
    				+ "                  Seal Methods Planners Team </p> "
    				+ "                <table> "
    				+ "                  <tbody> "
    				+ "                    <tr align=left> "
    				+ "                      <td width=724 align=left valign=top><table width=608> "
    				+ "                        <tbody> "
    				+ "                          <tr align=left> "
    				+ "                            <td width=719 align=left valign=top bgcolor=#ECF8FF><strong>APPOIMENT INFORMATION</strong> "
    				+ "                                <div>Date :&nbsp; <strong><span class=Estilo1>" + "#RFQ : " + q.getNroRfq() + "   Date:" + q.getCreatedDate() +  "</span>&nbsp;</strong><br /> "
    				+ "                                   Contact Name :&nbsp;<strong><br /> "
    				+ "                                    " + q.getCustomer().getContactName() + " -- Phone: " + q.getCustomer().getContactPhone() +  "&nbsp;<br /> "
    				+ "                                    </strong>Property Address:&nbsp;<strong>" + q.getCustomer().getContactEmail() + "&nbsp;</strong><br /> "
    				+ "                                    Observations "
    				+ "                                  :&nbsp;<strong> " + q.getQuoteNotes() + "</strong></div></td> "
    				+ "                            </tr> "
    				+ "                        </tbody> "
    				+ "                      </table></td> "
    				+ "                    </tr> "
    				+ "                  </tbody> "
    				+ "                </table> "
    				+ "                <table> "
    				+ "                  <tbody> "
    				+ "                    <tr align=left> "
    				+ "                      <td align=left bgcolor=#ebebeb valign=top><table> "
    				+ "                        <tbody> "
    				+ "                          <tr align=left> "
    				+ "                            <td align=left valign=top><h4 align=center><img src=https://cdn.supplyfx.com/images/7021231449058346550.JPG width=110 height=115 /></h4> "
    				+ "                                <div> "
    				+ "                                  <p><strong>Santa Fe Springs, CA</strong><br /> "
    				+ "                                    Monday-Friday: 9:00am-5:30pm<br /> "
    				+ "                                  </p> "
    				+ "                                </div></td> "
    				+ "                            <td align=left valign=top></td> "
    				+ "                          </tr> "
    				+ "                        </tbody> "
    				+ "                      </table></td> "
    				+ "                      <td align=left bgcolor=#ebebeb valign=top><table> "
    				+ "                        <tbody> "
    				+ "                          <tr align=left> "
    				+ "                           <td align=left valign=top><h4 align=left>FOR MORE INFORMATION PLEASE CHECK OUT OUR WEBSITE</h4> "
    				+ "                                <div><strong><span id=et-info-phone>323-736-1949 - </span><a href=https://www.sealmethodsinc.com/>www.sealmethodsinc.com/</a></strong></div> "
    				+ "                                <br /> "
    				+ "                              <div> "
    				+ "                                <h4 align=left>CONNECT WITH US</h4> "
    				+ "                                <strong>sales@sealmethodsinc.com</strong><br /> "
    				+ "                               <a href=https://www.facebook.com/Seal-Methods-133774880008750/><img src=http://significadodesimbolos.com/wp-content/uploads/2014/09/simbolos-facebook-300x300.png width=41 height=39 border=0 /></a>&nbsp;<a href=https://twitter.com/SealMethodsInc><img src=http://www.avenueroadadvertising.com/wp-content/uploads/2015/05/twitter-logo.png width=41 height=39 border=0 /></a>&nbsp;<a href=https://www.youtube.com/channel/UCI5FZLsoHd0oNSDS9ZWf4Ng target=_blank><img src=http://pngimg.com/uploads/instagram/instagram_PNG14.png width=40 height=38 border=0 /></a></div></td> "
    				+ "                           <td align=left valign=top></td> "
    				+ "                          </tr> "
    				+ "                        </tbody> "
    				+ "                      </table></td> "
    				+ "                    </tr> "
    				+ "                  </tbody> "
    				+ "                </table></td> "
    				+ "            </tr> "
    				+ "          </tbody> "
    				+ "        </table></td> "
    				+ "      </tr> "
    				+ "    </tbody> "
    				+ "  </table></td> "
    				+ " </tr> "
    				+ "</table>"; 
			System.out.println("SENDING EMAIL TO....." +  q.getEstimator().getEmailContact());
			
			Users us = userRep.findByUsername(username);
			if (us.getUserType().equals("1")) {
	    		emailService.sendHTMlEmail(q.getEstimator().getEmailContact(), "New Message , Rfq No : " + q.getNroRfq(), textEmail);
	    		emailService.sendHTMlEmail(q.getSeller().getEmailContact(), "New Message , Rfq No : " + q.getNroRfq(), textEmailSeller);
				
			}
			
			if (us.getUserType().equals("2")) {
	    		emailService.sendHTMlEmail(q.getEstimator().getEmailContact(), "New Message , Rfq No : " + q.getNroRfq(), textEmail);
				
			}
			
			if (us.getUserType().equals("3") && us.getNotification().equals("1")) {
	    		emailService.sendHTMlEmail(q.getSeller().getEmailContact(), "New Message , Rfq No : " + q.getNroRfq(), textEmailSeller);
		
			}
			

    		System.out.println("EMAIl sent to :");
    		
    		
		   }
		catch (Exception e) {
			System.out.println("Error : sending email.." +  q.getEstimator().getEmailContact());
		}
            


		return new ResponseEntity<String>("0", HttpStatus.OK);
		
		

	}	

	
	
	
	@RequestMapping("printQuoteForm")
	@ResponseBody
	public void generatePDFJasperleadsActivity(@RequestParam("idQuote") long idQuote,
			                           HttpServletRequest request,
				                       HttpServletResponse response) throws IOException, SQLException, ParseException {
		
		//DataSource ds = (DataSource)ApplicationContextProvider.getApplicationContext().getBean("dataSource");
		DataSource ds = (DataSource)appContext.getBean("dataSource");
		Connection c = ds.getConnection();
		String sourceFileName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idQuote", idQuote);
		
		sourceFileName = System.getProperty("user.dir") + "/src/main/resources/static/quoteForm.jrxml";
		System.out.println(sourceFileName);
		try {
			System.out.println("Start compiling!!! ...");
			JasperCompileManager.compileReportToFile(sourceFileName);
			System.out.println("Done compiling!!! ...");
			sourceFileName = System.getProperty("user.dir") + "/src/main/resources/static/quoteForm.jasper";
			
			//JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(
			//		dataList);
			JasperReport report = (JasperReport) JRLoader
					.loadObjectFromFile(sourceFileName);
			JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileName, parameters, c);
			if (jasperPrint != null) {
				byte[] pdfReport = JasperExportManager
						.exportReportToPdf(jasperPrint);
				response.reset();
				response.setContentType("application/pdf");
				response.setHeader("Cache-Control", "no-store");
				response.setHeader("Cache-Control", "private");
				response.setHeader("Pragma", "no-store");
				response.setContentLength(pdfReport.length);
				response.getOutputStream().write(pdfReport);
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
			
			c.close();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}	
	
	
	@RequestMapping("printQuoteFormOrderbyPart")
	@ResponseBody
	public void generateQuoteFormOrderByPart(@RequestParam("idQuote") long idQuote,
			                           HttpServletRequest request,
				                       HttpServletResponse response) throws IOException, SQLException, ParseException {
		
		//DataSource ds = (DataSource)ApplicationContextProvider.getApplicationContext().getBean("dataSource");
		DataSource ds = (DataSource)appContext.getBean("dataSource");
		Connection c = ds.getConnection();
		String sourceFileName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idQuote", idQuote);
		
		sourceFileName = System.getProperty("user.dir") + "/src/main/resources/static/quoteFormOrderByPart.jrxml";
		System.out.println(sourceFileName);
		try {
			System.out.println("Start compiling!!! ...");
			JasperCompileManager.compileReportToFile(sourceFileName);
			System.out.println("Done compiling!!! ...");
			sourceFileName = System.getProperty("user.dir") + "/src/main/resources/static/quoteFormOrderByPart.jasper";
			
			//JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(
			//		dataList);
			JasperReport report = (JasperReport) JRLoader
					.loadObjectFromFile(sourceFileName);
			JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileName, parameters, c);
			if (jasperPrint != null) {
				byte[] pdfReport = JasperExportManager
						.exportReportToPdf(jasperPrint);
				response.reset();
				response.setContentType("application/pdf");
				response.setHeader("Cache-Control", "no-store");
				response.setHeader("Cache-Control", "private");
				response.setHeader("Pragma", "no-store");
				response.setContentLength(pdfReport.length);
				response.getOutputStream().write(pdfReport);
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
			
			c.close();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}	
	
	
	
	@RequestMapping("sheetcostForm")
	@ResponseBody
	public void generatePDFJaspersheetcost(@RequestParam("idItem") long idItem,
			                           HttpServletRequest request,
				                       HttpServletResponse response) throws IOException, SQLException, ParseException {
		
		//DataSource ds = (DataSource)ApplicationContextProvider.getApplicationContext().getBean("dataSource");
		DataSource ds = (DataSource)appContext.getBean("dataSource");
		Connection c = ds.getConnection();
		String sourceFileName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("idItem", idItem);
		
		sourceFileName = System.getProperty("user.dir") + "/src/main/resources/static/sheetcostForm.jrxml";
		System.out.println(sourceFileName + "IdItem : " + idItem);
		try {
			System.out.println("Start compiling!!! ...");
			JasperCompileManager.compileReportToFile(sourceFileName);
			System.out.println("Done compiling!!! ...");
			sourceFileName = System.getProperty("user.dir") + "/src/main/resources/static/sheetcostForm.jasper";
			
			//JRBeanCollectionDataSource beanColDataSource = new JRBeanCollectionDataSource(
			//		dataList);
			JasperReport report = (JasperReport) JRLoader
					.loadObjectFromFile(sourceFileName);

			
			JasperPrint jasperPrint = JasperFillManager.fillReport(sourceFileName, parameters, c);
			if (jasperPrint != null) {
				byte[] pdfReport = JasperExportManager
						.exportReportToPdf(jasperPrint);
				response.reset();
				response.setContentType("application/pdf");
				response.setHeader("Cache-Control", "no-store");
				response.setHeader("Cache-Control", "private");
				response.setHeader("Pragma", "no-store");
				response.setContentLength(pdfReport.length);
				response.getOutputStream().write(pdfReport);
				response.getOutputStream().flush();
				response.getOutputStream().close();
			}
			
			c.close();
		} catch (JRException e) {
			e.printStackTrace();
		}
	}
	
	
	
	@RequestMapping(value = "quote/testnotifyMessage", method = RequestMethod.GET)
	public  @ResponseBody ResponseEntity<String> testNotify(@RequestParam("idCustomer")  String idCustomer) throws IOException{
		
		//Quote q = quoteRep.findById(idQuote);    
		try {
			DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
			String customerName = "Luis Sanchez";
			String linkLogo = "http://www.classicdogspetscare.com/wp-content/uploads/2017/11/classicdog_resize_2.png";
			String verifyCode = "857900";
			
			
			String textEmail =  "<!DOCTYPE HTML>\r\n" + 
					"<!-- saved from url=(0104)mhtml:file://C:\\Users\\luiss\\AppData\\Local\\Microsoft\\Windows\\INetCache\\Content.Outlook\\H0SG760V\\email.mht -->\r\n" + 
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><!--[if IE]><html xmlns=\"http://www.w3.org/1999/xhtml\" class=\"ie\"><![endif]--><!--[if !IE]><!--><HTML \r\n" + 
					"style=\"margin: 0px; padding: 0px;\" xmlns=\"http://www.w3.org/1999/xhtml\"><!--<![endif]--><HEAD><META \r\n" + 
					"content=\"IE=11.0000\" http-equiv=\"X-UA-Compatible\">\r\n" + 
					" \r\n" + 
					"<META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">     \r\n" + 
					"<TITLE></TITLE>     <!--[if !mso]><!-->\r\n" + 
					"<META http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->     \r\n" + 
					"<META name=\"viewport\" content=\"width=device-width\">\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"@media only screen and (min-width: 620px){.wrapper{min-width:600px !important}.wrapper h1{}.wrapper h1{font-size:26px !important;line-height:34px !important}.wrapper h2{}.wrapper h3{}.column{}.wrapper .size-8{font-size:8px !important;line-height:14px !important}.wrapper .size-9{font-size:9px !important;line-height:16px !important}.wrapper .size-10{font-size:10px !important;line-height:18px !important}.wrapper .size-11{font-size:11px !important;line-height:19px !important}.wrapper .size-12{font-size:12px !important;line-height:19px !important}.wrapper .size-13{font-size:13px !important;line-height:21px !important}.wrapper .size-14{font-size:14px !important;line-height:21px !important}.wrapper .size-15{font-size:15px !important;line-height:23px !important}.wrapper .size-16{font-size:16px !important;line-height:24px !important}.wrapper .size-17{font-size:17px !important;line-height:26px \r\n" + 
					"!important}.wrapper .size-18{font-size:18px !important;line-height:26px !important}.wrapper .size-20{font-size:20px !important;line-height:28px !important}.wrapper .size-22{font-size:22px !important;line-height:31px !important}.wrapper .size-24{font-size:24px !important;line-height:32px !important}.wrapper .size-26{font-size:26px !important;line-height:34px !important}.wrapper .size-28{font-size:28px !important;line-height:36px !important}.wrapper .size-30{font-size:30px !important;line-height:38px !important}.wrapper .size-32{font-size:32px !important;line-height:40px !important}.wrapper .size-34{font-size:34px !important;line-height:43px !important}.wrapper .size-36{font-size:36px !important;line-height:43px !important}.wrapper .size-40{font-size:40px !important;line-height:47px !important}.wrapper .size-44{font-size:44px !important;line-height:50px !important}.wrapper \r\n" + 
					".size-48{font-size:48px !important;line-height:54px !important}.wrapper .size-56{font-size:56px !important;line-height:60px !important}.wrapper .size-64{font-size:64px !important;line-height:63px !important}}\r\n" + 
					"</STYLE>\r\n" + 
					"     \r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"body {\r\n" + 
					"  margin: 0;\r\n" + 
					"  padding: 0;\r\n" + 
					"}\r\n" + 
					"table {\r\n" + 
					"  border-collapse: collapse;\r\n" + 
					"  table-layout: fixed;\r\n" + 
					"}\r\n" + 
					"* {\r\n" + 
					"  line-height: inherit;\r\n" + 
					"}\r\n" + 
					"[x-apple-data-detectors],\r\n" + 
					"[href^=\"tel\"],\r\n" + 
					"[href^=\"sms\"] {\r\n" + 
					"  color: inherit !important;\r\n" + 
					"  text-decoration: none !important;\r\n" + 
					"}\r\n" + 
					".wrapper .footer__share-button a:hover,\r\n" + 
					".wrapper .footer__share-button a:focus {\r\n" + 
					"  color: #ffffff !important;\r\n" + 
					"}\r\n" + 
					".btn a:hover,\r\n" + 
					".btn a:focus,\r\n" + 
					".footer__share-button a:hover,\r\n" + 
					".footer__share-button a:focus,\r\n" + 
					".email-footer__links a:hover,\r\n" + 
					".email-footer__links a:focus {\r\n" + 
					"  opacity: 0.8;\r\n" + 
					"}\r\n" + 
					".preheader,\r\n" + 
					".header,\r\n" + 
					".layout,\r\n" + 
					".column {\r\n" + 
					"  transition: width 0.25s ease-in-out, max-width 0.25s ease-in-out;\r\n" + 
					"}\r\n" + 
					".preheader td {\r\n" + 
					"  padding-bottom: 8px;\r\n" + 
					"}\r\n" + 
					".layout,\r\n" + 
					"div.header {\r\n" + 
					"  max-width: 400px !important;\r\n" + 
					"  -fallback-width: 95% !important;\r\n" + 
					"  width: calc(100% - 20px) !important;\r\n" + 
					"}\r\n" + 
					"div.preheader {\r\n" + 
					"  max-width: 360px !important;\r\n" + 
					"  -fallback-width: 90% !important;\r\n" + 
					"  width: calc(100% - 60px) !important;\r\n" + 
					"}\r\n" + 
					".snippet,\r\n" + 
					".webversion {\r\n" + 
					"  Float: none !important;\r\n" + 
					"}\r\n" + 
					".column {\r\n" + 
					"  max-width: 400px !important;\r\n" + 
					"  width: 100% !important;\r\n" + 
					"}\r\n" + 
					".fixed-width.has-border {\r\n" + 
					"  max-width: 402px !important;\r\n" + 
					"}\r\n" + 
					".fixed-width.has-border .layout__inner {\r\n" + 
					"  box-sizing: border-box;\r\n" + 
					"}\r\n" + 
					".snippet,\r\n" + 
					".webversion {\r\n" + 
					"  width: 50% !important;\r\n" + 
					"}\r\n" + 
					".ie .btn {\r\n" + 
					"  width: 100%;\r\n" + 
					"}\r\n" + 
					"[owa] .column div,\r\n" + 
					"[owa] .column button {\r\n" + 
					"  display: block !important;\r\n" + 
					"}\r\n" + 
					"[owa] .wrapper > tbody > tr > td {\r\n" + 
					"  overflow-x: auto;\r\n" + 
					"  overflow-y: hidden;\r\n" + 
					"}\r\n" + 
					"[owa] .wrapper > tbody > tr > td > div {\r\n" + 
					"  min-width: 600px;\r\n" + 
					"}\r\n" + 
					".ie .column,\r\n" + 
					"[owa] .column,\r\n" + 
					".ie .gutter,\r\n" + 
					"[owa] .gutter {\r\n" + 
					"  display: table-cell;\r\n" + 
					"  float: none !important;\r\n" + 
					"  vertical-align: top;\r\n" + 
					"}\r\n" + 
					".ie div.preheader,\r\n" + 
					"[owa] div.preheader,\r\n" + 
					".ie .email-footer,\r\n" + 
					"[owa] .email-footer {\r\n" + 
					"  max-width: 560px !important;\r\n" + 
					"  width: 560px !important;\r\n" + 
					"}\r\n" + 
					".ie .snippet,\r\n" + 
					"[owa] .snippet,\r\n" + 
					".ie .webversion,\r\n" + 
					"[owa] .webversion {\r\n" + 
					"  width: 280px !important;\r\n" + 
					"}\r\n" + 
					".ie div.header,\r\n" + 
					"[owa] div.header,\r\n" + 
					".ie .layout,\r\n" + 
					"[owa] .layout,\r\n" + 
					".ie .one-col .column,\r\n" + 
					"[owa] .one-col .column {\r\n" + 
					"  max-width: 600px !important;\r\n" + 
					"  width: 600px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col .column,\r\n" + 
					"[owa] .two-col .column {\r\n" + 
					"  max-width: 300px !important;\r\n" + 
					"  width: 300px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col .column,\r\n" + 
					"[owa] .three-col .column,\r\n" + 
					".ie .narrow,\r\n" + 
					"[owa] .narrow {\r\n" + 
					"  max-width: 200px !important;\r\n" + 
					"  width: 200px !important;\r\n" + 
					"}\r\n" + 
					".ie .wide,\r\n" + 
					"[owa] .wide {\r\n" + 
					"  width: 400px !important;\r\n" + 
					"}\r\n" + 
					".ie .fixed-width.has-border,\r\n" + 
					"[owa] .fixed-width.x_has-border,\r\n" + 
					".ie .has-gutter.has-border,\r\n" + 
					"[owa] .has-gutter.x_has-border {\r\n" + 
					"  max-width: 602px !important;\r\n" + 
					"  width: 602px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col.has-gutter .column,\r\n" + 
					"[owa] .two-col.x_has-gutter .column {\r\n" + 
					"  max-width: 290px !important;\r\n" + 
					"  width: 290px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col.has-gutter .column,\r\n" + 
					"[owa] .three-col.x_has-gutter .column,\r\n" + 
					".ie .has-gutter .narrow,\r\n" + 
					"[owa] .has-gutter .narrow {\r\n" + 
					"  max-width: 188px !important;\r\n" + 
					"  width: 188px !important;\r\n" + 
					"}\r\n" + 
					".ie .has-gutter .wide,\r\n" + 
					"[owa] .has-gutter .wide {\r\n" + 
					"  max-width: 394px !important;\r\n" + 
					"  width: 394px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col.has-gutter.has-border .column,\r\n" + 
					"[owa] .two-col.x_has-gutter.x_has-border .column {\r\n" + 
					"  max-width: 292px !important;\r\n" + 
					"  width: 292px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col.has-gutter.has-border .column,\r\n" + 
					"[owa] .three-col.x_has-gutter.x_has-border .column,\r\n" + 
					".ie .has-gutter.has-border .narrow,\r\n" + 
					"[owa] .has-gutter.x_has-border .narrow {\r\n" + 
					"  max-width: 190px !important;\r\n" + 
					"  width: 190px !important;\r\n" + 
					"}\r\n" + 
					".ie .has-gutter.has-border .wide,\r\n" + 
					"[owa] .has-gutter.x_has-border .wide {\r\n" + 
					"  max-width: 396px !important;\r\n" + 
					"  width: 396px !important;\r\n" + 
					"}\r\n" + 
					".ie .fixed-width .layout__inner {\r\n" + 
					"  border-left: 0 none white !important;\r\n" + 
					"  border-right: 0 none white !important;\r\n" + 
					"}\r\n" + 
					".ie .layout__edges {\r\n" + 
					"  display: none;\r\n" + 
					"}\r\n" + 
					".mso .layout__edges {\r\n" + 
					"  font-size: 0;\r\n" + 
					"}\r\n" + 
					".layout-fixed-width,\r\n" + 
					".mso .layout-full-width {\r\n" + 
					"  background-color: #ffffff;\r\n" + 
					"}\r\n" + 
					"@media only screen and (min-width: 620px) {\r\n" + 
					"  .column,\r\n" + 
					"  .gutter {\r\n" + 
					"    display: table-cell;\r\n" + 
					"    Float: none !important;\r\n" + 
					"    vertical-align: top;\r\n" + 
					"  }\r\n" + 
					"  div.preheader,\r\n" + 
					"  .email-footer {\r\n" + 
					"    max-width: 560px !important;\r\n" + 
					"    width: 560px !important;\r\n" + 
					"  }\r\n" + 
					"  .snippet,\r\n" + 
					"  .webversion {\r\n" + 
					"    width: 280px !important;\r\n" + 
					"  }\r\n" + 
					"  div.header,\r\n" + 
					"  .layout,\r\n" + 
					"  .one-col .column {\r\n" + 
					"    max-width: 600px !important;\r\n" + 
					"    width: 600px !important;\r\n" + 
					"  }\r\n" + 
					"  .fixed-width.has-border,\r\n" + 
					"  .fixed-width.ecxhas-border,\r\n" + 
					"  .has-gutter.has-border,\r\n" + 
					"  .has-gutter.ecxhas-border {\r\n" + 
					"    max-width: 602px !important;\r\n" + 
					"    width: 602px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col .column {\r\n" + 
					"    max-width: 300px !important;\r\n" + 
					"    width: 300px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col .column,\r\n" + 
					"  .column.narrow {\r\n" + 
					"    max-width: 200px !important;\r\n" + 
					"    width: 200px !important;\r\n" + 
					"  }\r\n" + 
					"  .column.wide {\r\n" + 
					"    width: 400px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col.has-gutter .column,\r\n" + 
					"  .two-col.ecxhas-gutter .column {\r\n" + 
					"    max-width: 290px !important;\r\n" + 
					"    width: 290px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col.has-gutter .column,\r\n" + 
					"  .three-col.ecxhas-gutter .column,\r\n" + 
					"  .has-gutter .narrow {\r\n" + 
					"    max-width: 188px !important;\r\n" + 
					"    width: 188px !important;\r\n" + 
					"  }\r\n" + 
					"  .has-gutter .wide {\r\n" + 
					"    max-width: 394px !important;\r\n" + 
					"    width: 394px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col.has-gutter.has-border .column,\r\n" + 
					"  .two-col.ecxhas-gutter.ecxhas-border .column {\r\n" + 
					"    max-width: 292px !important;\r\n" + 
					"    width: 292px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col.has-gutter.has-border .column,\r\n" + 
					"  .three-col.ecxhas-gutter.ecxhas-border .column,\r\n" + 
					"  .has-gutter.has-border .narrow,\r\n" + 
					"  .has-gutter.ecxhas-border .narrow {\r\n" + 
					"    max-width: 190px !important;\r\n" + 
					"    width: 190px !important;\r\n" + 
					"  }\r\n" + 
					"  .has-gutter.has-border .wide,\r\n" + 
					"  .has-gutter.ecxhas-border .wide {\r\n" + 
					"    max-width: 396px !important;\r\n" + 
					"    width: 396px !important;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					"@media only screen and (-webkit-min-device-pixel-ratio: 2), only screen and (min--moz-device-pixel-ratio: 2), only screen and (-o-min-device-pixel-ratio: 2/1), only screen and (min-device-pixel-ratio: 2), only screen and (min-resolution: 192dpi), only screen and (min-resolution: 2dppx) {\r\n" + 
					"  .fblike {\r\n" + 
					"    background-image: url(http://i7.cmail19.com/static/eb/master/13-the-blueprint-3/images/fblike@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .tweet {\r\n" + 
					"    background-image: url(http://i8.cmail19.com/static/eb/master/13-the-blueprint-3/images/tweet@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .linkedinshare {\r\n" + 
					"    background-image: url(http://i9.cmail19.com/static/eb/master/13-the-blueprint-3/images/lishare@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .forwardtoafriend {\r\n" + 
					"    background-image: url(http://i10.cmail19.com/static/eb/master/13-the-blueprint-3/images/forward@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					"@media (max-width: 321px) {\r\n" + 
					"  .fixed-width.has-border .layout__inner {\r\n" + 
					"    border-width: 1px 0 !important;\r\n" + 
					"  }\r\n" + 
					"  .layout,\r\n" + 
					"  .column {\r\n" + 
					"    min-width: 320px !important;\r\n" + 
					"    width: 320px !important;\r\n" + 
					"  }\r\n" + 
					"  .border {\r\n" + 
					"    display: none;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					".mso div {\r\n" + 
					"  border: 0 none white !important;\r\n" + 
					"}\r\n" + 
					".mso .w560 .divider {\r\n" + 
					"  Margin-left: 260px !important;\r\n" + 
					"  Margin-right: 260px !important;\r\n" + 
					"}\r\n" + 
					".mso .w360 .divider {\r\n" + 
					"  Margin-left: 160px !important;\r\n" + 
					"  Margin-right: 160px !important;\r\n" + 
					"}\r\n" + 
					".mso .w260 .divider {\r\n" + 
					"  Margin-left: 110px !important;\r\n" + 
					"  Margin-right: 110px !important;\r\n" + 
					"}\r\n" + 
					".mso .w160 .divider {\r\n" + 
					"  Margin-left: 60px !important;\r\n" + 
					"  Margin-right: 60px !important;\r\n" + 
					"}\r\n" + 
					".mso .w354 .divider {\r\n" + 
					"  Margin-left: 157px !important;\r\n" + 
					"  Margin-right: 157px !important;\r\n" + 
					"}\r\n" + 
					".mso .w250 .divider {\r\n" + 
					"  Margin-left: 105px !important;\r\n" + 
					"  Margin-right: 105px !important;\r\n" + 
					"}\r\n" + 
					".mso .w148 .divider {\r\n" + 
					"  Margin-left: 54px !important;\r\n" + 
					"  Margin-right: 54px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-8,\r\n" + 
					".ie .size-8 {\r\n" + 
					"  font-size: 8px !important;\r\n" + 
					"  line-height: 14px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-9,\r\n" + 
					".ie .size-9 {\r\n" + 
					"  font-size: 9px !important;\r\n" + 
					"  line-height: 16px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-10,\r\n" + 
					".ie .size-10 {\r\n" + 
					"  font-size: 10px !important;\r\n" + 
					"  line-height: 18px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-11,\r\n" + 
					".ie .size-11 {\r\n" + 
					"  font-size: 11px !important;\r\n" + 
					"  line-height: 19px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-12,\r\n" + 
					".ie .size-12 {\r\n" + 
					"  font-size: 12px !important;\r\n" + 
					"  line-height: 19px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-13,\r\n" + 
					".ie .size-13 {\r\n" + 
					"  font-size: 13px !important;\r\n" + 
					"  line-height: 21px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-14,\r\n" + 
					".ie .size-14 {\r\n" + 
					"  font-size: 14px !important;\r\n" + 
					"  line-height: 21px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-15,\r\n" + 
					".ie .size-15 {\r\n" + 
					"  font-size: 15px !important;\r\n" + 
					"  line-height: 23px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-16,\r\n" + 
					".ie .size-16 {\r\n" + 
					"  font-size: 16px !important;\r\n" + 
					"  line-height: 24px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-17,\r\n" + 
					".ie .size-17 {\r\n" + 
					"  font-size: 17px !important;\r\n" + 
					"  line-height: 26px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-18,\r\n" + 
					".ie .size-18 {\r\n" + 
					"  font-size: 18px !important;\r\n" + 
					"  line-height: 26px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-20,\r\n" + 
					".ie .size-20 {\r\n" + 
					"  font-size: 20px !important;\r\n" + 
					"  line-height: 28px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-22,\r\n" + 
					".ie .size-22 {\r\n" + 
					"  font-size: 22px !important;\r\n" + 
					"  line-height: 31px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-24,\r\n" + 
					".ie .size-24 {\r\n" + 
					"  font-size: 24px !important;\r\n" + 
					"  line-height: 32px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-26,\r\n" + 
					".ie .size-26 {\r\n" + 
					"  font-size: 26px !important;\r\n" + 
					"  line-height: 34px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-28,\r\n" + 
					".ie .size-28 {\r\n" + 
					"  font-size: 28px !important;\r\n" + 
					"  line-height: 36px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-30,\r\n" + 
					".ie .size-30 {\r\n" + 
					"  font-size: 30px !important;\r\n" + 
					"  line-height: 38px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-32,\r\n" + 
					".ie .size-32 {\r\n" + 
					"  font-size: 32px !important;\r\n" + 
					"  line-height: 40px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-34,\r\n" + 
					".ie .size-34 {\r\n" + 
					"  font-size: 34px !important;\r\n" + 
					"  line-height: 43px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-36,\r\n" + 
					".ie .size-36 {\r\n" + 
					"  font-size: 36px !important;\r\n" + 
					"  line-height: 43px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-40,\r\n" + 
					".ie .size-40 {\r\n" + 
					"  font-size: 40px !important;\r\n" + 
					"  line-height: 47px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-44,\r\n" + 
					".ie .size-44 {\r\n" + 
					"  font-size: 44px !important;\r\n" + 
					"  line-height: 50px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-48,\r\n" + 
					".ie .size-48 {\r\n" + 
					"  font-size: 48px !important;\r\n" + 
					"  line-height: 54px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-56,\r\n" + 
					".ie .size-56 {\r\n" + 
					"  font-size: 56px !important;\r\n" + 
					"  line-height: 60px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-64,\r\n" + 
					".ie .size-64 {\r\n" + 
					"  font-size: 64px !important;\r\n" + 
					"  line-height: 63px !important;\r\n" + 
					"}\r\n" + 
					"</STYLE>\r\n" + 
					"       <!--[if !mso]><!-->\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"@import url(https://fonts.googleapis.com/css?family=PT+Serif:400,700,400italic,700italic|Ubuntu:400,700,400italic,700italic);\r\n" + 
					"</STYLE>\r\n" + 
					"<LINK href=\"email_files/css.css\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"body{background-color:#ededf1}.logo a:hover,.logo a:focus{color:#859bb1 !important}.mso .layout-has-border{border-top:1px solid #b4b4c4;border-bottom:1px solid #b4b4c4}.mso .layout-has-bottom-border{border-bottom:1px solid #b4b4c4}.mso .border,.ie .border{background-color:#b4b4c4}.mso h1,.ie h1{}.mso h1,.ie h1{font-size:26px !important;line-height:34px !important}.mso h2,.ie h2{}.mso h3,.ie h3{}.mso .layout__inner,.ie .layout__inner{}.mso .footer__share-button p{}.mso .footer__share-button p{font-family:Ubuntu,sans-serif}\r\n" + 
					"</STYLE>\r\n" + 
					"\r\n" + 
					"<META name=\"GENERATOR\" content=\"MSHTML 11.00.10570.1001\"></HEAD> <!--[if mso]>\r\n" + 
					"  <body class=\"mso\">\r\n" + 
					"<![endif]--> <!--[if !mso]><!--> \r\n" + 
					"  \r\n" + 
					"<BODY class=\"full-padding\" style=\"margin: 0px; padding: 0px; -webkit-text-size-adjust: 100%;\"><!--<![endif]--> \r\n" + 
					"    \r\n" + 
					"<TABLE class=\"wrapper\" role=\"presentation\" style=\"width: 100%; border-collapse: collapse; table-layout: fixed; min-width: 320px; background-color: rgb(237, 237, 241);\" \r\n" + 
					"cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
					"  <TBODY>\r\n" + 
					"  <TR>\r\n" + 
					"    <TD>\r\n" + 
					"      <DIV role=\"banner\">\r\n" + 
					"      <DIV class=\"preheader\" style=\"margin: 0px auto; width: calc(28000% - 167440px); min-width: 280px; max-width: 560px;\">\r\n" + 
					"      <DIV style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" class=\"preheader\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 280px\" valign=\"top\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"snippet\" style=\"padding: 10px 0px 5px; width: calc(14000% - 78120px); color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; display: table-cell; min-width: 140px; max-width: 280px;\"></DIV><!--[if (mso)|(IE)]></td><td style=\"width: 280px\" valign=\"top\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"webversion\" style=\"padding: 10px 0px 5px; width: calc(14100% - 78680px); text-align: right; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; display: table-cell; min-width: 139px; max-width: 280px;\"></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV>\r\n" + 
					"      <DIV class=\"header\" id=\"emb-email-header-container\" style=\"margin: 0px auto; width: calc(28000% - 167400px); min-width: 320px; max-width: 600px;\"><!--[if (mso)|(IE)]><table align=\"center\" class=\"header\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 600px\"><![endif]--> \r\n" + 
					"                \r\n" + 
					"      <DIV align=\"center\" class=\"logo emb-logo-margin-box\" style=\"margin: 6px 20px 20px; color: rgb(195, 206, 217); line-height: 32px; font-family: Roboto,Tahoma,sans-serif; font-size: 26px;\">\r\n" + 
					"      <DIV align=\"center\" class=\"logo-center\" id=\"emb-email-header\"><IMG width=\"261\" \r\n" + 
					"      style=\"border: 0px currentColor; border-image: none; width: 100%; height: auto; display: block; max-width: 261px;\" \r\n" + 
					"      alt=\"\" src=\"" + linkLogo + "\"></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"              </DIV></DIV>\r\n" + 
					"      <DIV>\r\n" + 
					"      <DIV class=\"layout one-col fixed-width\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse; background-color: rgb(255, 255, 255);\" \r\n" + 
					"      emb-background-style=\"\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-fixed-width\" emb-background-style><td style=\"width: 600px\" class=\"w560\"><![endif]--> \r\n" + 
					"                \r\n" + 
					"      <DIV class=\"column\" style=\"width: calc(28000% - 167400px); text-align: left; color: rgb(124, 126, 127); line-height: 21px; font-family: PT Serif,Georgia,serif; font-size: 14px; min-width: 320px; max-width: 600px;\">\r\n" + 
					"      <DIV style=\"margin-top: 24px; margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 10px; font-size: 1px; mso-line-height-rule: exactly;\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <H3 style=\"text-align: center; color: rgb(120, 137, 145); line-height: 24px; font-size: 16px; font-style: normal; font-weight: normal; margin-top: 0px; margin-bottom: 0px;\">\r\n" + 
					"      MESSAGE</H3>\r\n" + 
					"      <H1 style=\"text-align: left; color: rgb(62, 71, 81); line-height: 31px; font-family: Ubuntu,sans-serif; font-size: 22px; font-style: normal; font-weight: normal; margin-top: 12px; margin-bottom: 0px;\">Dear \r\n" + 
					"      " + customerName + ",</H1>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 20px;\">Welcome to Classic Dogs Pet Miami  \r\n" + 
					"      .</P></DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV class=\"divider\" style=\"width: 40px; line-height: 2px; font-size: 2px; margin-right: auto; margin-bottom: 20px; margin-left: auto; display: block; background-color: rgb(180, 180, 196);\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 5px; font-size: 1px; mso-line-height-rule: exactly;\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <H2 style=\"color: rgb(62, 71, 81); line-height: 24px; font-family: Ubuntu,sans-serif; font-size: 16px; font-style: normal; font-weight: normal; margin-top: 0px; margin-bottom: 0px;\"><STRONG>To Activate \r\n" + 
					"      your Account, enter the code below in the request form</STRONG></H2>\r\n" + 
					"      <P style=\"margin-top: 16px; margin-bottom: 0px;\"><STRONG>Code </STRONG>: \r\n" + 
					"      " + verifyCode + "</P>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 20px;\"> Or Just Click the button below : \r\n" + 
					"       \r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV class=\"btn btn--shadow btn--large\" style=\"text-align: center; margin-bottom: 20px;\"><!--[if !mso]--><A \r\n" + 
					"      style=\"padding: 12px 24px 13px; border-radius: 4px; transition:opacity 0.1s ease-in; text-align: center; color: rgb(255, 255, 255) !important; line-height: 24px; font-family: PT Serif, Georgia, serif; font-size: 14px; font-weight: bold; text-decoration: none !important; display: inline-block; box-shadow: inset 0px -2px 0px 0px rgba(0,0,0,0.2); background-color: rgb(78, 170, 204);\" \r\n" + 
					"      href=\"http://192.168.1.4:8080/smiwebapp/\"> \r\n" + 
					" Activate Account </A><!--[endif]-->       <!--[if mso]><p style=\"line-height:0;margin:0;\">&nbsp;</p><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" href=\"http://quoting.westerntape.com\" style=\"width:160px\" arcsize=\"9%\" fillcolor=\"#4EAACC\" stroke=\"f\"><v:shadow on=\"t\" color=\"#3E88A3\" offset=\"0,2px\"></v:shadow><v:textbox style=\"mso-fit-shape-to-text:t\" inset=\"0px,11px,0px,10px\"><center style=\"font-size:14px;line-height:24px;color:#FFFFFF;font-family:PT Serif,Georgia,serif;font-weight:bold;mso-line-height-rule:exactly;mso-text-raise:4px\">Activate Account</center></v:textbox></v:roundrect><![endif]--></DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-bottom: 24px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <P style=\"margin-top: 0px; margin-bottom: 0px;\">Questions? Please Contact Us,<BR>Classic \r\n" + 
					"      Dog Team</P></DIV></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"              </DIV></DIV>\r\n" + 
					"      <DIV style=\"line-height: 20px; font-size: 20px;\">&nbsp;</DIV>\r\n" + 
					"      <DIV role=\"contentinfo\">\r\n" + 
					"      <DIV class=\"layout email-footer\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 400px;\" valign=\"top\" class=\"w360\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column wide\" style=\"width: calc(8000% - 47600px); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; min-width: 320px; max-width: 400px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px;\">\r\n" + 
					"      <DIV>Classic Dog Miami. USA<BR>6620 SW 49th ST, Davie FL \r\n" + 
					"      33314<BR>Phone:&nbsp;+1 786 602 74 48 &nbsp;&nbsp; &nbsp; \r\n" + 
					"      Fax:&nbsp;+1 786 505 04 71&nbsp; &nbsp; \r\n" + 
					"      Email:&nbsp;sales@classicdogspetscare.com</DIV></DIV>\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px; margin-top: 18px;\"></DIV><!--[if mso]>&nbsp;<![endif]--> \r\n" + 
					"                    </DIV></DIV><!--[if (mso)|(IE)]></td><td style=\"width: 200px;\" valign=\"top\" class=\"w160\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column narrow\" style=\"width: calc(72200px - 12000%); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; min-width: 200px; max-width: 320px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\"></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV>\r\n" + 
					"      <DIV class=\"layout one-col email-footer\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 600px;\" class=\"w560\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column\" style=\"width: calc(28000% - 167400px); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; min-width: 320px; max-width: 600px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px;\"><A style=\"transition:opacity 0.1s ease-in; color: rgb(124, 126, 127); text-decoration: underline;\" \r\n" + 
					"      href=\"\"></A>   \r\n" + 
					"                    </DIV></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV></DIV>\r\n" + 
					"      <DIV \r\n" + 
					"  style=\"line-height: 40px; font-size: 40px;\">&nbsp;</DIV></DIV></TD></TR></TBODY></TABLE><IMG \r\n" + 
					"width=\"1\" height=\"1\" style=\"margin: 0px !important; padding: 0px !important; border: 0px currentColor !important; border-image: none !important; width: 1px !important; height: 1px !important; overflow: hidden; display: block !important; visibility: hidden !important; position: fixed;\" \r\n" + 
					"alt=\"\" src=\"email_files/o.gif\" border=\"0\"></BODY></HTML>\r\n" + 
					"" ;
			
			
			String textWrong =  "<!DOCTYPE HTML>\r\n" + 
					"<!-- saved from url=(0104)mhtml:file://C:\\Users\\luiss\\AppData\\Local\\Microsoft\\Windows\\INetCache\\Content.Outlook\\H0SG760V\\email.mht -->\r\n" + 
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><!--[if IE]><html xmlns=\"http://www.w3.org/1999/xhtml\" class=\"ie\"><![endif]--><!--[if !IE]><!--><HTML \r\n" + 
					"style=\"margin: 0px; padding: 0px;\" xmlns=\"http://www.w3.org/1999/xhtml\"><!--<![endif]--><HEAD><META \r\n" + 
					"content=\"IE=11.0000\" http-equiv=\"X-UA-Compatible\">\r\n" + 
					" \r\n" + 
					"<META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">     \r\n" + 
					"<TITLE></TITLE>     <!--[if !mso]><!-->\r\n" + 
					"<META http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->     \r\n" + 
					"<META name=\"viewport\" content=\"width=device-width\">\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"@media only screen and (min-width: 620px){.wrapper{min-width:600px !important}.wrapper h1{}.wrapper h1{font-size:26px !important;line-height:34px !important}.wrapper h2{}.wrapper h3{}.column{}.wrapper .size-8{font-size:8px !important;line-height:14px !important}.wrapper .size-9{font-size:9px !important;line-height:16px !important}.wrapper .size-10{font-size:10px !important;line-height:18px !important}.wrapper .size-11{font-size:11px !important;line-height:19px !important}.wrapper .size-12{font-size:12px !important;line-height:19px !important}.wrapper .size-13{font-size:13px !important;line-height:21px !important}.wrapper .size-14{font-size:14px !important;line-height:21px !important}.wrapper .size-15{font-size:15px !important;line-height:23px !important}.wrapper .size-16{font-size:16px !important;line-height:24px !important}.wrapper .size-17{font-size:17px !important;line-height:26px \r\n" + 
					"!important}.wrapper .size-18{font-size:18px !important;line-height:26px !important}.wrapper .size-20{font-size:20px !important;line-height:28px !important}.wrapper .size-22{font-size:22px !important;line-height:31px !important}.wrapper .size-24{font-size:24px !important;line-height:32px !important}.wrapper .size-26{font-size:26px !important;line-height:34px !important}.wrapper .size-28{font-size:28px !important;line-height:36px !important}.wrapper .size-30{font-size:30px !important;line-height:38px !important}.wrapper .size-32{font-size:32px !important;line-height:40px !important}.wrapper .size-34{font-size:34px !important;line-height:43px !important}.wrapper .size-36{font-size:36px !important;line-height:43px !important}.wrapper .size-40{font-size:40px !important;line-height:47px !important}.wrapper .size-44{font-size:44px !important;line-height:50px !important}.wrapper \r\n" + 
					".size-48{font-size:48px !important;line-height:54px !important}.wrapper .size-56{font-size:56px !important;line-height:60px !important}.wrapper .size-64{font-size:64px !important;line-height:63px !important}}\r\n" + 
					"</STYLE>\r\n" + 
					"     \r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"body {\r\n" + 
					"  margin: 0;\r\n" + 
					"  padding: 0;\r\n" + 
					"}\r\n" + 
					"table {\r\n" + 
					"  border-collapse: collapse;\r\n" + 
					"  table-layout: fixed;\r\n" + 
					"}\r\n" + 
					"* {\r\n" + 
					"  line-height: inherit;\r\n" + 
					"}\r\n" + 
					"[x-apple-data-detectors],\r\n" + 
					"[href^=\"tel\"],\r\n" + 
					"[href^=\"sms\"] {\r\n" + 
					"  color: inherit !important;\r\n" + 
					"  text-decoration: none !important;\r\n" + 
					"}\r\n" + 
					".wrapper .footer__share-button a:hover,\r\n" + 
					".wrapper .footer__share-button a:focus {\r\n" + 
					"  color: #ffffff !important;\r\n" + 
					"}\r\n" + 
					".btn a:hover,\r\n" + 
					".btn a:focus,\r\n" + 
					".footer__share-button a:hover,\r\n" + 
					".footer__share-button a:focus,\r\n" + 
					".email-footer__links a:hover,\r\n" + 
					".email-footer__links a:focus {\r\n" + 
					"  opacity: 0.8;\r\n" + 
					"}\r\n" + 
					".preheader,\r\n" + 
					".header,\r\n" + 
					".layout,\r\n" + 
					".column {\r\n" + 
					"  transition: width 0.25s ease-in-out, max-width 0.25s ease-in-out;\r\n" + 
					"}\r\n" + 
					".preheader td {\r\n" + 
					"  padding-bottom: 8px;\r\n" + 
					"}\r\n" + 
					".layout,\r\n" + 
					"div.header {\r\n" + 
					"  max-width: 400px !important;\r\n" + 
					"  -fallback-width: 95% !important;\r\n" + 
					"  width: calc(100% - 20px) !important;\r\n" + 
					"}\r\n" + 
					"div.preheader {\r\n" + 
					"  max-width: 360px !important;\r\n" + 
					"  -fallback-width: 90% !important;\r\n" + 
					"  width: calc(100% - 60px) !important;\r\n" + 
					"}\r\n" + 
					".snippet,\r\n" + 
					".webversion {\r\n" + 
					"  Float: none !important;\r\n" + 
					"}\r\n" + 
					".column {\r\n" + 
					"  max-width: 400px !important;\r\n" + 
					"  width: 100% !important;\r\n" + 
					"}\r\n" + 
					".fixed-width.has-border {\r\n" + 
					"  max-width: 402px !important;\r\n" + 
					"}\r\n" + 
					".fixed-width.has-border .layout__inner {\r\n" + 
					"  box-sizing: border-box;\r\n" + 
					"}\r\n" + 
					".snippet,\r\n" + 
					".webversion {\r\n" + 
					"  width: 50% !important;\r\n" + 
					"}\r\n" + 
					".ie .btn {\r\n" + 
					"  width: 100%;\r\n" + 
					"}\r\n" + 
					"[owa] .column div,\r\n" + 
					"[owa] .column button {\r\n" + 
					"  display: block !important;\r\n" + 
					"}\r\n" + 
					"[owa] .wrapper > tbody > tr > td {\r\n" + 
					"  overflow-x: auto;\r\n" + 
					"  overflow-y: hidden;\r\n" + 
					"}\r\n" + 
					"[owa] .wrapper > tbody > tr > td > div {\r\n" + 
					"  min-width: 600px;\r\n" + 
					"}\r\n" + 
					".ie .column,\r\n" + 
					"[owa] .column,\r\n" + 
					".ie .gutter,\r\n" + 
					"[owa] .gutter {\r\n" + 
					"  display: table-cell;\r\n" + 
					"  float: none !important;\r\n" + 
					"  vertical-align: top;\r\n" + 
					"}\r\n" + 
					".ie div.preheader,\r\n" + 
					"[owa] div.preheader,\r\n" + 
					".ie .email-footer,\r\n" + 
					"[owa] .email-footer {\r\n" + 
					"  max-width: 560px !important;\r\n" + 
					"  width: 560px !important;\r\n" + 
					"}\r\n" + 
					".ie .snippet,\r\n" + 
					"[owa] .snippet,\r\n" + 
					".ie .webversion,\r\n" + 
					"[owa] .webversion {\r\n" + 
					"  width: 280px !important;\r\n" + 
					"}\r\n" + 
					".ie div.header,\r\n" + 
					"[owa] div.header,\r\n" + 
					".ie .layout,\r\n" + 
					"[owa] .layout,\r\n" + 
					".ie .one-col .column,\r\n" + 
					"[owa] .one-col .column {\r\n" + 
					"  max-width: 600px !important;\r\n" + 
					"  width: 600px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col .column,\r\n" + 
					"[owa] .two-col .column {\r\n" + 
					"  max-width: 300px !important;\r\n" + 
					"  width: 300px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col .column,\r\n" + 
					"[owa] .three-col .column,\r\n" + 
					".ie .narrow,\r\n" + 
					"[owa] .narrow {\r\n" + 
					"  max-width: 200px !important;\r\n" + 
					"  width: 200px !important;\r\n" + 
					"}\r\n" + 
					".ie .wide,\r\n" + 
					"[owa] .wide {\r\n" + 
					"  width: 400px !important;\r\n" + 
					"}\r\n" + 
					".ie .fixed-width.has-border,\r\n" + 
					"[owa] .fixed-width.x_has-border,\r\n" + 
					".ie .has-gutter.has-border,\r\n" + 
					"[owa] .has-gutter.x_has-border {\r\n" + 
					"  max-width: 602px !important;\r\n" + 
					"  width: 602px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col.has-gutter .column,\r\n" + 
					"[owa] .two-col.x_has-gutter .column {\r\n" + 
					"  max-width: 290px !important;\r\n" + 
					"  width: 290px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col.has-gutter .column,\r\n" + 
					"[owa] .three-col.x_has-gutter .column,\r\n" + 
					".ie .has-gutter .narrow,\r\n" + 
					"[owa] .has-gutter .narrow {\r\n" + 
					"  max-width: 188px !important;\r\n" + 
					"  width: 188px !important;\r\n" + 
					"}\r\n" + 
					".ie .has-gutter .wide,\r\n" + 
					"[owa] .has-gutter .wide {\r\n" + 
					"  max-width: 394px !important;\r\n" + 
					"  width: 394px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col.has-gutter.has-border .column,\r\n" + 
					"[owa] .two-col.x_has-gutter.x_has-border .column {\r\n" + 
					"  max-width: 292px !important;\r\n" + 
					"  width: 292px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col.has-gutter.has-border .column,\r\n" + 
					"[owa] .three-col.x_has-gutter.x_has-border .column,\r\n" + 
					".ie .has-gutter.has-border .narrow,\r\n" + 
					"[owa] .has-gutter.x_has-border .narrow {\r\n" + 
					"  max-width: 190px !important;\r\n" + 
					"  width: 190px !important;\r\n" + 
					"}\r\n" + 
					".ie .has-gutter.has-border .wide,\r\n" + 
					"[owa] .has-gutter.x_has-border .wide {\r\n" + 
					"  max-width: 396px !important;\r\n" + 
					"  width: 396px !important;\r\n" + 
					"}\r\n" + 
					".ie .fixed-width .layout__inner {\r\n" + 
					"  border-left: 0 none white !important;\r\n" + 
					"  border-right: 0 none white !important;\r\n" + 
					"}\r\n" + 
					".ie .layout__edges {\r\n" + 
					"  display: none;\r\n" + 
					"}\r\n" + 
					".mso .layout__edges {\r\n" + 
					"  font-size: 0;\r\n" + 
					"}\r\n" + 
					".layout-fixed-width,\r\n" + 
					".mso .layout-full-width {\r\n" + 
					"  background-color: #ffffff;\r\n" + 
					"}\r\n" + 
					"@media only screen and (min-width: 620px) {\r\n" + 
					"  .column,\r\n" + 
					"  .gutter {\r\n" + 
					"    display: table-cell;\r\n" + 
					"    Float: none !important;\r\n" + 
					"    vertical-align: top;\r\n" + 
					"  }\r\n" + 
					"  div.preheader,\r\n" + 
					"  .email-footer {\r\n" + 
					"    max-width: 560px !important;\r\n" + 
					"    width: 560px !important;\r\n" + 
					"  }\r\n" + 
					"  .snippet,\r\n" + 
					"  .webversion {\r\n" + 
					"    width: 280px !important;\r\n" + 
					"  }\r\n" + 
					"  div.header,\r\n" + 
					"  .layout,\r\n" + 
					"  .one-col .column {\r\n" + 
					"    max-width: 600px !important;\r\n" + 
					"    width: 600px !important;\r\n" + 
					"  }\r\n" + 
					"  .fixed-width.has-border,\r\n" + 
					"  .fixed-width.ecxhas-border,\r\n" + 
					"  .has-gutter.has-border,\r\n" + 
					"  .has-gutter.ecxhas-border {\r\n" + 
					"    max-width: 602px !important;\r\n" + 
					"    width: 602px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col .column {\r\n" + 
					"    max-width: 300px !important;\r\n" + 
					"    width: 300px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col .column,\r\n" + 
					"  .column.narrow {\r\n" + 
					"    max-width: 200px !important;\r\n" + 
					"    width: 200px !important;\r\n" + 
					"  }\r\n" + 
					"  .column.wide {\r\n" + 
					"    width: 400px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col.has-gutter .column,\r\n" + 
					"  .two-col.ecxhas-gutter .column {\r\n" + 
					"    max-width: 290px !important;\r\n" + 
					"    width: 290px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col.has-gutter .column,\r\n" + 
					"  .three-col.ecxhas-gutter .column,\r\n" + 
					"  .has-gutter .narrow {\r\n" + 
					"    max-width: 188px !important;\r\n" + 
					"    width: 188px !important;\r\n" + 
					"  }\r\n" + 
					"  .has-gutter .wide {\r\n" + 
					"    max-width: 394px !important;\r\n" + 
					"    width: 394px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col.has-gutter.has-border .column,\r\n" + 
					"  .two-col.ecxhas-gutter.ecxhas-border .column {\r\n" + 
					"    max-width: 292px !important;\r\n" + 
					"    width: 292px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col.has-gutter.has-border .column,\r\n" + 
					"  .three-col.ecxhas-gutter.ecxhas-border .column,\r\n" + 
					"  .has-gutter.has-border .narrow,\r\n" + 
					"  .has-gutter.ecxhas-border .narrow {\r\n" + 
					"    max-width: 190px !important;\r\n" + 
					"    width: 190px !important;\r\n" + 
					"  }\r\n" + 
					"  .has-gutter.has-border .wide,\r\n" + 
					"  .has-gutter.ecxhas-border .wide {\r\n" + 
					"    max-width: 396px !important;\r\n" + 
					"    width: 396px !important;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					"@media only screen and (-webkit-min-device-pixel-ratio: 2), only screen and (min--moz-device-pixel-ratio: 2), only screen and (-o-min-device-pixel-ratio: 2/1), only screen and (min-device-pixel-ratio: 2), only screen and (min-resolution: 192dpi), only screen and (min-resolution: 2dppx) {\r\n" + 
					"  .fblike {\r\n" + 
					"    background-image: url(http://i7.cmail19.com/static/eb/master/13-the-blueprint-3/images/fblike@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .tweet {\r\n" + 
					"    background-image: url(http://i8.cmail19.com/static/eb/master/13-the-blueprint-3/images/tweet@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .linkedinshare {\r\n" + 
					"    background-image: url(http://i9.cmail19.com/static/eb/master/13-the-blueprint-3/images/lishare@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .forwardtoafriend {\r\n" + 
					"    background-image: url(http://i10.cmail19.com/static/eb/master/13-the-blueprint-3/images/forward@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					"@media (max-width: 321px) {\r\n" + 
					"  .fixed-width.has-border .layout__inner {\r\n" + 
					"    border-width: 1px 0 !important;\r\n" + 
					"  }\r\n" + 
					"  .layout,\r\n" + 
					"  .column {\r\n" + 
					"    min-width: 320px !important;\r\n" + 
					"    width: 320px !important;\r\n" + 
					"  }\r\n" + 
					"  .border {\r\n" + 
					"    display: none;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					".mso div {\r\n" + 
					"  border: 0 none white !important;\r\n" + 
					"}\r\n" + 
					".mso .w560 .divider {\r\n" + 
					"  Margin-left: 260px !important;\r\n" + 
					"  Margin-right: 260px !important;\r\n" + 
					"}\r\n" + 
					".mso .w360 .divider {\r\n" + 
					"  Margin-left: 160px !important;\r\n" + 
					"  Margin-right: 160px !important;\r\n" + 
					"}\r\n" + 
					".mso .w260 .divider {\r\n" + 
					"  Margin-left: 110px !important;\r\n" + 
					"  Margin-right: 110px !important;\r\n" + 
					"}\r\n" + 
					".mso .w160 .divider {\r\n" + 
					"  Margin-left: 60px !important;\r\n" + 
					"  Margin-right: 60px !important;\r\n" + 
					"}\r\n" + 
					".mso .w354 .divider {\r\n" + 
					"  Margin-left: 157px !important;\r\n" + 
					"  Margin-right: 157px !important;\r\n" + 
					"}\r\n" + 
					".mso .w250 .divider {\r\n" + 
					"  Margin-left: 105px !important;\r\n" + 
					"  Margin-right: 105px !important;\r\n" + 
					"}\r\n" + 
					".mso .w148 .divider {\r\n" + 
					"  Margin-left: 54px !important;\r\n" + 
					"  Margin-right: 54px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-8,\r\n" + 
					".ie .size-8 {\r\n" + 
					"  font-size: 8px !important;\r\n" + 
					"  line-height: 14px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-9,\r\n" + 
					".ie .size-9 {\r\n" + 
					"  font-size: 9px !important;\r\n" + 
					"  line-height: 16px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-10,\r\n" + 
					".ie .size-10 {\r\n" + 
					"  font-size: 10px !important;\r\n" + 
					"  line-height: 18px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-11,\r\n" + 
					".ie .size-11 {\r\n" + 
					"  font-size: 11px !important;\r\n" + 
					"  line-height: 19px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-12,\r\n" + 
					".ie .size-12 {\r\n" + 
					"  font-size: 12px !important;\r\n" + 
					"  line-height: 19px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-13,\r\n" + 
					".ie .size-13 {\r\n" + 
					"  font-size: 13px !important;\r\n" + 
					"  line-height: 21px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-14,\r\n" + 
					".ie .size-14 {\r\n" + 
					"  font-size: 14px !important;\r\n" + 
					"  line-height: 21px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-15,\r\n" + 
					".ie .size-15 {\r\n" + 
					"  font-size: 15px !important;\r\n" + 
					"  line-height: 23px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-16,\r\n" + 
					".ie .size-16 {\r\n" + 
					"  font-size: 16px !important;\r\n" + 
					"  line-height: 24px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-17,\r\n" + 
					".ie .size-17 {\r\n" + 
					"  font-size: 17px !important;\r\n" + 
					"  line-height: 26px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-18,\r\n" + 
					".ie .size-18 {\r\n" + 
					"  font-size: 18px !important;\r\n" + 
					"  line-height: 26px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-20,\r\n" + 
					".ie .size-20 {\r\n" + 
					"  font-size: 20px !important;\r\n" + 
					"  line-height: 28px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-22,\r\n" + 
					".ie .size-22 {\r\n" + 
					"  font-size: 22px !important;\r\n" + 
					"  line-height: 31px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-24,\r\n" + 
					".ie .size-24 {\r\n" + 
					"  font-size: 24px !important;\r\n" + 
					"  line-height: 32px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-26,\r\n" + 
					".ie .size-26 {\r\n" + 
					"  font-size: 26px !important;\r\n" + 
					"  line-height: 34px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-28,\r\n" + 
					".ie .size-28 {\r\n" + 
					"  font-size: 28px !important;\r\n" + 
					"  line-height: 36px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-30,\r\n" + 
					".ie .size-30 {\r\n" + 
					"  font-size: 30px !important;\r\n" + 
					"  line-height: 38px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-32,\r\n" + 
					".ie .size-32 {\r\n" + 
					"  font-size: 32px !important;\r\n" + 
					"  line-height: 40px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-34,\r\n" + 
					".ie .size-34 {\r\n" + 
					"  font-size: 34px !important;\r\n" + 
					"  line-height: 43px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-36,\r\n" + 
					".ie .size-36 {\r\n" + 
					"  font-size: 36px !important;\r\n" + 
					"  line-height: 43px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-40,\r\n" + 
					".ie .size-40 {\r\n" + 
					"  font-size: 40px !important;\r\n" + 
					"  line-height: 47px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-44,\r\n" + 
					".ie .size-44 {\r\n" + 
					"  font-size: 44px !important;\r\n" + 
					"  line-height: 50px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-48,\r\n" + 
					".ie .size-48 {\r\n" + 
					"  font-size: 48px !important;\r\n" + 
					"  line-height: 54px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-56,\r\n" + 
					".ie .size-56 {\r\n" + 
					"  font-size: 56px !important;\r\n" + 
					"  line-height: 60px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-64,\r\n" + 
					".ie .size-64 {\r\n" + 
					"  font-size: 64px !important;\r\n" + 
					"  line-height: 63px !important;\r\n" + 
					"}\r\n" + 
					"</STYLE>\r\n" + 
					"       <!--[if !mso]><!-->\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"@import url(https://fonts.googleapis.com/css?family=PT+Serif:400,700,400italic,700italic|Ubuntu:400,700,400italic,700italic);\r\n" + 
					"</STYLE>\r\n" + 
					"<LINK href=\"email_files/css.css\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"body{background-color:#ededf1}.logo a:hover,.logo a:focus{color:#859bb1 !important}.mso .layout-has-border{border-top:1px solid #b4b4c4;border-bottom:1px solid #b4b4c4}.mso .layout-has-bottom-border{border-bottom:1px solid #b4b4c4}.mso .border,.ie .border{background-color:#b4b4c4}.mso h1,.ie h1{}.mso h1,.ie h1{font-size:26px !important;line-height:34px !important}.mso h2,.ie h2{}.mso h3,.ie h3{}.mso .layout__inner,.ie .layout__inner{}.mso .footer__share-button p{}.mso .footer__share-button p{font-family:Ubuntu,sans-serif}\r\n" + 
					"</STYLE>\r\n" + 
					"\r\n" + 
					"<META name=\"GENERATOR\" content=\"MSHTML 11.00.10570.1001\"></HEAD> <!--[if mso]>\r\n" + 
					"  <body class=\"mso\">\r\n" + 
					"<![endif]--> <!--[if !mso]><!--> \r\n" + 
					"  \r\n" + 
					"<BODY class=\"full-padding\" style=\"margin: 0px; padding: 0px; -webkit-text-size-adjust: 100%;\"><!--<![endif]--> \r\n" + 
					"    \r\n" + 
					"<TABLE class=\"wrapper\" role=\"presentation\" style=\"width: 100%; border-collapse: collapse; table-layout: fixed; min-width: 320px; background-color: rgb(237, 237, 241);\" \r\n" + 
					"cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
					"  <TBODY>\r\n" + 
					"  <TR>\r\n" + 
					"    <TD>\r\n" + 
					"      <DIV role=\"banner\">\r\n" + 
					"      <DIV class=\"preheader\" style=\"margin: 0px auto; width: calc(28000% - 167440px); min-width: 280px; max-width: 560px;\">\r\n" + 
					"      <DIV style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" class=\"preheader\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 280px\" valign=\"top\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"snippet\" style=\"padding: 10px 0px 5px; width: calc(14000% - 78120px); color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; display: table-cell; min-width: 140px; max-width: 280px;\"></DIV><!--[if (mso)|(IE)]></td><td style=\"width: 280px\" valign=\"top\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"webversion\" style=\"padding: 10px 0px 5px; width: calc(14100% - 78680px); text-align: right; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; display: table-cell; min-width: 139px; max-width: 280px;\"></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV>\r\n" + 
					"      <DIV class=\"header\" id=\"emb-email-header-container\" style=\"margin: 0px auto; width: calc(28000% - 167400px); min-width: 320px; max-width: 600px;\"><!--[if (mso)|(IE)]><table align=\"center\" class=\"header\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 600px\"><![endif]--> \r\n" + 
					"                \r\n" + 
					"      <DIV align=\"center\" class=\"logo emb-logo-margin-box\" style=\"margin: 6px 20px 20px; color: rgb(195, 206, 217); line-height: 32px; font-family: Roboto,Tahoma,sans-serif; font-size: 26px;\">\r\n" + 
					"      <DIV align=\"center\" class=\"logo-center\" id=\"emb-email-header\"><IMG width=\"261\" \r\n" + 
					"      style=\"border: 0px currentColor; border-image: none; width: 100%; height: auto; display: block; max-width: 261px;\" \r\n" + 
					"      alt=\"\" src=\"http://www.westerntape.com/WT_images/logo-smi-new.png\"></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"              </DIV></DIV>\r\n" + 
					"      <DIV>\r\n" + 
					"      <DIV class=\"layout one-col fixed-width\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse; background-color: rgb(255, 255, 255);\" \r\n" + 
					"      emb-background-style=\"\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-fixed-width\" emb-background-style><td style=\"width: 600px\" class=\"w560\"><![endif]--> \r\n" + 
					"                \r\n" + 
					"      <DIV class=\"column\" style=\"width: calc(28000% - 167400px); text-align: left; color: rgb(124, 126, 127); line-height: 21px; font-family: PT Serif,Georgia,serif; font-size: 14px; min-width: 320px; max-width: 600px;\">\r\n" + 
					"      <DIV style=\"margin-top: 24px; margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 10px; font-size: 1px; mso-line-height-rule: exactly;\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <H3 style=\"text-align: center; color: rgb(120, 137, 145); line-height: 24px; font-size: 16px; font-style: normal; font-weight: normal; margin-top: 0px; margin-bottom: 0px;\">\r\n" + 
					"      MESSAGE</H3>\r\n" + 
					"      <H1 style=\"text-align: left; color: rgb(62, 71, 81); line-height: 31px; font-family: Ubuntu,sans-serif; font-size: 22px; font-style: normal; font-weight: normal; margin-top: 12px; margin-bottom: 0px;\">Dear \r\n" + 
					"      " + customerName + ",</H1>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 20px;\">New Activity was added to your quote \r\n" + 
					"      .</P></DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV class=\"divider\" style=\"width: 40px; line-height: 2px; font-size: 2px; margin-right: auto; margin-bottom: 20px; margin-left: auto; display: block; background-color: rgb(180, 180, 196);\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 5px; font-size: 1px; mso-line-height-rule: exactly;\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <H2 style=\"color: rgb(62, 71, 81); line-height: 24px; font-family: Ubuntu,sans-serif; font-size: 16px; font-style: normal; font-weight: normal; margin-top: 0px; margin-bottom: 0px;\"><STRONG>Quote \r\n" + 
					"      Information</STRONG></H2>\r\n" + 
					"      <P style=\"margin-top: 16px; margin-bottom: 0px;\"><STRONG>RFQ </STRONG>: \r\n" + 
					"      " + verifyCode + "</P>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 0px;\"><STRONG>Date:&nbsp; \r\n" + 
					"      " + verifyCode + "</STRONG><BR>Contact Name :&nbsp;<STRONG><STRONG>\r\n" + 
					"      " + customerName + " -- Phone: " + verifyCode + " &nbsp;</STRONG></STRONG><BR>Email \r\n" + 
					"      :&nbsp;<STRONG><A style=\"transition:opacity 0.1s ease-in; color: rgb(78, 170, 204); text-decoration: underline;\" \r\n" + 
					"      href=\"mailto: " + verifyCode + " \">" + verifyCode + "</A>&nbsp;</STRONG><BR>\r\n" + 
					"      Observations :&nbsp;<STRONG> " + verifyCode + "</STRONG></P>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 20px;\">"+ verifyCode +  "\r\n" + 
					"       \r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV class=\"btn btn--shadow btn--large\" style=\"text-align: center; margin-bottom: 20px;\"><!--[if !mso]--><A \r\n" + 
					"      style=\"padding: 12px 24px 13px; border-radius: 4px; transition:opacity 0.1s ease-in; text-align: center; color: rgb(255, 255, 255) !important; line-height: 24px; font-family: PT Serif, Georgia, serif; font-size: 14px; font-weight: bold; text-decoration: none !important; display: inline-block; box-shadow: inset 0px -2px 0px 0px rgba(0,0,0,0.2); background-color: rgb(78, 170, 204);\" \r\n" + 
					"      href=\"http://quoting.westerntape.com\">GO TO \r\n" + 
					"PIPELINE</A><!--[endif]-->       <!--[if mso]><p style=\"line-height:0;margin:0;\">&nbsp;</p><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" href=\"http://quoting.westerntape.com\" style=\"width:160px\" arcsize=\"9%\" fillcolor=\"#4EAACC\" stroke=\"f\"><v:shadow on=\"t\" color=\"#3E88A3\" offset=\"0,2px\"></v:shadow><v:textbox style=\"mso-fit-shape-to-text:t\" inset=\"0px,11px,0px,10px\"><center style=\"font-size:14px;line-height:24px;color:#FFFFFF;font-family:PT Serif,Georgia,serif;font-weight:bold;mso-line-height-rule:exactly;mso-text-raise:4px\">GO TO PIPELINE</center></v:textbox></v:roundrect><![endif]--></DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-bottom: 24px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <P style=\"margin-top: 0px; margin-bottom: 0px;\">Kind regards,<BR>Seal \r\n" + 
					"      Methods Planners Team</P></DIV></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"              </DIV></DIV>\r\n" + 
					"      <DIV style=\"line-height: 20px; font-size: 20px;\">&nbsp;</DIV>\r\n" + 
					"      <DIV role=\"contentinfo\">\r\n" + 
					"      <DIV class=\"layout email-footer\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 400px;\" valign=\"top\" class=\"w360\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column wide\" style=\"width: calc(8000% - 47600px); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; min-width: 320px; max-width: 400px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px;\">\r\n" + 
					"      <DIV>Seal Methods Inc. USA<BR>11915 Shoemaker Ave Santa Fe Springs, CA \r\n" + 
					"      90670<BR>Phone:&nbsp;800.423.4777&nbsp;&nbsp; &nbsp; \r\n" + 
					"      Fax:&nbsp;562.946.9439&nbsp; &nbsp; \r\n" + 
					"      Email:&nbsp;sales@sealmethodsinc.com</DIV></DIV>\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px; margin-top: 18px;\"></DIV><!--[if mso]>&nbsp;<![endif]--> \r\n" + 
					"                    </DIV></DIV><!--[if (mso)|(IE)]></td><td style=\"width: 200px;\" valign=\"top\" class=\"w160\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column narrow\" style=\"width: calc(72200px - 12000%); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; min-width: 200px; max-width: 320px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\"></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV>\r\n" + 
					"      <DIV class=\"layout one-col email-footer\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 600px;\" class=\"w560\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column\" style=\"width: calc(28000% - 167400px); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; min-width: 320px; max-width: 600px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px;\"><A style=\"transition:opacity 0.1s ease-in; color: rgb(124, 126, 127); text-decoration: underline;\" \r\n" + 
					"      href=\"\"></A>   \r\n" + 
					"                    </DIV></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV></DIV>\r\n" + 
					"      <DIV \r\n" + 
					"  style=\"line-height: 40px; font-size: 40px;\">&nbsp;</DIV></DIV></TD></TR></TBODY></TABLE><IMG \r\n" + 
					"width=\"1\" height=\"1\" style=\"margin: 0px !important; padding: 0px !important; border: 0px currentColor !important; border-image: none !important; width: 1px !important; height: 1px !important; overflow: hidden; display: block !important; visibility: hidden !important; position: fixed;\" \r\n" + 
					"alt=\"\" src=\"email_files/o.gif\" border=\"0\"></BODY></HTML>\r\n" + 
					"" ;			
			
    		emailService.sendHTMlEmail("luisIt@sealmethodsinc.com, polluxmetal@gmail.com", "Please Activate your Classic Dog Account", textEmail);
    		emailService.sendHTMlEmail("darbybarrios@gmail.com", "Please Activate your Classic Dog Account", textEmail);

    		System.out.println("EMAIl sent to :");
    		
    		
		   }
		catch (Exception e) {
			System.out.println("Error : sending email..");
		}
            


		return new ResponseEntity<String>("0", HttpStatus.OK);
		
		

	}		
		
	
}
