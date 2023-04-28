package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String UPDATE_CLIENT_QUERY = "UPDATE Client SET nom = ?, prenom = ?, email = ?, naissance = ? WHERE id = ?;";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(id) FROM Client;";

	/**
	 * crée un nouveau client dans la base de données
	 * @param client
	 * @return l'identifiant du client
	 * @throws DaoException
	 * @throws SQLException
	 */
	public long create(Client client) throws DaoException, SQLException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(CREATE_CLIENT_QUERY);
			preparedStatement.setString(1, client.getNom());
			preparedStatement.setString(2, client.getPrenom());
			preparedStatement.setString(3, client.getEmail());
			preparedStatement.setString(4, String.valueOf(client.getNaissance()));

			long id = ((PreparedStatement) preparedStatement).executeUpdate();
			connection.close();
			return id;
		}
		catch (SQLException e) {
			throw new DaoException("Echec de la création du client Dao");
		}
	}

	/**
	 * modifie un client dans la base de données
	 * @param client
	 * @return l'identifiant du client
	 * @throws DaoException
	 * @throws SQLException
	 */
	public long update(Client client) throws DaoException, SQLException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CLIENT_QUERY);
			preparedStatement.setString(1, client.getNom());
			preparedStatement.setString(2, client.getPrenom());
			preparedStatement.setString(3, client.getEmail());
			preparedStatement.setString(4, String.valueOf(client.getNaissance()));
			preparedStatement.setInt(5, client.getId());

			long id = ((PreparedStatement) preparedStatement).executeUpdate();
			connection.close();
			return id;
		}
		catch (SQLException e) {
			throw new DaoException("Echec de la modification du client Dao");
		}
	}

	/**
	 * supprime un client de la base de données
	 * @param client
	 * @return l'identifiant du client
	 * @throws DaoException
	 * @throws SQLException
	 */
	public long delete(Client client) throws DaoException, SQLException {
		try{
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CLIENT_QUERY);
			preparedStatement.setInt(1, client.getId());

			long id = ((PreparedStatement) preparedStatement).executeUpdate();
			connection.close();
			return id;
		}
		catch (SQLException e) {
			throw new DaoException("Echec de la suppression du client Dao");
		}
	}

	/**
	 * trouve un client dans la base de donnée grâce à son identifiant
	 * @param id
	 * @return le client trouvé
	 * @throws DaoException
	 */
	public Client findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_QUERY);
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			rs.next();

			Client client = new Client((int)id, rs.getString("nom"), rs.getString("prenom"),
					rs.getString("email"),
					rs.getDate("naissance").toLocalDate());
			return client;
		} catch (SQLException e) {
			throw new DaoException("Le client n'a pas été trouvé par son id");
		}
	}

	/**
	 * trouve tous les clients présents dans la base de données
	 * @return la liste de tous les clients de la base de données
	 * @throws DaoException
	 */
	public List<Client> findAll() throws DaoException {
		List<Client> clients= new ArrayList<Client>();
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			ResultSet rs = statement.executeQuery(FIND_CLIENTS_QUERY);
			while (rs.next()) {
				Client client = new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getDate("naissance").toLocalDate());
				clients.add(client);
			}
			return clients;
		}
		catch (SQLException e) {
			throw new DaoException("Les clients n'ont pas été trouvés");
		}
	}

	/**
	 * compte le nombre de clients dans la base de données
	 * @return le nombre de clients
	 * @throws DaoException
	 */
	public int count() throws DaoException {
		try  {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement statement = connection.prepareStatement(COUNT_CLIENTS_QUERY);
			ResultSet rs = ((PreparedStatement) statement).executeQuery();
			rs.next();
			return rs.getInt("COUNT(id)");
		} catch (SQLException e) {
			throw new DaoException("Le comptage des clients n'a pas fonctionné");
		}
	}

}
