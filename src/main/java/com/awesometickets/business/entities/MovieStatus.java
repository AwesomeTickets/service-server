package com.awesometickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "MovieStatus")
public class MovieStatus implements Serializable {

    private Integer movieStatusId;
    private String statusName;

    public MovieStatus() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_status_id")
    public Integer getMovieStatusId() {
        return movieStatusId;
    }

    public void setMovieStatusId(Integer movieStatusId) {
        this.movieStatusId = movieStatusId;
    }

    @Column(name = "status_name", nullable = false, length=4)
    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
