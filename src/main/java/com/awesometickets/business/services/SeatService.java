package com.awesometickets.business.services;

import com.awesometickets.business.entities.Seat;
import com.awesometickets.business.entities.repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.LinkedList;

@Service
public class SeatService {

    @Autowired
    private SeatRepository seatRepo;

    public SeatService() {
        super();
    }

    /**
     * Return the unavailable seats
     * (only row and col attribute are available).
     *
     * @param movieOnShowId The movieOnShow's ID
     */
    public List<Seat> getUnavailable(Integer movieOnShowId) {
        List<Seat> seatList = new LinkedList<Seat>();
        List<Object[]> seats =  seatRepo.findByMovieOnShowIDAndAvailable(movieOnShowId, false);
        for (Object[] seat : seats) {
            Integer row = (Integer)seat[0];
            Integer col = (Integer)seat[1];
            Seat s = new Seat();
            s.setRow(row);
            s.setCol(col);
            seatList.add(s);
        }
        return seatList;
    }

    /**
     * Check the seats is available and it will put the seat entities to the list.
     * @param seatList The list to put the seat entities
     * @return True if the seats are all available
     */
    public boolean checkSeatsAvailable(List<Seat> seatList) {
        for (Seat seat : seatList) {
            if (!seat.getAvailable()) return false;
        }
        return true;
    }

    /**
     * Check the seats is exist and it will put the seat entities to the list.
     * @param seatList The list to put the seat entities
     * @return True if the seats are all exist
     */
    public boolean checkSeatExist(List<Seat> seatList, Integer[] seats, Integer movieOnShowId) {
        for (int i = 0; i < seats.length; ) {
            int row = seats[i++];
            int col = seats[i++];
            Seat seat = seatRepo.findByRowAndColAndMovieOnShowId(row, col, movieOnShowId);
            if (seat == null) {
                seatList.clear();
                return false;
            }
            seatList.add(seat);
        }
        return true;
    }
}
