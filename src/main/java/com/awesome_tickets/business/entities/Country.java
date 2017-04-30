package com.awesome_tickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "Country")
public class Country implements Serializable {

    private Integer countryId;
    private String countryName;

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

    @Column(name = "country_name", nullable = false, length=6)
    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }
}
