package airport.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import airport.model.Flight;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Long> {
	
	@Query("SELECT f FROM Flight f WHERE "
			+ "(:destination IS NULL or f.destination like :destination ) AND "
			+ "(:datetime IS NULL or f.datetime like :datetime ) AND "
			+ "(:planeId IS NULL or f.plane.id = :planeId ) "
			)
	Page<Flight> search(
			@Param("destination") String destination, 
			@Param("datetime") String datetime, 
			@Param("planeId") Long planeId, 
			Pageable pageRequest);

}
