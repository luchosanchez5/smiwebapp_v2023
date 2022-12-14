package com.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.model.FileUpload;
import com.model.Quote;
import com.model.Users;
import com.repository.FileUploadRepository;



@Service
public class FileUploadService {
	
	@Autowired
	FileUploadRepository fileUploadRepository;
	
    // Retrieve file
    public FileUpload findByFilename(String filename) {
        return fileUploadRepository.findByFilename(filename);
    }
    
    public List<FileUpload> findAllByquoteAndStatFile(Quote q, String statFile){
    	
    	return fileUploadRepository.findAllByQuoteAndStatFile(q, statFile);
    }
    
    public List<FileUpload> findAllBySessionAndStat(String session, String statFile){
    	
    	return fileUploadRepository.findAllBySessionIdAndStatFile(session, statFile);
    }
    
    public void delete_trashfiles(String sessionId) {
    	
    	fileUploadRepository.deleteBySessionIdAndQuoteIsNull(sessionId);
    }
    
    public FileUpload findById(long id) {
    	return fileUploadRepository.findOneById(id);
    }
    
    public FileUpload findOneBySessionIDandName(String session, String filename) {
    	return fileUploadRepository.findTop1BySessionIdAndFilename(session, filename);
    }

    // Upload the file
    public void uploadFile(FileUpload doc) {
        fileUploadRepository.saveAndFlush(doc);
    }
    
    public void updatefile(FileUpload doc) {
    	fileUploadRepository.save(doc);
    }
    
    public void updatefiledetails(FileUpload doc, Quote q, Users user, String sessionid) {
    	
    	doc.setStatFile("1");
    	doc.setQuote(q);
    	doc.setUsers(user);
    	doc.setSessionId(sessionid);
    	fileUploadRepository.save(doc);
    	
    }

}
