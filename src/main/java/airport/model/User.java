package airport.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;



@Entity
@Table(name="user" , uniqueConstraints = { @UniqueConstraint(columnNames="username") ,
										   @UniqueConstraint(columnNames="password")} )
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "*Please provide a username")
	@Size(min=5)
	private String username;
	
	@NotEmpty(message = "*Please provide a password")
	@Size(min=6)
	private String password;
	
	@NotEmpty(message = "*Please provide a firstname")
	private String firstname;
	
	@NotEmpty(message = "*Please provide a lastname")
	private String lastname;
	
	@PositiveOrZero(message = "*Only positive number")
	@Size(min=13,max=13)
	private String jmbg;
	
	@NotEmpty(message = "*Please provide a city")
	private String city;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();
	

	public User() {
	}
	

	public User(Long id, String username, String firstname, String lastname,String jmbg,  String city, Set<Role> roles) {
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.jmbg = jmbg;
		this.city = city;
		this.roles = roles;
	}



	public User(String username,String password,String firstname,String lastname,String jmbg,String city) {
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.jmbg = jmbg;
		this.city = city;
	}



	public User(Long id, String username, String password, String firstname, String lastname, String jmbg, String city) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.jmbg = jmbg;
		this.city = city;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getJmbg() {
		return jmbg;
	}

	public void setJmbg(String jmbg) {
		this.jmbg = jmbg;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		roles.add(role);
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", jmbg=" + jmbg + ", city=" + city +"]";
	}
	
	
	
	

	
}
