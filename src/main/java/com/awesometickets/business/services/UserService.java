package com.awesometickets.business.services;

import com.awesometickets.business.entities.User;
import com.awesometickets.business.entities.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public void createUserWithPhoneNum(String phoneNum) {
        if (!isPhoneNumExist(phoneNum)) {
            User user = new User();
            user.setPhoneNum(phoneNum);
            user.setRemainPurchase(4);
            userRepo.save(user);
        }
    }

    public boolean isPhoneNumExist(String phoneNum) {
        return userRepo.findByPhoneNum(phoneNum).size() > 0;
    }
}
