package airport.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import airport.dto.TicketDTO;
import airport.model.Flight;
import airport.model.Ticket;
import airport.model.User;
import airport.service.FlightService;
import airport.service.TicketService;
import airport.service.UserService;
import airport.support.TicketDTOToTicket;
import airport.support.TicketToTicketDTO;



//@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(value="/api/tickets")
public class ApiTicketController {
	
	@Autowired
	private TicketService ticketService;
	
	@Autowired
	private TicketToTicketDTO toDTO; 
	
	@Autowired
	private TicketDTOToTicket toTicket;
	
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private UserService userService;
	
	

	@GetMapping("/all")
	ResponseEntity<List<TicketDTO>> getAlls() {
		List<Ticket> ticketPage = null;
		ticketPage = ticketService.findAll();
		return new ResponseEntity<>( toDTO.convert(ticketPage) , HttpStatus.OK);
	}	
	
	
	
	@GetMapping()
	ResponseEntity<List<TicketDTO>> getAllTickets(
			@RequestParam (required = false) Long userId,
			@RequestParam (required = false) Long flightId,
			@RequestParam(value="pageNum", defaultValue="0") int pageNum){
		
		Page<Ticket> ticketPage = null;
		
		if(userId != null || flightId != null ) {
			ticketPage = ticketService.search(userId, flightId, pageNum);
		}
		else {
			ticketPage = ticketService.findAll(pageNum);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("totalPages", Integer.toString(ticketPage.getTotalPages()) );
		return new ResponseEntity<>( toDTO.convert(ticketPage.getContent()) , headers , HttpStatus.OK);
		
	}

	
	
	
	@GetMapping("/{id}")
	ResponseEntity<TicketDTO> getTicketById(@PathVariable Long id){
		Ticket ticket = ticketService.getById(id);
		if(ticket==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>( toDTO.convert(ticket), HttpStatus.OK);
	}
	
	
	
	
	@PreAuthorize("hasRole('EMPLOYEE') || hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	ResponseEntity<TicketDTO> deleteTicket(@PathVariable Long id){
		Ticket deleted = ticketService.delete(id);
		
		if(deleted == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>( toDTO.convert(deleted), HttpStatus.OK);
	}
		
	
	
	@PreAuthorize("hasRole('PASSENGER') || hasRole('EMPLOYEE') || hasRole('ADMIN') ")
	@PostMapping(consumes = "application/json")
	ResponseEntity<TicketDTO> addTicket(@Valid @RequestBody TicketDTO newTicketDTO ){
		
		if(newTicketDTO==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Flight flight = flightService.getById(newTicketDTO.getFlightId());
		
		if(flight.getFreeSeats()>0) {
			flight.setFreeSeats(flight.getFreeSeats()-1);
			flightService.save(flight);
		
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Ticket savedTicket = ticketService.save(toTicket.convert(newTicketDTO));
		return new ResponseEntity<>( toDTO.convert(savedTicket), HttpStatus.CREATED);
		
	}
	
	
	
	@PreAuthorize("hasRole('PASSENGER') || hasRole('EMPLOYEE') || hasRole('ADMIN')")
	@PutMapping(value="/{id}" , consumes = "application/json")
	ResponseEntity<TicketDTO> updateTicket( @PathVariable Long id, @Valid @RequestBody TicketDTO ticketDTO){
				
		Ticket persisted = ticketService.getById(id);
		
		User user = userService.getById(ticketDTO.getUserId());
		Flight flight = flightService.getById(ticketDTO.getFlightId());
		
		persisted.setUser(user);
		persisted.setFlight(flight);
		
		ticketService.save(persisted);
		
		return new ResponseEntity<>(toDTO.convert(persisted), HttpStatus.OK);
	}
	
	
	
	
	@PreAuthorize("hasRole('PASSENGER')")
	@PostMapping(value="/buyTicket" , consumes = "application/json")
	ResponseEntity<TicketDTO> buyTicket(@Valid @RequestBody TicketDTO newTicketDTO ){
		
		if(newTicketDTO==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Flight flight = flightService.getById(newTicketDTO.getFlightId());
		
		if(flight.getFreeSeats()>0) {
			flight.setFreeSeats(flight.getFreeSeats()-1);
			flightService.save(flight);
		
		}
		else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	
		
		Ticket savedTicket = ticketService.save(toTicket.convert(newTicketDTO));
		
		return new ResponseEntity<>( toDTO.convert(savedTicket), HttpStatus.CREATED);
	}
	
	
	
	
	@ExceptionHandler(value=DataIntegrityViolationException.class)
	public ResponseEntity<Void> handle() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	

	
	
}
