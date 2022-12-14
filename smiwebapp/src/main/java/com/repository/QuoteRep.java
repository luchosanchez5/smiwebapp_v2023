package com.repository;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.model.Customer;
import com.model.Estimator;
import com.model.Quote;
import com.model.Seller;

@Transactional
public interface QuoteRep extends JpaRepository<Quote, Long> {

	
	List<Quote> findAllByStatReg(String stat);
	List<Quote> findAllByStatRegAndStatQuote(String stat, String statQuote);
	List<Quote> findAllByStatRegOrderByCreatedDateDesc(String stat);
	List<Quote> findAllSalesByStatRegAndSellerOrSeller2OrderByCreatedDateDesc(String stat, Seller sel,Seller sel2);
	List<Quote> findAllSalesByStatRegAndSharedQuoteAndSeller2OrderByCreatedDateDesc(String stat, String shQuote, Seller sel2);
	List<Quote> findAllByStatRegAndStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuote(String stat, String statQ0, String statQ1, String statQ2, String statQ3, String statQ4, String statQ5,
			                                                                                                                         String statQ6, String statQ7, String statQ8 );
	//List<Quote> findAllByStatRegAndStatQuote(String stat, String statQ0);
	List<Quote> findAllByEstimatorAndStatRegAndStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuoteOrStatQuote(Estimator est, String stat, String statQ0, String statQ1, String statQ2, String statQ3, String statQ4, String statQ5,
            String statQ6, String statQ7, String statQ8 );

	
	List<Quote> findAllByStatRegAndEstimator(String stat, Estimator est);
	List<Quote> findAllByStatRegAndEstimatorOrderByCreatedDateDesc(String stat, Estimator est);
	List<Quote> findAllByStatRegAndSellerOrderByCreatedDateDesc(String stat, Seller sell);
	Quote findById(long id);
	
	
	List<Quote> findByCustomerAndCreatedDateBetween(Customer cus, LocalDateTime ini, LocalDateTime end);
	List<Quote> findAllByStatRegAndEstimatorAndStatQuoteIn(String stat, Estimator est, List<String> status);
	List<Quote> findAllByStatRegAndSellerAndStatQuoteIn(String stat, Seller sel, List<String> status);
	
	
	
	@Query(value="Select  COALESCE(count(*),0) From Quote Where (stat_reg = '0') \r\n" + 
			     "And date_part('year', quote.created_date) = :year", nativeQuery=true)
	long findTotalQuotesYear(@Param("year") long year);
	
	// New for dashboard 01/05/2021
	@Query(value="Select  COALESCE(count(*),0) From Quote Where (stat_reg = '0')", nativeQuery=true)
    long findTotalQuotesHistory();
	
	
	//
	
	
	
	
	
	@Query(value="select COALESCE(q1.tot,0), i from generate_series(CURRENT_DATE - interval '25 day',CURRENT_DATE,'1 day') i left join (Select  COALESCE(count(*),0) as tot , date(quote.created_date) dateq1  From Quote Where quote.stat_reg <> '1' \r\n" + 
			"And date_part('year', quote.created_date) = date_part('year', CURRENT_DATE)Group by date(quote.created_date)) q1 on i = q1.dateq1 WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)", nativeQuery=true)
	List<Object[]> sparkline1mainDash();
	
	
	@Query(value="select COALESCE(q1.tot,0), i from generate_series(TO_DATE('20201231','YYYYMMDD') - interval '25 day',TO_DATE('20201231','YYYYMMDD'),'1 day') i left join (Select  COALESCE(count(*),0) as tot , date(quote.created_date) dateq1  From Quote Where quote.stat_reg <> '1' \r\n" + 
			"And date_part('year', quote.created_date) = :year Group by date(quote.created_date)) q1 on i = q1.dateq1 WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)", nativeQuery=true)
	List<Object[]> sparkline1mainDashYear(@Param("year") long year);
	
