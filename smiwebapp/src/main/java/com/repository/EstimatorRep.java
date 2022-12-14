package com.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.model.Estimator;
import com.model.Users;

@Transactional
public interface EstimatorRep extends JpaRepository<Estimator, Long> {
	
	List<Estimator> findAllByStatReg(String stat);
	Estimator findByUser(Users user);
	Estimator findOneById(long id);
	
	
 /*	@Query(value="Select  COALESCE(count(*),0) From Quote Where quote.stat_reg <> '1' and quote.id_estimator = :id_estimator And date_part('year', quote.created_date) = date_part('year', CURRENT_DATE)", nativeQuery=true)
	long findTotalQuotesEstimator(@Param("id_estimator") BigInteger id_estimator); 
	Removed Filter YEar   01/07/2021
	*/ 
	
	@Query(value="Select  COALESCE(count(*),0) From Quote Where quote.stat_reg <> '1' and quote.id_estimator = :id_estimator", nativeQuery=true)
	long findTotalQuotesEstimator(@Param("id_estimator") BigInteger id_estimator);
	
	@Query(value="Select  COALESCE(count(*),0) From Quote Where quote.stat_reg <> '1' and quote.id_estimator = :id_estimator And date_part('year', quote.created_date) = :year", nativeQuery=true)
	long findTotalQuotesEstimatorYear(@Param("id_estimator") BigInteger id_estimator, @Param("year") long year);
	
	//New 2/12/2020
	
	/*@Query(value="Select Coalesce(count(*), 0) From quote Where quote.stat_reg <> '1' And quote.id_estimator = :id_estimator And\r\n" + 
			"    Date_Part('year', quote.created_date) = date_part('year', CURRENT_DATE) And\r\n" + 
			"    quote.stat_quote in ('0','1','3')", nativeQuery=true)
	long findTotalQuotesPendingEstimator(@Param("id_estimator") BigInteger id_estimator); 
	
	Removed Filter YEar   01/07/2021
	*/
	
	
	@Query(value="Select Coalesce(count(*), 0) From quote Where quote.stat_reg <> '1' And quote.id_estimator = :id_estimator And\r\n" + 
			"    quote.stat_quote in ('0','1','3')", nativeQuery=true)
	long findTotalQuotesPendingEstimator(@Param("id_estimator") BigInteger id_estimator); 
	
	@Query(value="Select Coalesce(count(*), 0) From quote Where quote.stat_reg <> '1' And quote.id_estimator = :id_estimator And\r\n" + 
			"    Date_Part('year', quote.created_date) = :year And\r\n" + 
			"    quote.stat_quote in ('0','1','3')", nativeQuery=true)
	long findTotalQuotesPendingEstimatorYear(@Param("id_estimator") BigInteger id_estimator, @Param("year") long year); 	
	
	/*@Query(value="Select Coalesce(count(*), 0) From quote Where quote.stat_reg <> '1' And quote.id_estimator = :id_estimator And\r\n" + 
			"    Date_Part('year', quote.created_date) = date_part('year', CURRENT_DATE) And\r\n" + 
			"    quote.stat_quote in ('7')", nativeQuery=true)
	long findTotalQuotesOnHoldEstimator(@Param("id_estimator") BigInteger id_estimator);  
	
	*Removed filter 01/07/2021
	*/
	
	@Query(value="Select Coalesce(count(*), 0) From quote Where quote.stat_reg <> '1' And quote.id_estimator = :id_estimator And\r\n" + 
			"    quote.stat_quote in ('7')", nativeQuery=true)
	long findTotalQuotesOnHoldEstimator(@Param("id_estimator") BigInteger id_estimator); 
	
	@Query(value="Select Coalesce(count(*), 0) From quote Where quote.stat_reg <> '1' And quote.id_estimator = :id_estimator And\r\n" + 
			"    Date_Part('year', quote.created_date) = :year And\r\n" + 
			"    quote.stat_quote in ('7')", nativeQuery=true)
	long findTotalQuotesOnHoldEstimatorYear(@Param("id_estimator") BigInteger id_estimator, @Param("year") long year); 	
	
	/* @Query(value="Select Coalesce(count(*), 0) From quote Where quote.stat_reg <> '1' And quote.id_estimator = :id_estimator And\r\n" + 
			"    Date_Part('year', quote.created_date) = date_part('year', CURRENT_DATE) And\r\n" + 
			"    quote.stat_quote in ('6')", nativeQuery=true)
	long findTotalQuotesOrderedEstimator(@Param("id_estimator") BigInteger id_estimator); 	
	
	*Removed Filter Year
	*/
	
