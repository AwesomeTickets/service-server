package com.tickets.business.entities.repositories;

import com.tickets.business.entities.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by CrazeWong on 2017/3/29.
 */
@Repository
public interface CountryRepository extends CrudRepository<Country, Long> {
    // TODO CountryRepository
}