	@Query(value="Select COALESCE(count(*),0) From item Inner Join quote q on q.id = item.id_quote \r\n" + 
			"Where q.stat_reg = '0' And item.stat_reg = '0' \r\n" + 
			"And date_part('year', item.created_date) = :year",nativeQuery=true)
	long findTotalItemsYear(@Param("year") long year);
	
	// New for Dashboard 01/05/2021
	
	@Query(value="Select COALESCE(count(*),0) From item Inner Join quote q on q.id = item.id_quote \r\n" + 
			"Where q.stat_reg = '0' And item.stat_reg = '0'",nativeQuery=true)
	long findTotalItemsHistory();
	
	
	@Query(value="Select Coalesce(count(*), 0) From quote Where quote.stat_reg <> '1' And \r\n" + 
			"    Date_Part('year', quote.created_date) = :year And\r\n" + 
			"    quote.stat_quote in ('0','1','3','7')", nativeQuery=true)
	long findTotalPendQuotes(@Param("year") long year); 
	
	
	// New for Dashboard 01/05/2021
	
	@Query(value="Select Coalesce(count(*), 0) From quote Where quote.stat_reg <> '1' And \r\n" + 
			"    quote.stat_quote in ('0','1','3','7')", nativeQuery=true)
	long findTotalPendQuotesHistory(); 	
	
	
	
	
	
/*	@Query(value="select COALESCE(q1.tot,0), i from generate_series(CURRENT_DATE - interval '25 day',CURRENT_DATE,'1 day') i left join (Select COALESCE(count(*),0) tot, \r\n" + 
			"date(item.created_date) dateq1 From item Inner Join quote q on q.id = item.id_quote Where item.stat_reg <> '1' And \r\n" + 
			"date_part('year', item.created_date) = date_part('year', CURRENT_DATE) Group By date(item.created_date)) q1 on i = q1.dateq1 WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)",nativeQuery=true)
	List<Object[]> sparkline2mainDash(); */
	
	@Query(value="select COALESCE(q1.tot,0), i from generate_series(CURRENT_DATE - interval '25 day',CURRENT_DATE,'1 day') i left join (Select  COALESCE(count(*),0) as tot , date(quote.created_date) dateq1  From Quote Where quote.stat_reg <> '1' \r\n" + 
			"And quote.stat_quote in ('0','1','3','7') And date_part('year', quote.created_date) = date_part('year', CURRENT_DATE)Group by date(quote.created_date)) q1 on i = q1.dateq1 WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)", nativeQuery=true)
	List<Object[]> sparkline2mainDash(); //new
	
	
	@Query(value="select COALESCE(q1.tot,0), i from generate_series(TO_DATE('20201231','YYYYMMDD') - interval '25 day',TO_DATE('20201231','YYYYMMDD'),'1 day') i left join (Select  COALESCE(count(*),0) as tot , date(quote.created_date) dateq1  From Quote Where quote.stat_reg <> '1' \r\n" + 
			"And quote.stat_quote in ('0','1','3','7') And date_part('year', quote.created_date) = :year Group by date(quote.created_date)) q1 on i = q1.dateq1 WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)", nativeQuery=true)
	List<Object[]> sparkline2mainDashYear(@Param("year") long year); //new
	
	@Query(value="Select COALESCE(count(*),0) From Item i, item_status s, quote q where i.id_itemstatus = s.id and q.stat_reg = '0' and i.stat_reg = '0' and s.stat_reg = '0' and s.pending = 'Y' and q.id = i.id_quote\r\n" + 
			     "And date_part('year', i.created_date) = :year", nativeQuery=true)
	long findTotalItemsPendingYear(@Param("year") long year);  
	
	//New for Dashboard 01/05/2021
	
