package com.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class EmailTemplateServiceImp implements EmailTemplateService {

	@Override
	public String buildAsnTemplate(String customerName, LocalDateTime dateAction, Double quantity, String custPo,
			String emailContac, String emailTaker, String shipMethod, String trackingNo) {
		// TODO Auto-generated method stub
		
		String textEmail = "<!DOCTYPE html>\r\n" + 
				"<html lang=\"en\"\r\n" + 
				"	xmlns=\"http://www.w3.org/1999/xhtml\"\r\n" + 
				"	xmlns:th=\"http://www.thymeleaf.org\">\r\n" + 
				"	<head>\r\n" + 
				"		<meta name=\"viewport\" content=\"width=device-width\">\r\n" + 
				"			<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\r\n" + 
				"				<title>ASN Seal Methods</title>\r\n" + 
				"				<style>\r\n" + 
				"        /* -------------------------------------\r\n" + 
				"            INLINED WITH htmlemail.io/inline\r\n" + 
				"        ------------------------------------- */\r\n" + 
				"        /* -------------------------------------\r\n" + 
				"            RESPONSIVE AND MOBILE FRIENDLY STYLES\r\n" + 
				"        ------------------------------------- */\r\n" + 
				"        @media only screen and (max-width: 620px) {\r\n" + 
				"            table[class=body] h1 {\r\n" + 
				"                font-size: 28px !important;\r\n" + 
				"                margin-bottom: 10px !important;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            table[class=body] p,\r\n" + 
				"            table[class=body] ul,\r\n" + 
				"            table[class=body] ol,\r\n" + 
				"            table[class=body] td,\r\n" + 
				"            table[class=body] span,\r\n" + 
				"            table[class=body] a {\r\n" + 
				"                font-size: 16px !important;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            table[class=body] .wrapper,\r\n" + 
				"            table[class=body] .article {\r\n" + 
				"                padding: 10px !important;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            table[class=body] .content {\r\n" + 
				"                padding: 0 !important;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            table[class=body] .container {\r\n" + 
				"                padding: 0 !important;\r\n" + 
				"                width: 100% !important;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            table[class=body] .main {\r\n" + 
				"                border-left-width: 0 !important;\r\n" + 
				"                border-radius: 0 !important;\r\n" + 
				"                border-right-width: 0 !important;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            table[class=body] .btn table {\r\n" + 
				"                width: 100% !important;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            table[class=body] .btn a {\r\n" + 
				"                width: 100% !important;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            table[class=body] .img-responsive {\r\n" + 
				"                height: auto !important;\r\n" + 
				"                max-width: 100% !important;\r\n" + 
				"                width: auto !important;\r\n" + 
				"            }\r\n" + 
				"        }\r\n" + 
				"\r\n" + 
				"        /* -------------------------------------\r\n" + 
				"            PRESERVE THESE STYLES IN THE HEAD\r\n" + 
				"        ------------------------------------- */\r\n" + 
				"        @media all {\r\n" + 
				"            .ExternalClass {\r\n" + 
				"                width: 100%;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            .ExternalClass,\r\n" + 
				"            .ExternalClass p,\r\n" + 
				"            .ExternalClass span,\r\n" + 
				"            .ExternalClass font,\r\n" + 
				"            .ExternalClass td,\r\n" + 
				"            .ExternalClass div {\r\n" + 
				"                line-height: 100%;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            .apple-link a {\r\n" + 
				"                color: inherit !important;\r\n" + 
				"                font-family: inherit !important;\r\n" + 
				"                font-size: inherit !important;\r\n" + 
				"                font-weight: inherit !important;\r\n" + 
				"                line-height: inherit !important;\r\n" + 
				"                text-decoration: none !important;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            #MessageViewBody a {\r\n" + 
				"                color: inherit;\r\n" + 
				"                text-decoration: none;\r\n" + 
				"                font-size: inherit;\r\n" + 
				"                font-family: inherit;\r\n" + 
				"                font-weight: inherit;\r\n" + 
				"                line-height: inherit;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            .btn-primary table td:hover {\r\n" + 
				"                background-color: #ffffff !important;\r\n" + 
				"            }\r\n" + 
				"\r\n" + 
				"            .btn-primary a:hover {\r\n" + 
				"                background-color: #ffffff !important;\r\n" + 
				"                border-color: #ffffff !important;\r\n" + 
				"            }\r\n" + 
				"        }\r\n" + 
				"    </style>\r\n" + 
				"			</head>\r\n" + 
				"			<body class=\"\"\r\n" + 
				"      style=\"background-color: #f6f6f6; font-family: sans-serif; -webkit-font-smoothing: antialiased; font-size: 14px; line-height: 1.4; margin: 0; padding: 0; -ms-text-size-adjust: 100%; -webkit-text-size-adjust: 100%;\">\r\n" + 
				"				<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"body\"\r\n" + 
				"       style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background-color: #f6f6f6;\">\r\n" + 
				"					<tr>\r\n" + 
				"						<td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\r\n" + 
				"						<td class=\"container\"\r\n" + 
				"            style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; display: block; Margin: 0 auto; max-width: 580px; padding: 10px; width: 580px;\">\r\n" + 
				"							<div class=\"content\"\r\n" + 
				"                 style=\"box-sizing: border-box; display: block; Margin: 0 auto; max-width: 580px; padding: 10px;\">\r\n" + 
				"								<!-- START CENTERED WHITE CONTAINER -->\r\n" + 
				"								<table class=\"main\"\r\n" + 
				"                       style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; border-radius: 3px;\">\r\n" + 
				"									<!-- START MAIN CONTENT AREA -->\r\n" + 
				"									<tr>\r\n" + 
				"										<td class=\"wrapper\"\r\n" + 
				"                            style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px;\">\r\n" + 
				"											<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\r\n" + 
				"                                   style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\r\n" + 
				"												<tr>\r\n" + 
				"													<td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\" align=\"center\">\r\n" + 
				"														<img src=\"https://cdn.shortpixel.ai/client/q_glossy,ret_img/https://sealmethodsinc.com/wp-content/themes/smi.2/images/logo-smi-new.png\" alt=\"Useful alt text\" width=\"410\" height=\"120\" border=\"0\"\r\n" + 
				"                                             style=\"border:0; outline:none; text-decoration:none; display:block;\">\r\n" + 
				"														</td>\r\n" + 
				"													</tr>\r\n" + 
				"												</table>\r\n" + 
				"											</td>\r\n" + 
				"										</tr>\r\n" + 
				"									</table>\r\n" + 
				"									<table class=\"main\"\r\n" + 
				"                       style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; background: #ffffff; border-radius: 3px;\">\r\n" + 
				"										<!-- START MAIN CONTENT AREA -->\r\n" + 
				"										<tr>\r\n" + 
				"											<td class=\"wrapper\"\r\n" + 
				"                            style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; box-sizing: border-box; padding: 20px;\">\r\n" + 
				"												<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\r\n" + 
				"                                   style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\r\n" + 
				"													<tr>\r\n" + 
				"														<td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">\r\n" + 
				"															<p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">\r\n" + 
				"                                            Dear "+ customerName +",</p>\r\n" + 
				"															<p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">\r\n" + 
				"                                            Your order has been shipped out. It is now on its way to you.</p>\r\n" + 
				"                                          \r\n" + 
				"                                            \r\n" + 
				"                                            															<p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">\r\n" + 
				"                                            P.O. : "+ custPo +" <br>\r\n" + 
				"                                            P/N : " + " <br>\r\n" +
				"                                            Quantity Shipped : " + quantity + " <br>\r\n" + 
				"                                            CARRIER : "+ shipMethod +" <br>\r\n" + 
				"                                            TRACKING NUMBER : " + trackingNo + " <br>\r\n" + 
				"                                            DATE : " + dateAction + "<br> </p>\r\n" + 
				"                                            \r\n" + 
				"                                            \r\n" + 
				"													<p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">\r\n" + 
				"                                        If you have any question please feel free to contact me : \r\n" + 
				"                                        " + emailTaker + ".</p>   \r\n" + 
				"													<p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">\r\n" + 
				"                                                Regards,\r\n" + 
				"                                                </p><br>                                         \r\n" + 
				"                                        \r\n" + 
				"													<p style=\"font-family: sans-serif; font-size: 14px; font-weight: normal; margin: 0; Margin-bottom: 15px;\">\r\n" + 
				"                                                \r\n" + 
				"                                                Seal Methods Customer Service Team.</p>                                          \r\n" + 
				"															<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"btn btn-primary\"\r\n" + 
				"                                               style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%; box-sizing: border-box;\">\r\n" + 
				"																<tbody>\r\n" + 
				"																	<tr>\r\n" + 
				"																		<td align=\"center\"\r\n" + 
				"                                                    style=\"font-family: sans-serif; font-size: 14px; vertical-align: top; padding-bottom: 15px;\">\r\n" + 
				"																			<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\r\n" + 
				"                                                           style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: auto;\">\r\n" + 
				"																				<tbody>\r\n" + 
				"																					<tr>\r\n" + 
				"																						<td style=\"font-family: sans-serif; font-size: 50px; vertical-align: top; border-radius: 5px; text-align: center; color: #ebb136;\">\r\n" + 
				"																							<span th:text=\"${code}\"></span>\r\n" + 
				"																						</td>\r\n" + 
				"																					</tr>\r\n" + 
				"																				</tbody>\r\n" + 
				"																			</table>\r\n" + 
				"																		</td>\r\n" + 
				"																	</tr>\r\n" + 
				"																</tbody>\r\n" + 
				"															</table>\r\n" + 
				"														</td>\r\n" + 
				"													</tr>\r\n" + 
				"												</table>\r\n" + 
				"											</td>\r\n" + 
				"										</tr>\r\n" + 
				"										<!-- END MAIN CONTENT AREA -->\r\n" + 
				"									</table>\r\n" + 
				"									<!-- START FOOTER -->\r\n" + 
				"									<div class=\"footer\" style=\"clear: both; Margin-top: 10px; text-align: center; width: 100%;\">\r\n" + 
				"										<table border=\"0\" cellpadding=\"0\" cellspacing=\"0\"\r\n" + 
				"                           style=\"border-collapse: separate; mso-table-lspace: 0pt; mso-table-rspace: 0pt; width: 100%;\">\r\n" + 
				"											<tr>\r\n" + 
				"												<td class=\"content-block\"\r\n" + 
				"                                style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\">\r\n" + 
				"													<span class=\"apple-link\" style=\"color: #999999; font-size: 12px; text-align: center;\">Seal Methods Inc., USA</span> <br>\r\n" + 
				"													<span class=\"apple-link\" style=\"color: #999999; font-size: 12px; text-align: center;\">11915 Shoemaker Ave Santa Fe Springs, CA 90670</span>		\r\n" + 
				"													<span class=\"apple-link\" style=\"color: #999999; font-size: 12px; text-align: center;\">Phone: 800.423.4777     Fax: 562.946.9439    </span> <br>		\r\n" + 
				"													<span class=\"apple-link\" style=\"color: #999999; font-size: 12px; text-align: center;\">Email: sales@sealmethodsinc.com</span> <br>													\r\n" + 
				"												</td>\r\n" + 
				"											</tr>\r\n" + 
				"											<tr>\r\n" + 
				"												<td class=\"content-block\"\r\n" + 
				"                                style=\"font-family: sans-serif; vertical-align: top; padding-bottom: 10px; padding-top: 10px; font-size: 12px; color: #999999; text-align: center;\">\r\n" + 
				"\r\n" + 
				"												</td>\r\n" + 
				"											</tr>\r\n" + 
				"										</table>\r\n" + 
				"									</div>\r\n" + 
				"									<!-- END FOOTER -->\r\n" + 
				"									<!-- END CENTERED WHITE CONTAINER -->\r\n" + 
				"								</div>\r\n" + 
				"							</td>\r\n" + 
				"							<td style=\"font-family: sans-serif; font-size: 14px; vertical-align: top;\">&nbsp;</td>\r\n" + 
				"						</tr>\r\n" + 
				"					</table>\r\n" + 
				"				</body>\r\n" + 
				"			</html>" ;
		
		
		
		
		return textEmail;
	}

}
