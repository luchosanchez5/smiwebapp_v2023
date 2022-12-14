package com.controller;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bea.xml.stream.samples.Parse;
import com.model.Estimator;
import com.model.QueryQuotes;
import com.model.Users;
import com.model.V_notifications;
import com.repository.QuoteNoteRep;
import com.repository.UserRep;

@Controller
public class NotificationsController {
	
	@Autowired
	private UserRep userRep;
	
	@Autowired
	private QuoteNoteRep quotenoteRep;
	
	
	
	@RequestMapping(value="notifications/estimator")
	@ResponseBody	
	public List<V_notifications> listNotificationsEstimator(String username){
		List<V_notifications> qSel = new ArrayList<V_notifications>();
		
		Users user = userRep.findByUsername(username);
		System.out.println("Estimator ID : " +  user.getId());
		
		
		List<Object[]> resulQ = quotenoteRep.notificationsEstimator(user.getId());
		//List<Object[]> resulQ = quotenoteRep.notificationsEstimatorTop4(user.getId());
		
		for (int j = 0; j < resulQ.size(); j++) {
			
			V_notifications q = new V_notifications();
			Timestamp qDate = null;
			
			q.setId((BigInteger) resulQ.get(j)[0]);
			q.setComment((String) resulQ.get(j)[1]);
			qDate = (Timestamp) resulQ.get(j)[2];
			q.setDateNot2(qDate);
			//q.setDateNot2((LocalDateTime) resulQ.get(j)[2]);
			//q.setStatus((String) resulQ.get(j)[3]);
			q.setTypeNotification((String) resulQ.get(j)[4]);
			q.setId_quote((BigInteger) resulQ.get(j)[5]);
			q.setNroRfq((String) resulQ.get(j)[6]);
			//q.setId_user_note((BigInteger) resulQ.get(j)[7]);
			//q.setStat_read_estimator((String) resulQ.get(j)[8]);
			//q.setStat_read_seller((String) resulQ.get(j)[9]);
			//q.setId_estimator((BigInteger) resulQ.get(j)[10]);
			//q.setId_seller((BigInteger) resulQ.get(j)[11]);
			//q.setId_user_est((BigInteger) resulQ.get(j)[12]);
			//q.setId_user_seller((BigInteger) resulQ.get(j)[13]);
			q.setName_user_message((String) resulQ.get(j)[14]);
			q.setImage((String) resulQ.get(j)[15]);
			q.setOrigin((String) resulQ.get(j)[16]);
			
			

			
			qSel.add(q);
			
		}
		
		
		
		return qSel;
	}
	
	
	@RequestMapping(value="notifications/estimatorTop4")
	@ResponseBody	
	public List<V_notifications> listNotificationsEstimatorTop4(String username){
		List<V_notifications> qSel = new ArrayList<V_notifications>();
		
		Users user = userRep.findByUsername(username);
		System.out.println("Estimator ID : " +  user.getId());
		
		
		//List<Object[]> resulQ = quotenoteRep.notificationsEstimator(user.getId());
		List<Object[]> resulQ = quotenoteRep.notificationsEstimatorTop4(user.getId());
		
		for (int j = 0; j < resulQ.size(); j++) {
			
			V_notifications q = new V_notifications();
			Timestamp qDate = null;
			
			q.setComment((String) resulQ.get(j)[1]);
			qDate = (Timestamp) resulQ.get(j)[2];
			q.setDateNot2(qDate);
			q.setTypeNotification((String) resulQ.get(j)[4]);
			q.setId_quote((BigInteger) resulQ.get(j)[5]);
			q.setNroRfq((String) resulQ.get(j)[6]);
			q.setName_user_message((String) resulQ.get(j)[14]);
			q.setImage((String) resulQ.get(j)[15]);
			

			
			qSel.add(q);
			
		}
		
		
		
		return qSel;
	}
	
	
	@RequestMapping(value="notifications/estimatorcount")
	@ResponseBody	
	public String listNotificationsEstimatorCount(String username){
		
		Users user = userRep.findByUsername(username);
		
		int total = quotenoteRep.countnotificationsEstimator(user.getId());
		
        String resul =  String.valueOf(total);		
		
		return resul;
	}		
	

}
