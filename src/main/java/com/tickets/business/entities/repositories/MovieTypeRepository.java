package com.tickets.business.entities.repositories;

import com.tickets.business.entities.MovieType;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by CrazeWong on 2017/3/30.
 */
public interface MovieTypeRepository extends CrudRepository<MovieType, Integer> {
    // TODO MovieTypeRepository
    List<MovieType> findByType(String type);
}
