package com.awesome_tickets.business.entities.repositories;

import com.awesome_tickets.business.entities.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface MovieRepository extends CrudRepository<Movie, Integer> {

    @Query("select m.movieId from Movie m where m.movieStatus.statusName = ?1")
    List<Integer> findByMovieStatus(String status);

    @Query("select m.movieId, m.posterLarge from Movie m where m.posterLarge is not null AND m.posterLarge !=''")
    List<Object[]> findLargePoster();

    @Query("from Movie m " +
            "left outer join fetch m.country " +
            "left outer join fetch m.movieStatus " +
            "left outer join fetch m.movieType " +
            "left outer join fetch m.movieStyleSet " +
            "where m.movieId = ?1")
    Movie findOneWithAllDetails(Integer movieId);
}
