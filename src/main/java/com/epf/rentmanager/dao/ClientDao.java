package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;
import org.springframework.stereotype.Repository;

@Repository
public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	private static final String COUNT_CLIENTS_QUERY = "SELECT COUNT(id) FROM Client;";
	
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
	
	public long delete(Client client) throws DaoException {
		return 0;
	}

	public Client findById(long id) throws DaoException {
		try {
			Connection connection = ConnectionManager.getConnection();
			PreparedStatement preparedStatement = connection.prepareStatement(FIND_CLIENT_QUERY);
			preparedStatement.setLong(1, id);
			ResultSet rs = preparedStatement.executeQuery();
			//autre possibilité mais dangereuse si on fait un statement tout court
			// String pID = "3";
			//ResultSet rs = preparedStatement.executeQuery("SELECT * from CLIENT WHERE id =" + pID);
			rs.next();

			Client client = new Client((int)id, rs.getString("nom"), rs.getString("prenom"),
					rs.getString("email"),
					rs.getDate("naissance").toLocalDate());
			return client;
		} catch (SQLException e) {
			throw new DaoException("Erreur");
		}
	}

	public List<Client> findAll() throws DaoException {
		//fait avec le prof le 20/02
		List<Client> clients= new ArrayList<Client>();
		try {
			Connection connection = ConnectionManager.getConnection();
			Statement statement = connection.createStatement();
			//le rs est un tableau de données avec un curseur
			ResultSet rs = statement.executeQuery(FIND_CLIENTS_QUERY);

			//tant qu'il y a une ligne, on crée un client avec les données fournies
			while (rs.next()) {
				Client client = new Client(rs.getInt("id"), rs.getString("nom"), rs.getString("prenom"), rs.getString("email"), rs.getDate("naissance").toLocalDate());
				clients.add(client);
			}
			return clients;
		}
		catch (SQLException e) {
			throw new DaoException("Erreur");
		}
	}

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
