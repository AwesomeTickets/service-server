package com.tickets.business.entities;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;


@Entity
@Table(name = "movie")
public class Movie implements Serializable {

    private Integer movieID;

    private Country country;

    private MovieStatus movieStatus;

    private MovieType movieType;

    private Set<MovieStyle> movieStyleSet;

    private Date pubdate;

    private String title;

    private float rating;

    private int length;

    private String posterSmall;

    private String posterLarge;

    public Movie() {
    }

    public Movie(Country country, MovieStatus movieStatus, MovieType movieType, Set<MovieStyle> movieStyleSet,
                 String title, float rating, int length, String posterSmall, String posterLarge) {
        this.country = country;
        this.movieStatus = movieStatus;
        this.movieType = movieType;
        this.movieStyleSet = movieStyleSet;
        this.title = title;
        this.rating = rating;
        this.length = length;
        this.posterSmall = posterSmall;
        this.posterLarge = posterLarge;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "movieID")
    public Integer getMovieID() {
        return movieID;
    }

    public void setMovieID(Integer movieID) {
        this.movieID = movieID;
    }

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "countryID")
    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "movieStatusID")
    public MovieStatus getMovieStatus() {
        return movieStatus;
    }

    public void setMovieStatus(MovieStatus movieStatus) {
        this.movieStatus = movieStatus;
    }

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "movieTypeID")
    public MovieType getMovieType() {
        return movieType;
    }

    public void setMovieType(MovieType movieType) {
        this.movieType = movieType;
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "movie_has_style",
            joinColumns = {@JoinColumn(name = "movieID", referencedColumnName = "movieID")},
            inverseJoinColumns = {@JoinColumn(name = "movieStyleID", referencedColumnName ="movieStyleID")})
    public Set<MovieStyle> getMovieStyleSet() {
        return movieStyleSet;
    }

    public void setMovieStyleSet(Set<MovieStyle> movieStyleSet) {
        this.movieStyleSet = movieStyleSet;
    }

    @Column(name = "pubdate", nullable = false, length = 10)
    public Date getPubdate() {
        return pubdate;
    }

    public void setPubdate(Date pubdate) {
        this.pubdate = pubdate;
    }

    @Column(name = "title", nullable = false, length=64)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "rating", columnDefinition="float(2,1) default 0.0")
    @DecimalMin("0.1")
    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Column(name = "length")
    @Min(1)
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    @Column(name = "posterSmall", length=128)
    public String getPosterSmall() {
        return posterSmall;
    }

    public void setPosterSmall(String posterSmall) {
        this.posterSmall = posterSmall;
    }

    @Column(name = "posterLarge", length=128)
    public String getPosterLarge() {
        return posterLarge;
    }

    public void setPosterLarge(String posterLarge) {
        this.posterLarge = posterLarge;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;

        Movie movie = (Movie) o;

        return getMovieID().equals(movie.getMovieID());
    }

    @Override
    public int hashCode() {
        return getMovieID().hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Movie [");
        sb.append("movieID=").append(movieID);
        sb.append(", country=").append(country.getName());
        sb.append(", movieStatus=").append(movieStatus.getStatus());
        sb.append(", movieType=").append(movieType.getType());
        sb.append(", movieStyleSet=").append(movieStyleSet);
        sb.append(", pubdate=").append(pubdate);
        sb.append(", title=").append(title);
        sb.append(", rating=").append(rating);
        sb.append(", length=").append(length);
        sb.append(", posterSmall=").append(posterSmall);
        sb.append(", posterLarge=").append(posterLarge);
        sb.append(']');
        return sb.toString();
    }
}
