package com.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.model.TempItem;
import com.model.Users;

@Transactional
public interface TempitemRep extends JpaRepository<TempItem, Long> {

	
	List<TempItem> findAllByUsersAndSessionId(Users user, String sessionId);
	List<TempItem> findAllBySessionId( String sessionId);
	List<TempItem> findAllBySessionIdAndStatReg( String sessionId, String stat);
	void deleteAllBySessionId(String sessionid);
	TempItem findOneById(long id);
	
	
}
