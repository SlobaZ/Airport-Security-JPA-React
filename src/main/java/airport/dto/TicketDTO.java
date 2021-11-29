package airport.dto;

public class TicketDTO {
	
	private Long id;
	
	private Long userId;
	private String userFirstname;
	private String userLastname;
	private String userJmbg;
	private String userCity;
	
	private Long flightId;
	private String flightDestination;
	private String flightDatetime;
	private Double flightPrice;
	private Integer flightFreeSeats;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getUserFirstname() {
		return userFirstname;
	}
	public void setUserFirstname(String userFirstname) {
		this.userFirstname = userFirstname;
	}
	public String getUserLastname() {
		return userLastname;
	}
	public void setUserLastname(String userLastname) {
		this.userLastname = userLastname;
	}
	public String getUserJmbg() {
		return userJmbg;
	}
	public void setUserJmbg(String userJmbg) {
		this.userJmbg = userJmbg;
	}
	public String getUserCity() {
		return userCity;
	}
	public void setUserCity(String userCity) {
		this.userCity = userCity;
	}
	
	
	
	public Long getFlightId() {
		return flightId;
	}
	public void setFlightId(Long flightId) {
		this.flightId = flightId;
	}
	public String getFlightDestination() {
		return flightDestination;
	}
	public void setFlightDestination(String flightDestination) {
		this.flightDestination = flightDestination;
	}
	public String getFlightDatetime() {
		return flightDatetime;
	}
	public void setFlightDatetime(String flightDatetime) {
		this.flightDatetime = flightDatetime;
	}
	public Double getFlightPrice() {
		return flightPrice;
	}
	public void setFlightPrice(Double flightPrice) {
		this.flightPrice = flightPrice;
	}
	public Integer getFlightFreeSeats() {
		return flightFreeSeats;
	}
	public void setFlightFreeSeats(Integer flightFreeSeats) {
		this.flightFreeSeats = flightFreeSeats;
	}
	
	
	
	

}
