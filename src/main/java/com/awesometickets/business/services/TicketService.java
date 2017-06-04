package com.awesometickets.business.services;

import com.awesometickets.business.entities.Seat;
import com.awesometickets.business.entities.Ticket;
import com.awesometickets.business.entities.TicketHistoryDTO;
import com.awesometickets.business.entities.User;
import com.awesometickets.business.entities.repositories.SeatRepository;
import com.awesometickets.business.entities.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepo;

    @Autowired
    private SeatRepository seatRepo;

    private Random random = new Random();

    /**
     * Buy a ticket.
     *
     * @param seatList The seats to be bought
     * @param user The user to buy the ticket
     * @return The ticket code of the ticket
     */
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
    public String buyTicketAndGenerateCode(List<Seat> seatList, User user) {
        String ticketCode = genCode();
        Ticket ticket = new Ticket();
        ticket.setCode(ticketCode);
        ticket.setValid(true);
        ticket.setUser(user);
        ticketRepo.save(ticket);
        for (Seat seat : seatList) {
            seat.setTicket(ticket);
            seat.setAvailable(false);
            seatRepo.save(seat);
        }
        user.setRemainPurchase(user.getRemainPurchase() - 1);
        return ticketCode;
    }

    /**
     * Find a ticket by its code.
     *
     * @param code The ticket code
     * @return The ticket object, or null if ticket not exist
     */
    public Ticket findByCode(String code) {
        return ticketRepo.findByCode(code);
    }

    /**
     * Fetch the seats of a ticket.
     *
     * @param ticket The ticket
     * @return The seats list
     */
    public List<Seat> findTicketSeats(Ticket ticket) {
        return seatRepo.findByTicketIdWithMovieOnShowAndTicket(ticket.getTicketId());
    }

    /**
     * Make a ticket invalid.
     *
     * @param ticket The ticket to be processed.
     * @return True if the ticket checked successfully.
     *         False if the ticket has already been checked.
     */
    public boolean checkTicket(Ticket ticket) {
        if (!ticket.getValid()) {
            return false;
        }
        ticket.setValid(false);
        ticketRepo.save(ticket);
        return true;
    }

    /**
     * Query all tickets that the user buy.
     *
     * @param phoneNum the user's phone number.
     */
    public Collection<TicketHistoryDTO> getAllTicketsByPhoneNum(String phoneNum) {
        List<Object[]> los = seatRepo.findByUserIdWithAllTickets(phoneNum);
        Map<String, TicketHistoryDTO> map = new LinkedHashMap<String, TicketHistoryDTO>();
        for (int i = 0; i < los.size(); i++) {
            TicketHistoryDTO dto;
            if (!map.containsKey((String) los.get(i)[0])) {
                dto = new TicketHistoryDTO();
                dto.code = (String)los.get(i)[0];
                dto.valid = (Boolean) los.get(i)[1];
                dto.movieOnShowId = (Integer) los.get(i)[2];
                dto.seats = new ArrayList<Integer[]>();
                map.put(dto.code, dto);
            } else {
                dto = map.get((String) los.get(i)[0]);
            }
            dto.seats.add(new Integer[] {(Integer)los.get(i)[3], (Integer)los.get(i)[4]});
        }
        return map.values();
    }

    /**
     * Generate a ticket code with 10 characters.
     *
     * @return The generated ticket code
     */
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
    private String genCode() {
        String codeStr;
        do {
            double code = random.nextDouble() * 8999999999D + 1000000000D;
            codeStr = String.valueOf((long)code);
        } while (hasCode(codeStr));
        return codeStr;
    }

    /**
     * Check if a ticket code has been generated.
     *
     * @param code The ticket code to check
     * @return True if the ticket code has been generated.
     */
    @Transactional(propagation= Propagation.REQUIRED, rollbackFor = Exception.class)
    private boolean hasCode(String code) {
        return !ticketRepo.findUserIdByCode(code).isEmpty();
    }

}
