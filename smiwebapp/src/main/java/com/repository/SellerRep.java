package com.repository;

import java.math.BigInteger;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.model.Seller;
import com.model.Users;

@Transactional
public interface SellerRep extends JpaRepository<Seller, Integer> {

	
	List<Seller> findAllByStatSellerAndStatReg(String statSeller, String statReg);
	Seller findOneByIdUser(String id);
	Seller findOneByUser(Users us);
	Seller findOneById(long id);
	
	@Query(value="Select  COALESCE(count(*),0) From Quote Where quote.stat_reg <> '1' and quote.id_seller = :id_seller or quote.id_seller2 = :id_seller ", nativeQuery=true)
	long findTotalQuotesEstimator(@Param("id_seller") BigInteger id_seller);
	
	@Query(value="Select COALESCE(count(*),0) From item Inner Join quote q on q.id = item.id_quote Where q.stat_reg <> '1' And item.stat_reg <> '1' And q.id_seller = :id_seller ", nativeQuery=true)
	long findTotalItemsEstimator(@Param("id_seller") long id_seller);
	
	@Query(value="Select COALESCE(count(*),0) From Item i, item_status s, quote q where i.id_itemstatus = s.id " + 
			     "and q.stat_reg = '0' and i.stat_reg = '0' and s.stat_reg = '0' and s.action = '1' and q.id = i.id_quote and q.id_seller = :id_seller", nativeQuery=true)
	long findTotalItemsPending(@Param("id_seller") long id_seller);
	
	@Query(value="Select COALESCE(count(*),0) From Item i, item_status s, quote q where i.id_itemstatus = s.id " + 
		     "and q.stat_reg = '0' and i.stat_reg = '0' and s.stat_reg = '0' and s.action = '1' and q.id = i.id_quote and q.id_seller = :id_seller", nativeQuery=true)
    long findListlItemsPending(@Param("id_seller") long id_seller);
	
	
	@Query(value="Select COALESCE(count(*),0) From Item i, item_status s, quote q where i.id_itemstatus = s.id " + 
			     "and s.stat_reg = '0' and i.stat_reg = '0' and q.stat_reg = '0' and s.action = '3' and q.id = i.id_quote and q.id_seller = :id_seller", nativeQuery=true)
	long findTotalItemsOrder(@Param("id_seller") long id_seller);
	
	@Query(value="select i, COALESCE(q1.tot,0) from generate_series(CURRENT_DATE - interval '16 day',CURRENT_DATE,'1 day') i left join\r\n" + 
		     "(SELECT date(quote.created_date) created_date, coalesce(count(*),0) as tot , id_seller FROM quote\r\n" + 
		     "WHERE quote.stat_reg <> '1'  And (quote.created_date >= current_date - interval '70 Days') And quote.id_seller = :id_seller\r\n" + 
		     "GROUP BY date(quote.created_date), id_seller ORDER By date(quote.created_date)) q1 on q1.created_date = date(i)", nativeQuery=true)
    List<Object[]> graphicQuotesPerDaySeller(@Param("id_seller") long id_seller);
    

	@Query(value="SELECT count(*), industry.industry_desc FROM quote INNER JOIN item ON item.id_quote = quote.id INNER JOIN industry ON item.id_industry = industry.id\r\n" + 
			     "WHERE quote.id_seller = :id_seller AND item.stat_reg <> '1' GROUP BY  industry.industry_desc", nativeQuery=true)	
	List<Object[]> donutChartIndustryEstimator(@Param("id_seller") long id_seller);
	
