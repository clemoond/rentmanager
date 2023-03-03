package com.epf.rentmanager.service;

import java.util.ArrayList;
import java.util.List;

import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.dao.VehicleDao;

public class VehicleService {

	private VehicleDao vehicleDao;
	public static VehicleService instance = null;
	
	private VehicleService() {
		this.vehicleDao = VehicleDao.getInstance();
	}
	
	public static VehicleService getInstance() {
		if (instance == null) {
			instance = new VehicleService();
		}
		
		return instance;
	}
	
	
	public long create(Vehicle vehicle) throws ServiceException {
		// TODO: créer un véhicule
		return 0;
	}

	public Vehicle findById(long id) throws ServiceException {
		if(id<=0){
			throw new ServiceException("L'id est négatif");
		}
		try {
			return vehicleDao.getInstance().findById(id);
		} catch (DaoException e) {
			e.printStackTrace();
			throw new ServiceException("Erreur");
		}
	}

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return this.vehicleDao.getInstance().findAll();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public int count() {
		//compter le nombre de véhicle
		return 1;
	}
	
}
