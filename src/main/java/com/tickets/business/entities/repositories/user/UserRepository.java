package com.tickets.business.entities.repositories.user;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tickets.business.entities.User;


@Repository
public interface UserRepository extends CrudRepository<User, Long>, UserDao {
	
    List<User> findByUsername(String username);
    
}
