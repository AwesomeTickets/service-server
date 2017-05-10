package com.awesometickets.business.services;

import com.awesometickets.business.entities.User;
import com.awesometickets.business.entities.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    /**
     * Create a user with his phone number, do nothing if the phone number exists.
     *
     * @param phoneNum The phone number to save a new user
     */
    public void createUserWithPhoneNum(String phoneNum) {
        if (!isPhoneNumExist(phoneNum)) {
            User user = new User();
            user.setPhoneNum(phoneNum);
            // The default purchase count is four.
            user.setRemainPurchase(4);
            userRepo.save(user);
        }
    }

    /**
     * Check if a phone number exists.
     *
     * @param phoneNum The phone number to check
     */
    public boolean isPhoneNumExist(String phoneNum) {
        return userRepo.findByPhoneNum(phoneNum).size() > 0;
    }

    /**
     * Get a user using the phone number
     *
     * @param phoneNum The phone number
     */
    public User getUserByPhoneNum(String phoneNum) {
        List<User> users = userRepo.findByPhoneNum(phoneNum);
        if (users.size() == 0) return null;
        else return users.get(0);
    }
}
