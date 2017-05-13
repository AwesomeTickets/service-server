package com.awesometickets.business.entities;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.sql.Time;
import java.sql.Date;
import java.util.Set;


@Entity
@Table(name = "MovieOnShow")
public class MovieOnShow implements Serializable {

    private Integer movieOnShowId;
    private String lang;
    private Date showDate;
    private Time showTime;
    private Float price;
    private Movie movie;
    private CinemaHall cinemaHall;
    private Set<Seat> seatSet;

    public MovieOnShow() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movie_on_show_id")
    public Integer getMovieOnShowId() {
        return movieOnShowId;
    }

    public void setMovieOnShowId(Integer movieOnShowId) {
        this.movieOnShowId = movieOnShowId;
    }

    @Column(name = "lang", nullable = false, length=2)
    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Column(name = "show_date", nullable = false)
    public Date getShowDate() {
        return showDate;
    }

    public void setShowDate(Date showDate) {
        this.showDate = showDate;
    }

    @Column(name = "show_time", nullable = false)
    public Time getShowTime() {
        return showTime;
    }

    public void setShowTime(Time showTime) {
        this.showTime = showTime;
    }

    @Column(name = "price", columnDefinition="decimal(5,2)")
    @DecimalMin("0.01")
    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", foreignKey=@ForeignKey(name="FK_R_MovieOnShow_Movie"))
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_hall_id", foreignKey=@ForeignKey(name="FK_R_MovieOnShow_CinemaHall"))
    public CinemaHall getCinemaHall() {
        return cinemaHall;
    }

    public void setCinemaHall(CinemaHall cinemaHall) {
        this.cinemaHall = cinemaHall;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "movieOnShow")
    public Set<Seat> getSeatSet() {
        return seatSet;
    }

    public void setSeatSet(Set<Seat> seatSet) {
        this.seatSet = seatSet;
    }
}
