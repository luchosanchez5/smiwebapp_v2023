package com.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.model.Referral;
import com.repository.ReferralRep;

@Controller
public class ReferralController {
	
	@Autowired
	private ReferralRep referralRep;
	
	@RequestMapping("list-referrals")
	@ResponseBody
	private List<Referral> findAllReferralByStatRed(){
		
		List<Referral> listRe = referralRep.findAllByStatReg("0");
		return listRe;
		
	}
	

}
