package com.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.model.FileUpload;
import com.model.Quote;

@Repository
@Transactional
public interface FileUploadRepository extends JpaRepository<FileUpload, Long> {
	
	FileUpload findByFilename(String filename);
	FileUpload findOneById(long id);
	FileUpload findTop1BySessionIdAndFilename(String session, String filename);
	List<FileUpload> findAllByQuoteAndStatFile(Quote q, String stat);
	List<FileUpload> findAllBySessionIdAndStatFile(String session,String stat );
	void deleteBySessionIdAndQuoteIsNull(String session);
}
