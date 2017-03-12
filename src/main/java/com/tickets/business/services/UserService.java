package com.tickets.business.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tickets.business.entities.User;
import com.tickets.business.entities.repositories.UserRepository;


@Service
public class UserService {
    
	@Autowired
    private UserRepository userRepo; 
    
    public UserService() {
        super();
    }
    
    public void create(User user) {
        userRepo.save(user);
    }
    
    public boolean hasUsername(String username) {
    	return !userRepo.findByUsername(username).isEmpty();
    }

    public boolean permitLogin(String username, String password) {
    	return userRepo.findByUsername(username).get(0).getPassword().equals(password);
    }
    
}
