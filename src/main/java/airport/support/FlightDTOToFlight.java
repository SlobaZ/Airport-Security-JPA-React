package airport.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import airport.dto.FlightDTO;
import airport.model.Flight;
import airport.model.Plane;
import airport.model.Runway;
import airport.service.FlightService;
import airport.service.PlaneService;
import airport.service.RunwayService;



@Component
public class FlightDTOToFlight implements Converter<FlightDTO, Flight>{

	@Autowired
	private FlightService flightService;
	
	@Autowired
	private PlaneService planeService;
	
	@Autowired
	private RunwayService runwayService;
		
	@Override
	public Flight convert(FlightDTO flightDTO) {
		
		Plane plane = planeService.getById(flightDTO.getPlaneId());
		Runway runway = runwayService.getById(flightDTO.getRunwayId());
		
		Flight flight = null;
		
		if(flightDTO.getId()!=null){
			flight = flightService.getById(flightDTO.getId());
			
			if(flight == null){
				throw new IllegalStateException("Tried to "
						+ "modify a non-existant Flight");
			}
		}
		else {
			flight = new Flight();
		}
		
		flight.setId(flightDTO.getId());
		flight.setDestination(flightDTO.getDestination());
		flight.setDatetime(flightDTO.getDatetime());
		flight.setPrice(flightDTO.getPrice());
		flight.setFreeSeats(flightDTO.getFreeSeats());
		
		flight.setPlane(plane);
		flight.setRunway(runway);
		
		return flight;
	}

	

	public List<Flight> convert(List<FlightDTO> flightDTOs){
		List<Flight> ret = new ArrayList<>();
		
		for(FlightDTO flightDTO : flightDTOs){
			ret.add(convert(flightDTO));
		}
		
		return ret;
	}
}
