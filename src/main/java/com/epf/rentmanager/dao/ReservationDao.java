package com.epf.rentmanager.dao;

import java.sql.*;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDao {

	private static ReservationDao instance = null;
	private ReservationDao() {}
	
	private static final String CREATE_RESERVATION_QUERY = "INSERT INTO Reservation(client_id, vehicle_id, debut, fin) VALUES(?, ?, ?, ?);";
	private static final String UPDATE_RESERVATION_QUERY = "UPDATE Reservation SET client_id = ?, vehicle_id = ?, debut = ?, fin = ? WHERE id = ?;";
	private static final String DELETE_RESERVATION_QUERY = "DELETE FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATION_QUERY = "SELECT vehicle_id, debut, fin FROM Reservation WHERE id=?;";
	private static final String FIND_RESERVATIONS_BY_CLIENT_QUERY = "SELECT id, vehicle_id, debut, fin FROM Reservation WHERE client_id=?;";
	private static final String FIND_RESERVATIONS_BY_VEHICLE_QUERY = "SELECT id, client_id, debut, fin FROM Reservation WHERE vehicle_id=?;";
	private static final String FIND_RESERVATIONS_QUERY = "SELECT id, client_id, vehicle_id, debut, fin FROM Reservation;";
	private static final String COUNT_RESERVATIONS_QUERY = "SELECT COUNT(id) FROM Reservation;";


	/**
	 * crée une nouvelle réservation dans la base de données
	 * @param reservation
	 * @return l'identifiant de la réservation
	 * @throws DaoException
	 * @throws SQLException
	 */
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

	/**
	 * modifie une réservation dans la base de données
	 * @param reservation
	 * @return l'identifiant de la réservation
	 * @throws DaoException
	 * @throws SQLException
	 */
	public long update(Reservation reservation) throws DaoException, SQLException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_RESERVATION_QUERY);
			preparedStatement.setString(1, String.valueOf(reservation.getClient_id()));
			preparedStatement.setString(2, String.valueOf(reservation.getVehicle_id()));
			preparedStatement.setString(3, String.valueOf(reservation.getDebut()));
			preparedStatement.setString(4, String.valueOf(reservation.getFin()));
			preparedStatement.setInt(5, reservation.getId());

			long id = ((PreparedStatement) preparedStatement).executeUpdate();
			connection.close();
			return id;
		}
		catch (SQLException e) {
			throw new DaoException("Echec de la modification de la réservation Dao");
		}
	}

	/**
	 * supprime une réservation de la base de données
	 * @param reservation
	 * @return l'identifiant de la réservation
	 * @throws DaoException
	 * @throws SQLException
	 */
	public long delete(Reservation reservation) throws DaoException, SQLException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_RESERVATION_QUERY);
			preparedStatement.setInt(1, reservation.getId());

			long id = ((PreparedStatement) preparedStatement).executeUpdate();
			connection.close();
			return id;
		}
		catch (SQLException e) {
			throw new DaoException("Echec de la suppression de la réservation Dao");
		}
	}

	/**
	 * vérifie si un véhicule a déjà été utilisé dans la période sélectionnée
	 * @param reservation
	 * @return un booléen : false si la voiture n'est pas réservable et true sinon
	 * @throws DaoException
	 * @throws SQLException
	 */
	public boolean isAvailableThisDay(Reservation reservation) throws DaoException, SQLException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			preparedStatement.setInt(1, reservation.getVehicle_id());
			ResultSet rs = preparedStatement.executeQuery();

			while (rs.next()) {
				LocalDate start = rs.getDate("debut").toLocalDate();
				LocalDate end = rs.getDate("fin").toLocalDate();
				if (end.isEqual(reservation.getDebut())) {
					return false;
				}
				if (end.isAfter(ChronoLocalDate.from(reservation.getDebut())) && start.isBefore(ChronoLocalDate.from(reservation.getFin()))) {
					return false;
				}
			}
			return true;
		} catch (SQLException e) {
			throw new DaoException("Echec de la requête isAvailableThisDay Dao");
		}
	}

	/**
	 * vérifie si un véhicule est réservé plus de 30 jours d'affilée
	 * @param reservation
	 * @return un booléen : false si la voiture n'est pas réservable et true sinon
	 * @throws DaoException
	 * @throws SQLException
	 */
	public boolean isAvailableThisDay2(Reservation reservation) throws DaoException, SQLException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			preparedStatement.setInt(1, reservation.getVehicle_id());
			ResultSet rs = preparedStatement.executeQuery();
			long cpt_jours = 0;
			LocalDate startDate = reservation.getDebut();
			while (rs.next()) {
				LocalDate endDate = rs.getDate("fin").toLocalDate();
				long nb_jours = ChronoUnit.DAYS.between(startDate, endDate);
				cpt_jours += nb_jours;
				if (cpt_jours >= 30) {
					return false;
				}
				startDate = rs.getDate("debut").toLocalDate();
			}
			long nb_jours = ChronoUnit.DAYS.between(startDate, reservation.getFin());
			cpt_jours += nb_jours;
			if (cpt_jours >= 30) {
				return false;
			}
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * trouve une réservation dans la base de données grâce à son identifiant
	 * @param id
	 * @return la réservation trouvée
	 * @throws DaoException
	 */
	public Reservation findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATION_QUERY);
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();
			Reservation reservation = new Reservation((int) id, rs.getInt("client_id"), rs.getInt("vehicle_id"),
					rs.getDate("debut").toLocalDate(), rs.getDate("fin").toLocalDate());
			return reservation;
		} catch (SQLException e) {
			throw new DaoException("Erreur, Réservation non trouvée par son id");
		}
	}

	/**
	 * trouve une réservation dans la base de données grâce à l'identifiant du client
	 * @param clientId
	 * @return la réservation trouvée
	 * @throws DaoException
	 */
	public List<Reservation> findResaByClientId(long clientId) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_CLIENT_QUERY);
			preparedStatement.setLong(1, clientId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Reservation reservation = new Reservation(rs.getInt("id"), (int)clientId,
						rs.getInt("vehicle_id"), rs.getDate("debut").toLocalDate(),
						rs.getDate("fin").toLocalDate());
				reservations.add(reservation);
			}
			return reservations;
		} catch (SQLException e) {
			throw new DaoException("Erreur, la réservation n'a pas été trouvée par l'id du client");
		}
	}

	/**
	 * trouve une réservation dans la base de données grâce à l'identifiant du véhicule
	 * @param vehicleId
	 * @return la réservation trouvée
	 * @throws DaoException
	 */
	public List<Reservation> findResaByVehicleId(long vehicleId) throws DaoException {
		List<Reservation> reservations = new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_RESERVATIONS_BY_VEHICLE_QUERY);
			preparedStatement.setLong(1, vehicleId);
			ResultSet rs = preparedStatement.executeQuery();
			while (rs.next()) {
				Reservation reservation = new Reservation(rs.getInt("id"), rs.getInt("client_id"),
						(int) vehicleId, rs.getDate("debut").toLocalDate(),
						rs.getDate("fin").toLocalDate());
				reservations.add(reservation);
			}
			return reservations;
		} catch (SQLException e) {
			throw new DaoException("Erreur, la réservation n'a pas été trouvée par l'id du véhicule");
		}
	}

	/**
	 * trouve toutes les réservations de la base de données
	 * @return la liste de toutes les réservations trouvées
	 * @throws DaoException
	 */
	public List<Reservation> findAll() throws DaoException {
		List<Reservation> reservations= new ArrayList<Reservation>();
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_RESERVATIONS_QUERY);
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

	/**
	 * compte le nombre de réservations dans la base de données
	 * @return le nombre de réservations
	 * @throws DaoException
	 */
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
