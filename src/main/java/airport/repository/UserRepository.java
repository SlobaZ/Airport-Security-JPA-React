package airport.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import airport.model.ERole;
import airport.model.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
	
	Optional<User> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByPassword(String password);
	
	@Query("SELECT u FROM User u WHERE u.username = :username")
	User findByUserName(String username);	
	
	List<User> findByRoles_name(ERole name);
	
	Page<User> findByRoles_name(ERole name, Pageable pageRequest);

	
	@Query("SELECT u FROM User u join u.roles r WHERE "
			+ "( NOT r.name='Role_Admin' AND NOT r.name='Role_Employee' ) AND "
			+ "(:username IS NULL or u.username like :username ) AND "
			+ "(:lastname IS NULL or u.lastname like :lastname ) AND "
			+ "(:city IS NULL or u.city like :city ) "
			)
	Page<User> searchForEmployee(
			@Param("username") String username, 
			@Param("lastname") String lastname, 
			@Param("city") String city, 
			Pageable pageRequest);
		
	
	@Query("SELECT u FROM User u ")
	Page<User> findAllForAdmin(Pageable pageRequest);
	
	
	@Query("SELECT u FROM User u WHERE "
			+ "(:username IS NULL or u.username like :username ) AND "
			+ "(:lastname IS NULL or u.lastname like :lastname ) AND "
			+ "(:city IS NULL or u.city like :city ) "
			)
	Page<User> searchForAdmin(
			@Param("username") String username, 
			@Param("lastname") String lastname, 
			@Param("city") String city, 
			Pageable pageRequest);
	
	

	
	
}
