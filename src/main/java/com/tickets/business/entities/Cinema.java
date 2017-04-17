package com.tickets.business.entities;

import javax.persistence.*;
import java.util.Set;

/**
 * Created by CrazeWong on 2017/4/16.
 */
@Entity
@Table(name = "cinema")
public class Cinema {

    private Integer cinemaID;
    private String name;
    private String location;
    private Set<CinemaHall> cinemaHallSet;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cinemaID")
    public Integer getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(Integer cinemaID) {
        this.cinemaID = cinemaID;
    }

    @Column(name = "name", nullable = false, length=30, unique = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "location", nullable = false, length=50, unique = true)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @OneToMany(mappedBy = "cinema", fetch = FetchType.EAGER)
    public Set<CinemaHall> getCinemaHallSet() {
        return cinemaHallSet;
    }

    public void setCinemaHallSet(Set<CinemaHall> cinemaHallSet) {
        this.cinemaHallSet = cinemaHallSet;
    }

    public Cinema() {
    }

    public Cinema(String name, String location, Set<CinemaHall> cinemaHallSet) {
        this.name = name;
        this.location = location;
        this.cinemaHallSet = cinemaHallSet;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cinema)) return false;

        Cinema cinema = (Cinema) o;

        return getCinemaID().equals(cinema.getCinemaID());
    }

    @Override
    public int hashCode() {
        return getCinemaID().hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cinema [");
        sb.append("cinemaID=").append(cinemaID);
        sb.append(", name=").append(name);
        sb.append(", location=").append(location);
        sb.append(']');
        return sb.toString();
    }
}
