package com.controller;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.model.Estimator;
import com.model.Item;
import com.model.ItemStatus;
import com.model.Seller;
import com.model.Users;
import com.repository.EstimatorRep;
import com.repository.SellerRep;
import com.repository.UserRep;
import com.service.UserService;





@Controller
public class UsersController {
	
	@Autowired
	private UserRep dUserRep;
	
	@Autowired
	private EstimatorRep estimatorRep;
	
	@Autowired
	private SellerRep sellerRep;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("users/list")
	@ResponseBody
	public List<Users> listusers(){
	
		List<Users> users = (List<Users>) dUserRep.findAll();
		return users;
		
	}
	
	@RequestMapping("findProfileUser")
	@ResponseBody
	public Users findOnebyuser(String username){
		
		Users user = dUserRep.findByUsername(username);
		System.out.println("USER ACTUAL : " + user.getLastName());
		return user;

	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "/uploadAvatar")
	public void upload(@RequestParam("file") MultipartFile file, @RequestParam("username") String username ) throws IOException {

		byte[] bytes;
		
		 Resource resource = new ClassPathResource("/application.properties");
		 Properties pro = PropertiesLoaderUtils.loadProperties(resource);

		if (!file.isEmpty()) {
			bytes = file.getBytes();

			String currentDir = System.getProperty("user.dir");
			String fileName = file.getOriginalFilename();
			Users user = dUserRep.findByUsername(username);
			System.out.println("PATH AVATAR : " + currentDir);
			String dirImg = pro.getProperty("dir.images");
			//java.nio.file.Path path = Paths.get(currentDir+"/src/main/resources/static/images/" + fileName);
			java.nio.file.Path path = Paths.get(currentDir+ dirImg + fileName);
			Files.deleteIfExists(path);
			InputStream in = file.getInputStream();
			Files.copy(in, path);
			
			 
			 System.out.println("PROPERTIES PATH : " + currentDir+dirImg);
			
			
			if (user.getUserType().equals("2")) {
				
				Seller sel = sellerRep.findOneByUser(user);
				sel.setImgUrl(fileName);
			}
			
			if (user.getUserType().equals("3")) {
				
				Estimator es = estimatorRep.findOneByUser(user);
				es.setImgUrl(fileName);
			}
		
            
			user.setImageUrl(fileName);
			dUserRep.save(user);
			

			
		}


	}	
	
	
	@RequestMapping(value = "updateProfile", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Users> updateItem(@RequestParam("changeP") String changeP, @RequestBody Users us) {	
		
		dUserRep.save(us);	
		System.out.println("CHANGE P" +  changeP);
		
		if (us.getUserType().equals("2")) {
			
			Seller sel = sellerRep.findOneByUser(us);
			sel.setEmailContact(us.getEmail());
			sel.setPhoneContact(us.getMobilPhone());
		
		}
		
		if (us.getUserType().equals("3")) {
			
			Estimator es = estimatorRep.findOneByUser(us);
			es.setEmailContact(us.getEmail());
			es.setPhoneContact(us.getMobilPhone());
			
		}
		
		if (changeP.equals("1")) {
			userService.changePassword(us);
		}
		
		return new ResponseEntity<Users>(us, HttpStatus.OK);
	}	
	

}
