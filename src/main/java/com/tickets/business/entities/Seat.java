package com.tickets.business.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;

/**
 * Created by CrazeWong on 2017/4/16.
 */
@Entity
@Table(name = "seat", uniqueConstraints = {
        @UniqueConstraint(columnNames={"movieOnShowID", "row", "col"})})
public class Seat {
    private Integer seatID;
    private Integer row;
    private Integer col;
    private Boolean available;
    private MovieOnShow movieOnShow;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seatID")
    public Integer getSeatID() {
        return seatID;
    }

    public void setSeatID(Integer seatID) {
        this.seatID = seatID;
    }

    @Column(name = "row", nullable = false)
    @Min(1)
    public Integer getRow() {
        return row;
    }

    public void setRow(Integer row) {
        this.row = row;
    }

    @Column(name = "col", nullable = false)
    @Min(1)
    public Integer getCol() {
        return col;
    }

    public void setCol(Integer col) {
        this.col = col;
    }

    @Column(name = "available", nullable = false, columnDefinition="default TRUE")
    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "movieOnShowID")
    public MovieOnShow getMovieOnShow() {
        return movieOnShow;
    }

    public void setMovieOnShow(MovieOnShow movieOnShow) {
        this.movieOnShow = movieOnShow;
    }

    public Seat() {
    }

    public Seat(Integer row, Integer col, Boolean available) {
        this.row = row;
        this.col = col;
        this.available = available;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Seat)) return false;

        Seat seat = (Seat) o;

        return getSeatID().equals(seat.getSeatID());
    }

    @Override
    public int hashCode() {
        return getSeatID().hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Seat [");
        sb.append("seatID=").append(seatID);
        sb.append(", row=").append(row);
        sb.append(", col=").append(col);
        sb.append(", available=").append(available);
        sb.append(']');
        return sb.toString();
    }
}
