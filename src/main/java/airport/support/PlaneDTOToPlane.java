package airport.support;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import airport.dto.PlaneDTO;
import airport.model.Plane;
import airport.service.PlaneService;

@Component
public class PlaneDTOToPlane implements Converter<PlaneDTO,Plane>{
	
	@Autowired
	private PlaneService planeService;
	
	@Override
	public Plane convert(PlaneDTO dto) {
		
		Plane plane = null;
		
		if(dto.getId()!=null){
			plane = planeService.getById(dto.getId());
			
			if(plane == null){
				throw new IllegalStateException("Tried to "
						+ "modify a non-existant Plane");
			}
		}
		else {
			plane = new Plane();
		}
		
		plane.setId(dto.getId());
		plane.setName(dto.getName());
		plane.setBrand(dto.getBrand());
		plane.setModel(dto.getModel());
		plane.setSeats(dto.getSeats());
		
		return plane;
	}
	
	
	public List<Plane> convert (List<PlaneDTO> dtoPlanes){
		List<Plane> planes = new ArrayList<>();
		
		for(PlaneDTO dto : dtoPlanes){
			planes.add(convert(dto));
		}
		
		return planes;
	}


}
