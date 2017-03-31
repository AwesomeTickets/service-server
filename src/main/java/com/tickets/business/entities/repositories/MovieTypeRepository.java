package com.tickets.business.entities.repositories;

import com.tickets.business.entities.MovieType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MovieTypeRepository extends CrudRepository<MovieType, Integer> {

    List<MovieType> findByType(String type);

}
