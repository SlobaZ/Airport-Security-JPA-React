package airport.loginuserinfo;

import org.springframework.security.core.context.SecurityContextHolder;

public class LoggedInUser {
	
	static String loggedUser;
	
	public static void setLoggedInUser() {
		loggedUser =  SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	public static String getLoggedInUser() {
		return loggedUser;
	}
	
	public static void logoutUser() {
		loggedUser =  null;
	}
	
	
}
