package com.awesometickets.business.services;

import com.awesometickets.business.entities.User;
import com.awesometickets.business.entities.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.List;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    /**
     * Insert a user to database.
     *
     * @param phoneNum The phone number of the user
     * @param password The password
     * @return The user inserted
     */
    public User addUser(String phoneNum, String password) {
        User user = new User();
        user.setPhoneNum(phoneNum);
        user.setPassword(digest(password));
        user.setRemainPurchase(99999);
        userRepo.save(user);
        return user;
    }

    /**
     * Check if a user exists by its phone number.
     *
     * @param phoneNum The phone number
     */
    public boolean hasUser(String phoneNum) {
        return findUser(phoneNum) != null;
    }

    /**
     * Find a user by its phone number.
     *
     * @param phoneNum The phone number
     * @return The user object, or null if user does not exist
     */
    public User findUser(String phoneNum) {
        List<User> users = userRepo.findByPhoneNum(phoneNum);
        return users.isEmpty() ? null : users.get(0);
    }

    /**
     * Check if password matches.
     *
     * @param password The password to be checked
     * @param correctPassword The correct password
     * @return True if password matches
     */
    public boolean checkPassword(String password, String correctPassword) {
        return correctPassword.equals(digest(password));
    }

    /**
     * Generate the digest of a message.
     *
     * @param msg The message
     * @return The digest string with 32 characters
     */
    private String digest(String msg) {
        return DigestUtils.md5DigestAsHex(msg.getBytes());
    }
}
