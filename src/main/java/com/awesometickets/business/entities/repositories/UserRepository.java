package com.awesometickets.business.entities.repositories;

import com.awesometickets.business.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    List<User> findByPhoneNum(String phoneNum);
}
