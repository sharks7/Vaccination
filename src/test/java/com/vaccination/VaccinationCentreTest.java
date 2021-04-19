package com.vaccination;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class VaccinationCentreTest {

   private VaccinationCentre  vc = new VaccinationCentre();



    @Test
    public void distance_allAddressInvalid() {

        double allNullValues = vc.distance(0, 0, 0, 0);
        assertEquals(0,allNullValues,0);

    }


    @Test
    public void distance_invalidAddressForPerson() {

        double longitudeInvalid = vc.distance(54.665, 453.6, 53.286034, -6.444447);
        assertEquals(-1.0,longitudeInvalid,0);

        double latInvalid = vc.distance(98, -7.45545, 33.667, -5.454);
        assertEquals(-1.0,latInvalid,0);

    }

    @Test
    public void distance_invalidCentreAddress() {

        double centreLatInvalid = vc.distance(53.390890, -6.243620, 445.28603418885669, -6.4444477725802285);
        assertEquals(-1.0,centreLatInvalid,0);

        double centreLonInvalid = vc.distance(55.390890, -3.243620, 5.28603418885669, -656.4444477725802285);
        assertEquals(-1.0,centreLonInvalid,0);
    }


    @Test
    public void distance() {

        double distance = vc.distance(53.390890, -6.243620, 53.28603418885669, -6.4444477725802285);
        assertEquals(17.71,distance,0.01);
    }

    @Test
    public void nearestCentreCity() {


        String dublinCentre = vc.nearestCentreCity(53.390890, -6.243620);
        assertEquals("Dublin", dublinCentre);

        String corkCentre = vc.nearestCentreCity(0, 0);
        assertEquals("Cork", corkCentre);

        String galwayCentre = vc.nearestCentreCity(53.2988, -8.9845);
        assertEquals("Galway", galwayCentre);
    }

    @Test
    public void invalid_nearestCentreCity() {


        String centre = vc.nearestCentreCity(453.390890, -6.23620);
        assertEquals("Invalid address", centre);

        String anotherCentre = vc.nearestCentreCity(0, -9000.33);
        assertEquals("Invalid address", anotherCentre);

    }

}