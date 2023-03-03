package com.epf.rentmanager.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.persistence.ConnectionManager;

public class ClientDao {
	
	private static ClientDao instance = null;
	private ClientDao() {}

	public static ClientDao getInstance() {
		if(instance == null) {
			instance = new ClientDao();
		}
		return instance;
	}
	
	private static final String CREATE_CLIENT_QUERY = "INSERT INTO Client(nom, prenom, email, naissance) VALUES(?, ?, ?, ?);";
	private static final String DELETE_CLIENT_QUERY = "DELETE FROM Client WHERE id=?;";
	private static final String FIND_CLIENT_QUERY = "SELECT nom, prenom, email, naissance FROM Client WHERE id=?;";
	private static final String FIND_CLIENTS_QUERY = "SELECT id, nom, prenom, email, naissance FROM Client;";
	
	public long create(Client client) throws DaoException, SQLException {
		Connection connection = ConnectionManager.getConnection();
		Statement statement = connection.createStatement();
		PreparedStatement stmt = connection.prepareStatement(CREATE_CLIENT_QUERY);
		stmt.setString(1, client.getNom());
		stmt.setString(2, client.getPrenom());
		stmt.setString(3, client.getEmail());
		//stmt.setString(4, client.getNaissance());

		stmt.execute();
		return 1;
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

	public ArrayList<Client> findAll() throws DaoException {
		//fait avec le prof le 20/02
		ArrayList<Client> clients= new ArrayList<Client>();
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
			connection.close();
			return clients;
		}
		catch (SQLException e) {
			throw new DaoException("Erreur");
		}
	}

}
