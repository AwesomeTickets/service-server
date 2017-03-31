package com.tickets.business.entities.repositories;

import com.tickets.business.entities.MovieStyle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by CrazeWong on 2017/3/30.
 */
public interface MovieStyleRepository extends CrudRepository<MovieStyle, Integer> {
    // TODO MovieStyleRepository
    List<MovieStyle> findByStyle(String style);
}
