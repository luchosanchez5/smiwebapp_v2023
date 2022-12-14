package com.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.model.Role;
import com.model.Status;
import com.model.UserRole;
import com.model.Users;
import com.repository.RoleRep;
import com.repository.UserRep;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {
	
	@Autowired
	private UserRep userRepository;
	@Autowired
    private RoleRep roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Override
	public Users findUserByEmail(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public Users findUserByusername(String user) {
		return userRepository.findByUsername(user);
	}
	
	@Override
	public void saveUser(Users user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //user.setActive(1);
		UserRole userRole = new UserRole();
        Role role = roleRepository.findByDescription("Administrator");
        
        userRole.setUser(user);
        userRole.setRole(role);
        userRole.setCreateDate(LocalDateTime.now());
        userRole.setStatus(Status.CREATED);
        
        
        
        user.setRoles(new HashSet<UserRole>(Arrays.asList(userRole)));
		userRepository.save(user);
	}
	
	@Override
	public void changePassword(Users user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		userRepository.save(user);
	}
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		System.out.println("============================");
		System.out.println("ACTUAL USER : " + userName);
		System.out.println("============================");

		Users user = userRepository.findByUsername(userName);
		System.out.println("USER PASSWORD : " + user.getPassword());
		System.out.println("USER ROLES : " + user.getRoles().toString());
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		return buildUserForAuthentication(user, authorities);
	}

	private List<GrantedAuthority> getUserAuthority(Set<UserRole> set) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (UserRole role : set) {
			roles.add(new SimpleGrantedAuthority(role.getRole().getDescription()));
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(Users user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
	}
	
	

}
