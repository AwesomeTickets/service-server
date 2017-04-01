package com.tickets.business.entities.repositories;

import com.tickets.business.entities.Country;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CountryRepository extends CrudRepository<Country, Integer> {

    List<Country> findByName(String name);

}
