package com.awesometickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Cinema")
public class Cinema implements Serializable {

	private Integer cinemaId;
	private String cinemaName;
	private String cinemaAddr;

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

    @Column(name = "cinema_name", nullable = false, length=30)
    public String getCinemaName() {
        return cinemaName;
    }

    public void setCinemaName(String cinemaName) {
        this.cinemaName = cinemaName;
    }

    @Column(name = "cinema_addr", nullable = false, length=50)
    public String getCinemaAddr() {
        return cinemaAddr;
    }

    public void setCinemaAddr(String cinemaAddr) {
        this.cinemaAddr = cinemaAddr;
    }
}
