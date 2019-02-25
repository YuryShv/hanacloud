package com.hw6.hw6springdemo.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.hw6.hw6springdemo.domain.Destination;
import com.hw6.hw6springdemo.domain.Property;
import com.sap.cloud.sdk.cloudplatform.CloudPlatform;
import com.sap.cloud.sdk.cloudplatform.ScpCfCloudPlatform;
import com.sap.cloud.sdk.cloudplatform.connectivity.DestinationAccessor;
import com.sap.cloud.sdk.cloudplatform.connectivity.GenericDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.ScpCfDestination;
import com.sap.cloud.sdk.cloudplatform.connectivity.ScpCfService;
import com.sap.cloud.sdk.cloudplatform.security.AuthToken;
import com.sap.cloud.sdk.cloudplatform.security.AuthTokenAccessor; 
 
@Service public class CloudService {  
	@Autowired  private CloudPlatform platform;  
	@Autowired private ScpCfCloudPlatform spacename;
	@Autowired private ScpCfCloudPlatform getSchema;
	public String getApplicationName() { 
		return platform.getApplicationName();  
		}
	public Map<String, JsonElement> getSpaceName() {
		return spacename.getVcapApplication();
	}
	public Map<String, JsonArray> getSchemaName() {
		return getSchema.getVcapServices();
	}
	public List<Destination> getDestinations() {
		List<Destination> destinationList = new ArrayList<Destination>();
		Map<String, GenericDestination> destinationMap = DestinationAccessor.getGenericDestinationsByName();
		destinationMap.forEach((key, value) -> {
			Destination destination = new Destination();
			destination.setName(value.getName());
			destination.setDescription(value.getDescription().orElseGet(() -> {
				return "No description";
			}));
			destination.setDestinationType(value.getDestinationType().toString());
			Map<String, String> propertyMap = value.getPropertiesByName();
			List<Property> propertyList = new ArrayList<Property>();
			propertyMap.forEach((name, data) -> {
				Property property = new Property();
				property.setName(name);
				property.setValue(data);
				propertyList.add(property);
			});
			destination.setPropertyList(propertyList);
			destinationList.add(destination);
		});
		return destinationList;
	}
	} 