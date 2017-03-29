package com.tickets.business.entities.repositories;

import com.tickets.business.entities.Movie;
import com.tickets.business.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {
    // TODO MovieRepository
}
