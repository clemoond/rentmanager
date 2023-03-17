package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
@Service
public class ReservationService {
    private ReservationDao reservationDao;
    public static ReservationService instance = null;

    private ReservationService(ReservationDao reservationDao) {

        this.reservationDao = reservationDao;
    }


    public long create(Reservation client) throws ServiceException {
        try{
            return reservationDao.create(client);
        }
        catch (DaoException | SQLException exc){
            throw new ServiceException("La réservation n'a pas été créée.");
        }
    }

    public Reservation findResaByClientId(long id) throws ServiceException {
        if(id<=0){
            throw new ServiceException("L'id est négatif");
        }
        try {
            return reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur");
        }
    }

    public Reservation findResaByVehicleId(long id) throws ServiceException {
        if(id<=0){
            throw new ServiceException("L'id est négatif");
        }
        try {
            return reservationDao.findResaByVehicleId(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur");
        }
    }

    public List<Reservation> findAll() throws ServiceException {
        try {
            return this.reservationDao.findAll();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    public int count() throws DaoException {
        //compter le nombre de réservations
        return this.reservationDao.count();
    }
}
