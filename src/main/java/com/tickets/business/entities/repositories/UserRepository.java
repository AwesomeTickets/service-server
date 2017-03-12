package com.tickets.business.entities.repositories;

import org.springframework.stereotype.Repository;

import com.tickets.business.entities.User;


@Repository
public class UserRepository extends AbstractJpaDao<User> {
	
//    private final List<SeedStarter> seedStarters = new ArrayList<SeedStarter>();
    
    
    
    public UserRepository() {
        super();
        setClazz(User.class);
    }
    
    
    
//    public List<SeedStarter> findAll() {
//        return new ArrayList<SeedStarter>(this.seedStarters);
//    }
//
//    
//    public void add(final SeedStarter seedStarter) {
//        this.seedStarters.add(seedStarter);
//    }
    
    
    
}
