package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.dao.ClientDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientService {

	private ClientDao clientDao;
	public static ClientService instance = null;
	
	private ClientService(ClientDao clientDao) {
		this.clientDao = clientDao;
	}
	
	
	public long create(Client client) throws ServiceException {
		try{
			return this.clientDao.create(client);
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
			return clientDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Erreur");
		}
	}

	public List<Client> findAll() throws ServiceException {
		try {
			return this.clientDao.findAll();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public int count() throws DaoException {
		//compter le nombre de clients
		return this.clientDao.count();
	}
}
