package airport.payload.response;

import java.util.List;

public class JwtResponse {
	
	private String token;
	private String type = "Bearer";
	private Long id;
	private String username;
	private String firstname;
	private String lastname;
	private String jmbg;
	private String city;
	private List<String> roles;

	public JwtResponse(String accessToken, Long id, String username, String firstname, String lastname, String jmbg, String city, List<String> roles) {
		this.token = accessToken;
		this.id = id;
		this.username = username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.jmbg = jmbg;
		this.city = city;
		this.roles = roles;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
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

	public List<String> getRoles() {
		return roles;
	}

	@Override
	public String toString() {
		return "JwtResponse [token=" + token + ", type=" + type + ", id=" + id + ", username=" + username
				+ ", firstname=" + firstname + ", lastname=" + lastname + ", jmbg=" + jmbg + ", city=" + city
				+ ", roles=" + roles + "]";
	}
	
	
	//
	
	
}
