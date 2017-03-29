package com.tickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "movie_type")
public class MovieType implements Serializable {

    private Integer movieTypeID;

    private String type;

    public MovieType() {
    }

    public MovieType(String type) {
        this.type = type;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movieTypeID")
    public Integer getMovieTypeID() {
        return movieTypeID;
    }

    public void setMovieTypeID(Integer movieTypeID) {
        this.movieTypeID = movieTypeID;
    }

    @Column(name = "type", nullable = false, unique = true, length=8)
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieType)) return false;

        MovieType movieType = (MovieType) o;

        return getMovieTypeID().equals(movieType.getMovieTypeID());
    }

    @Override
    public int hashCode() {
        return getMovieTypeID().hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MovieType [");
        sb.append("movieTypeID=").append(movieTypeID);
        sb.append(", type=").append(type);
        sb.append(']');
        return sb.toString();
    }
}