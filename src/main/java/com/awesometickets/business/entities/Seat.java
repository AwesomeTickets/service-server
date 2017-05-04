package com.awesometickets.business.entities;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;


@Entity
@Table(name = "Seat")
public class Seat implements Serializable {

    private Integer seatId;
    private Integer row;
    private Integer col;
    private Boolean available;
    private MovieOnShow movieOnShow;
    private Ticket ticket;

    public Seat() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "seat_id")
    public Integer getSeatId() {
        return seatId;
    }

    public void setSeatId(Integer seatId) {
        this.seatId = seatId;
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
    @JoinColumn(name = "movie_on_show_id", foreignKey=@ForeignKey(name="FK_R_Seat_MovieOnShow"))
    public MovieOnShow getMovieOnShow() {
        return movieOnShow;
    }

    public void setMovieOnShow(MovieOnShow movieOnShow) {
        this.movieOnShow = movieOnShow;
    }

    @OneToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "ticket_id", foreignKey=@ForeignKey(name="FK_R_Seat_Ticket"))
    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }
}
