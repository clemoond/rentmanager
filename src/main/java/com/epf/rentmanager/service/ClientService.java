package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.time.LocalDate;
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

	/**
	 * crée un client et vérifie les contraintes nécessaires sur le client
	 * @param client
	 * @return l'identifiant du client
	 * @throws ServiceException
	 */
	public long create(Client client) throws ServiceException {
		try{
			if (!isMajor(client)){
				throw new ServiceException("Le client n'a pas l'âge légal.");
			}
			if (!isEmailValid(client)) {
				throw new ServiceException("Cet email existe déjà, veuillez renseigner une autre adresse mail.");
			}
			return this.clientDao.create(client);
		}
		catch (DaoException | SQLException exc){
			throw new ServiceException("Le client n'a pas été créé.");
		}
	}

	/**
	 * modifie un client et vérifie les contraintes nécessaires sur le client
	 * @param client
	 * @return l'identifiant du client
	 * @throws ServiceException
	 */
	public long update(Client client) throws ServiceException {
		try{
			if (!isMajor(client)){
				throw new ServiceException("Le client n'a pas l'âge légal.");
			}
			if (!isEmailValid(client)) {
				throw new ServiceException("Cet email existe déjà, veuillez renseigner une autre adresse mail.");
			}
			return this.clientDao.update(client);
		}
		catch (DaoException | SQLException exc){
			throw new ServiceException("Le client n'a pas été modifié.");
		}
	}

	/**
	 * vérifie si le client est majeur
	 * @param client
	 * @return un booléen : true si le client est majeur, false sinon
	 */
	private boolean isMajor(Client client){
		int age = LocalDate.now().getYear() - client.getNaissance().getYear();
		System.out.println(age);
		if (age >= 18){
			return true;
		}
		return false;
	}

	/**
	 * vérifie si l'email du client existe déjà
	 * @param client
	 * @return un booléen : false si l'email existe déjà et false sinon
	 */
	private boolean isEmailValid(Client client) {
		try {
			List<Client> clients = findAll();
			for(Client client_i : clients) {
				if (client_i.getEmail().equals(client.getEmail())) {
					return false;
				}
			}
			return true;
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * supprime un client
	 * @param client
	 * @return l'identifiant du client
	 * @throws ServiceException
	 */
	public long delete(Client client) throws ServiceException {
		try{
			return this.clientDao.delete(client);
		}
		catch (DaoException | SQLException exc){
			throw new ServiceException("Le client n'a pas été supprimé.");
		}
	}

	/**
	 * trouve un client par son identifiant
	 * @param id
	 * @return le client trouvé
	 * @throws ServiceException
	 */
	public Client findById(long id) throws ServiceException {
		if(id<=0){
			throw new ServiceException("L'id est négatif");
		}
		try {
			return clientDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Erreur, pas trouvé de client avec cet id");
		}
	}

	/**
	 * trouve tous les clients
	 * @return la liste de tous les clients
	 * @throws ServiceException
	 */
	public List<Client> findAll() throws ServiceException {
		try {
			return this.clientDao.findAll();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * compte le nombre de clients
	 * @return le nombre de clients
	 * @throws DaoException
	 */
	public int count() throws DaoException {
		return this.clientDao.count();
	}
}