	@Query(value="Select COALESCE(count(*),0) From Item i, item_status s, quote q where i.id_itemstatus = s.id and q.stat_reg = '0' and i.stat_reg = '0' and s.stat_reg = '0' and s.pending = 'Y' and q.id = i.id_quote", nativeQuery=true)
	long findTotalItemsPendingHistory(); 
	
	
	@Query(value="Select Coalesce(count(*), 0) From quote Where quote.stat_reg <> '1' And\r\n" + 
			"    Date_Part('year', quote.created_date) = date_part('year', CURRENT_DATE) And\r\n" + 
			"    quote.stat_quote in ('0','1','3','5','7')", nativeQuery=true)
	long findTotalQuotesPending();  //added 2/13/2020
	
	@Query(value="Select\r\n" + 
			"    Coalesce(Sum(i.po_amount), 0)\r\n" + 
			"From\r\n" + 
			"    item i,\r\n" + 
			"    item_status s,\r\n" + 
			"    quote q\r\n" + 
			"Where\r\n" + 
			"    i.id_itemstatus = s.id And\r\n" + 
			"    q.id = i.id_quote And\r\n" + 
			"    s.stat_reg = '0' And\r\n" + 
			"    i.stat_reg = '0' And\r\n" + 
			"    s.ordened = 'Y' And\r\n" + 
			"    q.stat_reg = '0' And\r\n" + 
			"    Date_Part('year', q.created_date) = :year", nativeQuery=true)
	long findTotalAmountOrder(@Param("year") long year);
	
	
	@Query(value="Select\r\n" + 
			"    Coalesce(Sum(i.po_amount), 0)\r\n" + 
			"From\r\n" + 
			"    item i,\r\n" + 
			"    item_status s,\r\n" + 
			"    quote q\r\n" + 
			"Where\r\n" + 
			"    i.id_itemstatus = s.id And\r\n" + 
			"    q.id = i.id_quote And\r\n" + 
			"    s.stat_reg = '0' And\r\n" + 
			"    i.stat_reg = '0' And\r\n" + 
			"    s.ordened = 'Y' And\r\n" + 
			"    q.stat_reg = '0'", nativeQuery=true)
	long findTotalAmountOrderHistory();	
	
	
	
	@Query(value="Select COALESCE(q1.tot,0), i from generate_series(CURRENT_DATE - interval '25 day',CURRENT_DATE,'1 day') i left join\r\n" + 
			"(Select COALESCE(count(*),0) tot, date(i.created_date) dateq1  From Item i, item_status s, quote q where i.id_itemstatus = s.id and q.stat_reg = '0' and s.stat_reg = '0' and s.action = '1' and q.id = i.id_quote\r\n" + 
			"And date_part('year', i.created_date) = date_part('year', CURRENT_DATE) group by date(i.created_date)) q1 on i = q1.dateq1\r\n" + 
			"WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)", nativeQuery=true)
	List<Object[]> sparkline3mainDash();
	

	
	/*@Query(value="Select * From vTotalItemsperStatusAction Where action ='3'",nativeQuery=true)*/
	@Query(value="Select COALESCE(count(*),0) From Item i, item_status s, quote q where i.id_itemstatus = s.id and s.stat_reg = '0' and q.stat_reg = '0' and i.stat_reg = '0' and s.action = '3' and s.ordened ='Y' and q.id = i.id_quote\r\n" + 
    "And date_part('year', i.created_date) = :year", nativeQuery=true)
	BigInteger findTotalItemsOrdenedYear(@Param("year") long year);
	
