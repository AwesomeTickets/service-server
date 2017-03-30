package com.tickets.business.entities.repositories;

import com.tickets.business.entities.MovieStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by CrazeWong on 2017/3/30.
 */
public interface MovieStatusRepository extends CrudRepository<MovieStatus, Integer> {
    // TODO MovieStatusRepository
    List<MovieStatus> findByStatus(String status);
}