	@Query(value="Select Coalesce(count(*), 0) From quote Where quote.stat_reg <> '1' And quote.id_estimator = :id_estimator And\r\n" + 
			"    quote.stat_quote in ('6')", nativeQuery=true)
	long findTotalQuotesOrderedEstimator(@Param("id_estimator") BigInteger id_estimator); 
	
	
	@Query(value="Select Coalesce(count(*), 0) From quote Where quote.stat_reg <> '1' And quote.id_estimator = :id_estimator And\r\n" + 
			"    Date_Part('year', quote.created_date) = :year And\r\n" + 
			"    quote.stat_quote in ('6')", nativeQuery=true)
	long findTotalQuotesOrderedEstimatorYear(@Param("id_estimator") BigInteger id_estimator, @Param("year") long year); 	
	
	/*@Query(value="Select * From vquotes where year = :year and id_estimator = :id_estimator ",nativeQuery=true )
	List<Object[]> findallquotes(@Param("year") long year, @Param("id_estimator") BigInteger id_estimator); */
	  
	
	/* @Query(value="Select COALESCE(count(*),0) From item Inner Join quote q on q.id = item.id_quote Where item.stat_reg <> '1' And item.stat_reg <> '1' And q.id_estimator = :id_estimator And date_part('year', q.created_date) = date_part('year', CURRENT_DATE)", nativeQuery=true)
	long findTotalItemsEstimator(@Param("id_estimator") long id_estimator); 
	
	Removed filter Year 01/07/2021
	*/
	
	@Query(value="Select COALESCE(count(*),0) From item Inner Join quote q on q.id = item.id_quote Where item.stat_reg <> '1' And item.stat_reg <> '1' And q.id_estimator = :id_estimator", nativeQuery=true)
	long findTotalItemsEstimator(@Param("id_estimator") long id_estimator);
	
	@Query(value="Select COALESCE(count(*),0) From item Inner Join quote q on q.id = item.id_quote Where item.stat_reg <> '1' And item.stat_reg <> '1' And q.id_estimator = :id_estimator And date_part('year', q.created_date) = :year", nativeQuery=true)
	long findTotalItemsEstimatorYear(@Param("id_estimator") long id_estimator, @Param("year") long year );
	
	/* @Query(value="Select COALESCE(count(*),0) From Item i, item_status s, quote q where i.id_itemstatus = s.id \r\n" + 
			"and s.stat_reg = '0' and i.stat_reg = '0' and s.pending = 'Y' and q.id = i.id_quote and q.id_estimator = :id_estimator And date_part('year', q.created_date) = date_part('year', CURRENT_DATE)", nativeQuery=true)
	long findTotalItemsPending(@Param("id_estimator") long id_estimator); 
	
	Removed filter Year 01/07/2021
	*/
	
	@Query(value="Select COALESCE(count(*),0) From Item i, item_status s, quote q where i.id_itemstatus = s.id \r\n" + 
			"and s.stat_reg = '0' and i.stat_reg = '0' and s.pending = 'Y' and q.id = i.id_quote and q.id_estimator = :id_estimator", nativeQuery=true)
	long findTotalItemsPending(@Param("id_estimator") long id_estimator);	
	
	@Query(value="Select COALESCE(count(*),0) From Item i, item_status s, quote q where i.id_itemstatus = s.id \r\n" + 
			"and s.stat_reg = '0' and i.stat_reg = '0' and s.pending = 'Y' and q.id = i.id_quote and q.id_estimator = :id_estimator And date_part('year', q.created_date) = :year", nativeQuery=true)
	long findTotalItemsPendingYear(@Param("id_estimator") long id_estimator, @Param("year") long year);
	
