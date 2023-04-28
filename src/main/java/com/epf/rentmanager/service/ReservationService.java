package com.epf.rentmanager.service;

import com.epf.rentmanager.dao.ReservationDao;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Reservation;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservationService {
    private ReservationDao reservationDao;
    private int dureeReservation;

    public static ReservationService instance = null;

    private ReservationService(ReservationDao reservationDao) {

        this.reservationDao = reservationDao;
    }


    /**
     * crée une réservation et vérifie les contraintes nécessaires sur la réservation
     * @param reservation
     * @return l'identifiant de la réservation
     * @throws ServiceException
     */
    public long create(Reservation reservation) throws ServiceException {
        dureeReservation = (int) ChronoUnit.DAYS.between(reservation.getDebut(), reservation.getFin());
        try{
            if (dureeReservation > 7){
                throw new ServiceException("La réservation de ce véhicule par un même utilisateur ne peut pas excéder 7 jours");
            }
            if (!isAvailableThisDay(reservation)){
                throw new ServiceException("La voiture n'est pas réservable car déjà utilisée au moins un jour pendant la période sélectionnée");
            }
            if (!isAvailableThisDay2(reservation)){
                throw new ServiceException("La voiture n'est pas réservable plus de 30 jours d'affilée");
            }
            return reservationDao.create(reservation);
        }
        catch (DaoException | SQLException exc){
            throw new ServiceException("La réservation n'a pas été créée.");
        }
    }

    /**
     * modifie une réservation et vérifie les contraintes nécessaires sur la réservation
     * @param reservation
     * @return l'identifiant de la réservation
     * @throws ServiceException
     */
    public long update(Reservation reservation) throws ServiceException {
        try{
            return this.reservationDao.update(reservation);
        }
        catch (DaoException | SQLException exc){
            throw new ServiceException("La réservation n'a pas été modifiée.");
        }
    }

    /**
     * vérifie si un véhicule a déjà été utilisé dans la période sélectionnée
     * @param reservation
     * @return un booléen : false si la voiture n'est pas réservable et true sinon
     * @throws ServiceException
     */
    public boolean isAvailableThisDay(Reservation reservation) throws ServiceException {
        try {
            return this.reservationDao.isAvailableThisDay(reservation);
        }
        catch (DaoException | SQLException exc) {
            throw new ServiceException("Echec de la requête isAvailableThisDay Service");
        }
    }

    /**
     * vérifie si un véhicule est réservé plus de 30 jours d'affilée
     * @param reservation
     * @return un booléen : false si la voiture n'est pas réservable et true sinon
     * @throws ServiceException
     */
    public boolean isAvailableThisDay2(Reservation reservation) throws ServiceException {
        try {
            return this.reservationDao.isAvailableThisDay2(reservation);
        }
        catch (DaoException | SQLException exc) {
            throw new ServiceException("Echec de la requête isAvailableThisDay2 Service");
        }
    }

    /**
     * supprime une réservation
     * @param reservation
     * @return l'identifiant de la réservation
     * @throws ServiceException
     */
    public long delete(Reservation reservation) throws ServiceException {
        try{
            return this.reservationDao.delete(reservation);
        }
        catch (DaoException | SQLException exc){
            throw new ServiceException("La réservation n'a pas été supprimée.");
        }
    }

    /**
     * trouve une réservation grâce à son identifiant
     * @param id
     * @return la réservation trouvée
     * @throws ServiceException
     */
    public Reservation findById(long id) throws ServiceException {
        if(id<=0){
            throw new ServiceException("L'id est négatif");
        }
        try {
            return reservationDao.findById(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur, pas trouvé de réservation avec cet id");
        }
    }

    /**
     * trouve les réservations associées à un client
     * @param id
     * @return la liste des réservations trouvées
     * @throws ServiceException
     */
    public List<Reservation> findResaByClientId(long id) throws ServiceException {
        if(id<=0){
            throw new ServiceException("L'id est négatif");
        }
        try {
            return reservationDao.findResaByClientId(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur, la réservation n'a pas été trouvée");
        }
    }

    /**
     * trouve les réservations associées à un véhicule
     * @param id
     * @return la liste des réservations trouvées
     * @throws ServiceException
     */
    public List<Reservation> findResaByVehicleId(long id) throws ServiceException {
        if(id<=0){
            throw new ServiceException("L'id est négatif");
        }
        try {
            return reservationDao.findResaByVehicleId(id);
        } catch (DaoException e) {
            e.printStackTrace();
            throw new ServiceException("Erreur, la réservation n'a pas été trouvée");
        }
    }

    /**
     * trouve toutes les réservations
     * @return la liste de toutes les réservations
     * @throws ServiceException
     */
    public List<Reservation> findAll() throws ServiceException {
        try {
            return this.reservationDao.findAll();
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * compte le nombre de réservations
     * @return le nombre de réservations
     * @throws DaoException
     */
    public int count() throws DaoException {
        return this.reservationDao.count();
    }
}
