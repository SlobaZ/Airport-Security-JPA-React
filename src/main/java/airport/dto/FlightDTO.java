package airport.dto;

public class FlightDTO {

	private Long id;
	private String destination;
	private String datetime;
	private Double price;
	private Integer freeSeats;
	
	private Long planeId;
	private String planeName;
	private String planeBrand;
	private String planeModel;
	private Integer planeSeats;
	
	private Long runwayId;
	private String runwayName;
	
	
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
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getDatetime() {
		return datetime;
	}
	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}
	public Integer getFreeSeats() {
		return freeSeats;
	}
	public void setFreeSeats(Integer freeSeats) {
		this.freeSeats = freeSeats;
	}
	
	
	
	public Long getPlaneId() {
		return planeId;
	}
	public void setPlaneId(Long planeId) {
		this.planeId = planeId;
	}
	public String getPlaneName() {
		return planeName;
	}
	public void setPlaneName(String planeName) {
		this.planeName = planeName;
	}
	public String getPlaneBrand() {
		return planeBrand;
	}
	public void setPlaneBrand(String planeBrand) {
		this.planeBrand = planeBrand;
	}
	public String getPlaneModel() {
		return planeModel;
	}
	public void setPlaneModel(String planeModel) {
		this.planeModel = planeModel;
	}
	public Integer getPlaneSeats() {
		return planeSeats;
	}
	public void setPlaneSeats(Integer planeSeats) {
		this.planeSeats = planeSeats;
	}
	
	
	
	public Long getRunwayId() {
		return runwayId;
	}
	public void setRunwayId(Long runwayId) {
		this.runwayId = runwayId;
	}
	public String getRunwayName() {
		return runwayName;
	}
	public void setRunwayName(String runwayName) {
		this.runwayName = runwayName;
	}
	
	
	
}
