package com.hw6.hw6springdemo.service;
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service; 

import com.sap.cloud.sdk.cloudplatform.CloudPlatform; 
 
@Service public class CloudService {  
	@Autowired  private CloudPlatform platform;  
	public String getApplicationName() { 
		return platform.getApplicationName();  
		}
	} 