	/*
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
			"    q.id_estimator = :id_estimator And\r\n" + 
			"    Date_Part('year', q.created_date) = Date_Part('year', Current_Date)", nativeQuery=true)
	long findTotalAmountOrder(@Param("id_estimator") long id_estimator); 
	
	*Removed Filter Year 01/07/2021
	*/
	
	
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
			"    q.id_estimator = :id_estimator", nativeQuery=true)
	long findTotalAmountOrder(@Param("id_estimator") long id_estimator);
	
	
	
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
			"    q.id_estimator = :id_estimator And\r\n" + 
			"    Date_Part('year', q.created_date) = :year", nativeQuery=true)
	long findTotalAmountOrderYear(@Param("id_estimator") long id_estimator, @Param("year") long year);
	
	
	
	
	/*@Query(value="Select COALESCE(count(*),0) From Item i, item_status s, quote q where i.id_itemstatus = s.id " + 
			     "and q.stat_reg = '0' and s.stat_reg = '0' and s.action = '3' and i.stat_reg ='0' and q.id = i.id_quote and q.id_estimator = :id_estimator And date_part('year', q.created_date) = date_part('year', CURRENT_DATE)", nativeQuery=true)
	long findTotalItemsOrder(@Param("id_estimator") long id_estimator); 
	
	Removed filter Year 01/07/2021
	*/
	
	@Query(value="Select COALESCE(count(*),0) From Item i, item_status s, quote q where i.id_itemstatus = s.id " + 
		     "and q.stat_reg = '0' and s.stat_reg = '0' and s.action = '3' and i.stat_reg ='0' and q.id = i.id_quote and q.id_estimator = :id_estimator", nativeQuery=true)
	long findTotalItemsOrder(@Param("id_estimator") long id_estimator);
	
	@Query(value="Select COALESCE(count(*),0) From Item i, item_status s, quote q where i.id_itemstatus = s.id " + 
		     "and q.stat_reg = '0' and s.stat_reg = '0' and s.action = '3' and i.stat_reg ='0' and q.id = i.id_quote and q.id_estimator = :id_estimator And date_part('year', q.created_date) = :year", nativeQuery=true)
	long findTotalItemsOrderYear(@Param("id_estimator") long id_estimator, @Param("year") long year );	
	
	@Query(value="select i, COALESCE(q1.tot,0) from generate_series(CURRENT_DATE - interval '16 day',CURRENT_DATE,'1 day') i left join\r\n" + 
			     "(SELECT date(quote.created_date) created_date, coalesce(count(*),0) as tot , id_estimator FROM quote\r\n" + 
			     "WHERE quote.stat_reg <> '1'  And (quote.created_date >= current_date - interval '70 Days') And quote.id_estimator = :id_estimator\r\n" + 
			     "GROUP BY date(quote.created_date), id_estimator ORDER By date(quote.created_date)) q1 on q1.created_date = date(i)", nativeQuery=true)
	List<Object[]> graphicQuotesPerDayEstimator(@Param("id_estimator") long id_estimator);
	
	
	@Query(value="SELECT count(*), industry.industry_desc FROM quote INNER JOIN item ON item.id_quote = quote.id INNER JOIN industry ON item.id_industry = industry.id\r\n" + 
			     "WHERE quote.id_estimator = :id_estimator AND item.stat_reg <> '1' GROUP BY  industry.industry_desc", nativeQuery=true)	
	List<Object[]> donutChartIndustryEstimator(@Param("id_estimator") long id_estimator);
	
	@Query(value="SELECT count(*), item_type.desc_item_type FROM quote INNER JOIN item ON item.id_quote = quote.id INNER JOIN item_type ON item.id_itemtype = item_type.id\r\n" + 
			     "WHERE quote.id_estimator = :id_estimator AND item.stat_reg <> '1' GROUP BY item_type.desc_item_type", nativeQuery=true)	
    List<Object[]> donutChartItemtypeEstimator(@Param("id_estimator") long id_estimator);
	
	
	@Query(value="SELECT  count(*), qitem_type.descitem_type FROM quote INNER JOIN item ON item.id_quote = quote.id INNER JOIN qitem_type ON item.id_qitemtype = qitem_type.id\r\n" + 
			     "WHERE quote.id_estimator = :id_estimator AND item.stat_reg <> '1' GROUP BY qitem_type.descitem_type", nativeQuery=true)	
    List<Object[]> donutChartQItemtypeEstimator(@Param("id_estimator") long id_estimator);
    

	@Query(value="Select sum(totday) From (select COALESCE(q1.tot,0) as Totday, i from generate_series(CURRENT_DATE - interval '25 day',CURRENT_DATE,'1 day') i left join\r\n" + 
			"(SELECT coalesce(count(*),0) as tot, date(item.created_date) as dateq1, quote.id_estimator FROM quote INNER JOIN item ON item.id_quote = quote.id\r\n" + 
			"WHERE item.stat_reg <> '1' AND quote.id_estimator = :id_estimator Group by date(item.created_date), quote.id_estimator ) q1 On q1.dateq1 = i WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)) q2 ", nativeQuery=true)	
    long totalSparkline1Estimator(@Param("id_estimator") long id_estimator);    
    
    @Query(value="select COALESCE(q1.tot,0), i from generate_series(CURRENT_DATE - interval '25 day',CURRENT_DATE,'1 day') i left join\r\n" + 
			"(SELECT coalesce(count(*),0) as tot, date(item.created_date) as dateq1, quote.id_estimator FROM quote INNER JOIN item ON item.id_quote = quote.id\r\n" + 
			"WHERE item.stat_reg <> '1' AND quote.id_estimator = :id_estimator Group by date(item.created_date), quote.id_estimator ) q1 On q1.dateq1 = i\r\n" + 
			"WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7) ", nativeQuery=true)	
    List<Object[]> sparkline1Estimator(@Param("id_estimator") long id_estimator);
    
    @Query(value="Select COALESCE(q1.revenue,0), i from generate_series(CURRENT_DATE - interval '25 day',CURRENT_DATE,'1 day') i left join\r\n" + 
    		"(SELECT coalesce(sum(item.po_amount)) AS revenue , date(item.date_ordered) dateq1, quote.id_estimator\r\n" + 
    		"FROM item INNER JOIN item_status ON item.id_itemstatus = item_status.id INNER JOIN quote ON item.id_quote = quote.id WHERE\r\n" + 
    		"item_status.action = '3' AND quote.id_estimator = :id_estimator GROUP By date(item.date_ordered), quote.id_estimator) q1 On q1.dateq1 = i\r\n" + 
    		"WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)", nativeQuery=true)	
    List<Object[]> sparkline2Estimator(@Param("id_estimator") long id_estimator); 
    
    
    @Query(value="Select Sum(q2.totrev) From (Select COALESCE(q1.revenue,0) as totrev, i from generate_series(CURRENT_DATE - interval '60 day',CURRENT_DATE,'1 day') i left join\r\n" + 
    		"(SELECT coalesce(sum(item.smi_sale_cost - item.smi_total_cost)) AS revenue , date(item.created_date) dateq1, quote.id_estimator FROM item\r\n" + 
    		"INNER JOIN item_status ON item.id_itemstatus = item_status.id INNER JOIN quote ON item.id_quote = quote.id WHERE item_status.action = '3' AND\r\n" + 
    		"quote.id_estimator = :id_estimator GROUP By date(item.created_date), quote.id_estimator) q1 On q1.dateq1 = i WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)) q2 ", nativeQuery=true)	
    long totalSparkline2Estimator(@Param("id_estimator") long id_estimator);     
    

    @Query(value="Select COALESCE(q1.totord,0), i from generate_series(CURRENT_DATE - interval '25 day',CURRENT_DATE,'1 day') i left join (SELECT\r\n" + 
    		"coalesce(count(*),0) AS totord , date(item.created_date) dateq1, quote.id_estimator FROM item INNER JOIN item_status ON item.id_itemstatus = item_status.id\r\n" + 
    		"INNER JOIN quote ON item.id_quote = quote.id WHERE  item_status.action = '3' AND quote.id_estimator = :id_estimator GROUP By date(item.created_date), quote.id_estimator) q1  \r\n" + 
    		"On q1.dateq1 = i WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)", nativeQuery=true)	
    List<Object[]> sparkline3Estimator(@Param("id_estimator") long id_estimator); 
    
    
    @Query(value="Select Sum(q2.totord) From (Select COALESCE(q1.tot,0) as totord, i from generate_series(CURRENT_DATE - interval '35 day',CURRENT_DATE,'1 day') i left join\r\n" + 
    		"(SELECT coalesce(count(*),0) AS tot , date(item.created_date) dateq1, quote.id_estimator FROM item INNER JOIN item_status ON item.id_itemstatus = item_status.id\r\n" + 
    		" INNER JOIN quote ON item.id_quote = quote.id WHERE item_status.action = '3' AND quote.id_estimator = :id_estimator GROUP By date(item.created_date), quote.id_estimator) q1 \r\n" + 
    		"On q1.dateq1 = i WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)) q2 ", nativeQuery=true)	
    long totalSparkline3Estimator(@Param("id_estimator") long id_estimator);  
    
    @Query(value="Select COALESCE(q1.totact,0), i from generate_series(CURRENT_DATE - interval '35 day',CURRENT_DATE,'1 day') i left join\r\n" + 
    		"(SELECT coalesce(count(*),0) totact, quote.id_estimator ,date(item_activity.created_date) dateq1 FROM  item_activity INNER JOIN item ON item_activity.id_item = item.id_item\r\n" + 
    		"INNER JOIN quote ON item.id_quote = quote.id WHERE quote.id_estimator = 2 GROUP BY quote.id_estimator, date(item_activity.created_date)) q1\r\n" + 
    		"On q1.dateq1 = i WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)  ", nativeQuery=true)	
    List<Object[]> sparkline4Estimator(@Param("id_estimator") long id_estimator);     
    
    
	
	Estimator findOneByUser(Users user);
	
	

}
