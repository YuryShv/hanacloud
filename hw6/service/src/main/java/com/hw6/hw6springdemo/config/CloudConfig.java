package com.hw6.hw6springdemo.config;
import java.util.Map;

import org.springframework.context.annotation.Bean; 
import org.springframework.context.annotation.Configuration;

import com.google.gson.JsonElement;
import com.sap.cloud.sdk.cloudplatform.CloudPlatform; 

import com.sap.cloud.sdk.cloudplatform.CloudPlatformAccessor;
import com.sap.cloud.sdk.cloudplatform.ScpCfCloudPlatform;

@Configuration public class CloudConfig {    
	@Bean  public CloudPlatform platform() {  
	return CloudPlatformAccessor.getCloudPlatform(); 
	}  
@Bean
	public ScpCfCloudPlatform namespace() {
		return ScpCfCloudPlatform.getInstanceOrThrow();
	}
}
