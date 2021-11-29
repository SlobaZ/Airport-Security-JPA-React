package airport.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import airport.dto.RunwayDTO;
import airport.model.Runway;



@Component
public class RunwayToRunwayDTO implements Converter<Runway,RunwayDTO> {
	
	@Override
	public RunwayDTO convert(Runway runway) {
		if(runway==null){
			return null;
		}
		RunwayDTO dto = new RunwayDTO();
		dto.setId(runway.getId());
		dto.setName(runway.getName());
		
		return dto;
	}
	
	
	public List<RunwayDTO> convert(List<Runway> runways){
		List<RunwayDTO> ret = new ArrayList<>();
		
		for(Runway p: runways){
			ret.add(convert(p));
		}
		
		return ret;
	}


}
