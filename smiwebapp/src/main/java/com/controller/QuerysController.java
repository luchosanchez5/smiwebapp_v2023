package com.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Estimator;
import com.model.QueryQuotes;
import com.model.Quote;
import com.model.Seller;
import com.model.Users;
import com.repository.CustomerRep;
import com.repository.EstimatorRep;
import com.repository.ItemRep;
import com.repository.MaterialRep;
import com.repository.QuoteNoteRep;
import com.repository.QuoteRep;
import com.repository.SellerRep;
import com.repository.TempitemRep;
import com.repository.UserRep;
import com.service.FileUploadService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

@Controller
public class QuerysController {
	
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
	
	
	@RequestMapping(value="querys/quotesEstimatorsYear")
	@ResponseBody	
	public List<QueryQuotes> quotesEstimatorsYear(long year){
		List<QueryQuotes> qSel = new ArrayList<QueryQuotes>();
		List<Object[]> resulQ = (List<Object[]>) quoteRep.quotesEstimatorsYear(year);
		
		for (int j = 0; j < resulQ.size(); j++) {
			
			QueryQuotes q = new QueryQuotes();
						
			q.setName((String) resulQ.get(j)[1]);
			q.setLastName((String) resulQ.get(j)[2]);
			q.setTotal(((BigInteger) resulQ.get(j)[3]).longValue());
			q.setItems(((BigInteger) resulQ.get(j)[4]).longValue());
			q.setItemsPend(((BigInteger) resulQ.get(j)[5]).longValue());
			q.setQuotesPend(((BigInteger) resulQ.get(j)[6]).longValue());
			q.setItemsOrdened(((BigInteger) resulQ.get(j)[7]).longValue());
			q.setQuotesHold(((BigInteger) resulQ.get(j)[8]).longValue());
			
			q.setYear(((Double) resulQ.get(j)[0]).longValue());
			
			
			
		    qSel.add(q);
			
		}
		
		
		
		return qSel;
	}
	
