package com.awesome_tickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "movie_type")
public class MovieType implements Serializable {

    private Integer movieTypeId;
    private String type;

    public MovieType() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_type_id")
    public Integer getMovieTypeId() {
        return movieTypeId;
    }

    public void setMovieTypeId(Integer movieTypeId) {
        this.movieTypeId = movieTypeId;
    }

    @Column(name = "type", nullable = false, unique = true, length=8)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}