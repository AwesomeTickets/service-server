package com.awesome_tickets.business.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by CrazeWong on 2017/4/22.
 */
@Entity
@Table(name = "movie_has_style")
public class MovieHasStyle implements Serializable {
    private Movie movie;
    private MovieStyle movieStyle;

    @Id
    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "movieID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @Id
    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "movieStyleID")
    @OnDelete(action = OnDeleteAction.CASCADE)
    public MovieStyle getMovieStyle() {
        return movieStyle;
    }

    public void setMovieStyle(MovieStyle movieStyle) {
        this.movieStyle = movieStyle;
    }
}
