package airport.service.impl;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import airport.model.User;


public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String username;

	@JsonIgnore
	private String password;
	
	private String firstname;
	
	private String lastname;
	
	private String jmbg;
	
	private String city;

	private Collection<? extends GrantedAuthority> authorities;
	
	

	public UserDetailsImpl(Long id, String username, String password, String firstname, String lastname, String jmbg, String city, 
							Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.jmbg = jmbg;
		this.city = city;
		this.authorities = authorities;
	}
	

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(), 
				user.getUsername(), 
				user.getPassword(), 
				user.getFirstname(),
				user.getLastname(),
				user.getJmbg(),
				user.getCity(),
				authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}
	

	public String getFirstname() {
		return firstname;
	}
	
	public String getLastname() {
		return lastname;
	}

	public String getJmbg() {
		return jmbg;
	}
	
	public String getCity() {
		return city;
	}



	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}


	@Override
	public String toString() {
		return "UserDetailsImpl [id=" + id + ", username=" + username + ", password=" + password + ", firstname="
				+ firstname + ", lastname=" + lastname + ", jmbg=" + jmbg + ", city=" + city + ", authorities="
				+ authorities + "]";
	}

	
	//
	
	
	
}
