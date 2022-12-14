package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.model.Quote;
import com.model.QuoteNote;

@Transactional
public interface QuoteNoteRep extends JpaRepository<QuoteNote, Long> {
	
	List<QuoteNote> findAllByStatReg(String stat);
	List<QuoteNote> findAllByStatRegAndQuote(String stat, Quote q);
	List<QuoteNote> findAllByStatRegAndQuoteAndTypeNoteOrderByCreatedDateDesc(String stat, Quote q, String type);
	
	@Query(value="SELECT * FROM v_notifications WHERE id_user_est = :id_estimator AND id_user_note <> :id_estimator AND status <> '1' AND stat_read_estimator is Null or stat_read_estimator = '0'  ORDER BY date_not DESC",nativeQuery=true )
	List<Object[]> notificationsEstimator(@Param("id_estimator") long id_estimator);
	
	
	@Query(value="SELECT count(*) FROM v_notifications WHERE id_user_est = :id_estimator AND id_user_note <> :id_estimator AND status <> '1' AND stat_read_estimator is Null or stat_read_estimator = '0'",nativeQuery=true )
	int countnotificationsEstimator(@Param("id_estimator") long id_estimator);
	
	
	@Query(value="SELECT * FROM v_notifications WHERE id_user_est = :id_estimator AND id_user_note <> :id_estimator AND status <> '1' AND stat_read_estimator is Null or stat_read_estimator = '0' ORDER BY date_not DESC limit 4",nativeQuery=true )
	List<Object[]> notificationsEstimatorTop4(@Param("id_estimator") long id_estimator);
	
	
	QuoteNote findOneById(long id);

}
