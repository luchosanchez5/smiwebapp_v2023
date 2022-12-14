package com.controller;


import java.time.LocalDateTime;
import java.util.Iterator;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.model.FileUpload;
import com.model.Quote;
import com.model.Users;
import com.repository.QuoteRep;
import com.repository.UserRep;
import com.service.FileUploadService;


@CrossOrigin
@RestController
public class FileController {

    @Autowired
    FileUploadService fileUploadService;
    
    @Autowired
    QuoteRep quoteRep;
    
	@Autowired
	private UserRep userRep;

    // Download a file
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public ResponseEntity downloadFile(@RequestParam("filename") String filename, @RequestParam("id") long id ) {

       // FileUpload fileUpload = fileUploadService.findByFilename(filename);
        FileUpload fileUpload = fileUploadService.findById(id);

        // No file found based on the supplied filename
        if (fileUpload == null) {
            return new ResponseEntity<>("{}", HttpStatus.NOT_FOUND);
        }

        // Generate the http headers with the file properties
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "attachment; filename=" + fileUpload.getFilename());

        // Split the mimeType into primary and sub types
        String primaryType, subType;
        try {
            primaryType = fileUpload.getMimeType().split("/")[0];
            subType = fileUpload.getMimeType().split("/")[1];
        }
            catch (IndexOutOfBoundsException | NullPointerException ex) {
            return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        headers.setContentType( new MediaType(primaryType, subType) );

        return new ResponseEntity<>(fileUpload.getFile(), headers, HttpStatus.OK);
    }
    
    
    @RequestMapping(
            value = "upload",
            method = RequestMethod.POST  
        )
        public ResponseEntity uploadFile(MultipartHttpServletRequest request) {
    	    
    	   FileUpload newFile = new FileUpload();
            try {
                Iterator<String> itr = request.getFileNames();

                while (itr.hasNext()) {
                    String uploadedFile = itr.next();
                    MultipartFile file = request.getFile(uploadedFile);
                    String mimeType = file.getContentType();
                    String filename = file.getOriginalFilename();
                    byte[] bytes = file.getBytes();

                    //FileUpload newFile = new FileUpload(filename, bytes, mimeType);
                    
                    newFile.setFilename(filename);
                    newFile.setFile(bytes);
                    newFile.setMimeType(mimeType);
                    newFile.setCreatedDate(LocalDateTime.now());
                    newFile.setStatFile("0");
                    
                    

                    fileUploadService.uploadFile(newFile);
                }
            }
            catch (Exception e) {
                return new ResponseEntity<>("{}", HttpStatus.INTERNAL_SERVER_ERROR);
            }

            return new ResponseEntity<>(newFile, HttpStatus.OK);
        }
    
			
    
    @RequestMapping(value = "quoteFile/updateSession", method = RequestMethod.PUT)
			public ResponseEntity updateFileSession(@RequestParam("id") long id,@RequestParam("sessionId") String sessionId){
				
    	        FileUpload newFile =  fileUploadService.findById(id);
    	        newFile.setSessionId(sessionId);
    	        fileUploadService.updatefile(newFile);
				
				return new ResponseEntity<>(newFile, HttpStatus.OK);
	} 
    
    @RequestMapping(value = "quoteFile/updateFiledetails", method = RequestMethod.PUT)
			public ResponseEntity updateFile(@RequestParam("sessionId") String sessionId,
					                         @RequestParam("fileName") String fileName, 
					                         @RequestParam("idQuote") long idQuote,
					                         @RequestParam("userName") String userName,
					                         @RequestParam("id") long id){
				
    	        Quote q = quoteRep.findById(idQuote);
    	        Users user = userRep.findByUsername(userName);
    	        
    	        
    	        FileUpload newFile =  fileUploadService.findById(id);
    	        fileUploadService.updatefiledetails(newFile, q, user, sessionId);
				
				return new ResponseEntity<>(newFile, HttpStatus.OK);
	} 
    
    
    @RequestMapping(value = "quoteFile/deletesessionFile", method = RequestMethod.PUT)
			public ResponseEntity deleteFileSession(@RequestParam("sessionId") String sessionId,
					                                @RequestParam("fileName") String fileName){
				
    	        FileUpload newFile =  fileUploadService.findOneBySessionIDandName(sessionId, fileName);
    	        newFile.setStatFile("1");
    	        fileUploadService.updatefile(newFile);
				
				return new ResponseEntity<>(newFile, HttpStatus.OK);
	} 
    
    
	
}
