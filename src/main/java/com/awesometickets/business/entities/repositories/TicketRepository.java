package com.awesometickets.business.entities.repositories;

import com.awesometickets.business.entities.Ticket;
import com.awesometickets.business.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {

    Ticket findByDigestAndUser(String digest, User user);
}
