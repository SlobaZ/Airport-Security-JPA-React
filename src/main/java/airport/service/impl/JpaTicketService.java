package airport.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import airport.model.Ticket;
import airport.repository.TicketRepository;

import airport.service.TicketService;

@Service
public class JpaTicketService implements TicketService {
	
	@Autowired
	private TicketRepository ticketRepository;
		
	

	@Override
	public Ticket getById(Long id) {
		return ticketRepository.getById(id);
	}

	@Override
	public List<Ticket> findAll() {
		return ticketRepository.findAll();
	}

	@Override
	public Page<Ticket> findAll(int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 20);
		return ticketRepository.findAll(pageable);
	}

	@Override
	public Ticket save(Ticket ticket) {
		return ticketRepository.save(ticket);
	}

	@Override
	public List<Ticket> saveAll(List<Ticket> tickets) {
		return ticketRepository.saveAll(tickets);
	}

	@Override
	public Ticket delete(Long id) {
		Ticket ticket = ticketRepository.getById(id);
		if(ticket!=null) {
			ticketRepository.delete(ticket);
		}
		return ticket;
	}

	@Override
	public Page<Ticket> search(Long idUser, Long idFlight, int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 20);
		return ticketRepository.search(idUser, idFlight, pageable);
	}
	
	
	
	

}
