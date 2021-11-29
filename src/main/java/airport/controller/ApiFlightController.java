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

import airport.dto.FlightDTO;
import airport.model.Flight;
import airport.model.Plane;
import airport.model.Runway;
import airport.service.FlightService;
import airport.service.PlaneService;
import airport.service.RunwayService;
import airport.support.FlightDTOToFlight;
import airport.support.FlightToFlightDTO;


//@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(value="/api/flights")
public class ApiFlightController {
	
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PlaneService planeService;
	
	@Autowired
	private RunwayService runwayService;
	
	@Autowired
	private FlightToFlightDTO toDTO;
	
	@Autowired
	private FlightDTOToFlight toFlight;
	

	@GetMapping("/all")
	ResponseEntity<List<FlightDTO>> getAlls() {
		List<Flight> flightPage = null;
		flightPage = flightService.findAll();
		return new ResponseEntity<>( toDTO.convert(flightPage) , HttpStatus.OK);
	}	
	
		
	@GetMapping()
	ResponseEntity<List<FlightDTO>> getAllFlights(
			@RequestParam (required = false) String destination,
			@RequestParam (required = false) String datetime,
			@RequestParam (required = false) Long planeId,
			@RequestParam(value="pageNum", defaultValue="0") int pageNum){
		
		Page<Flight> flightPage = null;
		
		if(destination != null || datetime != null || planeId!=null ) {
			flightPage = flightService.search(destination, datetime, planeId, pageNum);
		}
		else {
			flightPage = flightService.findAll(pageNum);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("totalPages", Integer.toString(flightPage.getTotalPages()) );
		return new ResponseEntity<>( toDTO.convert(flightPage.getContent()) , headers , HttpStatus.OK);
		
	}

	
	
	
	
	@GetMapping("/{id}")
	ResponseEntity<FlightDTO> getFlightById(@PathVariable Long id){
		Flight flight = flightService.getById(id);
		if(flight==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>( toDTO.convert(flight), HttpStatus.OK);
	}
	
	
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@DeleteMapping("/{id}")
	ResponseEntity<FlightDTO> deleteFlight(@PathVariable Long id){
		Flight deleted = flightService.delete(id);
		
		if(deleted == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>( toDTO.convert(deleted), HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PostMapping(consumes = "application/json")
	ResponseEntity<FlightDTO> addFlight(@Valid @RequestBody FlightDTO newFlightDTO ){
		
		if(newFlightDTO==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Flight savedFlight = flightService.save(toFlight.convert(newFlightDTO));
		
		return new ResponseEntity<>( toDTO.convert(savedFlight), HttpStatus.CREATED);
	}
	
	
	
	@PreAuthorize("hasRole('EMPLOYEE')")
	@PutMapping(value="/{id}" , consumes = "application/json")
	ResponseEntity<FlightDTO> updateFlight( @PathVariable Long id, @Valid @RequestBody FlightDTO flightDTO){
				
		Flight persisted = flightService.getById(id);
		
		Plane plane = planeService.getById(flightDTO.getPlaneId());
		Runway runway = runwayService.getById(flightDTO.getRunwayId());
		
		persisted.setDestination(flightDTO.getDestination());
		persisted.setPrice(flightDTO.getPrice());
		persisted.setDatetime(flightDTO.getDatetime());
		persisted.setFreeSeats(flightDTO.getFreeSeats());

		persisted.setPlane(plane);
		persisted.setRunway(runway);
		
		flightService.save(persisted);
		
		return new ResponseEntity<>(toDTO.convert(persisted), HttpStatus.OK);
	}
	
	
	
	@ExceptionHandler(value=DataIntegrityViolationException.class)
	public ResponseEntity<Void> handle() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	

	
	
}
