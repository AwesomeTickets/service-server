package com.awesometickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "MovieType")
public class MovieType implements Serializable {

    private Integer movieTypeId;
    private String typeName;

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

    @Column(name = "type_name", nullable = false, unique = true)
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}