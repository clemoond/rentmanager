package com.epf.rentmanager.validator;

import com.epf.rentmanager.model.Client;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
@Component
public class ClientValidator {

    public static boolean isLegal(Client client){
        return LocalDate.now().getYear() - client.getNaissance().getYear() >= 18;
    }

}
