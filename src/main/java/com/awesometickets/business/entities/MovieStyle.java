package com.awesometickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@Table(name = "MovieStyle")
public class MovieStyle implements Serializable {

    private Integer movieStyleId;
    private String styleName;
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

    @Column(name = "style_name", nullable = false, length=2)
    public String getStyleName() {
        return styleName;
    }

    public void setStyleName(String styleName) {
        this.styleName = styleName;
    }

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "movieStyleSet")
    public Set<Movie> getMovieSet() {
        return movieSet;
    }

    public void setMovieSet(Set<Movie> movieSet) {
        this.movieSet = movieSet;
    }
}