package com.awesometickets.business.entities;

import javax.persistence.*;

@Entity
@Table(name = "TicketCode")
public class TicketCode {
    private String code;

    public TicketCode() {
        super();
    }

    @Id
    @Column(name = "code", nullable = false, length=10)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
