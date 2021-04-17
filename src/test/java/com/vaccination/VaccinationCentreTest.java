package com.vaccination;

import org.junit.Test;

import static org.junit.Assert.*;
public class VaccinationCentreTest {

    @Test
    public void nearestCentre() {

        VaccinationCentre vc = new VaccinationCentre();
        String centre =  vc.nearestCentre(53.390890, -6.243620);
        assertEquals("Dublin", centre);

        String wrongValues = vc.nearestCentre(0, 0);
        assertEquals(null,wrongValues);

    }

    @Test
    public void distance() {


        fail("Not yet implemented");
    }

    @Test
    public void printList() {
        //fail("Not yet implemented");
    }
}