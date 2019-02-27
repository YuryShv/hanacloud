package com.hw6.hw6springdemo.controller;
import java.sql.SQLException;
import java.util.List; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping; 
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hw6.hw6springdemo.domain.Person;
import com.hw6.hw6springdemo.service.PersonService; 

@RestController public class PersonController {    
	@Autowired 
	private PersonService personService; 
	   @GetMapping(value="/person") 
	   public List<Person> getAllPerson() {   
		   return personService.getPersonAll();  
		   }
	   @GetMapping(value="/person/{id}")
	   public Person getPerson(@PathVariable String id) {
		   return personService.getPerson(id);  
		   }   
	   @PostMapping(value="/person")
	   public void createPerson(@RequestBody Person person) {
		   personService.createPerson(person); 
		   }
	   @DeleteMapping(value="/person/{id}")
	   public void deletePerson(@PathVariable String id) {
		   personService.deletePerson(id);  
		   } 
		@GetMapping(value="/personCars/{id}")
		public List<String> getPersonCars(@PathVariable String id) throws SQLException {
			return personService.getPersonCars(id);
		}
	   @PutMapping(value="/person")
	   public void updatePerson(@RequestBody Person person) { 
		   personService.updatePerson(person);  
	   }   
} 