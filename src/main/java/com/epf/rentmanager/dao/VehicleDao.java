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
	private static final String UPDATE_VEHICLE_QUERY = "UPDATE Vehicle SET constructeur = ?, modele = ?, nb_places = ? WHERE id = ?;";
	private static final String DELETE_VEHICLE_QUERY = "DELETE FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLE_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle WHERE id=?;";
	private static final String FIND_VEHICLES_BY_CLIENT_QUERY = "SELECT * FROM Reservation INNER JOIN Vehicle ON Reservation.vehicle_id = Vehicle.id WHERE client_id=?;";
	private static final String FIND_VEHICLES_QUERY = "SELECT id, constructeur, modele, nb_places FROM Vehicle;";
	private static final String COUNT_VEHICLES_QUERY = "SELECT COUNT(id) FROM Vehicle;";

	/**
	 * crée un véhicule dans la base de données
	 * @param vehicle
	 * @return l'identifiant du véhicule créé
	 * @throws DaoException
	 */
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

	/**
	 * modifie un véhicule dans la base de données
	 * @param vehicle
	 * @return l'identifiant du véhicule modifié
	 * @throws DaoException
	 */
	public long update(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_VEHICLE_QUERY);
			preparedStatement.setString(1, vehicle.getConstructeur());
			preparedStatement.setString(2, vehicle.getModele());
			preparedStatement.setString(3, String.valueOf(vehicle.getNb_places()));
			preparedStatement.setInt(4, vehicle.getId());

			long id = ((PreparedStatement) preparedStatement).executeUpdate();
			connection.close();
			return id;
		}
		catch (SQLException e) {
			throw new DaoException("Echec de la modification du véhicule Dao");
		}
	}

	/**
	 * supprime un véhicule de la base de données
	 * @param vehicle
	 * @return l'identifiant du véhicule supprimé
	 * @throws DaoException
	 */
	public long delete(Vehicle vehicle) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VEHICLE_QUERY);
			preparedStatement.setInt(1, vehicle.getId());

			long id = preparedStatement.executeUpdate();
			connection.close();
			return id;
		}
		catch (SQLException e) {
			throw new DaoException("Echec de la suppression du véhicule Dao");
		}
	}

	/**
	 * trouve un véhicule dans la base de données grâce à son identifiant
	 * @param id
	 * @return le véhicule trouvé
	 * @throws DaoException
	 */
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

	/**
	 * trouve les véhicules dans la base de données grâce à l'identifiant du client
	 * @param clientId
	 * @return la liste des véhicules trouvés
	 * @throws DaoException
	 */
	public List<Vehicle> findVehiclesByClientId(long clientId) throws DaoException {
		List<Vehicle> vehicles = new ArrayList<Vehicle>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_VEHICLES_BY_CLIENT_QUERY);
			preparedStatement.setLong(1, clientId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Vehicle vehicle = new Vehicle(rs.getInt("id"), rs.getString("constructeur"),
						rs.getString("modele"), rs.getByte("nb_places"));
				vehicles.add(vehicle);
			}
			connection.close();

		} catch (SQLException e) {
			throw new DaoException("Erreur, les véhicules n'ont pas été trouvés par l'id du client");
		}
		return vehicles;
	}

	/**
	 * trouve tous les véhicules de la base de données
	 * @return la liste de tous les véhicules
	 * @throws DaoException
	 */
	public List<Vehicle> findAll() throws DaoException {
		List<Vehicle> vehicles= new ArrayList<Vehicle>();
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_VEHICLES_QUERY);
			while (rs.next()) {
				Vehicle vehicle = new Vehicle(rs.getInt("id"), rs.getString("constructeur"),
						rs.getString("modele"), rs.getByte("nb_places"));
				vehicles.add(vehicle);
			}
		}
		catch (SQLException e) {
			throw new DaoException("Erreur 1");
		}
		return vehicles;
	}

	/**
	 * compte le nombre de véhicules dans la base de données
	 * @return le nombre de véhicules
	 * @throws DaoException
	 */
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
