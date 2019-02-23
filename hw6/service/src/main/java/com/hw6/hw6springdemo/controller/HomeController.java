package com.hw6.hw6springdemo.controller;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod;

import com.hw6.hw6springdemo.domain.Destination;
import com.hw6.hw6springdemo.service.CloudService;
import com.hw6.hw6springdemo.service.SecurityService;
import com.sap.cloud.sdk.s4hana.connectivity.rfc.exception.AccessDeniedException; 
@Controller public class HomeController {    
	@Autowired  private CloudService cloudService;  
	@Autowired  private SecurityService securityService; 
	@RequestMapping(value="/", method=RequestMethod.GET)  
	public String getHome(Model model) {   
		String appName = cloudService.getApplicationName();   
		model.addAttribute("appName", appName); 
		List<Destination> destinations = cloudService.getDestinations();
		model.addAttribute("destinations", destinations);
		return "index"; 
	} 
	
	@RequestMapping(value="/scope", method=RequestMethod.GET)
	public String checkScope() throws AccessDeniedException {
		securityService.userHasAuthorization("Display");
		return "scope";
	}
}	 