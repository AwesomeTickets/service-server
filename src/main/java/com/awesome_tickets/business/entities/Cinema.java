package com.awesome_tickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cinema")
public class Cinema implements Serializable {

	private Integer cinemaID;

	private String name;

	private String location;

    public Cinema() {
    }

    public Cinema(String name, String location) {
        this.name = name;
        this.location = location;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cinemaID")
    public Integer getCinemaID() {
        return cinemaID;
    }

    public void setCinemaID(Integer cinemaID) {
        this.cinemaID = cinemaID;
    }

    @Column(name = "name", nullable = false, unique = true, length=30)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "location", nullable = false, unique = true, length=50)
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Cinema)) return false;

        Cinema cinema = (Cinema) o;

        return getCinemaID().equals(cinema.getCinemaID())&&getLocation().equals(cinema.getLocation());
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cinema [");
        sb.append("id=").append(cinemaID);
        sb.append(", name=").append(name);
        sb.append(", location=").append(location);
        sb.append(']');
        return sb.toString();
    }

}
