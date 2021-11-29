package airport;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import airport.model.ERole;
import airport.model.Flight;
import airport.model.Plane;
import airport.model.Role;
import airport.model.Runway;
import airport.model.Ticket;
import airport.model.User;
import airport.repository.RoleRepository;
import airport.service.FlightService;
import airport.service.PlaneService;
import airport.service.RunwayService;
import airport.service.TicketService;
import airport.service.UserService;

@Component
public class TestData {
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PlaneService planeService;
	
	@Autowired
	private RunwayService runwayService;
	
	@Autowired
	private FlightService flightService;
	
	@Autowired
	private TicketService ticketService;
	
	@PostConstruct
	public void init() {
		
		Role role1 = new Role();
		role1.setName(ERole.ROLE_ADMIN);
		role1 = roleRepository.save(role1);
		
		Role role2 = new Role();
		role2.setName(ERole.ROLE_EMPLOYEE);
		role2 = roleRepository.save(role2);
		
		Role role3 = new Role();
		role3.setName(ERole.ROLE_PASSENGER);
		role3 = roleRepository.save(role3);
		
		User user1 = new User();
		user1.setUsername("Admin");
		user1.setPassword("$2a$10$hKDVYxLefVHV/vtuPhWD3OigtRyOykRLDdUAp80Z1crSoS1lFqaFS");   // admin
		user1.setFirstname("Administrator");
		user1.setLastname("Administrator");
		user1.setJmbg("1234567890123");
		user1.setCity("AdminCity");
		user1 = userService.save(user1);
		
		User user2 = new User();
		user2.setUsername("Employee");
		user2.setPassword("$2a$10$Locf9fRBO84ejEc/bQFEROChVsd2ixjv4M2kYX6KSLp74iacK.N3W");  // 123456
		user2.setFirstname("Pera");
		user2.setLastname("Peric");
		user2.setJmbg("0510968123456");
		user2.setCity("New York");
		user2 = userService.save(user2);
		
		User user3 = new User();
		user3.setUsername("VasaVasic");
		user3.setPassword("$2a$10$bwQVsArIQJtmkPckmfRZGOEMAGBXcHaziXIEgstc9ePsPG6sYEFK.");  // 654321
		user3.setFirstname("Vasa");
		user3.setLastname("Vasic");
		user3.setJmbg("1106974123456");
		user3.setCity("Detroit");
		user3 = userService.save(user3);
		
		User user4 = new User();
		user4.setUsername("SimaSimic");
		user4.setPassword("$2a$10$43jqK4UjygMmsQS4khzCbO7zlak7SGkQiUJYSIZgPKCJU7X9xy/dy");  // 987654
		user4.setFirstname("Sima");
		user4.setLastname("Simic");
		user4.setJmbg("2208978123456");
		user4.setCity("Pennsylvania");
		user4 = userService.save(user4);

		user1.addRole(role1);
		user2.addRole(role2);
		user3.addRole(role3);
		user4.addRole(role3);
		user1 = userService.save(user1);
		user2 = userService.save(user2);
		user3 = userService.save(user3);
		user4 = userService.save(user4);
		
		Plane plane1 = new Plane();
		plane1.setName("A 1001");
		plane1.setBrand("Boeing");
		plane1.setModel("737");
		plane1.setSeats(5);
		plane1 = planeService.save(plane1);
		
		Plane plane2 = new Plane();
		plane2.setName("B 2002");
		plane2.setBrand("Airbus");
		plane2.setModel("A380");
		plane2.setSeats(6);
		plane2 = planeService.save(plane2);
		
		Plane plane3 = new Plane();
		plane3.setName("C 3003");
		plane3.setBrand("Embraer");
		plane3.setModel("ERJ37");
		plane3.setSeats(7);
		plane3 = planeService.save(plane3);
		
		Runway runway1 = new Runway();
		runway1.setName("Runway A101");
		runway1 = runwayService.save(runway1);
		
		Runway runway2 = new Runway();
		runway2.setName("Runway B202");
		runway2 = runwayService.save(runway2);
		
		Runway runway3 = new Runway();
		runway3.setName("Runway C303");
		runway3 = runwayService.save(runway3);
		
		Flight flight1 = new Flight();
		flight1.setDestination("Berlin");
		flight1.setDatetime("14.11.2021. 18:00");
		flight1.setPrice(700.0);
		flight1.setFreeSeats(5);
		flight1.setPlane(plane1);
		flight1.setRunway(runway1);
		flight1 = flightService.save(flight1);
		
		Flight flight2 = new Flight();
		flight2.setDestination("London");
		flight2.setDatetime("15.11.2021. 14:00");
		flight2.setPrice(600.0);
		flight2.setFreeSeats(6);
		flight2.setPlane(plane2);
		flight2.setRunway(runway2);
		flight2 = flightService.save(flight2);
		
		Flight flight3 = new Flight();
		flight3.setDestination("Moscow");
		flight3.setDatetime("16.11.2021. 16:00");
		flight3.setPrice(1200.0);
		flight3.setFreeSeats(7);
		flight3.setPlane(plane3);
		flight3.setRunway(runway3);
		flight3 = flightService.save(flight3);
		
		Flight flight4 = new Flight();
		flight4.setDestination("Paris");
		flight4.setDatetime("17.11.2021. 09:00");
		flight4.setPrice(750.0);
		flight4.setFreeSeats(6);
		flight4.setPlane(plane1);
		flight4.setRunway(runway2);
		flight4 = flightService.save(flight4);
		
		
		flight1.addUser(user3);
		flight1.addUser(user4);
		flight1 = flightService.save(flight1);
		
		
		Ticket ticket1 = new Ticket();
		ticket1.setUser(user3);
		ticket1.setFlight(flight1);
		ticket1 = ticketService.save(ticket1);
		
		flight1.setFreeSeats(flight1.getFreeSeats()-1);
		flight1 = flightService.save(flight1);
		
		Ticket ticket2 = new Ticket();
		ticket2.setUser(user4);
		ticket2.setFlight(flight1);
		ticket2 = ticketService.save(ticket2);
		
		flight1.setFreeSeats(flight1.getFreeSeats()-1);
		flight1 = flightService.save(flight1);
		
		
	}

}
