package airport.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import airport.dto.PlaneDTO;
import airport.model.Plane;

@Component
public class PlaneToPlaneDTO implements Converter<Plane,PlaneDTO>{
	
	@Override
	public PlaneDTO convert(Plane plane) {
		if(plane==null){
			return null;
		}
		PlaneDTO dto = new PlaneDTO();
		dto.setId(plane.getId());
		dto.setName(plane.getName());
		dto.setBrand(plane.getBrand());
		dto.setModel(plane.getModel());
		dto.setSeats(plane.getSeats());
		
		return dto;
	}
	
	
	public List<PlaneDTO> convert(List<Plane> planes){
		List<PlaneDTO> ret = new ArrayList<>();
		
		for(Plane p: planes){
			ret.add(convert(p));
		}
		
		return ret;
	}

}
