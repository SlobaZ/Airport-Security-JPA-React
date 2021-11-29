package airport.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import airport.dto.TicketDTO;
import airport.model.Ticket;


@Component
public class TicketToTicketDTO implements Converter<Ticket, TicketDTO> {

	@Override
	public TicketDTO convert(Ticket ticket) {
		if(ticket==null){
			return null;
		}
		
		TicketDTO dto = new TicketDTO();
		
		dto.setId(ticket.getId());
		
		dto.setUserId(ticket.getUser().getId());
		dto.setUserFirstname(ticket.getUser().getFirstname());
		dto.setUserLastname(ticket.getUser().getLastname());
		dto.setUserJmbg(ticket.getUser().getJmbg());
		dto.setUserCity(ticket.getUser().getCity());
		
		dto.setFlightId(ticket.getFlight().getId());
		dto.setFlightDestination(ticket.getFlight().getDestination());
		dto.setFlightDatetime(ticket.getFlight().getDatetime());
		dto.setFlightPrice(ticket.getFlight().getPrice());
		dto.setFlightFreeSeats(ticket.getFlight().getFreeSeats());
		
		return dto;
	}
	
	public List<TicketDTO> convert(List<Ticket> tickets){
		List<TicketDTO> ret = new ArrayList<>();
		
		for(Ticket t: tickets){
			ret.add(convert(t));
		}
		
		return ret;
	}

}
