package airport.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import airport.model.Ticket;

public interface TicketService {
	
	Ticket getById(Long id);
	List<Ticket> findAll();
	Page<Ticket> findAll(int pageNum);
	Ticket save(Ticket ticket);
	List<Ticket> saveAll(List<Ticket> tickets);
	Ticket delete(Long id);
	
	Page<Ticket> search( 
			@Param("userId") Long userId,
			@Param("flightId") Long flightId,
			 int pageNum);

	
}
