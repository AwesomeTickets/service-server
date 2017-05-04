package com.awesometickets.business.entities.repositories;

import com.awesometickets.business.entities.TicketCode;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketCodeRepository extends CrudRepository<TicketCode, String> {
}
