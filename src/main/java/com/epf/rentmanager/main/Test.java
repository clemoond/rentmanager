package com.epf.rentmanager.main;

import com.epf.rentmanager.configuration.AppConfiguration;
import com.epf.rentmanager.exception.DaoException;
import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Reservation;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.ReservationService;
import com.epf.rentmanager.service.VehicleService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.List;

public class Test {
    public static void main(String[] args) throws DaoException {
        ApplicationContext context = new AnnotationConfigApplicationContext(AppConfiguration.class);
        ClientService clientService = context.getBean(ClientService.class);
        VehicleService vehicleService = context.getBean(VehicleService.class);

        /*
        //Afficher liste véhicules
        VehicleService vehicleService = VehicleService.getInstance();
        List<Vehicle> vehicles = null;
        try {
            vehicles = vehicleService.findAll();
            System.out.println(vehicles);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        System.out.println(vehicleService.count());

        //Afficher liste clients
        ClientService clientService = ClientService.getInstance();
        List<Client> clients = null;
        try {
            clients = clientService.findAll();
            System.out.println(clients);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        System.out.println(clientService.count());

        //Afficher liste résa
        ReservationService reservationService = ReservationService.getInstance();
        List<Reservation> reservations = null;
        try {
            reservations = reservationService.findAll();
            System.out.println(reservations);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        System.out.println(reservationService.count());


        //afficher client qui a un id = 2
        ClientService clientService = ClientService.getInstance();
        Client client = null;
        try{
            client = clientService.findById(2);
            System.out.println(client);
        }
        catch (ServiceException e){
            throw new RuntimeException(e);
        }

        //afficher véhicule qui a un id = 2
        Vehicle vehicle = null;
        try{
            vehicle = vehicleService.findById(2);
            System.out.println(vehicle);
        }
        catch (ServiceException e){
            throw new RuntimeException(e);
        }
        */
    }
}
