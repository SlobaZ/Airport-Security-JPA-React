package airport.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import airport.model.Flight;
import airport.repository.FlightRepository;
import airport.service.FlightService;

@Service
public class JpaFlightService implements FlightService {
	
	@Autowired
	private FlightRepository flightRepository;

	@Override
	public Flight getById(Long id) {
		return flightRepository.getById(id);
	}

	@Override
	public List<Flight> findAll() {
		return flightRepository.findAll();
	}

	@Override
	public Page<Flight> findAll(int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 20);
		return flightRepository.findAll(pageable);
	}

	@Override
	public Flight save(Flight flight) {
		return flightRepository.save(flight);
	}

	@Override
	public List<Flight> saveAll(List<Flight> flights) {
		return flightRepository.saveAll(flights);
	}

	@Override
	public Flight delete(Long id) {
		Flight flight = flightRepository.getById(id);
		if(flight!=null) {
			flightRepository.delete(flight);
		}
		return flight;
	}

	@Override
	public Page<Flight> search(String destination, String datetime, Long planeId, int pageNum) {
		if( destination != null) {
			destination = '%' + destination + '%';
		}
		PageRequest pageable = PageRequest.of(pageNum, 20);
		return flightRepository.search(destination, datetime, planeId, pageable);
	}

}
