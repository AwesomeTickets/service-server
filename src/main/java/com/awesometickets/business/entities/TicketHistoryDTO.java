package com.awesometickets.business.entities;

import java.util.List;

/**
 * A Data Transfer Object for
 */
public class TicketHistoryDTO {
    public String code;
    public Boolean valid;
    public List<Integer[]> seats;
    public Integer movieOnShowId;
}