	@Query(value="SELECT count(*), item_type.desc_item_type FROM quote INNER JOIN item ON item.id_quote = quote.id INNER JOIN item_type ON item.id_itemtype = item_type.id\r\n" + 
			     "WHERE quote.id_seller = :id_seller AND item.stat_reg <> '1' GROUP BY item_type.desc_item_type", nativeQuery=true)	
	List<Object[]> donutChartItemtypeEstimator(@Param("id_seller") long id_seller);
	
	
	@Query(value="SELECT  count(*), qitem_type.descitem_type FROM quote INNER JOIN item ON item.id_quote = quote.id INNER JOIN qitem_type ON item.id_qitemtype = qitem_type.id\r\n" + 
			     "WHERE quote.id_seller = :id_seller AND item.stat_reg <> '1' GROUP BY qitem_type.descitem_type", nativeQuery=true)	
	List<Object[]> donutChartQItemtypeEstimator(@Param("id_seller") long id_seller);
	
	
	@Query(value="Select sum(totday) From (select COALESCE(q1.tot,0) as Totday, i from generate_series(CURRENT_DATE - interval '25 day',CURRENT_DATE,'1 day') i left join\r\n" + 
			"(SELECT coalesce(count(*),0) as tot, date(item.created_date) as dateq1, quote.id_seller FROM quote INNER JOIN item ON item.id_quote = quote.id\r\n" + 
			"WHERE item.stat_reg <> '1' AND quote.id_seller = :id_seller Group by date(item.created_date), quote.id_seller ) q1 On q1.dateq1 = i WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)) q2 ", nativeQuery=true)	
	long totalSparkline1Estimator(@Param("id_seller") long id_seller);    
	
	@Query(value="select COALESCE(q1.tot,0), i from generate_series(CURRENT_DATE - interval '25 day',CURRENT_DATE,'1 day') i left join\r\n" + 
			"(SELECT coalesce(count(*),0) as tot, date(item.created_date) as dateq1, quote.id_seller FROM quote INNER JOIN item ON item.id_quote = quote.id\r\n" + 
			"WHERE item.stat_reg <> '1' AND quote.id_seller = :id_seller Group by date(item.created_date), quote.id_seller ) q1 On q1.dateq1 = i\r\n" + 
			"WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7) ", nativeQuery=true)	
	List<Object[]> sparkline1Estimator(@Param("id_seller") long id_seller);
	
	@Query(value="Select COALESCE(q1.revenue,0), i from generate_series(CURRENT_DATE - interval '25 day',CURRENT_DATE,'1 day') i left join\r\n" + 
			"(SELECT coalesce(sum(item.smi_sale_cost - item.smi_total_cost)) AS revenue , date(item.created_date) dateq1, quote.id_seller\r\n" + 
			"FROM item INNER JOIN item_status ON item.id_itemstatus = item_status.id INNER JOIN quote ON item.id_quote = quote.id WHERE\r\n" + 
			"item_status.action = '3' AND quote.id_seller = :id_seller GROUP By date(item.created_date), quote.id_seller) q1 On q1.dateq1 = i\r\n" + 
			"WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)", nativeQuery=true)	
	List<Object[]> sparkline2Estimator(@Param("id_seller") long id_seller); 
	
	
	@Query(value="Select Sum(q2.totrev) From (Select COALESCE(q1.revenue,0) as totrev, i from generate_series(CURRENT_DATE - interval '25 day',CURRENT_DATE,'1 day') i left join\r\n" + 
			"(SELECT coalesce(sum(item.smi_sale_cost - item.smi_total_cost)) AS revenue , date(item.created_date) dateq1, quote.id_seller FROM item\r\n" + 
			"INNER JOIN item_status ON item.id_itemstatus = item_status.id INNER JOIN quote ON item.id_quote = quote.id WHERE item_status.action = '3' AND\r\n" + 
			"quote.id_seller = :id_seller GROUP By date(item.created_date), quote.id_seller) q1 On q1.dateq1 = i WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)) q2 ", nativeQuery=true)	
	long totalSparkline2Estimator(@Param("id_seller") long id_seller);     
	
	
	@Query(value="Select COALESCE(q1.totord,0), i from generate_series(CURRENT_DATE - interval '25 day',CURRENT_DATE,'1 day') i left join (SELECT\r\n" + 
			"coalesce(count(*),0) AS totord , date(item.created_date) dateq1, quote.id_seller FROM item INNER JOIN item_status ON item.id_itemstatus = item_status.id\r\n" + 
			"INNER JOIN quote ON item.id_quote = quote.id WHERE  item_status.action = '3' AND quote.id_seller = :id_seller GROUP By date(item.created_date), quote.id_seller) q1  \r\n" + 
			"On q1.dateq1 = i WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)", nativeQuery=true)	
	List<Object[]> sparkline3Estimator(@Param("id_seller") long id_seller); 
	
	
	@Query(value="Select Sum(q2.totord) From (Select COALESCE(q1.tot,0) as totord, i from generate_series(CURRENT_DATE - interval '35 day',CURRENT_DATE,'1 day') i left join\r\n" + 
			"(SELECT coalesce(count(*),0) AS tot , date(item.created_date) dateq1, quote.id_seller FROM item INNER JOIN item_status ON item.id_itemstatus = item_status.id\r\n" + 
			" INNER JOIN quote ON item.id_quote = quote.id WHERE item_status.action = '3' AND quote.id_seller = :id_seller GROUP By date(item.created_date), quote.id_seller) q1 \r\n" + 
			"On q1.dateq1 = i WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)) q2 ", nativeQuery=true)	
	long totalSparkline3Estimator(@Param("id_seller") long id_seller);  
	
