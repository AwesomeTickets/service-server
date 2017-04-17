package com.tickets.business.entities.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tickets.business.entities.Cinema;


@Repository
public interface CinemaRepository extends CrudRepository<Cinema, Integer> {
}
