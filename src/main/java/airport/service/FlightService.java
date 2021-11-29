package airport.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import airport.model.Flight;

public interface FlightService {
	
	Flight getById(Long id);
	List<Flight> findAll();
	Page<Flight> findAll(int pageNum);
	Flight save(Flight flight);
	List<Flight> saveAll(List<Flight> flights);
	Flight delete(Long id);
	
	Page<Flight> search(
			@Param("destination") String destination, 
			@Param("datetime") String datetime, 
			@Param("planeId") Long planeId,
			 int pageNum);

}
