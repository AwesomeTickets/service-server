package com.awesometickets.business.services;

import com.awesometickets.business.entities.TicketCode;
import com.awesometickets.business.entities.repositories.TicketCodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Random;


/**
 * Generate and compute digest of ticket code.
 */
@Service
public class TicketCodeService {

    private Random random = new Random();

    @Autowired
    private TicketCodeRepository codeRepo;

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
