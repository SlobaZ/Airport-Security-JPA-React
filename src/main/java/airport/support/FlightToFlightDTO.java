package airport.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import airport.dto.FlightDTO;
import airport.model.Flight;


@Component
public class FlightToFlightDTO implements Converter<Flight, FlightDTO>{

	@Override
	public FlightDTO convert(Flight flight) {
		
		FlightDTO dto = new FlightDTO();
		
		dto.setId(flight.getId());	
		dto.setDestination(flight.getDestination());
		dto.setDatetime(flight.getDatetime());
		dto.setPrice(flight.getPrice());
		dto.setFreeSeats(flight.getFreeSeats());
		
		dto.setPlaneId(flight.getPlane().getId());
		dto.setPlaneName(flight.getPlane().getName());
		dto.setPlaneBrand(flight.getPlane().getBrand());
		dto.setPlaneModel(flight.getPlane().getModel());
		dto.setPlaneSeats(flight.getPlane().getSeats());
		
		dto.setRunwayId(flight.getRunway().getId());
		dto.setRunwayName(flight.getRunway().getName());

		return dto;
	}

	public List<FlightDTO> convert(List<Flight> flights){
		List<FlightDTO> ret = new ArrayList<>();
		
		for(Flight flight : flights){
			ret.add(convert(flight));
		}
		
		return ret;
	}

}
