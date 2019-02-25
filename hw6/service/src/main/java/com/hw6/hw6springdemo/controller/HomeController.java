package com.hw6.hw6springdemo.controller;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping; 
import org.springframework.web.bind.annotation.RequestMethod;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.hw6.hw6springdemo.domain.Destination;
import com.hw6.hw6springdemo.service.CloudService;
import com.hw6.hw6springdemo.service.SecurityService;
import com.sap.cloud.sdk.cloudplatform.ScpCfCloudPlatform;
import com.sap.cloud.sdk.cloudplatform.security.AuthToken;
import com.sap.cloud.sdk.cloudplatform.security.AuthTokenFacade;
import com.sap.cloud.sdk.cloudplatform.security.BearerCredentials;
import com.sap.cloud.sdk.cloudplatform.security.user.ScpCfUser;
import com.sap.cloud.sdk.cloudplatform.security.user.ScpCfUserFacade;
import com.sap.cloud.sdk.s4hana.connectivity.rfc.exception.AccessDeniedException; 
@Controller public class HomeController {    
	@Autowired  private CloudService cloudService;  
	@Autowired  private SecurityService securityService; 
	@RequestMapping(value="/", method=RequestMethod.GET)  
	public String getHome(Model model) {   
		Map<String, JsonElement> vcap = cloudService.getSpaceName();
		JsonElement vc = vcap.get("space_name");
		model.addAttribute("VCAP",vc.toString());
		DecodedJWT token = new AuthTokenFacade().getCurrentToken().get().getJwt();
		model.addAttribute("token",token);
		JsonArray hanatrial = cloudService.getSchemaName().get("hanatrial");
		List<String> sch = getValuesForGivenKey(hanatrial.toString(), "schema");
		String appName = cloudService.getApplicationName();   
		model.addAttribute("appName", appName); 
		model.addAttribute("hanatrial", hanatrial); 
		model.addAttribute("schema", sch);
		List<Destination> destinations = cloudService.getDestinations();
		model.addAttribute("destinations", destinations);
		return "index"; 
	} 
	public List<String> getValuesForGivenKey(String jsonArrayStr, String key) {
	    JSONArray jsonArray = new JSONArray(jsonArrayStr);
	    return IntStream.range(0, jsonArray.length())
	      .mapToObj(index -> ((JSONObject)jsonArray.get(index)).optString(key))
	      .collect(Collectors.toList());
	}
	@RequestMapping(value="/scope", method=RequestMethod.GET)
	public String checkScope() throws AccessDeniedException {
		securityService.userHasAuthorization("Display");
		return "scope";
	}
}	 