	@RequestMapping(value="querys/findsummaryquotes")
	@ResponseBody	
	public List<QueryQuotes> quotesSummaryHistory(){
		List<QueryQuotes> qSel = new ArrayList<QueryQuotes>();
		List<Object[]> resulQ = (List<Object[]>) quoteRep.findsummaryquotes();
		
		for (int j = 0; j < resulQ.size(); j++) {
			
			QueryQuotes q = new QueryQuotes();
			
			q.setStat_quote((String) resulQ.get(j)[1]);
			q.setStat_desc((String) resulQ.get(j)[2]);
			
			long totQ = ((BigDecimal) resulQ.get(j)[3]).longValue();
			q.setTotal(totQ);
			
			long tot = ((BigDecimal) resulQ.get(j)[4]).longValue();
			q.setItems(tot);
			
			Double bd1 = (Double) resulQ.get(j)[5];
			q.setProficit(bd1);
			
			q.setPorcL(((BigDecimal) resulQ.get(j)[6]).longValue());
			
		    qSel.add(q);
			
		}

		return qSel;
	}	
	
	
	@RequestMapping(value="querys/findsummaryquotesYear")
	@ResponseBody	
	public List<QueryQuotes> quotesSummaryYear(long year){
		List<QueryQuotes> qSel = new ArrayList<QueryQuotes>();
		List<Object[]> resulQ = (List<Object[]>) quoteRep.findsummaryquotesYear(year);
		
		for (int j = 0; j < resulQ.size(); j++) {
			
			QueryQuotes q = new QueryQuotes();
			
			q.setStat_quote((String) resulQ.get(j)[1]);
			q.setStat_desc((String) resulQ.get(j)[2]);
			
			long totQ = ((BigInteger) resulQ.get(j)[3]).longValue();
			q.setTotal(totQ);
			
			long tot = ((BigDecimal) resulQ.get(j)[4]).longValue();
			q.setItems(tot);
			
			Double bd1 = (Double) resulQ.get(j)[5];
			q.setProficit(bd1);
			
			q.setPorcL(((BigInteger) resulQ.get(j)[6]).longValue());
			
		    qSel.add(q);
			
		}

		return qSel;
	}
	
	
	@RequestMapping(value="querys/findsummaryquotesSeller")
	@ResponseBody	
	public List<QueryQuotes> quotesSummaryYearSeller(long year, String userId){
		Users user = userRep.findByUsername(userId);
		Seller sel = sellerRep.findOneByIdUser(user.getId().toString());		
		List<QueryQuotes> qSel = new ArrayList<QueryQuotes>();
		List<Object[]> resulQ = (List<Object[]>) quoteRep.findsummaryquotesSeller(year, sel.getId());
		
		for (int j = 0; j < resulQ.size(); j++) {
			
			QueryQuotes q = new QueryQuotes();
			
			q.setStat_quote((String) resulQ.get(j)[1]);
			q.setStat_desc((String) resulQ.get(j)[2]);
			q.setTotal(((BigInteger) resulQ.get(j)[3]).longValue());
			
			long tot = ((BigDecimal) resulQ.get(j)[4]).longValue();
			q.setItems(tot);
			
			Double bd1 = (Double) resulQ.get(j)[5];
			q.setProficit(bd1);
			
			q.setPorcL(((BigInteger) resulQ.get(j)[6]).longValue());
			
		    qSel.add(q);
			
		}

		return qSel;
	}	
	
	
	@RequestMapping(value="querys/findsummaryquotesEstimator")
	@ResponseBody	
	public List<QueryQuotes> quotesSummaryYearEstimator(long year, String userId){
		Users user = userRep.findByUsername(userId);
		Seller sel = sellerRep.findOneByIdUser(user.getId().toString());	
		Estimator es = estimatorRep.findOneByUser(user);
		List<QueryQuotes> qSel = new ArrayList<QueryQuotes>();
		List<Object[]> resulQ = (List<Object[]>) quoteRep.findsummaryquotesEstimator(year, es.getId());
		
		for (int j = 0; j < resulQ.size(); j++) {
			
			QueryQuotes q = new QueryQuotes();
			
			q.setStat_quote((String) resulQ.get(j)[1]);
			q.setStat_desc((String) resulQ.get(j)[2]);
			q.setTotal(((BigInteger) resulQ.get(j)[3]).longValue());
			
			long tot = ((BigDecimal) resulQ.get(j)[4]).longValue();
			q.setItems(tot);
			
			Double bd1 = (Double) resulQ.get(j)[5];
			q.setProficit(bd1);
			
			q.setPorcL(((BigInteger) resulQ.get(j)[6]).longValue());
			
		    qSel.add(q);
			
		}

		return qSel;
	}	
	
	@RequestMapping(value="querys/findsummarylostquotesSeller")
	@ResponseBody	
	public List<QueryQuotes> lostquotesSummaryYearSeller(long year, String userId){
		Users user = userRep.findByUsername(userId);
		Seller sel = sellerRep.findOneByIdUser(user.getId().toString());		
		List<QueryQuotes> qSel = new ArrayList<QueryQuotes>();
		List<Object[]> resulQ = (List<Object[]>) quoteRep.findsummarylostquotesSeller(year, sel.getId());
		
		for (int j = 0; j < resulQ.size(); j++) {
			
			QueryQuotes q = new QueryQuotes();
			
			q.setStat_quote((String)   (resulQ.get(j)[8]).toString());
			q.setStat_desc((String) resulQ.get(j)[9]);
			q.setTotal(((BigInteger) resulQ.get(j)[3]).longValue());
			
			long tot = ((BigDecimal) resulQ.get(j)[4]).longValue();
			q.setItems(tot);
			
			Double bd1 = (Double) resulQ.get(j)[5];
			q.setProficit(bd1);
			
			q.setPorcL(((BigInteger) resulQ.get(j)[6]).longValue());
			
		    qSel.add(q);
			
		}

		return qSel;
	}	
		
	
	
