package com.tickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "movie_style")
public class MovieStyle implements Serializable {

    private Integer movieStyleID;

    private String style;

    private Set<Movie> movieSet;

    public MovieStyle() {
    }

    public MovieStyle(String style) {
        this.style = style;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movieStyleID")
    public Integer getMovieStyleID() {
        return movieStyleID;
    }

    public void setMovieStyleID(Integer movieStyleID) {
        this.movieStyleID = movieStyleID;
    }

    @Column(name = "style", nullable = false, unique = true, length=16)
    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "movieStyleSet")
    public Set<Movie> getMovieSet() {
        return movieSet;
    }

    public void setMovieSet(Set<Movie> movieSet) {
        this.movieSet = movieSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieStyle)) return false;

        MovieStyle that = (MovieStyle) o;

        return getMovieStyleID().equals(that.getMovieStyleID());
    }

    @Override
    public int hashCode() {
        return getMovieStyleID().hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MovieStyleRepository [");
        sb.append("movieStyleID=").append(movieStyleID);
        sb.append(", style=").append(style);
        sb.append(']');
        return sb.toString();
    }
}