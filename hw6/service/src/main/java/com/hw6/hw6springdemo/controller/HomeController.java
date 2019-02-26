package com.hw6.hw6springdemo.controller;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.codec.binary.Base64;
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
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.hw6.hw6springdemo.domain.Destination;
import com.hw6.hw6springdemo.service.CloudService;
import com.hw6.hw6springdemo.service.SecurityService;
import com.sap.cloud.sdk.cloudplatform.security.AuthToken;
import com.sap.cloud.sdk.s4hana.connectivity.rfc.exception.AccessDeniedException; 
@Controller public class HomeController {    
	private static final String HANATRIAL = "hanatrial";
	private static final String CREDENTIALS = "credentials";
	private static final String SCHEMA = "schema";
	private static final String FINAL_NAME = "given_name";
	private static final String FAMILY_NAME = "family_name";
	private static final String SPACE_NAME = "space_name";
	
	
	@Autowired  private CloudService cloudService;  
	@Autowired  private SecurityService securityService; 
	@RequestMapping(value="/", method=RequestMethod.GET)  
	public String getHome(Model model) {   
		Map<String, JsonElement> vcap = cloudService.getSpaceName();
		String appName = cloudService.getApplicationName();
		JsonElement vc = vcap.get(SPACE_NAME);
		JsonArray hanatrial = cloudService.getSchemaName().get(HANATRIAL);
		JsonElement schema = hanatrial.get(0).getAsJsonObject().get(CREDENTIALS).getAsJsonObject().get(SCHEMA);
		model.addAttribute("VCAP",vc.toString());
		model.addAttribute("appName", appName);  
		model.addAttribute("schema", schema);
		List<Destination> destinations = cloudService.getDestinations();
		model.addAttribute("destinations", destinations);
		return "index"; 
	} 
	@RequestMapping(value="/jwt", method=RequestMethod.GET)  
	public String getJWT(Model model) {   
		Optional<AuthToken> token = cloudService.getCurrToken();
		JsonObject jo = cloudService.getInfo(token);
		JsonElement name = jo.get(FINAL_NAME);
		JsonElement familyname = jo.get(FAMILY_NAME);
		model.addAttribute("token", jo);
		model.addAttribute("name", name);
		model.addAttribute("familyname", familyname);
		return "jwt"; 
	} 
	@RequestMapping(value="/scope", method=RequestMethod.GET)
	public String checkScope() throws AccessDeniedException {
		securityService.userHasAuthorization("Display");
		return "scope";
	}
}	 