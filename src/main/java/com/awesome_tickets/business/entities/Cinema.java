package com.awesome_tickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "cinema")
public class Cinema implements Serializable {

	private Integer cinemaId;
	private String name;
	private String location;

    public Cinema() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cinema_id")
    public Integer getCinemaId() {
        return cinemaId;
    }

    public void setCinemaId(Integer cinemaId) {
        this.cinemaId = cinemaId;
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
}