	@Query(value="Select COALESCE(q1.totact,0), i from generate_series(CURRENT_DATE - interval '35 day',CURRENT_DATE,'1 day') i left join\r\n" + 
			"(SELECT coalesce(count(*),0) totact, quote.id_seller ,date(item_activity.created_date) dateq1 FROM  item_activity INNER JOIN item ON item_activity.id_item = item.id_item\r\n" + 
			"INNER JOIN quote ON item.id_quote = quote.id WHERE quote.id_seller = 2 GROUP BY quote.id_seller, date(item_activity.created_date)) q1\r\n" + 
			"On q1.dateq1 = i WHERE EXTRACT(ISODOW FROM i) NOT IN (6, 7)  ", nativeQuery=true)	
	List<Object[]> sparkline4Estimator(@Param("id_seller") long id_seller); 
	
	
	@Query(value="SELECT item_status.item_status_desc, round((count(*) * 100 )/(SELECT  count(*) as Total\r\n" + 
			"FROM quote INNER JOIN item ON item.id_quote = quote.id INNER JOIN item_status ON item.id_itemstatus = item_status.id\r\n" + 
			"WHERE quote.id_seller = :id_seller AND date_part('year', quote.created_date) = date_part('year', CURRENT_DATE) AND  quote.stat_reg <> '1' AND item.stat_reg <> '1' ),2), item_status.id \r\n" + 
			"FROM quote INNER JOIN item ON item.id_quote = quote.id  INNER JOIN item_status ON item.id_itemstatus = item_status.id\r\n" + 
			"WHERE quote.id_seller = :id_seller AND quote.stat_reg <> '1' AND item.stat_reg <> '1' GROUP BY item_status.id,item_status.item_status_desc", nativeQuery=true)	
	List<Object[]> percentitemseller(@Param("id_seller") long id_seller); 
	
	@Query(value="SELECT quote.id_seller, item_activity.desc_activity, item.item_number, users.image_url, item_activity.created_date \r\n" + 
			"FROM  item_activity  INNER JOIN item ON item_activity.id_item = item.id_item INNER JOIN quote ON item.id_quote = quote.id\r\n" + 
			"INNER JOIN seller ON quote.id_seller = seller.id INNER JOIN users ON seller.id_login = users.id WHERE item_activity.stat_record <> '1'\r\n" + 
			"AND quote.id_seller = :id_seller\r\n" + 
			"ORDER BY  item_activity.created_date DESC limit 10", nativeQuery=true)
	List<Object[]> sellerActivities(@Param("id_seller") long id_seller);
	
	@Query(value="Select total, m From vgraphicprofileseller Where id_seller = :id_seller order by mint", nativeQuery=true)
	List<Object[]> graphicprofileseller(@Param("id_seller") long id_seller);
	
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
			"    q.id_seller = :id_seller And\r\n" + 
			"    Date_Part('year', q.created_date) = Date_Part('year', Current_Date)", nativeQuery=true)
	long findTotalAmountOrder(@Param("id_seller") long id_seller);
	
	@Query(value="Select * From vCustomersSeller Where id_seller = :id_seller", nativeQuery=true)
	List<Object[]> findAllCustomersSellers(@Param("id_seller") long id_seller);
	

    
     
}