	// New for Dashboard 01/05/2021
	@Query(value="Select COALESCE(count(*),0) From Item i, item_status s, quote q where i.id_itemstatus = s.id and s.stat_reg = '0' and q.stat_reg = '0' and i.stat_reg = '0' and s.action = '3' and s.ordened ='Y' and q.id = i.id_quote", nativeQuery=true)
	BigInteger findTotalItemsOrdenedHistory();
	
	                            
	@Query(value="Select COALESCE(q1.tot,0), i from generate_series(CURRENT_DATE - interval '25 day',CURRENT_DATE,'1 day') i left join\r\n" + 
			"(Select COALESCE(count(*),0) tot, date(i.date_ordered) dateq1  From Item i, item_status s, quote q where i.id_itemstatus = s.id and s.stat_reg = '0' and s.action = '3' and s.ordened ='Y' and q.id = i.id_quote\r\n" + 
			"And date_part('year', i.date_ordered) = date_part('year', CURRENT_DATE) group by date(i.date_ordered)) q1 on i = q1.dateq1\r\n" + 
			"WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)", nativeQuery=true)
	List<Object[]> sparkline4mainDash();
	
/*
	@Query(value="Select COALESCE(q1.tot,0), i from generate_series(CURRENT_DATE - interval '25 day',CURRENT_DATE,'1 day') i left join\r\n" + 
			"(Select COALESCE(count(*),0) tot, date(i.created_date) dateq1  From Item i, item_status s, quote q where i.id_itemstatus = s.id and s.stat_reg = '0' and s.action = '3' and q.id = i.id_quote\r\n" + 
			"And date_part('year', i.created_date) = date_part('year', CURRENT_DATE) group by date(i.created_date)) q1 on i = q1.dateq1\r\n" + 
			"WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)", nativeQuery=true)
	List<Object[]> sparkline4mainDash();  */
	
	@Query(value="SELECT  v.sure_name, v.last_name, v.total, v.items, v.items_ordened, (v.items_ordened * 100) / v.items AS Porc , v.id_seller\r\n" + 
			"FROM vlisttopsellers v ORDER BY Porc DESC",nativeQuery=true )
	List<Object[]> topSellersDashYear();
	
	
	@Query(value="Select * From vgraphicmaindashadmindaily",nativeQuery=true )
	List<Object[]> graphicMainDashDaily();
	
	
	@Query(value="Select m, total From vgraphicmaindashadminmonthly",nativeQuery=true )
	List<Object[]> graphicMainDashMonthly();
	
	@Query(value="Select * From vtotalitemsactivitysperday",nativeQuery=true )
	List<Object[]> sparkline5maindash(); 
	
	
	@Query(value="SELECT count(*), industry.industry_desc FROM quote INNER JOIN item ON item.id_quote = quote.id INNER JOIN industry ON item.id_industry = industry.id \r\n" + 
			      "WHERE  item.stat_reg <> '1' And date_part('year', quote.created_date) = date_part('year', CURRENT_DATE) GROUP BY  industry.industry_desc",nativeQuery=true)
	List<Object[]> donutchartIndustry();
	
	@Query(value="SELECT count(*), item_type.desc_item_type FROM quote INNER JOIN item ON item.id_quote = quote.id INNER JOIN item_type ON item.id_itemtype = item_type.id \r\n" + 
			"WHERE item.stat_reg <> '1' And date_part('year', quote.created_date) = date_part('year', CURRENT_DATE) GROUP BY item_type.desc_item_type",nativeQuery=true)
	List<Object[]> donutchartType();
	
	@Query(value="SELECT  count(*), qitem_type.descitem_type FROM quote INNER JOIN item ON item.id_quote = quote.id INNER JOIN qitem_type ON item.id_qitemtype = qitem_type.id \r\n" + 
			"WHERE item.stat_reg <> '1' And date_part('year', quote.created_date) = date_part('year', CURRENT_DATE) GROUP BY qitem_type.descitem_type",nativeQuery=true)
	List<Object[]> donutchartqitemType();
	
	@Query(value="SELECT date_part, sure_name, last_name, quotes, items, pending_items, pending_quotes, items_ordened, onhold_quotes  From vTotalQuotesPerEstimator Order by quotes desc",nativeQuery=true  )
	List<Object[]> quotesEstimatorsYear(@Param("year") long year);
	
	@Query(value="SELECT date_part, sure_name, last_name, quotes, items, id, pending_quotes, pending_items, items_ordened, (items_ordened * 100) / items AS Porc From vTotalQuotesPerSeller Order by quotes desc",nativeQuery=true  )
	List<Object[]> quotesSellersYear(@Param("year") long year);	
	
	
	@Query(value="Select * From vsummaryquotes",nativeQuery=true )
	List<Object[]> findsummaryquotes(); 
	
