package com.awesome_tickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "country")
public class Country implements Serializable {

    private Integer countryId;
    private String name;

    public Country() {
        super();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "country_id")
    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    @Column(name = "name", nullable = false, unique = true, length=6)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
