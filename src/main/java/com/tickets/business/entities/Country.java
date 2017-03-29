package com.tickets.business.entities;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by CrazeWong on 2017/3/29.
 * Country entity, name such as ??
 */

@Entity
@Table(name = "country")
public class Country implements Serializable {

    private Integer countryID;

    private String name;

    public Country() {
    }

    public Country(String name) {
        this.name = name;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "countryID")
    public Integer getCountryID() {
        return countryID;
    }

    public void setCountryID(Integer countryID) {
        this.countryID = countryID;
    }

    @Column(name = "name", nullable = false, unique = true, length=32)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Country)) return false;

        Country country = (Country) o;

        return getCountryID().equals(country.getCountryID());
    }

    @Override
    public int hashCode() {
        return getCountryID().hashCode();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Country [");
        sb.append("id=").append(countryID);
        sb.append(", name=").append(name);
        sb.append(']');
        return sb.toString();
    }
}
