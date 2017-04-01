package com.tickets.business.entities.repositories;

import com.tickets.business.entities.Movie;
import com.tickets.business.entities.MovieStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {
    // TODO MovieRepository

    @Query("select m.movieID from Movie m where m.movieStatus = ?1")
    List<Integer> findByStatus(MovieStatus movieStatus);
}
