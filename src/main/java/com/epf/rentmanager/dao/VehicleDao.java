package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class VehicleDao {
	
	private static VehicleDao instance = null;

	private VehicleDao() {}
	
	private static final String CREATE_VEHICLE_QUERY = "INSERT INTO Vehicle(constructeur, modele, nb_places) VALUES(?, ?, ?);";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) FROM Vehicle;";
	
	public long create(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_VEHICLE_QUERY);
			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setString(2, vehicle.getModele());
			preparedStatement.setInt(3, vehicle.getNb_places());
			long id = ((PreparedStatement) preparedStatement).executeUpdate();
			connection.close();
			return id;
		} catch (SQLException e) {
			throw new DaoException("Echec de la crétion du véhicule Dao");
		}
	}

	public long delete(Vehicle vehicle) throws DaoException {
		return 0;
	}

	public Vehicle findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLE_QUERY);
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();

			Vehicle vehicle = new Vehicle((int)id, rs.getString("constructeur"),
					rs.getString("modele"), rs.getByte("nb_places"));
			return vehicle;
		} catch (SQLException e) {
			throw new DaoException("Erreur");
		}
	}

	public List<Vehicle> findAll() throws DaoException {
		//fait avec le prof le 20/02
		List<Vehicle> vehicles= new ArrayList<Vehicle>();
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			//le rs est un tableau de données avec un curseur
			ResultSet rs = statement.executeQuery(FIND_VEHICLES_QUERY);

			//tant qu'il y a une ligne, on crée un véhicule avec les données fournies
			while (rs.next()) {
				Vehicle vehicle = new Vehicle(rs.getInt("id"), rs.getString("constructeur"),
						rs.getString("modele"), rs.getByte("nb_places"));
				vehicles.add(vehicle);
			}

		}
		catch (SQLException e) {
			//e.printStackTrace(); //pour afficher une exception
			throw new DaoException("Erreur 1");
		}
		return vehicles;
	}

	public int count() throws DaoException {
		try  {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(COUNT_VEHICLES_QUERY);
			ResultSet rs = ((PreparedStatement) statement).executeQuery();
			rs.next();
			return rs.getInt("COUNT(id)");
		} catch (SQLException e) {
			throw new DaoException("Le comptage des véhicules n'a pas fonctionné");
		}
	}


	

}
