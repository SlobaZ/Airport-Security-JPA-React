package airport.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import airport.model.Ticket;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
	
	@Query("SELECT t FROM Ticket t WHERE "
			+ "(:userId IS NULL or t.user.id = :userId ) AND "
			+ "(:flightId IS NULL or t.flight.id = :flightId ) "
			)
	Page<Ticket> search(
			@Param("userId") Long userId, 
			@Param("flightId") Long flightId,
			Pageable pageRequest);
	
	
}
