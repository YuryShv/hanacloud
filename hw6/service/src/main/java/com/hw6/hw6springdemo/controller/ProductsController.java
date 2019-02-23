package com.hw6.hw6springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hw6.hw6springdemo.domain.Products;
import com.hw6.hw6springdemo.service.ProductService;
import com.sap.cloud.sdk.odatav2.connectivity.ODataException;

@RestController public class ProductsController {    
	@Autowired 
	private ProductService productService; 
	   @GetMapping(value="/products") 
	   public List<Products> getAllProducts() throws ODataException {   
		   return productService.getProductsOdata("OData");  
		   }
}
	   
