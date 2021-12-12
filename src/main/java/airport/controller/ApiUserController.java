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
import org.springframework.security.authentication.AuthenticationManager;
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

import airport.dto.UserDTO;
import airport.model.ERole;
import airport.model.User;
import airport.service.UserService;
import airport.support.UserDTOToUser;
import airport.support.UserToUserDTO;


//@CrossOrigin(origins="http://localhost:3000")
@RestController
@RequestMapping(value="/api/users")
public class ApiUserController {
	

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserToUserDTO toDTO; 
	
	@Autowired
	private UserDTOToUser toUser;
	
		
	
	@GetMapping("/all")
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE')")
	ResponseEntity<List<UserDTO>> getAlls() {
		List<User> users = null;
		users = userService.findByRoles_name(ERole.ROLE_PASSENGER);
		return new ResponseEntity<>( toDTO.convert(users) , HttpStatus.OK);
	}	
	
	
	@GetMapping()
	@PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_EMPLOYEE')")
	ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam (required = false) String username,
			@RequestParam (required = false) String lastname,
			@RequestParam (required = false) String city,
			@RequestParam(value="pageNum", defaultValue="0") int pageNum){
			
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String loggedUser = String.valueOf ( userDetails.getUsername() );
		
		Page<User> userPage = null;
		
		if("Admin".equals(loggedUser)) {
			if(username != null || lastname != null || city != null ) {
				userPage = userService.searchForAdmin(username, lastname, city, pageNum);
			}
			else {
				userPage = userService.findAllForAdmin(pageNum);
			}
		}
		if("Employee".equals(loggedUser)) {
			if(username != null || lastname != null || city != null ) {
				userPage = userService.searchForEmployee(username, lastname, city, pageNum);
			}
			else {
				userPage = userService.findByRoles_name(ERole.ROLE_PASSENGER, pageNum);
			}
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("totalPages", Integer.toString(userPage.getTotalPages()) );
		return new ResponseEntity<>( toDTO.convert(userPage.getContent()) , headers , HttpStatus.OK);
		
	}
	
	
	
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYEE')")
	ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
		User user = userService.getById(id);
		if(user==null){
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>( toDTO.convert(user), HttpStatus.OK);
	}
	
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{id}")
	ResponseEntity<UserDTO> deleteUser(@PathVariable Long id){
		User deleted = userService.delete(id);
		
		if(deleted == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<>( toDTO.convert(deleted), HttpStatus.OK);
	}
	
	
	
	@PreAuthorize("hasRole('ADMIN') || hasRole('EMPLOYEE')")
	@PostMapping(consumes = "application/json")
	ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO newUserDTO ){
		
		if(newUserDTO==null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		User savedUser = userService.save(toUser.convert(newUserDTO));
		
		return new ResponseEntity<>( toDTO.convert(savedUser), HttpStatus.CREATED);
	}
	
	
	
	@PreAuthorize("hasRole('ADMIN') || hasRole('EMPLOYEE')")
	@PutMapping(value="/{id}" , consumes = "application/json")
	ResponseEntity<UserDTO> updateUser( @PathVariable Long id, @Valid @RequestBody UserDTO UserDTO){
				
		User persisted = userService.getById(id);
		
		persisted.setUsername(UserDTO.getUsername());
		persisted.setPassword(UserDTO.getPassword());
		persisted.setFirstname(UserDTO.getFirstname());
		persisted.setLastname(UserDTO.getLastname());
		persisted.setJmbg(UserDTO.getJmbg());
		persisted.setCity(UserDTO.getCity());
		
		userService.save(persisted);
		
		return new ResponseEntity<>(toDTO.convert(persisted), HttpStatus.OK);
	}
	
	
	
	@ExceptionHandler(value=DataIntegrityViolationException.class)
	public ResponseEntity<Void> handle() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	
	
	
	
	

	
	
}
