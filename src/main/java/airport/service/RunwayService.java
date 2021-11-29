package airport.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import airport.model.Runway;

public interface RunwayService {
	
	Runway getById(Long id);
	List<Runway> findAll();
	Page<Runway> findAll(int pageNum);
	Runway save(Runway runway);
	List<Runway> saveAll(List<Runway> runways);
	Runway delete(Long id);
	
	Page<Runway> search(
			@Param("name") String name, 
			 int pageNum);

}
