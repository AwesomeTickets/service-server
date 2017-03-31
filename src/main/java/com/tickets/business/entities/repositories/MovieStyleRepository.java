package com.tickets.business.entities.repositories;

import com.tickets.business.entities.MovieStyle;
import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface MovieStyleRepository extends CrudRepository<MovieStyle, Integer> {

    List<MovieStyle> findByStyle(String style);

}
