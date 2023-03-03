package com.epf.rentmanager.main;

import com.epf.rentmanager.exception.ServiceException;
import com.epf.rentmanager.model.Client;
import com.epf.rentmanager.model.Vehicle;
import com.epf.rentmanager.service.ClientService;
import com.epf.rentmanager.service.VehicleService;

import java.util.List;

public class Test {
    public static void main(String[] args){

        //Afficher liste véhicules
        VehicleService vehicleService = VehicleService.getInstance();
        List<Vehicle> vehicles = null;
        try {
            vehicles = vehicleService.findAll();
            System.out.println(vehicles);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

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
    }
}
