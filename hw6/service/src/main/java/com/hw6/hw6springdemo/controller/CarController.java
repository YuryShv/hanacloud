package com.hw6.hw6springdemo.controller;
import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hw6.hw6springdemo.domain.Cars;
import com.hw6.hw6springdemo.service.CarsService;


@RestController public class CarController {    
	@Autowired 
	private CarsService carService; 
	   @GetMapping(value="/car") 
	   public List<Cars> getCarAll() {   
		   return carService.getCarAll(); 
		   }
	   @GetMapping(value="/car/{id}")
	   public Cars getCar(@PathVariable String id) {
		   return carService.getCar(id);  
		   }   
	   @PostMapping(value="/car")
	   public void createCar(@RequestBody Cars car) {
		   carService.createCar(car); 
		   }
	   @DeleteMapping(value="/car/{id}")
	   public void deleteCar(@PathVariable String id) {
		   carService.deleteCar(id);  
		   } 
	   @PutMapping(value="/car")
	   public void updatePerson(@RequestBody Cars car) { 
		   carService.updateCar(car);  
	   }   
} 