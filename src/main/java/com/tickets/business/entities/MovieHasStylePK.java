package com.tickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by CrazeWong on 2017/3/29.
 * As a composite pk for MovieHasStyle
 */
@Embeddable
public class MovieHasStylePK implements Serializable {

    private Movie movie;

    private MovieStyle movieStyle;

    public MovieHasStylePK() {
    }

    public MovieHasStylePK(Movie movie, MovieStyle movieStyle) {
        this.movie = movie;
        this.movieStyle = movieStyle;
    }

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "movieID")
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "movieStyleID")
    public MovieStyle getMovieStyle() {
        return movieStyle;
    }

    public void setMovieStyle(MovieStyle movieStyle) {
        this.movieStyle = movieStyle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieHasStylePK)) return false;

        MovieHasStylePK that = (MovieHasStylePK) o;

        if (!getMovie().equals(that.getMovie())) return false;
        return getMovieStyle().equals(that.getMovieStyle());
    }

    @Override
    public int hashCode() {
        int result = getMovie().hashCode();
        result = 31 * result + getMovieStyle().hashCode();
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MovieHasStylePK [");
        sb.append("movieID=").append(movie.getMovieID());
        sb.append(", movieStyleID=").append(movieStyle.getMovieStyleID());
        sb.append(']');
        return sb.toString();
    }
}

