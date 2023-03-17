package com.epf.rentmanager.dao;

import java.security.interfaces.RSAKey;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(id) FROM Reservation;";
		
	public long create(Reservation reservation) throws DaoException, SQLException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_RESERVATION_QUERY);
			preparedStatement.setString(1, String.valueOf(reservation.getClient_id()));
			preparedStatement.setString(2, String.valueOf(reservation.getVehicle_id()));
			preparedStatement.setString(3, String.valueOf(reservation.getDebut()));
			preparedStatement.setString(4, String.valueOf(reservation.getFin()));

			long id = ((PreparedStatement) preparedStatement).executeUpdate();
			connection.close();
			return id;
		}
			catch (SQLException e) {
			throw new DaoException("Echec de la création de la réservation Dao");
		}
	}
	
	public long delete(Reservation reservation) throws DaoException {
		return 0;
	}

	
	public Reservation findResaByClientId(long clientId) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			preparedStatement.setLong(1, clientId);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			Reservation reservation = new Reservation(rs.getInt("id"), (int)clientId,
					rs.getInt("vehicle_id"), rs.getDate("debubt").toLocalDate(),
					rs.getDate("fin").toLocalDate());

			return reservation;
		} catch (SQLException e) {
			throw new DaoException("Erreur");
		}
	}
	
	public Reservation findResaByVehicleId(long vehicleId) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			preparedStatement.setLong(1, vehicleId);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			Reservation reservation = new Reservation(rs.getInt("id"), rs.getInt("client_id"),
					(int)vehicleId, rs.getDate("debubt").toLocalDate(),
					rs.getDate("fin").toLocalDate());

			return reservation;
		} catch (SQLException e) {
			throw new DaoException("Erreur");
		}
	}

	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations= new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			//le rs est un tableau de données avec un curseur
			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY);

			//tant qu'il y a une ligne, on crée une réservation avec les données fournies
			while (rs.next()) {
				Reservation reservation = new Reservation(rs.getInt("id"), rs.getInt("client_id"),
						rs.getInt("vehicle_id"), rs.getDate("debut").toLocalDate(),
						rs.getDate("fin").toLocalDate());
				reservations.add(reservation);
			}
			return reservations;
		}
		catch (SQLException e) {
			throw new DaoException("Erreur");
		}
	}

	public int count() throws DaoException {
		try  {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(COUNT_RESERVATIONS_QUERY);
			ResultSet rs = ((PreparedStatement) statement).executeQuery();
			rs.next();
			return rs.getInt("COUNT(id)");
		} catch (SQLException e) {
			throw new DaoException("Le comptage des réservations n'a pas fonctionné");
		}
	}
}
