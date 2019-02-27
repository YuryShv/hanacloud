package com.hw6.hw6springdemo.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hw6.hw6springdemo.domain.Cars;
import com.hw6.hw6springdemo.domain.Person;
import com.hw6.hw6springdemo.intfce.IPersonDao;


@Repository
public class PersonDao implements IPersonDao {
	private static final String DB_NAME = "\"hw3::User\"";
	private static final String DB_CAR = "\"hw3::ExtraInfo.Cars\"";
	private static final String USER_ID = "\"usid\"";
	private static final Logger logger = LoggerFactory.getLogger(PersonDao.class);
	private static Timestamp getCurrTime() {
		Date date = new Date(0);
		Timestamp currtime = new Timestamp(date.getTime());
		return currtime;
	}
	@Autowired
	private DataSource dataSource;

	@Override
	public Optional<Person> getById(String id) {
		Optional<Person> entity = null;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmnt = conn.prepareStatement(
						"SELECT TOP 1 * FROM "+ DB_NAME + " WHERE "+ USER_ID +" = ?")) {
			stmnt.setString(1, id);
			ResultSet result = stmnt.executeQuery();
			if (result.next()) {
				Person person = new Person();
				person.setId(id);
				person.setName(result.getString("name"));
				person.setTs_update(result.getTimestamp("ts_update"));
				person.setTs_create(result.getTimestamp("ts_create"));
				entity = Optional.of(person);
			} else {
				entity = Optional.empty();
			}
		} catch (SQLException e) {
			logger.error("Error while trying to get entity by Id: " + e.getMessage());
		}
		return entity;
	}

	@Override
	public List<Person> getAll() {
		List<Person> personList = new ArrayList<Person>();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmnt = conn
						.prepareStatement("SELECT * FROM "+DB_NAME+"")) {
			ResultSet result = stmnt.executeQuery();
			while (result.next()) {
				Person person = new Person();
				person.setId(result.getString("usid"));
				person.setName(result.getString("name"));
				person.setTs_update(result.getTimestamp("ts_update"));
				person.setTs_create(result.getTimestamp("ts_create"));
				personList.add(person);
			}
		} catch (SQLException e) {
			logger.error("Error while trying to get list of entities: " + e.getMessage());
		}
		return personList;
	}
	public List<String> getCars(String id) throws SQLException {
		 List<String> list = new ArrayList<String>();
		    try (Connection conn = dataSource.getConnection();
		        PreparedStatement stmnt = conn.prepareStatement("SELECT * FROM "+DB_NAME+" INNER JOIN "+DB_CAR+" ON "+DB_NAME+"."+USER_ID+" = "+DB_CAR+"."+USER_ID+" WHERE "+DB_NAME+"."+USER_ID+" = ?")) {
		      stmnt.setString(1, id);
		      ResultSet result = stmnt.executeQuery();        
		      while (result.next()) {
		        list.add(result.getString("usid"));
		        list.add(result.getString("crid"));
		      }          
		    } catch (SQLException e) {
		      logger.error("Error while trying to get entity by Id: " + e.getMessage());
		    }
		    return list;
	}
	@Override
	public void save(Person entity) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmnt = conn.prepareStatement(
						"INSERT INTO "+DB_NAME+" VALUES (?,?,?)")) {
			stmnt.setString(1, entity.getName());
			Timestamp currtime = getCurrTime();
			stmnt.setTimestamp(2, currtime);
			stmnt.setTimestamp(3, currtime);
			stmnt.execute();
		} catch (SQLException e) {
			logger.error("Error while trying to add entity: " + e.getMessage());
		}
	}

	@Override
	public void delete(String id) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmnt = conn.prepareStatement("DELETE FROM "+DB_NAME+" WHERE "+USER_ID+" = ?")) {
			stmnt.setString(1, id);
			stmnt.execute();
		} catch (SQLException e) {
			logger.error("Error while trying to delete entity: " + e.getMessage());
		}
	}

	@Override
	public void update(Person entity) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmnt = conn.prepareStatement(
						"UPDATE "+DB_NAME+" SET \"name\" = ?, \"ts_update\" = ? WHERE "+USER_ID+" = ?")) {
			stmnt.setString(1, entity.getName());
			Timestamp currtime = getCurrTime();
			stmnt.setTimestamp(2, currtime);
			stmnt.setString(3, entity.getId());
			stmnt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error while trying to update entity: " + e.getMessage());
		}
	}

}
