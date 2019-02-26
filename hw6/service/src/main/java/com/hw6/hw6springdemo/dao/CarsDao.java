package com.hw6.hw6springdemo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hw6.hw6springdemo.domain.Cars;
import com.hw6.hw6springdemo.intfce.ICarsDao;

@Repository
public class CarsDao implements ICarsDao {
	private static final String DB_NAME = "\"hw3::ExtraInfo.Cars\"";
	private static final String CAR_ID = "\"crid\"";
	private static final Logger logger = LoggerFactory.getLogger(CarsDao.class);
	@Autowired
	private DataSource dataSource;

	@Override
	public Optional<Cars> getById(String id) {
		Optional<Cars> entity = null;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmnt = conn.prepareStatement(
						"SELECT TOP 1 * FROM "+DB_NAME+" WHERE "+CAR_ID+" = ?")) {
			stmnt.setString(1, id);
			ResultSet result = stmnt.executeQuery();
			if (result.next()) {
				Cars car = new Cars();
				car.setCrid(id);
				car.setUsid(result.getString("usid"));
				car.setName(result.getString("name"));
				entity = Optional.of(car);
			} else {
				entity = Optional.empty();
			}
		} catch (SQLException e) {
			logger.error("Error while trying to get entity by Id: " + e.getMessage());
		}
		return entity;
	}

	@Override
	public List<Cars> getAll() {
		List<Cars> carList = new ArrayList<Cars>();
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmnt = conn
						.prepareStatement("SELECT * FROM "+DB_NAME+"")) {
			ResultSet result = stmnt.executeQuery();
			while (result.next()) {
				Cars car = new Cars();
				car.setCrid(result.getString("crid"));
				car.setUsid(result.getString("usid"));
				car.setName(result.getString("name"));
				carList.add(car);
			}
		} catch (SQLException e) {
			logger.error("Error while trying to get list of entities: " + e.getMessage());
		}
		return carList;
	}

	@Override
	public void save(Cars entity) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmnt = conn.prepareStatement(
						"INSERT INTO "+DB_NAME+" VALUES (?,?,?)")) {
			stmnt.setString(1, entity.getCrid());
			stmnt.setString(2, entity.getUsid());
			stmnt.setString(3, entity.getName());
			stmnt.execute();
		} catch (SQLException e) {
			logger.error("Error while trying to add entity: " + e.getMessage());
		}
	}

	@Override
	public void delete(String id) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmnt = conn.prepareStatement("DELETE FROM "+DB_NAME+" WHERE "+CAR_ID+" = ?")) {
			stmnt.setString(1, id);
			stmnt.execute();
		} catch (SQLException e) {
			logger.error("Error while trying to delete entity: " + e.getMessage());
		}
	}

	@Override
	public void update(Cars entity) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement stmnt = conn.prepareStatement(
						"UPDATE "+DB_NAME+" SET \"usid\" = ?, \"name\" = ? WHERE "+CAR_ID+" = ?")) {
			stmnt.setString(1, entity.getUsid());
			stmnt.setString(2, entity.getName());
			stmnt.setString(3, entity.getCrid());
			stmnt.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error while trying to update entity: " + e.getMessage());
		}
	}

}
