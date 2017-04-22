package com.awesome_tickets.business.entities.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.awesome_tickets.business.entities.Cinema;


@Repository
public interface CinemaRepository extends CrudRepository<Cinema, Integer> {
}
