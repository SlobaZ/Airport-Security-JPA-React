package airport.controller;

import java.util.List;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import airport.dto.PlaneDTO;
import airport.model.Plane;
import airport.service.PlaneService;
import airport.support.PlaneDTOToPlane;
import airport.support.PlaneToPlaneDTO;



//@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(value="/api/planes")
public class ApiPlaneController {
	
	@Autowired
	private PlaneService planeService;
	
	@Autowired
	private PlaneToPlaneDTO toDTO; 
	
	@Autowired
	private PlaneDTOToPlane toPlane;
	

	@GetMapping("/all")
	ResponseEntity<List<PlaneDTO>> getAlls() {
		List<Plane> planePage = null;
		planePage = planeService.findAll();
		return new ResponseEntity<>( toDTO.convert(planePage) , HttpStatus.OK);
	}	
	
		
	@GetMapping()
	ResponseEntity<List<PlaneDTO>> getAllPlanes(
			@RequestParam (required = false) String name,
			@RequestParam (required = false) String brand,
			@RequestParam(value="pageNum", defaultValue="0") int pageNum){
		
		Page<Plane> planePage = null;
		
		if(name != null || brand != null ) {
			planePage = planeService.search(name, brand, pageNum);
		}
		else {
			planePage = planeService.findAll(pageNum);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("totalPages", Integer.toString(planePage.getTotalPages()) );
		return new ResponseEntity<>( toDTO.convert(planePage.getContent()) , headers , HttpStatus.OK);
		
	}

	
	
	
	
	@GetMapping("/{id}")
	ResponseEntity<PlaneDTO> getPlaneById(@PathVariable Long id){
		Plane plane = planeService.getById(id);
		if(plane==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>( toDTO.convert(plane), HttpStatus.OK);
	}
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	ResponseEntity<PlaneDTO> deletePlane(@PathVariable Long id){
		Plane deleted = planeService.delete(id);
		
		if(deleted == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>( toDTO.convert(deleted), HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(consumes = "application/json")
	ResponseEntity<PlaneDTO> addPlane(@Valid @RequestBody PlaneDTO newplaneDTO ){
		
		if(newplaneDTO==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Plane savedplane = planeService.save(toPlane.convert(newplaneDTO));
		
		return new ResponseEntity<>( toDTO.convert(savedplane), HttpStatus.CREATED);
	}
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value="/{id}" , consumes = "application/json")
	ResponseEntity<PlaneDTO> updatePlane( @PathVariable Long id, @Valid @RequestBody PlaneDTO planeDTO){
				
		Plane persisted = planeService.getById(id);
		
		persisted.setName(planeDTO.getName());
		persisted.setBrand(planeDTO.getBrand());
		persisted.setModel(planeDTO.getModel());
		persisted.setSeats(planeDTO.getSeats());
		
		planeService.save(persisted);
		
		return new ResponseEntity<>(toDTO.convert(persisted), HttpStatus.OK);
	}
	
	
	
	@ExceptionHandler(value=DataIntegrityViolationException.class)
	public ResponseEntity<Void> handle() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	

	
	
}
