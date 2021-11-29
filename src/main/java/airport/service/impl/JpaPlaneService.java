package airport.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import airport.model.Plane;
import airport.repository.PlaneRepository;
import airport.service.PlaneService;

@Service
public class JpaPlaneService implements PlaneService {
	
	@Autowired
	private PlaneRepository planeRepository;

	@Override
	public Plane getById(Long id) {
		return planeRepository.getById(id);
	}

	@Override
	public List<Plane> findAll() {
		return planeRepository.findAll();
	}

	@Override
	public Page<Plane> findAll(int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 20);
		return planeRepository.findAll(pageable);
	}

	@Override
	public Plane save(Plane plane) {
		return planeRepository.save(plane);
	}

	@Override
	public List<Plane> saveAll(List<Plane> planes) {
		return planeRepository.saveAll(planes);
	}

	@Override
	public Plane delete(Long id) {
		Plane plane = planeRepository.getById(id);
		if(plane!=null) {
			planeRepository.delete(plane);
		}
		return plane;
	}

	@Override
	public Page<Plane> search(String name, String brand, int pageNum) {
		if( name != null) {
			name = '%' + name + '%';
		}
		if( brand != null) {
			brand = '%' + brand + '%';
		}
		PageRequest pageable = PageRequest.of(pageNum, 20);
		return planeRepository.search(name, brand, pageable);
	}

}
