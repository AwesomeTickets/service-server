package com.tickets.business.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tickets.business.entities.User;
import com.tickets.business.entities.repositories.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository seedstarterRepository; 
    
    
    public UserService() {
        super();
    }
    
    
    
    public List<User> findAll() {
        return this.seedstarterRepository.findAll();
    }

    public void add(final User seedStarter) {
        this.seedstarterRepository.add(seedStarter);
    }
    
}
