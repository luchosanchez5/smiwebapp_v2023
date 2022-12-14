package com.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Estimator;
import com.model.Item;
import com.model.ItemActivity;
import com.model.Quote;
import com.model.QuoteNote;
import com.repository.ItemRep;
import com.repository.ItemactivityRep;
import com.repository.QuoteNoteRep;
import com.repository.QuoteRep;
import com.repository.TempitemRep;
import com.service.EmailService;

@Controller
public class ItemactivityController {
	
	@Autowired
	private QuoteRep quoteRep;
	
	@Autowired
	private ItemRep itemRep;
	
	@Autowired
	private QuoteNoteRep quotenoteRep;
	
	@Autowired
	private ItemactivityRep iactRep;
	
    @Autowired
    EmailService emailService;	

	
	@RequestMapping("list-itemactivitys")
	@ResponseBody
	private List<ItemActivity> listfindAllByItem(long itemId) {
		
		Item item = itemRep.findOneByIdItem(itemId);
		List<ItemActivity> listAct = iactRep.findAllByStatRecordAndItem("0", item);
		return listAct;
		
	}
	
	@RequestMapping(value = "item/newActivity", method = RequestMethod.POST)
	public  @ResponseBody ResponseEntity<ItemActivity> new_activity(@RequestParam("idItem") int idItem, @RequestBody ItemActivity pract) throws IOException{
		
        
		
		Item it = itemRep.findOneByIdItem(idItem);
		Quote q = it.getQuote();
		
		pract.setCreatedDate(LocalDateTime.now());
		pract.setItem(it);
		pract.setStatRecord("0");
		
		iactRep.save(pract);
        
		QuoteNote qn = new QuoteNote();
		qn.setComment("Comment on Item " + pract.getItem().getItemNumber() + " : " +   pract.getDescActivity());
		qn.setCreatedDate(LocalDateTime.now());
		qn.setQuote(q);
		qn.setStatReg("0");
		qn.setUser(pract.getUser());
		
		quotenoteRep.save(qn);
		
	
		return new ResponseEntity<ItemActivity>(pract, HttpStatus.OK);
		
	}
	
	@RequestMapping(value = "item/newActStatus", method = RequestMethod.POST)
	public  @ResponseBody ResponseEntity<ItemActivity> new_status(@RequestParam("idItem") int idItem, @RequestBody ItemActivity pract) throws IOException{
		
        
		System.out.println("NEW ACTIVITY" + pract.getDescActivity());
		
		Item it = itemRep.findOneByIdItem(idItem);
		Quote q = it.getQuote();
		
		pract.setCreatedDate(LocalDateTime.now());
		pract.setItem(it);
		pract.setStatRecord("0");
		
		iactRep.save(pract);
        
		QuoteNote qn = new QuoteNote();
		qn.setComment("Status Changed on Item " + pract.getItem().getItemNumber() + " : " +   pract.getDescActivity());
		qn.setCreatedDate(LocalDateTime.now());
		qn.setQuote(q);
		qn.setStatReg("0");
		qn.setUser(pract.getUser());
		
		quotenoteRep.save(qn);
		
		
	
		return new ResponseEntity<ItemActivity>(pract, HttpStatus.OK);
		
	}	
	
