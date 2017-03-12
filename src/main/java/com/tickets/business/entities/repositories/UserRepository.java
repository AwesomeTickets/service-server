package com.tickets.business.entities.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.tickets.business.entities.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
    List<User> findByUsername(String username);
    
}
