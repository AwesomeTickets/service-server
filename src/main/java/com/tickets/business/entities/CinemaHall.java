package com.tickets.business.entities;

import javax.persistence.*;


@Entity
@Table(name = "cinema_hall")
public class CinemaHall {

    private Integer cinemaHallID;
    private String name;
    private Cinema cinema;
    private String seatLayout;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cinemaHallID")
    public Integer getCinemaHallID() {
        return cinemaHallID;
    }

    public void setCinemaHallID(Integer cinemaHallID) {
        this.cinemaHallID = cinemaHallID;
    }

    @Column(name = "name", nullable = false, length=16)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "seatLayout", nullable = false, length=256)
    public String getSeatLayout() {
        return seatLayout;
    }

    public void setSeatLayout(String seatLayout) {
        this.seatLayout = seatLayout;
    }

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "cinemaID")
    public Cinema getCinema() {
        return cinema;
    }

    public void setCinema(Cinema cinema) {
        this.cinema = cinema;
    }

    public CinemaHall() {
    }

    public CinemaHall(String name, String seatLayout) {
        this.name = name;
        this.seatLayout = seatLayout;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CinemaHall)) return false;

        CinemaHall that = (CinemaHall) o;

        return getCinemaHallID().equals(that.getCinemaHallID());
    }

    @Override
    public int hashCode() {
        return getCinemaHallID().hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("CinemaHall [");
        sb.append("cinemaHallID=").append(cinemaHallID);
        sb.append(", name=").append(name);
        sb.append(", seatLayout=").append(seatLayout);
        sb.append(']');
        return sb.toString();
    }
}
