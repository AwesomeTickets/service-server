package com.tickets.business.entities.repositories;

import com.tickets.business.entities.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by CrazeWong on 2017/3/29.
 */
@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {
    // TODO CountryRepository
    List<Country> findByName(String name);
}
