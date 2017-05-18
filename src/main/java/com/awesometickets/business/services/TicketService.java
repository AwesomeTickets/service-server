package com.awesometickets.business.services;

import com.awesometickets.business.entities.Seat;
import com.awesometickets.business.entities.Ticket;
import com.awesometickets.business.entities.User;
import com.awesometickets.business.entities.repositories.SeatRepository;
import com.awesometickets.business.entities.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;


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
     * Generate a ticket code with 10 characters.
     *
     * @return The generated ticket code
     */
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
    private boolean hasCode(String code) {
        return !ticketRepo.findUserIdByCode(code).isEmpty();
    }
}
