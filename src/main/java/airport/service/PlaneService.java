package airport.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import airport.model.Plane;

public interface PlaneService {
	
	Plane getById(Long id);
	List<Plane> findAll();
	Page<Plane> findAll(int pageNum);
	Plane save(Plane plane);
	List<Plane> saveAll(List<Plane> planes);
	Plane delete(Long id);
	
	Page<Plane> search(
			@Param("name") String name, 
			@Param("brand") String brand, 
			 int pageNum);

}
