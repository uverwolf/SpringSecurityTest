package com.uverwolf.auth.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.uverwolf.auth.models.User;
import com.uverwolf.auth.repositories.RoleRepository;
import com.uverwolf.auth.repositories.UserRepository;

@Service
public class UserService {
	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;
	
	public void saveWithUserRole(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(roleRepo.findByName("ROLE_USER"));
		userRepo.save(user);
	}
    // 2 
   public void saveUserWithAdminRole(User user) {
       user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
       user.setRoles(roleRepo.findByName("ROLE_ADMIN"));
       userRepo.save(user);
   }    
   
   // 3
   public User findByUsername(String username) {
       return userRepo.findByUsername(username);
   }
   public List<User> todos(){
	   return userRepo.findAll();
   }
}
