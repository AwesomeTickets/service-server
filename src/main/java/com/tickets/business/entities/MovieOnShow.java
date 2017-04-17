package com.tickets.business.entities;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.sql.Time;
import java.sql.Date;
import java.util.Set;

/**
 * Created by CrazeWong on 2017/4/16.
 */
@Entity
@Table(name = "movie_on_show", uniqueConstraints = {
        @UniqueConstraint(columnNames={"movieID", "cinemaHallID", "showDate", "showTime"})})
public class MovieOnShow {
    private Integer movieOnShowID;
    private String lang;
    private Date showDate;
    private Time showTime;
    private float price;
    private Movie movie;
    private CinemaHall cinemaHall;
    private Set<Seat> seatSet;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movieOnShowID")
    public Integer getMovieOnShowID() {
        return movieOnShowID;
    }

    public void setMovieOnShowID(Integer movieOnShowID) {
        this.movieOnShowID = movieOnShowID;
    }

    @Column(name = "lang", nullable = false, length=16)
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Column(name = "showDate", nullable = false)
    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    @Column(name = "showTime", nullable = false)
    public Time getShowTime() {
        return showTime;
    }

    public void setShowTime(Time showTime) {
        this.showTime = showTime;
    }

    @Column(name = "price", columnDefinition="decimal(5,2)")
    @DecimalMin("0.01")
    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
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
    @JoinColumn(name = "cinemaHallID")
    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "movieOnShow")
    public Set<Seat> getSeatSet() {
        return seatSet;
    }

    public void setSeatSet(Set<Seat> seatSet) {
        this.seatSet = seatSet;
    }


}
