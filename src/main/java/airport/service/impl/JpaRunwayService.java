package airport.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import airport.model.Runway;
import airport.repository.RunwayRepository;
import airport.service.RunwayService;

@Service
public class JpaRunwayService implements RunwayService{
	
	@Autowired
	private RunwayRepository runwayRepository;

	@Override
	public Runway getById(Long id) {
		return runwayRepository.getById(id);
	}

	@Override
	public List<Runway> findAll() {
		return runwayRepository.findAll();
	}

	@Override
	public Page<Runway> findAll(int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 20);
		return runwayRepository.findAll(pageable);
	}

	@Override
	public Runway save(Runway runway) {
		return runwayRepository.save(runway);
	}

	@Override
	public List<Runway> saveAll(List<Runway> runways) {
		return runwayRepository.saveAll(runways);
	}

	@Override
	public Runway delete(Long id) {
		Runway runway = runwayRepository.getById(id);
		if(runway!=null) {
			runwayRepository.delete(runway);
		}
		return runway;
	}

	@Override
	public Page<Runway> search(String name, int pageNum) {
		if( name != null) {
			name = '%' + name + '%';
		}
		PageRequest pageable = PageRequest.of(pageNum, 20);
		return runwayRepository.search(name, pageable);
	}
	

}
