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

@Entity
@Table(name="runway")
public class Runway {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@NotEmpty(message = "*Please provide a name")
	private String name;
		
	@OneToMany(mappedBy="runway", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Flight> flights = new ArrayList<Flight>();

	public Runway() {
	}

	public Runway(Long id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public Runway(Long id, String name, List<Flight> flights) {
		this.id = id;
		this.name = name;
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

	public List<Flight> getFlights() {
		return flights;
	}

	public void setFlights(List<Flight> flights) {
		this.flights = flights;
	}
	
	
	
	
	

}
