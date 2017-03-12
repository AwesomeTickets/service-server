package com.tickets.business.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

public class User implements Serializable {

    private Integer id = null;
    private Date datePlanted = null;
    private Boolean covered = null;
    
    public User() {
        super();
    }


    public Integer getId() {
        return this.id;
    }


    public void setId(final Integer id) {
        this.id = id;
    }


    public Date getDatePlanted() {
        return this.datePlanted;
    }


    public void setDatePlanted(final Date datePlanted) {
        this.datePlanted = datePlanted;
    }


    public Boolean getCovered() {
        return this.covered;
    }


    public void setCovered(final Boolean covered) {
        this.covered = covered;
    }


    @Override
    public String toString() {
        return "SeedStarter [id=" + this.id + ", datePlanted=" + this.datePlanted
                + ", covered=" + this.covered + ", type=" + this.type + ", features="
                + Arrays.toString(this.features) + ", rows=" + this.rows + "]";
    }
    
}
