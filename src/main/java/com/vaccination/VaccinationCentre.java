package com.vaccination;

import pojo.Node;
import pojo.People;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class VaccinationCentre {

    /**
     * This method is used to find the nearest centre for vaccination
     *
     * @param latPerson value of latitude for the person
     * @param lonPerson value of longitude for the person
     * @return String This returns the value of nearest city(vaccination centre) for the person
     */
    public String nearestCentre(double latPerson, double lonPerson) {

        final double latGalway = 53.298810877564875;
        final double lonGalway = -8.997003657335881;
        final double latCork = 51.89742637092438;
        final double lonCork = -8.465763459121026;
        final double latDublin = 53.28603418885669;
        final double lonDublin = -6.4444477725802285;

        VaccinationCentre vaccine = new VaccinationCentre();

        double disFromGalway = vaccine.distance(latPerson, lonPerson, latGalway, lonGalway);
        System.out.println("\nDistance from Galway centre " + disFromGalway + " Kms");

        double disFromCork = vaccine.distance(latPerson, lonPerson, latCork, lonCork);
        System.out.println("Distance from Cork centre " + disFromCork + " Kms");

        double disFromDublin = vaccine.distance(latPerson, lonPerson, latDublin, lonDublin);
        System.out.println("Distance from Dublin centre " + disFromDublin + " Kms");

        if ((disFromCork < disFromDublin) && (disFromCork < disFromGalway)) {
            return "Cork";
        } else if ((disFromDublin < disFromCork) && (disFromDublin < disFromGalway)) {
            return "Dublin";
        } else
            return "Galway";
    }

    /**
     * This method finds the distance (in Kms) between the person and the vaccination
     * centre. It uses longitude and latitude of both the location, converts it into
     * radians and then calculate the distance using the haversine formula in great
     * circle distance calculations
     *
     * @param lat       value of latitude for the person
     * @param lon       value of longitude for the person
     * @param centreLat value of latitude for the vaccination centre
     * @param centreLon value of longitude for the vaccination centre
     * @return double distance between the two location
     */
    public double distance(double lat, double lon, double centreLat, double centreLon) {


        // Radius of earth in kilometers.
        final double radius = 6371;

        double latInRad = (lat) * (Math.PI / 180);
        double lonInRad = (lon) * (Math.PI / 180);
        double centreLatInRad = (centreLat) * (Math.PI / 180);
        double centreLonInRad = (centreLon) * (Math.PI / 180);

        double deltaLonInRad = Math.abs(lonInRad - centreLonInRad);

        //Haversine Formula
        /*double HaversineCentralAngle = Math.pow(Math.sin(deltaLat / 2), 2)
                + Math.cos(latInRad) * Math.cos(centreLatInRad)
                * Math.pow(Math.sin(deltaLon / 2),2);

        double centralAngle = 2 * Math.asin(Math.sqrt(HaversineCentralAngle));*/


        //spherical law of cosines
        double centralAngle = Math.acos((Math.sin(latInRad) * Math.sin(centreLatInRad))
                + (Math.cos(latInRad) * Math.cos(centreLatInRad) * Math.cos(deltaLonInRad)));


        //return the distance between the two locations
        return centralAngle * radius;

    }


    /**
     * printing the details of people present in the list
     *
     * @param people list of people going to a specific vaccination centre
     * @param city   name of the city where vaccination centre is located
     */
    public void printList(ArrayList<People> people, String city) {

        System.out.println("List for " + city + " City is: ");
        for (People p : people) {
            System.out.println("\nName " + p.getName());
            System.out.println("Age: " + p.getAge());
        }
    }


    /**
     * This is the main method which is used to read the text (json) file,
     * parsing the data and storing it into JsonArray. The array then fetches
     * each key value pair and stores into node object which is then further
     * saved in different arraylist based on vaccination centre location. The
     * values are sorted by Age priority in People class.
     *
     * @param args Unused
     * @throws IOException    On input error exception
     * @throws ParseException On parsing error exception
     */
    public static void main(String[] args) throws IOException, ParseException {

        VaccinationCentre vc = new VaccinationCentre();


        JSONParser jsonParser = new JSONParser();
        FileReader fileReader = new FileReader(".\\files\\People.txt");
        Object obj = jsonParser.parse(fileReader);
        JSONArray peopleArray = (JSONArray) obj;

        ArrayList<Node> data = new ArrayList<>();

        //parsing and adding each
        for (Object o : peopleArray) {

            JSONObject person = (JSONObject) o;
            String name = (String) person.get("Name");
            long age = (long) person.get("Age");
            String latitude = (String) person.get("Latitude");
            String longitude = (String) person.get("Longitude");

            //parsing age, latitude and longitude variables from long, string, and string to int, double, and double respectively.
            int intAge = Math.toIntExact(age);
            double lon = Double.parseDouble(longitude);
            double lat = Double.parseDouble(latitude);

            Node node = new Node(name, intAge, lat, lon);

            data.add(node);
        }

        //sorting the list by age priority
        Collections.sort(data);

        ArrayList<People> corkCityHall = new ArrayList<>();
        ArrayList<People> dublinConventionCentre = new ArrayList<>();
        ArrayList<People> galwayRaceCourse = new ArrayList<>();

        //storing the name and age of the person according to the nearest centre calculation
        for (Node s : data) {
            People people = new People(s.getName(), s.getAge());
            String centre = vc.nearestCentre(s.getLatitude(), s.getLongitude());
            if (centre.equals("Cork"))
                corkCityHall.add(people);
            else if (centre.equals("Dublin"))
                dublinConventionCentre.add(people);
            else
                galwayRaceCourse.add(people);
        }


        //printing the list according
        vc.printList(corkCityHall, "Cork");
        vc.printList(dublinConventionCentre, "Dublin");

        vc.printList(galwayRaceCourse, "Galway");

    }
}
