package airport.service.impl;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import airport.model.ERole;
import airport.model.User;
import airport.repository.UserRepository;
import airport.service.UserService;

@Service
public class UserDetailsServiceImpl implements UserService, UserDetailsService{
	
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = (userRepository.findByUsername(username))
				  .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));

		return UserDetailsImpl.build(user);
	}


	@Override
	public User getById(Long id) {
		return userRepository.getById(id);
	}



	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public List<User> saveAll(List<User> users) {
		return userRepository.saveAll(users);
	}

	@Override
	public User delete(Long id) {
		User user = userRepository.getById(id);
		if(user!=null) {
			userRepository.delete(user);
		}
		return user;
	}
	
	@Override
	public Optional<User> findByUsername(String username) {
		return userRepository.findByUsername(username);
	}
	
	@Override
	public User findByUserName(String username) {
		return userRepository.findByUserName(username);
	}
	
	
	@Override
	public List<User> findByRoles_name(ERole name) {
		return userRepository.findByRoles_name(name);
	}
	
	
	@Override
	public Page<User> findByRoles_name(ERole name, int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 20);
		return userRepository.findByRoles_name(name, pageable);
	}
	
	
	@Override
	public Page<User> searchForEmployee(String username, String lastname, String city, int pageNum) {
		if( username != null) {
			username = '%' + username + '%';
		}
		if( lastname != null) {
			lastname = '%' + lastname + '%';
		}
		if( city != null) {
			city = '%' + city + '%';
		}
		PageRequest pageable = PageRequest.of(pageNum, 20);
		return userRepository.searchForEmployee(username, lastname, city, pageable);
	}


	@Override
	public Page<User> findAllForAdmin(int pageNum) {
		PageRequest pageable = PageRequest.of(pageNum, 20);
		return userRepository.findAllForAdmin(pageable);
	}
	
	@Override
	public Page<User> searchForAdmin(String username, String lastname, String city, int pageNum) {
		if( username != null) {
			username = '%' + username + '%';
		}
		if( lastname != null) {
			lastname = '%' + lastname + '%';
		}
		if( city != null) {
			city = '%' + city + '%';
		}
		PageRequest pageable = PageRequest.of(pageNum, 20);
		return userRepository.searchForAdmin(username, lastname, city, pageable);
	}


	
	

}
