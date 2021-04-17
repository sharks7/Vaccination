package com.vaccination;

import org.junit.Test;

import static org.junit.Assert.*;
public class VaccinationCentreTest {

    @Test
    public void nearestCentre() {

        VaccinationCentre vc = new VaccinationCentre();
        String centre =  vc.nearestCentre(53.390890, -6.243620);
        assertEquals("Dublin", centre);

        String anotherCentre = vc.nearestCentre(0, 0);
        assertEquals("Cork",anotherCentre);

    }

    @Test
    public void distance() {
        VaccinationCentre vc = new VaccinationCentre();
        double distance = vc.distance(53.390890, -6.243620, 53.28603418885669, -6.4444477725802285);
        assertEquals(17.71,distance,0.01);

    }

    @Test
    public void printList() {
        fail("Not yet implemented");
    }
}