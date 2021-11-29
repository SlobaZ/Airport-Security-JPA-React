package airport.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import airport.model.Runway;

@Repository
public interface RunwayRepository extends JpaRepository<Runway,Long> {
	
	@Query("SELECT r FROM Runway r WHERE "
			+ "(:name IS NULL or r.name like :name ) "
			)
	Page<Runway> search(
			@Param("name") String name, 
			Pageable pageRequest);

}


