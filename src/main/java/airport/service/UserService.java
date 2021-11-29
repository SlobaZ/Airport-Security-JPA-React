package airport.service;

import java.util.List;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;

import airport.model.ERole;
import airport.model.User;

public interface UserService {
	
	User getById(Long id);
	
	User save(User user);
	List<User> saveAll(List<User> users);
	User delete(Long id);
	
	Optional<User> findByUsername(String username);
	
	
	List<User> findByRoles_name(ERole name);
	
	Page<User> findByRoles_name(ERole name, int pageNum);

	
	Page<User> searchForEmployee(
			@Param("username") String username, 
			@Param("lastname") String lastname, 
			@Param("city") String city,
			 int pageNum);
	
	
	Page<User> findAllForAdmin(int pageNum);
	
	Page<User> searchForAdmin(
			@Param("username") String username, 
			@Param("lastname") String lastname, 
			@Param("city") String city,
			 int pageNum);
	
	

}
