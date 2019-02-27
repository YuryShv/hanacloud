package com.hw6.hw6springdemo.service;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional; 
import org.springframework.beans.factory.annotation.Autowired; 
import org.springframework.stereotype.Service;

import com.hw6.hw6springdemo.dao.PersonDao;
import com.hw6.hw6springdemo.domain.Person; 
@Service public class PersonService {  
	@Autowired 
	private PersonDao personDao;   
	public List<Person> getPersonAll() 
	{   return personDao.getAll();  
	}    
	public Person getPerson(String id) 
	{   Optional<Person> personOptional = this.personDao.getById(id);   
	Person person = null;  
	if (personOptional.isPresent()) {
		person = personOptional.get();   
		}   return person;  
	}    
	public void createPerson(Person person){ 
		this.personDao.save(person);  
	}    
	public void updatePerson(Person person) {   
		this.personDao.update(person);  
		} 
	public List<String> getPersonCars(String id) throws SQLException {
		return personDao.getCars(id);
	}
	public void deletePerson(String id) {   
		this.personDao.delete(id);  
		}   
	} 