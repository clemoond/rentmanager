package com.epf.rentmanager.service;

import java.sql.SQLException;
import java.util.ArrayList;
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

	public long create(Vehicle vehicle) throws ServiceException {
		try {
			return this.vehicleDao.create(vehicle);
		}
		catch (DaoException exc){
			throw new ServiceException("Le véhicule n'a pas été créé.");
		}
	}

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

	public List<Vehicle> findAll() throws ServiceException {
		try {
			return this.vehicleDao.findAll();
		} catch (DaoException e) {
			throw new RuntimeException(e);
		}
	}

	public int count() throws DaoException {
		//compter le nombre de véhicle
		return this.vehicleDao.count();
	}
	
}
