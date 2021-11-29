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

import airport.dto.RunwayDTO;
import airport.model.Runway;
import airport.service.RunwayService;
import airport.support.RunwayDTOToRunway;
import airport.support.RunwayToRunwayDTO;


//@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(value="/api/runways")
public class ApiRunwayController {
	
	@Autowired
	private RunwayService runwayService;
	
	@Autowired
	private RunwayToRunwayDTO toDTO; 
	
	@Autowired
	private RunwayDTOToRunway toRunway;
	

	@GetMapping("/all")
	ResponseEntity<List<RunwayDTO>> getAlls() {
		List<Runway> runwayPage = null;
		runwayPage = runwayService.findAll();
		return new ResponseEntity<>( toDTO.convert(runwayPage) , HttpStatus.OK);
	}	
	
		
	@GetMapping()
	ResponseEntity<List<RunwayDTO>> getAllRunways(
			@RequestParam(value="name", required=false) String name,
			@RequestParam(value="pageNum", defaultValue="0") int pageNum){
		
		Page<Runway> runwayPage = null;
		
		if(name != null ) {
			runwayPage = runwayService.search(name, pageNum);	
		}
		else {
			runwayPage = runwayService.findAll(pageNum);
		}

		HttpHeaders headers = new HttpHeaders();
		headers.add("totalPages", Integer.toString(runwayPage.getTotalPages()) );
		return new ResponseEntity<>( toDTO.convert(runwayPage.getContent()) , headers , HttpStatus.OK);
		
	}

	
	
	
	
	@GetMapping("/{id}")
	ResponseEntity<RunwayDTO> getRunwayById(@PathVariable Long id){
		Runway runway = runwayService.getById(id);
		if(runway==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>( toDTO.convert(runway), HttpStatus.OK);
	}
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	ResponseEntity<RunwayDTO> deleteRunway(@PathVariable Long id){
		Runway deleted = runwayService.delete(id);
		
		if(deleted == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>( toDTO.convert(deleted), HttpStatus.OK);
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping(consumes = "application/json")
	ResponseEntity<RunwayDTO> addRunway(@Valid @RequestBody RunwayDTO newRunwayDTO ){
		
		if(newRunwayDTO==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		Runway savedrunway = runwayService.save(toRunway.convert(newRunwayDTO));
		
		return new ResponseEntity<>( toDTO.convert(savedrunway), HttpStatus.CREATED);
	}
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping(value="/{id}" , consumes = "application/json")
	ResponseEntity<RunwayDTO> updateRunway( @PathVariable Long id, @Valid @RequestBody RunwayDTO runwayDTO){
				
		Runway persisted = runwayService.getById(id);
		
		persisted.setName(runwayDTO.getName());
		
		runwayService.save(persisted);
		
		return new ResponseEntity<>(toDTO.convert(persisted), HttpStatus.OK);
	}
	
	
	
	@ExceptionHandler(value=DataIntegrityViolationException.class)
	public ResponseEntity<Void> handle() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	

	
	
}