	@Query(value="Select * From vsummaryquotesYear Where year = :year ",nativeQuery=true )
	List<Object[]> findsummaryquotesYear(@Param("year") long year); 
	
	@Query(value="Select * From vsummaryquotesSeller where year = :year and id_seller = :seller",nativeQuery=true )
	List<Object[]> findsummaryquotesSeller(@Param("year") long year, @Param("seller") long seller); 
	
	@Query(value="Select * From vsummaryquotesEstimator where year = :year and id_estimator = :estimator",nativeQuery=true )
	List<Object[]> findsummaryquotesEstimator(@Param("year") long year, @Param("estimator") long estimator); 
	
	@Query(value="Select * From vQuotesSubStatusSeller where year = :year and id_seller = :seller",nativeQuery=true )
	List<Object[]> findsummarylostquotesSeller(@Param("year") long year, @Param("seller") long seller); 
	
	@Query(value="Select * From vquotes where year = :year",nativeQuery=true )
	List<Object[]> findallquotes(@Param("year") long year); 
	
	@Query(value="Select * From vquotes where year = :year and stat_quote = :status ",nativeQuery=true )
	List<Object[]> findallquotesByStatus(@Param("year") long year, @Param("status") String status ); 
	
	@Query(value="Select * From vquotes where stat_quote = :status ",nativeQuery=true )
	List<Object[]> findallquotesHistoryByStatus(@Param("status") String status ); 	
	
	
	@Query(value="Select * From vquotes where year = :year and stat_quote in ('0','1','3','7')",nativeQuery=true )
	List<Object[]> findallquotesPending(@Param("year") long year); 
	
	
	@Query(value="Select * From vquotes where stat_quote in ('0','1','3','7')",nativeQuery=true )
	List<Object[]> findallquotesPendingHistory(); 
	
	
	@Query(value="Select * From vquotes where year = :year and id_seller = :seller",nativeQuery=true )
	List<Object[]> findallquotesSeller(@Param("year") long year, @Param("seller") long seller); 
	
	@Query(value="Select * From vquotes where year = :year and id_estimator = :estimator",nativeQuery=true )
	List<Object[]> findallquotesEstimator(@Param("year") long year, @Param("estimator") long estimator); 
	
	@Query(value="Select * From vquotes where year = :year and id_seller = :seller and stat_quote = :status",nativeQuery=true )
	List<Object[]> findallquotesSellerByStatus(@Param("year") long year, @Param("seller") long seller, @Param("status") String status ); 
	
	@Query(value="Select * From vquotes where year = :year and id_estimator = :estimator and stat_quote = :status",nativeQuery=true )
	List<Object[]> findallquotesEstimatorByStatus(@Param("year") long year, @Param("estimator") long estimator, @Param("status") String status ); 
	
	@Query(value="Select * From vquotes where id_estimator = :estimator and stat_quote = :status",nativeQuery=true )
	List<Object[]> findallquotesEstimatorByStatusHistory(@Param("estimator") long estimator, @Param("status") String status ); 
	
	
	@Query(value="Select * From vquotes where year = :year and id_seller = :seller and stat_quote = :status",nativeQuery=true )
	List<Object[]> quotesquotesYearSellerStatus(@Param("year") long year, @Param("seller") long seller, String status); 	
	
	@Query(value="Select * From v_quotes_count_month_year where yint = :year order by mint",nativeQuery=true )
	List<Object[]> findcountquotesyearmonth(@Param("year") long year); 
	
	@Query(value="Select * From v_quotes_count_ordered_month_year where yint = :year order by mint ",nativeQuery=true )
	List<Object[]> findcountorderquotesyearmonth(@Param("year") long year); 
	
	@Query(value="Select total from vcountpendingitemsbyquote where id_quote = :id_quote",nativeQuery=true )
	long countPendingItemsByQuote(@Param("id_quote") long id_quote);
	
	
	List<Quote> findAllByCreatedDateBetween(LocalDateTime ini, LocalDateTime end);
	
	
}
