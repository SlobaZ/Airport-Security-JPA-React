package airport.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;

@Entity
@Table(name="plane")
public class Plane { 
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty(message = "*Please provide a name")
	private String name;
	
	@NotEmpty(message = "*Please provide a brand")
	private String brand;
	
	@NotEmpty(message = "*Please provide a model")
	private String model;
	
	@PositiveOrZero(message = "*Only positive number")
	private Integer seats;
	
	@OneToMany(mappedBy="plane",cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Flight> flights = new ArrayList<Flight>();
	

	public Plane() {
	}

	public Plane(Long id, String name, String brand, String model, Integer seats) {
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.model = model;
		this.seats = seats;
	}


	

	public Plane(Long id, String name, String brand, String model, Integer seats, List<Flight> flights) {
		this.id = id;
		this.name = name;
		this.brand = brand;
		this.model = model;
		this.seats = seats;
		this.flights = flights;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getSeats() {
		return seats;
	}

	public void setSeats(Integer seats) {
		this.seats = seats;
	}

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
	
	
	

}
