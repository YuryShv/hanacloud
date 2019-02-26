package com.hw6.hw6springdemo.config;
import java.util.Map;
import java.util.Optional;

import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration;

import com.sap.cloud.sdk.cloudplatform.CloudPlatform;
import com.sap.cloud.sdk.cloudplatform.CloudPlatformAccessor;
import com.sap.cloud.sdk.cloudplatform.ScpCfCloudPlatform;
import com.sap.cloud.sdk.cloudplatform.security.AuthTokenFacade;
@Configuration public class CloudConfig {    
	@Bean  public CloudPlatform platform() {  
	return CloudPlatformAccessor.getCloudPlatform(); 
	}  
@Bean
	public ScpCfCloudPlatform spacename() {
		return ScpCfCloudPlatform.getInstanceOrThrow();
		
	}
@Bean
public ScpCfCloudPlatform getSchema() {
	return ScpCfCloudPlatform.getInstanceOrThrow();
}
@Bean
public AuthTokenFacade authtoken() {
	return new AuthTokenFacade();
}
}