	@RequestMapping(value = "item/removeActivity", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<String> delete_itemact(@RequestParam("idAct") long idAct, @RequestParam("stat") String stat,@RequestParam("user") String user){
		
		ItemActivity act = iactRep.findOneByIdItemActivity(idAct);
		
		act.setStatRecord("1");
		iactRep.save(act);
		
	/*	LeadActivity leact = dLeadAct.findOne(id);
		if (leact.getUsername().equals(user)){
			leact.setStatRecord(stat);
			dLeadAct.save(leact);
			
		}*/
		
		return new ResponseEntity<String>(act.getStatRecord(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "item/notifyMessage", method = RequestMethod.POST)
	public  @ResponseBody ResponseEntity<String> new_notifyMessage(@RequestParam("message")  String message, @RequestParam("idQuote")  long idQuote, @RequestParam("idItem") long idItem , String role) throws IOException{
		
		Quote q = quoteRep.findById(idQuote); 
		Item i = itemRep.findOneByIdItem(idItem);
		String sender = "";
		
		if (role.equals("2")) {
			sender = q.getEstimator().getSureName() + " " + q.getEstimator().getLastName();
		}
		
		if (role.equals("3")) {
			sender = q.getSeller().getSureName() + " " + q.getSeller().getLastName();
		}
			
		
		try {
			DateTimeFormatter formatter = DateTimeFormatter.BASIC_ISO_DATE;
			
			
			
			String textEmail =  "<!DOCTYPE HTML>\r\n" + 
					"<!-- saved from url=(0104)mhtml:file://C:\\Users\\luiss\\AppData\\Local\\Microsoft\\Windows\\INetCache\\Content.Outlook\\H0SG760V\\email.mht -->\r\n" + 
					"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional //EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\"><!--[if IE]><html xmlns=\"http://www.w3.org/1999/xhtml\" class=\"ie\"><![endif]--><!--[if !IE]><!--><HTML \r\n" + 
					"style=\"margin: 0px; padding: 0px;\" xmlns=\"http://www.w3.org/1999/xhtml\"><!--<![endif]--><HEAD><META \r\n" + 
					"content=\"IE=11.0000\" http-equiv=\"X-UA-Compatible\">\r\n" + 
					" \r\n" + 
					"<META http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\">     \r\n" + 
					"<TITLE></TITLE>     <!--[if !mso]><!-->\r\n" + 
					"<META http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"><!--<![endif]-->     \r\n" + 
					"<META name=\"viewport\" content=\"width=device-width\">\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"@media only screen and (min-width: 620px){.wrapper{min-width:600px !important}.wrapper h1{}.wrapper h1{font-size:26px !important;line-height:34px !important}.wrapper h2{}.wrapper h3{}.column{}.wrapper .size-8{font-size:8px !important;line-height:14px !important}.wrapper .size-9{font-size:9px !important;line-height:16px !important}.wrapper .size-10{font-size:10px !important;line-height:18px !important}.wrapper .size-11{font-size:11px !important;line-height:19px !important}.wrapper .size-12{font-size:12px !important;line-height:19px !important}.wrapper .size-13{font-size:13px !important;line-height:21px !important}.wrapper .size-14{font-size:14px !important;line-height:21px !important}.wrapper .size-15{font-size:15px !important;line-height:23px !important}.wrapper .size-16{font-size:16px !important;line-height:24px !important}.wrapper .size-17{font-size:17px !important;line-height:26px \r\n" + 
					"!important}.wrapper .size-18{font-size:18px !important;line-height:26px !important}.wrapper .size-20{font-size:20px !important;line-height:28px !important}.wrapper .size-22{font-size:22px !important;line-height:31px !important}.wrapper .size-24{font-size:24px !important;line-height:32px !important}.wrapper .size-26{font-size:26px !important;line-height:34px !important}.wrapper .size-28{font-size:28px !important;line-height:36px !important}.wrapper .size-30{font-size:30px !important;line-height:38px !important}.wrapper .size-32{font-size:32px !important;line-height:40px !important}.wrapper .size-34{font-size:34px !important;line-height:43px !important}.wrapper .size-36{font-size:36px !important;line-height:43px !important}.wrapper .size-40{font-size:40px !important;line-height:47px !important}.wrapper .size-44{font-size:44px !important;line-height:50px !important}.wrapper \r\n" + 
					".size-48{font-size:48px !important;line-height:54px !important}.wrapper .size-56{font-size:56px !important;line-height:60px !important}.wrapper .size-64{font-size:64px !important;line-height:63px !important}}\r\n" + 
					"</STYLE>\r\n" + 
					"     \r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"body {\r\n" + 
					"  margin: 0;\r\n" + 
					"  padding: 0;\r\n" + 
					"}\r\n" + 
					"table {\r\n" + 
					"  border-collapse: collapse;\r\n" + 
					"  table-layout: fixed;\r\n" + 
					"}\r\n" + 
					"* {\r\n" + 
					"  line-height: inherit;\r\n" + 
					"}\r\n" + 
					"[x-apple-data-detectors],\r\n" + 
					"[href^=\"tel\"],\r\n" + 
					"[href^=\"sms\"] {\r\n" + 
					"  color: inherit !important;\r\n" + 
					"  text-decoration: none !important;\r\n" + 
					"}\r\n" + 
					".wrapper .footer__share-button a:hover,\r\n" + 
					".wrapper .footer__share-button a:focus {\r\n" + 
					"  color: #ffffff !important;\r\n" + 
					"}\r\n" + 
					".btn a:hover,\r\n" + 
					".btn a:focus,\r\n" + 
					".footer__share-button a:hover,\r\n" + 
					".footer__share-button a:focus,\r\n" + 
					".email-footer__links a:hover,\r\n" + 
					".email-footer__links a:focus {\r\n" + 
					"  opacity: 0.8;\r\n" + 
					"}\r\n" + 
					".preheader,\r\n" + 
					".header,\r\n" + 
					".layout,\r\n" + 
					".column {\r\n" + 
					"  transition: width 0.25s ease-in-out, max-width 0.25s ease-in-out;\r\n" + 
					"}\r\n" + 
					".preheader td {\r\n" + 
					"  padding-bottom: 8px;\r\n" + 
					"}\r\n" + 
					".layout,\r\n" + 
					"div.header {\r\n" + 
					"  max-width: 400px !important;\r\n" + 
					"  -fallback-width: 95% !important;\r\n" + 
					"  width: calc(100% - 20px) !important;\r\n" + 
					"}\r\n" + 
					"div.preheader {\r\n" + 
					"  max-width: 360px !important;\r\n" + 
					"  -fallback-width: 90% !important;\r\n" + 
					"  width: calc(100% - 60px) !important;\r\n" + 
					"}\r\n" + 
					".snippet,\r\n" + 
					".webversion {\r\n" + 
					"  Float: none !important;\r\n" + 
					"}\r\n" + 
					".column {\r\n" + 
					"  max-width: 400px !important;\r\n" + 
					"  width: 100% !important;\r\n" + 
					"}\r\n" + 
					".fixed-width.has-border {\r\n" + 
					"  max-width: 402px !important;\r\n" + 
					"}\r\n" + 
					".fixed-width.has-border .layout__inner {\r\n" + 
					"  box-sizing: border-box;\r\n" + 
					"}\r\n" + 
					".snippet,\r\n" + 
					".webversion {\r\n" + 
					"  width: 50% !important;\r\n" + 
					"}\r\n" + 
					".ie .btn {\r\n" + 
					"  width: 100%;\r\n" + 
					"}\r\n" + 
					"[owa] .column div,\r\n" + 
					"[owa] .column button {\r\n" + 
					"  display: block !important;\r\n" + 
					"}\r\n" + 
					"[owa] .wrapper > tbody > tr > td {\r\n" + 
					"  overflow-x: auto;\r\n" + 
					"  overflow-y: hidden;\r\n" + 
					"}\r\n" + 
					"[owa] .wrapper > tbody > tr > td > div {\r\n" + 
					"  min-width: 600px;\r\n" + 
					"}\r\n" + 
					".ie .column,\r\n" + 
					"[owa] .column,\r\n" + 
					".ie .gutter,\r\n" + 
					"[owa] .gutter {\r\n" + 
					"  display: table-cell;\r\n" + 
					"  float: none !important;\r\n" + 
					"  vertical-align: top;\r\n" + 
					"}\r\n" + 
					".ie div.preheader,\r\n" + 
					"[owa] div.preheader,\r\n" + 
					".ie .email-footer,\r\n" + 
					"[owa] .email-footer {\r\n" + 
					"  max-width: 560px !important;\r\n" + 
					"  width: 560px !important;\r\n" + 
					"}\r\n" + 
					".ie .snippet,\r\n" + 
					"[owa] .snippet,\r\n" + 
					".ie .webversion,\r\n" + 
					"[owa] .webversion {\r\n" + 
					"  width: 280px !important;\r\n" + 
					"}\r\n" + 
					".ie div.header,\r\n" + 
					"[owa] div.header,\r\n" + 
					".ie .layout,\r\n" + 
					"[owa] .layout,\r\n" + 
					".ie .one-col .column,\r\n" + 
					"[owa] .one-col .column {\r\n" + 
					"  max-width: 600px !important;\r\n" + 
					"  width: 600px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col .column,\r\n" + 
					"[owa] .two-col .column {\r\n" + 
					"  max-width: 300px !important;\r\n" + 
					"  width: 300px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col .column,\r\n" + 
					"[owa] .three-col .column,\r\n" + 
					".ie .narrow,\r\n" + 
					"[owa] .narrow {\r\n" + 
					"  max-width: 200px !important;\r\n" + 
					"  width: 200px !important;\r\n" + 
					"}\r\n" + 
					".ie .wide,\r\n" + 
					"[owa] .wide {\r\n" + 
					"  width: 400px !important;\r\n" + 
					"}\r\n" + 
					".ie .fixed-width.has-border,\r\n" + 
					"[owa] .fixed-width.x_has-border,\r\n" + 
					".ie .has-gutter.has-border,\r\n" + 
					"[owa] .has-gutter.x_has-border {\r\n" + 
					"  max-width: 602px !important;\r\n" + 
					"  width: 602px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col.has-gutter .column,\r\n" + 
					"[owa] .two-col.x_has-gutter .column {\r\n" + 
					"  max-width: 290px !important;\r\n" + 
					"  width: 290px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col.has-gutter .column,\r\n" + 
					"[owa] .three-col.x_has-gutter .column,\r\n" + 
					".ie .has-gutter .narrow,\r\n" + 
					"[owa] .has-gutter .narrow {\r\n" + 
					"  max-width: 188px !important;\r\n" + 
					"  width: 188px !important;\r\n" + 
					"}\r\n" + 
					".ie .has-gutter .wide,\r\n" + 
					"[owa] .has-gutter .wide {\r\n" + 
					"  max-width: 394px !important;\r\n" + 
					"  width: 394px !important;\r\n" + 
					"}\r\n" + 
					".ie .two-col.has-gutter.has-border .column,\r\n" + 
					"[owa] .two-col.x_has-gutter.x_has-border .column {\r\n" + 
					"  max-width: 292px !important;\r\n" + 
					"  width: 292px !important;\r\n" + 
					"}\r\n" + 
					".ie .three-col.has-gutter.has-border .column,\r\n" + 
					"[owa] .three-col.x_has-gutter.x_has-border .column,\r\n" + 
					".ie .has-gutter.has-border .narrow,\r\n" + 
					"[owa] .has-gutter.x_has-border .narrow {\r\n" + 
					"  max-width: 190px !important;\r\n" + 
					"  width: 190px !important;\r\n" + 
					"}\r\n" + 
					".ie .has-gutter.has-border .wide,\r\n" + 
					"[owa] .has-gutter.x_has-border .wide {\r\n" + 
					"  max-width: 396px !important;\r\n" + 
					"  width: 396px !important;\r\n" + 
					"}\r\n" + 
					".ie .fixed-width .layout__inner {\r\n" + 
					"  border-left: 0 none white !important;\r\n" + 
					"  border-right: 0 none white !important;\r\n" + 
					"}\r\n" + 
					".ie .layout__edges {\r\n" + 
					"  display: none;\r\n" + 
					"}\r\n" + 
					".mso .layout__edges {\r\n" + 
					"  font-size: 0;\r\n" + 
					"}\r\n" + 
					".layout-fixed-width,\r\n" + 
					".mso .layout-full-width {\r\n" + 
					"  background-color: #ffffff;\r\n" + 
					"}\r\n" + 
					"@media only screen and (min-width: 620px) {\r\n" + 
					"  .column,\r\n" + 
					"  .gutter {\r\n" + 
					"    display: table-cell;\r\n" + 
					"    Float: none !important;\r\n" + 
					"    vertical-align: top;\r\n" + 
					"  }\r\n" + 
					"  div.preheader,\r\n" + 
					"  .email-footer {\r\n" + 
					"    max-width: 560px !important;\r\n" + 
					"    width: 560px !important;\r\n" + 
					"  }\r\n" + 
					"  .snippet,\r\n" + 
					"  .webversion {\r\n" + 
					"    width: 280px !important;\r\n" + 
					"  }\r\n" + 
					"  div.header,\r\n" + 
					"  .layout,\r\n" + 
					"  .one-col .column {\r\n" + 
					"    max-width: 600px !important;\r\n" + 
					"    width: 600px !important;\r\n" + 
					"  }\r\n" + 
					"  .fixed-width.has-border,\r\n" + 
					"  .fixed-width.ecxhas-border,\r\n" + 
					"  .has-gutter.has-border,\r\n" + 
					"  .has-gutter.ecxhas-border {\r\n" + 
					"    max-width: 602px !important;\r\n" + 
					"    width: 602px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col .column {\r\n" + 
					"    max-width: 300px !important;\r\n" + 
					"    width: 300px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col .column,\r\n" + 
					"  .column.narrow {\r\n" + 
					"    max-width: 200px !important;\r\n" + 
					"    width: 200px !important;\r\n" + 
					"  }\r\n" + 
					"  .column.wide {\r\n" + 
					"    width: 400px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col.has-gutter .column,\r\n" + 
					"  .two-col.ecxhas-gutter .column {\r\n" + 
					"    max-width: 290px !important;\r\n" + 
					"    width: 290px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col.has-gutter .column,\r\n" + 
					"  .three-col.ecxhas-gutter .column,\r\n" + 
					"  .has-gutter .narrow {\r\n" + 
					"    max-width: 188px !important;\r\n" + 
					"    width: 188px !important;\r\n" + 
					"  }\r\n" + 
					"  .has-gutter .wide {\r\n" + 
					"    max-width: 394px !important;\r\n" + 
					"    width: 394px !important;\r\n" + 
					"  }\r\n" + 
					"  .two-col.has-gutter.has-border .column,\r\n" + 
					"  .two-col.ecxhas-gutter.ecxhas-border .column {\r\n" + 
					"    max-width: 292px !important;\r\n" + 
					"    width: 292px !important;\r\n" + 
					"  }\r\n" + 
					"  .three-col.has-gutter.has-border .column,\r\n" + 
					"  .three-col.ecxhas-gutter.ecxhas-border .column,\r\n" + 
					"  .has-gutter.has-border .narrow,\r\n" + 
					"  .has-gutter.ecxhas-border .narrow {\r\n" + 
					"    max-width: 190px !important;\r\n" + 
					"    width: 190px !important;\r\n" + 
					"  }\r\n" + 
					"  .has-gutter.has-border .wide,\r\n" + 
					"  .has-gutter.ecxhas-border .wide {\r\n" + 
					"    max-width: 396px !important;\r\n" + 
					"    width: 396px !important;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					"@media only screen and (-webkit-min-device-pixel-ratio: 2), only screen and (min--moz-device-pixel-ratio: 2), only screen and (-o-min-device-pixel-ratio: 2/1), only screen and (min-device-pixel-ratio: 2), only screen and (min-resolution: 192dpi), only screen and (min-resolution: 2dppx) {\r\n" + 
					"  .fblike {\r\n" + 
					"    background-image: url(http://i7.cmail19.com/static/eb/master/13-the-blueprint-3/images/fblike@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .tweet {\r\n" + 
					"    background-image: url(http://i8.cmail19.com/static/eb/master/13-the-blueprint-3/images/tweet@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .linkedinshare {\r\n" + 
					"    background-image: url(http://i9.cmail19.com/static/eb/master/13-the-blueprint-3/images/lishare@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"  .forwardtoafriend {\r\n" + 
					"    background-image: url(http://i10.cmail19.com/static/eb/master/13-the-blueprint-3/images/forward@2x.png) !important;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					"@media (max-width: 321px) {\r\n" + 
					"  .fixed-width.has-border .layout__inner {\r\n" + 
					"    border-width: 1px 0 !important;\r\n" + 
					"  }\r\n" + 
					"  .layout,\r\n" + 
					"  .column {\r\n" + 
					"    min-width: 320px !important;\r\n" + 
					"    width: 320px !important;\r\n" + 
					"  }\r\n" + 
					"  .border {\r\n" + 
					"    display: none;\r\n" + 
					"  }\r\n" + 
					"}\r\n" + 
					".mso div {\r\n" + 
					"  border: 0 none white !important;\r\n" + 
					"}\r\n" + 
					".mso .w560 .divider {\r\n" + 
					"  Margin-left: 260px !important;\r\n" + 
					"  Margin-right: 260px !important;\r\n" + 
					"}\r\n" + 
					".mso .w360 .divider {\r\n" + 
					"  Margin-left: 160px !important;\r\n" + 
					"  Margin-right: 160px !important;\r\n" + 
					"}\r\n" + 
					".mso .w260 .divider {\r\n" + 
					"  Margin-left: 110px !important;\r\n" + 
					"  Margin-right: 110px !important;\r\n" + 
					"}\r\n" + 
					".mso .w160 .divider {\r\n" + 
					"  Margin-left: 60px !important;\r\n" + 
					"  Margin-right: 60px !important;\r\n" + 
					"}\r\n" + 
					".mso .w354 .divider {\r\n" + 
					"  Margin-left: 157px !important;\r\n" + 
					"  Margin-right: 157px !important;\r\n" + 
					"}\r\n" + 
					".mso .w250 .divider {\r\n" + 
					"  Margin-left: 105px !important;\r\n" + 
					"  Margin-right: 105px !important;\r\n" + 
					"}\r\n" + 
					".mso .w148 .divider {\r\n" + 
					"  Margin-left: 54px !important;\r\n" + 
					"  Margin-right: 54px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-8,\r\n" + 
					".ie .size-8 {\r\n" + 
					"  font-size: 8px !important;\r\n" + 
					"  line-height: 14px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-9,\r\n" + 
					".ie .size-9 {\r\n" + 
					"  font-size: 9px !important;\r\n" + 
					"  line-height: 16px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-10,\r\n" + 
					".ie .size-10 {\r\n" + 
					"  font-size: 10px !important;\r\n" + 
					"  line-height: 18px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-11,\r\n" + 
					".ie .size-11 {\r\n" + 
					"  font-size: 11px !important;\r\n" + 
					"  line-height: 19px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-12,\r\n" + 
					".ie .size-12 {\r\n" + 
					"  font-size: 12px !important;\r\n" + 
					"  line-height: 19px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-13,\r\n" + 
					".ie .size-13 {\r\n" + 
					"  font-size: 13px !important;\r\n" + 
					"  line-height: 21px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-14,\r\n" + 
					".ie .size-14 {\r\n" + 
					"  font-size: 14px !important;\r\n" + 
					"  line-height: 21px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-15,\r\n" + 
					".ie .size-15 {\r\n" + 
					"  font-size: 15px !important;\r\n" + 
					"  line-height: 23px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-16,\r\n" + 
					".ie .size-16 {\r\n" + 
					"  font-size: 16px !important;\r\n" + 
					"  line-height: 24px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-17,\r\n" + 
					".ie .size-17 {\r\n" + 
					"  font-size: 17px !important;\r\n" + 
					"  line-height: 26px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-18,\r\n" + 
					".ie .size-18 {\r\n" + 
					"  font-size: 18px !important;\r\n" + 
					"  line-height: 26px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-20,\r\n" + 
					".ie .size-20 {\r\n" + 
					"  font-size: 20px !important;\r\n" + 
					"  line-height: 28px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-22,\r\n" + 
					".ie .size-22 {\r\n" + 
					"  font-size: 22px !important;\r\n" + 
					"  line-height: 31px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-24,\r\n" + 
					".ie .size-24 {\r\n" + 
					"  font-size: 24px !important;\r\n" + 
					"  line-height: 32px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-26,\r\n" + 
					".ie .size-26 {\r\n" + 
					"  font-size: 26px !important;\r\n" + 
					"  line-height: 34px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-28,\r\n" + 
					".ie .size-28 {\r\n" + 
					"  font-size: 28px !important;\r\n" + 
					"  line-height: 36px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-30,\r\n" + 
					".ie .size-30 {\r\n" + 
					"  font-size: 30px !important;\r\n" + 
					"  line-height: 38px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-32,\r\n" + 
					".ie .size-32 {\r\n" + 
					"  font-size: 32px !important;\r\n" + 
					"  line-height: 40px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-34,\r\n" + 
					".ie .size-34 {\r\n" + 
					"  font-size: 34px !important;\r\n" + 
					"  line-height: 43px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-36,\r\n" + 
					".ie .size-36 {\r\n" + 
					"  font-size: 36px !important;\r\n" + 
					"  line-height: 43px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-40,\r\n" + 
					".ie .size-40 {\r\n" + 
					"  font-size: 40px !important;\r\n" + 
					"  line-height: 47px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-44,\r\n" + 
					".ie .size-44 {\r\n" + 
					"  font-size: 44px !important;\r\n" + 
					"  line-height: 50px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-48,\r\n" + 
					".ie .size-48 {\r\n" + 
					"  font-size: 48px !important;\r\n" + 
					"  line-height: 54px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-56,\r\n" + 
					".ie .size-56 {\r\n" + 
					"  font-size: 56px !important;\r\n" + 
					"  line-height: 60px !important;\r\n" + 
					"}\r\n" + 
					".mso .size-64,\r\n" + 
					".ie .size-64 {\r\n" + 
					"  font-size: 64px !important;\r\n" + 
					"  line-height: 63px !important;\r\n" + 
					"}\r\n" + 
					"</STYLE>\r\n" + 
					"       <!--[if !mso]><!-->\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"@import url(https://fonts.googleapis.com/css?family=PT+Serif:400,700,400italic,700italic|Ubuntu:400,700,400italic,700italic);\r\n" + 
					"</STYLE>\r\n" + 
					"<LINK href=\"email_files/css.css\" rel=\"stylesheet\" type=\"text/css\"><!--<![endif]-->\r\n" + 
					"<STYLE type=\"text/css\">\r\n" + 
					"body{background-color:#ededf1}.logo a:hover,.logo a:focus{color:#859bb1 !important}.mso .layout-has-border{border-top:1px solid #b4b4c4;border-bottom:1px solid #b4b4c4}.mso .layout-has-bottom-border{border-bottom:1px solid #b4b4c4}.mso .border,.ie .border{background-color:#b4b4c4}.mso h1,.ie h1{}.mso h1,.ie h1{font-size:26px !important;line-height:34px !important}.mso h2,.ie h2{}.mso h3,.ie h3{}.mso .layout__inner,.ie .layout__inner{}.mso .footer__share-button p{}.mso .footer__share-button p{font-family:Ubuntu,sans-serif}\r\n" + 
					"</STYLE>\r\n" + 
					"\r\n" + 
					"<META name=\"GENERATOR\" content=\"MSHTML 11.00.10570.1001\"></HEAD> <!--[if mso]>\r\n" + 
					"  <body class=\"mso\">\r\n" + 
					"<![endif]--> <!--[if !mso]><!--> \r\n" + 
					"  \r\n" + 
					"<BODY class=\"full-padding\" style=\"margin: 0px; padding: 0px; -webkit-text-size-adjust: 100%;\"><!--<![endif]--> \r\n" + 
					"    \r\n" + 
					"<TABLE class=\"wrapper\" role=\"presentation\" style=\"width: 100%; border-collapse: collapse; table-layout: fixed; min-width: 320px; background-color: rgb(237, 237, 241);\" \r\n" + 
					"cellspacing=\"0\" cellpadding=\"0\">\r\n" + 
					"  <TBODY>\r\n" + 
					"  <TR>\r\n" + 
					"    <TD>\r\n" + 
					"      <DIV role=\"banner\">\r\n" + 
					"      <DIV class=\"preheader\" style=\"margin: 0px auto; width: calc(28000% - 167440px); min-width: 280px; max-width: 560px;\">\r\n" + 
					"      <DIV style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" class=\"preheader\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 280px\" valign=\"top\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"snippet\" style=\"padding: 10px 0px 5px; width: calc(14000% - 78120px); color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; display: table-cell; min-width: 140px; max-width: 280px;\"></DIV><!--[if (mso)|(IE)]></td><td style=\"width: 280px\" valign=\"top\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"webversion\" style=\"padding: 10px 0px 5px; width: calc(14100% - 78680px); text-align: right; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; display: table-cell; min-width: 139px; max-width: 280px;\"></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV>\r\n" + 
					"      <DIV class=\"header\" id=\"emb-email-header-container\" style=\"margin: 0px auto; width: calc(28000% - 167400px); min-width: 320px; max-width: 600px;\"><!--[if (mso)|(IE)]><table align=\"center\" class=\"header\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr><td style=\"width: 600px\"><![endif]--> \r\n" + 
					"                \r\n" + 
					"      <DIV align=\"center\" class=\"logo emb-logo-margin-box\" style=\"margin: 6px 20px 20px; color: rgb(195, 206, 217); line-height: 32px; font-family: Roboto,Tahoma,sans-serif; font-size: 26px;\">\r\n" + 
					"      <DIV align=\"center\" class=\"logo-center\" id=\"emb-email-header\"><IMG width=\"261\" \r\n" + 
					"      style=\"border: 0px currentColor; border-image: none; width: 100%; height: auto; display: block; max-width: 261px;\" \r\n" + 
					"      alt=\"\" src=\"http://www.westerntape.com/WT_images/logo-smi-new.png\"></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"              </DIV></DIV>\r\n" + 
					"      <DIV>\r\n" + 
					"      <DIV class=\"layout one-col fixed-width\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse; background-color: rgb(255, 255, 255);\" \r\n" + 
					"      emb-background-style=\"\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-fixed-width\" emb-background-style><td style=\"width: 600px\" class=\"w560\"><![endif]--> \r\n" + 
					"                \r\n" + 
					"      <DIV class=\"column\" style=\"width: calc(28000% - 167400px); text-align: left; color: rgb(124, 126, 127); line-height: 21px; font-family: PT Serif,Georgia,serif; font-size: 14px; min-width: 320px; max-width: 600px;\">\r\n" + 
					"      <DIV style=\"margin-top: 24px; margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 10px; font-size: 1px; mso-line-height-rule: exactly;\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <H3 style=\"text-align: center; color: rgb(120, 137, 145); line-height: 24px; font-size: 16px; font-style: normal; font-weight: normal; margin-top: 0px; margin-bottom: 0px;\">\r\n" + 
					"      MESSAGE</H3>\r\n" + 
					"      <H1 style=\"text-align: left; color: rgb(62, 71, 81); line-height: 31px; font-family: Ubuntu,sans-serif; font-size: 22px; font-style: normal; font-weight: normal; margin-top: 12px; margin-bottom: 0px;\">Dear \r\n" + 
					"      " + sender + ",</H1>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 20px;\">New activity was added to your Quote \r\n" + 
					"      .</P></DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV class=\"divider\" style=\"width: 40px; line-height: 2px; font-size: 2px; margin-right: auto; margin-bottom: 20px; margin-left: auto; display: block; background-color: rgb(180, 180, 196);\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 5px; font-size: 1px; mso-line-height-rule: exactly;\">&nbsp;</DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <H2 style=\"color: rgb(62, 71, 81); line-height: 24px; font-family: Ubuntu,sans-serif; font-size: 16px; font-style: normal; font-weight: normal; margin-top: 0px; margin-bottom: 0px;\"><STRONG>Quote \r\n" + 
					"      Information</STRONG></H2>\r\n" + 
					"      <P style=\"margin-top: 16px; margin-bottom: 0px;\"><STRONG>RFQ </STRONG>: \r\n" + 
					"      " + q.getNroRfq() + "</P>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 0px;\"><STRONG>Date:&nbsp; \r\n" + 
					"      " + formatter.format(q.getCreatedDate()) + "</STRONG><BR>Contact Name :&nbsp;<STRONG><STRONG>\r\n" + 
					"      " + q.getCustomer().getContactName() + " -- Phone: " + q.getCustomer().getContactPhone() + " &nbsp;</STRONG></STRONG><BR>Email \r\n" + 
					"      :&nbsp;<STRONG><A style=\"transition:opacity 0.1s ease-in; color: rgb(78, 170, 204); text-decoration: underline;\" \r\n" + 
					"      href=\"mailto: " + q.getCustomer().getContactEmail() + " \">" + q.getCustomer().getContactEmail() + "</A>&nbsp;</STRONG><BR>\r\n" + 
					"      Observations :&nbsp;<STRONG> " + q.getQuoteNotes() + "</STRONG></P>\r\n" + 
					"      <P style=\"margin-top: 20px; margin-bottom: 20px;\"> " + message + "\r\n" + 
					"       \r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-left: 20px;\">\r\n" + 
					"      <DIV class=\"btn btn--shadow btn--large\" style=\"text-align: center; margin-bottom: 20px;\"><!--[if !mso]--><A \r\n" + 
					"      style=\"padding: 12px 24px 13px; border-radius: 4px; transition:opacity 0.1s ease-in; text-align: center; color: rgb(255, 255, 255) !important; line-height: 24px; font-family: PT Serif, Georgia, serif; font-size: 14px; font-weight: bold; text-decoration: none !important; display: inline-block; box-shadow: inset 0px -2px 0px 0px rgba(0,0,0,0.2); background-color: rgb(78, 170, 204);\" \r\n" + 
					"      href=\"http://quoting.westerntape.com\">GO TO \r\n" + 
					"PIPELINE</A><!--[endif]-->       <!--[if mso]><p style=\"line-height:0;margin:0;\">&nbsp;</p><v:roundrect xmlns:v=\"urn:schemas-microsoft-com:vml\" href=\"http://quoting.westerntape.com\" style=\"width:160px\" arcsize=\"9%\" fillcolor=\"#4EAACC\" stroke=\"f\"><v:shadow on=\"t\" color=\"#3E88A3\" offset=\"0,2px\"></v:shadow><v:textbox style=\"mso-fit-shape-to-text:t\" inset=\"0px,11px,0px,10px\"><center style=\"font-size:14px;line-height:24px;color:#FFFFFF;font-family:PT Serif,Georgia,serif;font-weight:bold;mso-line-height-rule:exactly;mso-text-raise:4px\">GO TO PIPELINE</center></v:textbox></v:roundrect><![endif]--></DIV></DIV>\r\n" + 
					"      <DIV style=\"margin-right: 20px; margin-bottom: 24px; margin-left: 20px;\">\r\n" + 
					"      <DIV style=\"mso-line-height-rule: exactly; mso-text-raise: 4px;\">\r\n" + 
					"      <P style=\"margin-top: 0px; margin-bottom: 0px;\">Kind regards,<BR>Seal \r\n" + 
					"      Methods Planners Team</P></DIV></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"              </DIV></DIV>\r\n" + 
					"      <DIV style=\"line-height: 20px; font-size: 20px;\">&nbsp;</DIV>\r\n" + 
					"      <DIV role=\"contentinfo\">\r\n" + 
					"      <DIV class=\"layout email-footer\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 400px;\" valign=\"top\" class=\"w360\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column wide\" style=\"width: calc(8000% - 47600px); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; min-width: 320px; max-width: 400px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px;\">\r\n" + 
					"      <DIV>Seal Methods Inc. USA<BR>11915 Shoemaker Ave Santa Fe Springs, CA \r\n" + 
					"      90670<BR>Phone:&nbsp;800.423.4777&nbsp;&nbsp; &nbsp; \r\n" + 
					"      Fax:&nbsp;562.946.9439&nbsp; &nbsp; \r\n" + 
					"      Email:&nbsp;sales@sealmethodsinc.com</DIV></DIV>\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px; margin-top: 18px;\"></DIV><!--[if mso]>&nbsp;<![endif]--> \r\n" + 
					"                    </DIV></DIV><!--[if (mso)|(IE)]></td><td style=\"width: 200px;\" valign=\"top\" class=\"w160\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column narrow\" style=\"width: calc(72200px - 12000%); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; float: left; min-width: 200px; max-width: 320px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\"></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV>\r\n" + 
					"      <DIV class=\"layout one-col email-footer\" style=\"margin: 0px auto; width: calc(28000% - 167400px); -ms-word-wrap: break-word; min-width: 320px; max-width: 600px; overflow-wrap: break-word;\">\r\n" + 
					"      <DIV class=\"layout__inner\" style=\"width: 100%; display: table; border-collapse: collapse;\"><!--[if (mso)|(IE)]><table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" role=\"presentation\"><tr class=\"layout-email-footer\"><td style=\"width: 600px;\" class=\"w560\"><![endif]--> \r\n" + 
					"                  \r\n" + 
					"      <DIV class=\"column\" style=\"width: calc(28000% - 167400px); text-align: left; color: rgb(124, 126, 127); line-height: 19px; font-family: Ubuntu,sans-serif; font-size: 12px; min-width: 320px; max-width: 600px;\">\r\n" + 
					"      <DIV style=\"margin: 10px 20px;\">\r\n" + 
					"      <DIV style=\"line-height: 19px; font-size: 12px;\"><A style=\"transition:opacity 0.1s ease-in; color: rgb(124, 126, 127); text-decoration: underline;\" \r\n" + 
					"      href=\"\"></A>   \r\n" + 
					"                    </DIV></DIV></DIV><!--[if (mso)|(IE)]></td></tr></table><![endif]--> \r\n" + 
					"                </DIV></DIV></DIV>\r\n" + 
					"      <DIV \r\n" + 
					"  style=\"line-height: 40px; font-size: 40px;\">&nbsp;</DIV></DIV></TD></TR></TBODY></TABLE><IMG \r\n" + 
					"width=\"1\" height=\"1\" style=\"margin: 0px !important; padding: 0px !important; border: 0px currentColor !important; border-image: none !important; width: 1px !important; height: 1px !important; overflow: hidden; display: block !important; visibility: hidden !important; position: fixed;\" \r\n" + 
					"alt=\"\" src=\"email_files/o.gif\" border=\"0\"></BODY></HTML>\r\n" + 
					"" ;
			

			System.out.println("SENDING EMAIL TO....." +  q.getEstimator().getEmailContact());
			
			if (role.equals("2"))
				emailService.sendHTMlEmail(q.getEstimator().getEmailContact(), "New Message , Rfq No : " + q.getNroRfq(), textEmail);
    		
			if (role.equals("3"))
				emailService.sendHTMlEmail(q.getSeller().getEmailContact(), "New Message , Rfq No : " + q.getNroRfq(), textEmail);
			
			
    		System.out.println("EMAIl sent to :");
    		
    		
		   }
		catch (Exception e) {
			System.out.println("Error : sending email.." +  q.getEstimator().getEmailContact());
		}
            


		return new ResponseEntity<String>("0", HttpStatus.OK);
		
		

	}		
	
	
	

}
