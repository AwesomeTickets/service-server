package com.awesome_tickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "movie_style")
public class MovieStyle implements Serializable {

    private Integer movieStyleId;
    private String style;
    private Set<Movie> movieSet;

    public MovieStyle() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_style_id")
    public Integer getMovieStyleId() {
        return movieStyleId;
    }

    public void setMovieStyleId(Integer movieStyleId) {
        this.movieStyleId = movieStyleId;
    }

    @Column(name = "style", nullable = false, unique = true, length=2)
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
}