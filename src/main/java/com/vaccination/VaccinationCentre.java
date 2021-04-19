package com.vaccination;

import entity.Person;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class VaccinationCentre {


    /**
     * This method is converting each json line in the file to its respective attribute.
     * A custom data type, People is storing each person's details in the data arraylist
     * @param peopleArray JSONArray for storing the json values during parsing
     * @param data arraylist to store all the individuals detail after parsing from json
     */
    private void parsingJson(JSONArray peopleArray, ArrayList<Person> data) {
        //parsing and assigning each person's details to variables
        for (Object o : peopleArray) {

            JSONObject jsonObj = (JSONObject) o;
            String name = (String) jsonObj.get("Name");
            long age = (long) jsonObj.get("Age");
            String latitude = (String) jsonObj.get("Latitude");
            String longitude = (String) jsonObj.get("Longitude");

            //parsing age, latitude and longitude variables from long, string, and string to int, double, and double respectively.
            int intAge = Math.toIntExact(age);
            double lon = Double.parseDouble(longitude);
            double lat = Double.parseDouble(latitude);

            Person node = new Person(name, intAge, lat, lon);

            //add people details into data arraylist
            data.add(node);
        }
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

        //latitude and longitude validation
        if ((lat >= -90 && lat <= 90) && (lon >= -90 && lon <= 90) && (centreLat >= -90 && centreLat <= 90) && (centreLon >= -90 && centreLon <= 90)) {

            // Radius of earth in kilometers.
            final double radius = 6371;

            double latInRad = (lat) * (Math.PI / 180);
            double lonInRad = (lon) * (Math.PI / 180);
            double centreLatInRad = (centreLat) * (Math.PI / 180);
            double centreLonInRad = (centreLon) * (Math.PI / 180);

            double deltaLonInRad = Math.abs(lonInRad - centreLonInRad);

            //spherical law of cosines
            double centralAngle = Math.acos((Math.sin(latInRad) * Math.sin(centreLatInRad))
                    + (Math.cos(latInRad) * Math.cos(centreLatInRad) * Math.cos(deltaLonInRad)));


            //return the distance between the two locations
            return centralAngle * radius;

        } else
            return -1.0;

    }


    /**
     * This method is used to find the nearest centre for vaccination     *
     * @param latPerson value of latitude for the person
     * @param lonPerson value of longitude for the person
     * @return String This returns the value of nearest city(vaccination centre) for the person
     */
    public String nearestCentreCity(double latPerson, double lonPerson) {

        final double latGalway = 53.298810877564875;
        final double lonGalway = -8.997003657335881;
        final double latCork = 51.89742637092438;
        final double lonCork = -8.465763459121026;
        final double latDublin = 53.28603418885669;
        final double lonDublin = -6.4444477725802285;

        VaccinationCentre vaccine = new VaccinationCentre();


        double disFromGalway = vaccine.distance(latPerson, lonPerson, latGalway, lonGalway);
        //System.out.println("\nDistance from Galway centre " + disFromGalway + " Kms");

        double disFromCork = vaccine.distance(latPerson, lonPerson, latCork, lonCork);
        //System.out.println("Distance from Cork centre " + disFromCork + " Kms");

        double disFromDublin = vaccine.distance(latPerson, lonPerson, latDublin, lonDublin);
        //System.out.println("Distance from Dublin centre " + disFromDublin + " Kms");


        if (disFromCork == -1.0 || disFromDublin == -1.0  || disFromGalway == -1.0 ) {
            return "Invalid address";
        } else {
            if ((disFromCork < disFromDublin) && (disFromCork < disFromGalway)) {
                return "Cork";
            } else if ((disFromDublin < disFromCork) && (disFromDublin < disFromGalway)) {
                return "Dublin";
            } else
                return "Galway";
        }
    }

    /**
     * This method stores the person details into different vaccination centre arraylist
     * @param vaccine main class object
     * @param data Arraylist containing every person's details
     * @param corkCityHall arraylist containing individual details mapped to cork city hall
     * @param dublinConventionCentre arraylist containing individual details mapped to dublin convention centre
     * @param galwayRaceCourse arraylist containing individual details mapped to galway race course centre
     */
    private void centreList(VaccinationCentre vaccine, ArrayList<Person> data, ArrayList<Person> corkCityHall, ArrayList<Person> dublinConventionCentre, ArrayList<Person> galwayRaceCourse) {

        //storing details of the person according to the nearest centre calculation
        for (Person s : data) {

            String centre = vaccine.nearestCentreCity(s.getLatitude(), s.getLongitude());
            if (centre.equals("Cork"))
                corkCityHall.add(s);
            else if (centre.equals("Dublin"))
                dublinConventionCentre.add(s);
            else if(centre.equals("Galway"))
                galwayRaceCourse.add(s);
        }
    }


    /**
     * printing the details of people present in the list     *
     * @param vaccineCentre list of people going to a specific vaccination centre
     * @param city          name of the city where vaccination centre is located
     */
    public void printList(ArrayList<Person> vaccineCentre, String city) {

        System.out.println("\nList for " + city + " City is: ");
        for (Person p : vaccineCentre) {
            System.out.println("\nName " + p.getName());
            System.out.println("Age: " + p.getAge());
            System.out.println("Person can be contacted at: " + p.getLatitude() + " latitude and " + p.getLongitude() + " longitude");
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

        try {
            FileReader fileReader = new FileReader(".\\files\\People.txt");
            Object obj = jsonParser.parse(fileReader);
            JSONArray peopleArray = (JSONArray) obj;
            ArrayList<Person> data = new ArrayList<>();

            vc.parsingJson(peopleArray, data);

            //sorting the list by age priority
            Collections.sort(data);

            ArrayList<Person> corkCityHall = new ArrayList<>();
            ArrayList<Person> dublinConventionCentre = new ArrayList<>();
            ArrayList<Person> galwayRaceCourse = new ArrayList<>();

            vc.centreList(vc, data, corkCityHall, dublinConventionCentre, galwayRaceCourse);

            //printing the list according
            vc.printList(corkCityHall, "Cork");
            vc.printList(dublinConventionCentre, "Dublin");
            vc.printList(galwayRaceCourse, "Galway");

            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }
}
