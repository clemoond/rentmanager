package com.epf.rentmanager.service;

import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;
import org.springframework.stereotype.Service;

@Service
public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance = null;
	
	private VehicleService(VehicleDao vehicleDao) {
		this.vehicleDao = vehicleDao;
	}

	/**
	 * crée un véhicule
	 * @param vehicle
	 * @return l'identifiant du véhicule
	 * @throws ServiceException
	 */
	public long create(Vehicle vehicle) throws ServiceException {
		try {
			return this.vehicleDao.create(vehicle);
		}
		catch (DaoException exc){
			throw new ServiceException("Le véhicule n'a pas été créé.");
		}
	}

	/**
	 * modifie un véhicule
	 * @param vehicle
	 * @return l'identifiant du véhicule
	 * @throws ServiceException
	 */
	public long update(Vehicle vehicle) throws ServiceException {
		try{
			return this.vehicleDao.update(vehicle);
		}
		catch (DaoException exc) {
			throw new ServiceException("Le véhicule n'a pas été modifié.");
		}
	}

	/**
	 * supprime un véhicule
	 * @param vehicle
	 * @return l'identifiant du véhicule
	 * @throws ServiceException
	 */
	public long delete(Vehicle vehicle) throws ServiceException {
		try {
			return this.vehicleDao.delete(vehicle);
		}
		catch (DaoException exc) {
			throw new ServiceException("Le véhicule n'a pas été supprimé.");
		}
	}

	/**
	 * trouve un véhicule grâce à son identifiant
	 * @param id
	 * @return le véhicule trouvé
	 * @throws ServiceException
	 */
	public Vehicle findById(long id) throws ServiceException {
		if(id<=0){
			throw new ServiceException("L'id est négatif");
		}
		try {
			return vehicleDao.findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Erreur");
		}
	}

	/**
	 * trouve les véhicules associés à un client
	 * @param id
	 * @return la liste des véhicules trouvés
	 * @throws ServiceException
	 */
	public List<Vehicle> findVehiclesByClientId(long id) throws ServiceException {
		if(id<=0){
			throw new ServiceException("L'id est négatif");
		}
		try {
			return vehicleDao.findVehiclesByClientId(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Erreur, les véhicules n'ont pas été trouvés par l'id du client");
		}
	}

	/**
	 * trouve tous les véhicules
	 * @return la liste de tous les véhicules
	 * @throws ServiceException
	 */
	public List<Vehicle> findAll() throws ServiceException {
		try {
			return this.vehicleDao.findAll();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * compte le nombre de véhicules
	 * @return le nombre de véhicules
	 * @throws DaoException
	 */
	public int count() throws DaoException {
		return this.vehicleDao.count();
	}
	
}
