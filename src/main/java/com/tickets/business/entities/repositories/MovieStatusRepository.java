package com.tickets.business.entities.repositories;

import com.tickets.business.entities.MovieStatus;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MovieStatusRepository extends CrudRepository<MovieStatus, Integer> {

    List<MovieStatus> findByStatus(String status);

}
