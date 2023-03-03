package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;

public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance = null;
	
	private ClientService() {
		this.clientDao = ClientDao.getInstance();
	}
	
	public static ClientService getInstance() {
		if (instance == null) {
			instance = new ClientService();
		}
		
		return instance;
	}
	
	
	public long create(Client client) throws ServiceException {
		try{
			clientDao.create(client);
			return 1;
		}
		catch (DaoException | SQLException exc){
			throw new ServiceException("Le client n'a pas été créé.");
		}
	}

	public Client findById(long id) throws ServiceException {
		if(id<=0){
			throw new ServiceException("L'id est négatif");
		}
		try {
			return clientDao.getInstance().findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Erreur");
		}
	}

	public ArrayList<Client> findAll() throws ServiceException {
		try {
			return this.clientDao.getInstance().findAll();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public int count() {
		//compter le nombre de clients
		return 1;
	}
}
