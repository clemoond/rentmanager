package com.epf.rentmanager.validator;

import com.epf.rentmanager.model.Client;
import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ClientValidatorTest {
    @Test
    public void isLegal_should_return_true_when_age_is_over_18(){
        //Given
        Client legalClient = new Client("Jean", "Dupont", "jean.dupont@email.com", LocalDate.of(1988, 01, 22));

        //Then
        assertTrue(ClientValidator.isLegal(legalClient));
    }

    @Test
    public void isLegal_should_return_false_when_age_is_under_18(){
        //Given
        Client illegalClient = new Client("Jean", "Dupont", "jean.dupont@email.com", LocalDate.of(2010, 01,22));

        //Then
        assertFalse(ClientValidator.isLegal(illegalClient));
    }
}
