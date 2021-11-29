package airport.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import airport.dto.TicketDTO;
import airport.model.Flight;
import airport.model.Ticket;
import airport.model.User;
import airport.service.FlightService;
import airport.service.TicketService;
import airport.service.UserService;


@Component
public class TicketDTOToTicket implements Converter<TicketDTO, Ticket> {
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private UserService userService;

	@Override
	public Ticket convert(TicketDTO dto) {
		
		Flight flight = flightService.getById(dto.getFlightId());
		User user = userService.getById(dto.getUserId());
		
		Ticket ticket = null;
		
		if(dto.getId()!=null){
			ticket = ticketService.getById(dto.getId());
			
			if(ticket == null){
				throw new IllegalStateException("Tried to "
						+ "modify a non-existant Ticket");
			}
		}
		else {
			ticket = new Ticket();
		}
		
		ticket.setId(dto.getId());
		
		ticket.setUser(user);
		ticket.setFlight(flight);
		
		return ticket;
	}
	
	
	public List<Ticket> convert (List<TicketDTO> dtoTickets){
		List<Ticket> tickets = new ArrayList<>();
		
		for(TicketDTO dto : dtoTickets){
			tickets.add(convert(dto));
		}
		
		return tickets;
	}

}
