package com.awesometickets.business.entities.repositories;

import com.awesometickets.business.entities.Ticket;
import com.awesometickets.business.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends CrudRepository<Ticket, Integer> {

    Ticket findByCode(String code);

    @Query("select t.user.userId from Ticket t where t.code = ?1")
    List<Integer> findUserIdByCode(String code);
}
