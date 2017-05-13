package com.awesometickets.business.services;

import com.awesometickets.business.entities.Seat;
import com.awesometickets.business.entities.Ticket;
import com.awesometickets.business.entities.TicketCode;
import com.awesometickets.business.entities.User;
import com.awesometickets.business.entities.repositories.SeatRepository;
import com.awesometickets.business.entities.repositories.TicketCodeRepository;
import com.awesometickets.business.entities.repositories.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepo;

    @Autowired
    private TicketCodeRepository codeRepo;

    @Autowired
    private SeatRepository seatRepo;

    private Random random = new Random();

    /**
     * Buy a ticket.
     * @param seatList The seats in the ticket
     * @param user The buyer
     * @return The generated code
     */
    public String buyTicketAndGenerateCode(List<Seat> seatList, User user) {
        Ticket ticket = new Ticket();
        String code = genCode();
        String codeDigest = digest(code);

        user.setRemainPurchase(user.getRemainPurchase()-1);
        ticket.setDigest(codeDigest);
        ticket.setValid(true);
        ticket.setUser(user);
        ticketRepo.save(ticket);

        saveCode(code);
        for (Seat seat : seatList) {
            seat.setTicket(ticket);
            seat.setAvailable(false);
            seatRepo.save(seat);
        }

        return code;
    }

    /**
     * Fetch a ticket.
     * @param ticketCode
     * @param user
     * @return The seats with movieOnShow and ticket entity inside.
     *         @{null} if the user and code is not match,
     */
    public Ticket getTicket(String ticketCode, User user) {
        String digest = digest(ticketCode);
        Ticket ticket = ticketRepo.findByDigestAndUser(digest, user);
        return ticket;
    }

    /**
     * Fetch the seats in a ticket.
     * @param ticket
     * @return
     */
    public List<Seat> getTicketSeats(Ticket ticket) {
        return seatRepo.findByTicketIdWithMovieOnShowAndTicket(ticket.getTicketId());
    }

    /**
     * Check a ticket.
     * @param ticket
     * @return True if the ticket checked successfully.
     *         False if the ticket has already been checked.
     */
    public boolean checkTicket(Ticket ticket) {
        if (!ticket.getValid()) return false;
        ticket.setValid(false);
        ticketRepo.save(ticket);
        return true;
    }

    /**
     * Check a code exists or not.
     * @param code
     * @return True if code exist.
     */
    public boolean codeExist(String code) {
        return !(codeRepo.findOne(code) == null);
    }


    /**
     * Get the info of a ticket.
     * @param ticketCode
     * @param user
     * @return The seats with movieOnShow and ticket entity inside.
     */
    public List<Seat> ticketInfo(String ticketCode, User user) {
        String digest = digest(ticketCode);
        Ticket ticket = ticketRepo.findByDigestAndUser(digest, user);
        if (ticket == null) return null;
        return seatRepo.findByTicketIdWithMovieOnShowAndTicket(ticket.getTicketId());
    }

    /**
     * Generate a ticket code with 10 characters.
     *
     * @return The generated ticket code
     */
    public String genCode() {
        String codeStr;
        do {
            double code = random.nextDouble() * 8999999999D + 1000000000D;
            codeStr = String.valueOf((long)code);
        } while (hasCode(codeStr));
        return codeStr;
    }

    /**
     * Save a ticket code to database.
     *
     * @param code The ticket code to save
     */
    public void saveCode(String code) {
        TicketCode ticketCode = new TicketCode();
        ticketCode.setCode(code);
        codeRepo.save(ticketCode);
    }

    /**
     * Check if a ticket code has been generated.
     *
     * @param code The ticket code to check
     * @return True if the ticket code has been generated.
     */
    public boolean hasCode(String code) {
        return codeRepo.findOne(code) != null;
    }

    /**
     * Generate the digest of a ticket code.
     *
     * @param code The ticket code
     * @return The digest string with 32 characters
     */
    public String digest(String code) {
        return DigestUtils.md5DigestAsHex(code.getBytes());
    }
}
