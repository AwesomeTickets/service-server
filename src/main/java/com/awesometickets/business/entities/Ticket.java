package com.awesometickets.business.entities;

import javax.persistence.*;

@Entity
@Table(name = "Ticket")
public class Ticket {
    private Integer ticketId;
    private User user;
    private Boolean valid;
    private String digest;

    public Ticket() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ticket_id")
    public Integer getTicketId() {
        return ticketId;
    }

    public void setTicketId(Integer ticketId) {
        this.ticketId = ticketId;
    }

    @ManyToOne(cascade = {CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey=@ForeignKey(name="FK_R_Ticket_User"))
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "valid", nullable = false, columnDefinition="default TRUE")
    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    @Column(name = "digest", nullable = false, length=32, unique = true)
    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }
}
