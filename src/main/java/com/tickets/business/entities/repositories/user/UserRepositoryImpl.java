package com.tickets.business.entities.repositories.user;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by CrazeWong on 2017/3/28.
 */
public class UserRepositoryImpl implements UserDao {

    @PersistenceContext
    private EntityManager em;

//    public void testMethod() {
//        // Nothing.
//    }
}
