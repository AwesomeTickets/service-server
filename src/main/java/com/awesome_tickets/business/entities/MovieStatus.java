package com.awesome_tickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "movie_status")
public class MovieStatus implements Serializable {

    private Integer movieStatusId;
    private String status;

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

    @Column(name = "status", nullable = false, unique = true, length=4)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