	@RequestMapping(value="querys/findallvquotes")
	@ResponseBody	
	public List<Object[]> quotesallquotesYear(long year){

		List<Object[]> resulQ = (List<Object[]>) quoteRep.findallquotes(year);
		
		return resulQ;
	}	
	
	
	@RequestMapping(value="querys/findallvquotesByStatus")
	@ResponseBody	
	public List<Object[]> quotesallquotesYearbyStatus(long year, String status){

		List<Object[]> resulQ = (List<Object[]>) quoteRep.findallquotesByStatus(year, status);
		
		return resulQ;
	}
	
	
	@RequestMapping(value="querys/findallvquotesHistoryByStatus")
	@ResponseBody	
	public List<Object[]> quotesallquotesHistoryYearbyStatus(String status){

		List<Object[]> resulQ = (List<Object[]>) quoteRep.findallquotesHistoryByStatus(status);
		
		return resulQ;
	}	
	
	
	@RequestMapping(value="querys/findallvquotesPending")
	@ResponseBody	
	public List<Object[]> quotesallquotesPendingYear(long year){

		List<Object[]> resulQ = (List<Object[]>) quoteRep.findallquotesPending(year);
		
		return resulQ;
	}
	
	@RequestMapping(value="querys/findallvquotesPendingHistory")
	@ResponseBody	
	public List<Object[]> quotesallquotesPendingHistory(){

		List<Object[]> resulQ = (List<Object[]>) quoteRep.findallquotesPendingHistory();
		
		return resulQ;
	}
	
	
	@RequestMapping("querys/findallvquotesSeller")
	@ResponseBody
	private List<Object[]> quotesallquotesYearSeller(long year, String userId){
		
		Users user = userRep.findByUsername(userId);
		Seller sel = sellerRep.findOneByIdUser(user.getId().toString());
		List<Object[]> resulQ = (List<Object[]>) quoteRep.findallquotesSeller(year,sel.getId());
		return resulQ;
	}	
	
	
	@RequestMapping("querys/findallvquotesEstimator")
	@ResponseBody
	private List<Object[]> quotesallquotesYearEstimator(long year, String userId){
		
		Users user = userRep.findByUsername(userId);
		Estimator est = estimatorRep.findOneByUser(user);
		List<Object[]> resulQ = (List<Object[]>) quoteRep.findallquotesEstimator(year,est.getId());
		return resulQ;
	}	
	
	
	@RequestMapping("querys/findallvquotesSellerByStatus")
	@ResponseBody
	private List<Object[]> quotesallquotesYearSellerByStatus(long year, String userId, String status){
		
		Users user = userRep.findByUsername(userId);
		Seller sel = sellerRep.findOneByIdUser(user.getId().toString());
		List<Object[]> resulQ = (List<Object[]>) quoteRep.findallquotesSellerByStatus(year,sel.getId(),status);
		return resulQ;
	}	
	
	@RequestMapping("querys/findallvquotesEstimatorByStatus")
	@ResponseBody
	private List<Object[]> quotesallquotesYearEstimatorByStatus(long year, String userId, String status){
		
		Users user = userRep.findByUsername(userId);
		Estimator est = estimatorRep.findOneByUser(user);
		List<Object[]> resulQ = (List<Object[]>) quoteRep.findallquotesEstimatorByStatus(year,est.getId(),status);
		return resulQ;
	}	
	
	@RequestMapping("querys/findallvquotesEstimatorByStatusHistory")
	@ResponseBody
	private List<Object[]> quotesallquotesYearEstimatorByStatusHsitory(String userId, String status){
		
		Users user = userRep.findByUsername(userId);
		Estimator est = estimatorRep.findOneByUser(user);
		List<Object[]> resulQ = (List<Object[]>) quoteRep.findallquotesEstimatorByStatusHistory(est.getId(),status);
		return resulQ;
	}	
	
	
	
