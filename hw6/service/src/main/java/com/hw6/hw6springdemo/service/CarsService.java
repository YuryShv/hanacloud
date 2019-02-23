package com.hw6.hw6springdemo.service;
import java.util.List;
import java.util.Optional; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.hw6.hw6springdemo.dao.CarsDao;
import com.hw6.hw6springdemo.domain.Cars;

@Service public class CarsService {  
	@Autowired 
	private CarsDao carsDao;   
	public List<Cars> getCarAll() 
	{   return carsDao.getAll();  
	}    
	public Cars getCar(String id) 
	{   Optional<Cars> carOptional = this.carsDao.getById(id);   
	Cars car = null;  
	if (carOptional.isPresent()) {
		car = carOptional.get();   
		}   return car;  
	}    
	public void createCar(Cars car){ 
		this.carsDao.save(car);  
	}    
	public void updateCar(Cars car) {   
		this.carsDao.update(car);  
		}    
	public void deleteCar(String id) {   
		this.carsDao.delete(id);  
		}   
	} 