package com.tickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "movie_status")
public class MovieStatus implements Serializable {

    private Integer movieStatusID;

    private String status;

    public MovieStatus() {
    }

    public MovieStatus(String status) {
        this.status = status;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movieStatusID")
    public Integer getMovieStatusID() {
        return movieStatusID;
    }

    public void setMovieStatusID(Integer movieStatusID) {
        this.movieStatusID = movieStatusID;
    }

    @Column(name = "status", nullable = false, unique = true, length=16)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieStatus)) return false;

        MovieStatus that = (MovieStatus) o;

        if (!getMovieStatusID().equals(that.getMovieStatusID())) return false;
        return getStatus().equals(that.getStatus());
    }

    @Override
    public int hashCode() {
        return getMovieStatusID().hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("MovieStatus [");
        sb.append("movieStatusID=").append(movieStatusID);
        sb.append(", status=").append(status);
        sb.append(']');
        return sb.toString();
    }
}
