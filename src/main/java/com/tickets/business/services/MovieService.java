package com.tickets.business.services;

import com.tickets.business.entities.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepo;

    // TODO MovieService
}
