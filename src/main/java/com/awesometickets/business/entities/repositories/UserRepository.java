package com.awesometickets.business.entities.repositories;

import com.awesometickets.business.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

    @Query("select u.userId from User u where u.phoneNum= ?1")
    List<Integer> findByPhoneNum(String phoneNum);
}
