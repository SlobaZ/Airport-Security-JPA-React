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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name="flight")
public class Flight {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "*Please provide a destination")
	private String destination;
	
	@NotEmpty(message = "*Please provide a date & time")
	private String datetime;
	
	@PositiveOrZero(message = "*Only positive number")
	private Double price;
	
	@PositiveOrZero(message = "*Only positive number")
	private Integer freeSeats;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "plane")
	private Plane plane = new Plane();
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "runway")
	private Runway runway = new Runway();
	
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "flight_users", 
				joinColumns = @JoinColumn(name = "flight_id"), 
				inverseJoinColumns = @JoinColumn(name = "user_id"))
	private Set<User> users = new HashSet<>();

	public Flight() {
	}

	
	public Flight(Long id, String destination, String datetime,  Double price, Integer freeSeats) {
		this.id = id;
		this.destination = destination;
		this.datetime = datetime;
		this.price = price;
		this.freeSeats = freeSeats;
	}
	
	
	public Flight(Long id, String destination,  String datetime, Double price,  Integer freeSeats, Plane plane, Runway runway) {
		this.id = id;
		this.destination = destination;
		this.datetime = datetime;
		this.price = price;
		this.freeSeats = freeSeats;
		this.plane = plane;
		this.runway = runway;
	}

	public Flight(Long id, String destination, String datetime, Double price, Integer freeSeats, Plane plane, Runway runway, Set<User> users) {
		this.id = id;
		this.destination = destination;
		this.datetime = datetime;
		this.price = price;
		this.freeSeats = freeSeats;
		this.plane = plane;
		this.runway = runway;
		this.users = users;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getFreeSeats() {
		return freeSeats;
	}

	public void setFreeSeats(Integer freeSeats) {
		this.freeSeats = freeSeats;
	}


	public Plane getPlane() {
		return plane;
	}

	public void setPlane(Plane plane) {
		this.plane = plane;
	}

	public Runway getRunway() {
		return runway;
	}

	public void setRunway(Runway runway) {
		this.runway = runway;
	}

	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

	public void addUser(User user) {
		users.add(user);
	}

	




	

}
