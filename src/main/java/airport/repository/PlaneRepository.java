package airport.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import airport.model.Plane;

@Repository
public interface PlaneRepository extends JpaRepository<Plane,Long> {
	
	@Query("SELECT p FROM Plane p WHERE "
			+ "(:name IS NULL or p.name like :name ) AND "
			+ "(:brand IS NULL or p.brand like :brand ) "
			)
	Page<Plane> search(
			@Param("name") String name, 
			@Param("brand") String brand,  
			Pageable pageRequest);

}
