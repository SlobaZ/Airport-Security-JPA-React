package airport.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import airport.dto.RunwayDTO;
import airport.model.Runway;
import airport.service.RunwayService;

@Component
public class RunwayDTOToRunway implements Converter<RunwayDTO,Runway> {
	
	@Autowired
	private RunwayService runwayService;
	
	@Override
	public Runway convert(RunwayDTO dto) {
		
		Runway runway = null;
		
		if(dto.getId()!=null){
			runway = runwayService.getById(dto.getId());
			
			if(runway == null){
				throw new IllegalStateException("Tried to "
						+ "modify a non-existant Runway");
			}
		}
		else {
			runway = new Runway();
		}
		
		runway.setId(dto.getId());
		runway.setName(dto.getName());
		
		return runway;
	}
	
	
	public List<Runway> convert (List<RunwayDTO> dtoRunways){
		List<Runway> runways = new ArrayList<>();
		
		for(RunwayDTO dto : dtoRunways){
			runways.add(convert(dto));
		}
		return runways;
	}



}