	@RequestMapping("querys/findquotesSellerStatus")
	@ResponseBody
	private List<Object[]> quotesquotesYearSellerStatus(long year, String userId, String status){
		
		Users user = userRep.findByUsername(userId);
		Seller sel = sellerRep.findOneByIdUser(user.getId().toString());
		List<Object[]> resulQ = (List<Object[]>) quoteRep.quotesquotesYearSellerStatus(year,sel.getId(),status);
		return resulQ;
	}
	
	
	@RequestMapping(value="querys/quotesSellersYear")
	@ResponseBody	
	public List<QueryQuotes> quotesSellersYear(long year){
		List<QueryQuotes> qSel = new ArrayList<QueryQuotes>();
		List<Object[]> resulQ = (List<Object[]>) quoteRep.quotesSellersYear(year);
		
		for (int j = 0; j < resulQ.size(); j++) {
			
			QueryQuotes q = new QueryQuotes();
			
						
			q.setName((String) resulQ.get(j)[1]);
			q.setLastName((String) resulQ.get(j)[2]);
			q.setTotal(((BigInteger) resulQ.get(j)[3]).longValue());
			q.setItems(((BigInteger) resulQ.get(j)[4]).longValue());
			
			q.setItemsPend(((BigInteger) resulQ.get(j)[7]).longValue());
			q.setQuotesPend(((BigInteger) resulQ.get(j)[6]).longValue());
			q.setItemsOrdened(((BigInteger) resulQ.get(j)[8]).longValue());
			
			
			Seller sel = sellerRep.findOneById(((BigInteger) resulQ.get(j)[5]).longValue());
			q.setSeller(sel);
			q.setProficit(((BigInteger) resulQ.get(j)[9]).doubleValue());
			
			q.setYear(((Double) resulQ.get(j)[0]).longValue());
			
		
		    qSel.add(q);
			
		}
		
		
		
		return qSel;
	}
	
	
	
	@RequestMapping("q_quotesestimatoryear")
	@ResponseBody
	public void generatePDFJasperleadsActivity(@RequestParam("year") long year,
			                           HttpServletRequest request,
				                       HttpServletResponse response) throws IOException, SQLException, ParseException {
		
		//DataSource ds = (DataSource)ApplicationContextProvider.getApplicationContext().getBean("dataSource");
		DataSource ds = (DataSource)appContext.getBean("dataSource");
		Connection c = ds.getConnection();
		String sourceFileName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("pYear", year);
		
		sourceFileName = System.getProperty("user.dir") + "/src/main/resources/static/q_quotesestimatoryear.jrxml";
		System.out.println(sourceFileName);
		try {
			System.out.println("Start compiling!!! ...");
			JasperCompileManager.compileReportToFile(sourceFileName);
			System.out.println("Done compiling!!! ...");
			sourceFileName = System.getProperty("user.dir") + "/src/main/resources/static/q_quotesestimatoryear.jasper";
			
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
	
	
	@RequestMapping("r_quotesCustomers")
	@ResponseBody
	public void generatePDFJasperquotesCustomers(@RequestParam("initD") Date initD,@RequestParam("endD") Date endD,@RequestParam("idCustomer") long idCustomer,
			                           HttpServletRequest request,
				                       HttpServletResponse response) throws IOException, SQLException, ParseException {
		
		//DataSource ds = (DataSource)ApplicationContextProvider.getApplicationContext().getBean("dataSource");
		DataSource ds = (DataSource)appContext.getBean("dataSource");
		Connection c = ds.getConnection();
		String sourceFileName = "";
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("date1", initD);
		parameters.put("date2", endD);
		parameters.put("idCus", idCustomer);
		
		sourceFileName = System.getProperty("user.dir") + "/src/main/resources/static/qQuotesCustomers_a.jrxml";
		System.out.println(sourceFileName);
		try {
			System.out.println("Start compiling!!! ...");
			JasperCompileManager.compileReportToFile(sourceFileName);
			System.out.println("Done compiling!!! ...");
			sourceFileName = System.getProperty("user.dir") + "/src/main/resources/static/qQuotesCustomers_a.jasper";
			
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

}
