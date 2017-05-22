package com.awesometickets.business.services;

import com.awesometickets.business.entities.User;
import com.awesometickets.business.entities.repositories.UserRepository;
import com.awesometickets.util.MD5Parser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    /**
     * Create a user, do nothing if the phone number exists.
     * @param phoneNum the user phone number
     * @param password raw password, will be dealt with MD5
     */
    public User registerUserWithRawPassword(String phoneNum, String password) {
        String md5pw = MD5Parser.getMD5(password);
        User user = null;
        if (!isPhoneNumExist(phoneNum)) {
            user = new User();
            user.setPhoneNum(phoneNum);
            user.setPassword(md5pw);
            user.setRemainPurchase(99999);
            userRepo.save(user);
        }
        return user;
    }

    /**
     * Get a user, with password which has been dealt with MD5.
     * @param phoneNum
     * @param password
     * @return
     */
    public User getUserWithMD5Password(String phoneNum, String password) {
        return userRepo.findByPhoneNumAndPassword(phoneNum, password);
    }

    /**
     * Create a user with his phone number, do nothing if the phone number exists.
     *
     * @param phoneNum The phone number to save a new user
     */
    public void createUserWithPhoneNum(String phoneNum) {
        if (!isPhoneNumExist(phoneNum)) {
            User user = new User();
            user.setPhoneNum(phoneNum);
            user.setPassword("");
            user.setRemainPurchase(99999);
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
     * Get a user's id using the phone number
     *
     * @param phoneNum The phone number
     */
    public User getUserByPhoneNum(String phoneNum) {
        List<User> users = userRepo.findByPhoneNum(phoneNum);
        if (users.size() == 0) return null;
        else return users.get(0);
    }
}
