package airport.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;

//@CrossOrigin(origins="http://localhost:3000")
@Controller
public class ApiHomeController {
	
	@RequestMapping(value = {"/"})
    public String getIndex(HttpServletRequest request) {
		return "/index.html";
    }

}
