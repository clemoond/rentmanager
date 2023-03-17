package com.epf.rentmanager.validator;

import com.epf.rentmanager.model.Client;

import java.time.LocalDate;

public class ClientValidator {

    public static boolean isLegal(Client client){
        return LocalDate.now().getYear() - client.getNaissance().getYear() >= 18;
    }

}
