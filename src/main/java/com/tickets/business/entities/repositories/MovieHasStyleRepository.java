package com.tickets.business.entities.repositories;

import com.tickets.business.entities.MovieHasStyle;
import com.tickets.business.entities.MovieHasStylePK;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by CrazeWong on 2017/3/29.
 */
@Repository
public interface MovieHasStyleRepository extends CrudRepository<MovieHasStyle, MovieHasStylePK>{
    // TODO MovieHasStyleRepository
}
