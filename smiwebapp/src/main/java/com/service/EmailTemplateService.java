package com.service;

import java.time.LocalDateTime;

public interface EmailTemplateService {
	
	public String buildAsnTemplate(String customerName, LocalDateTime dateAction, Double quantity, String custPo,
			String emailContac, String emailTaker, String shipMethod, String trackingNo);

}
