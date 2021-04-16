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

public class Main {

    /**
     * This method is used to find the nearest centre for vaccination
     * @param lat_person value of latitude for the person
     * @param lon_person value of longitude for the person
     * @return String This returns the value of nearest city(vaccination centre) for the person
     */
    private static String nearestCentre(double lat_person, double lon_person){

        final double lat_Galway = 53.298810877564875;
        final double lon_Galway = -8.997003657335881;
        final double lat_Cork = 51.89742637092438;
        final double lon_Cork = -8.465763459121026;
        final double lat_Dublin =  53.28603418885669;
        final double lon_Dublin = -6.4444477725802285;

        double disFromGalway = distance(lat_person, lon_person, lat_Galway, lon_Galway);
        //System.out.println("\nDistance from Galway centre " +disFromGalway + " Kms");

        double disFromCork = distance(lat_person, lon_person, lat_Cork, lon_Cork);
        //System.out.println("Distance from Cork centre " +disFromCork + " Kms");

        double disFromDublin = distance(lat_person, lon_person, lat_Dublin, lon_Dublin);
        //System.out.println("Distance from Dublin centre " +disFromDublin + " Kms");

        if((disFromCork < disFromDublin) && (disFromCork < disFromGalway)){
            return "Cork";
        } else if((disFromDublin < disFromCork) && (disFromDublin < disFromGalway)){
            return "Dublin";
        } else
            return "Galway";
    }

    /**
     * This method finds the distance (in Kms) between the person and the vaccination
     * centre. It uses longitude and latitude of both the location, converts it into
     * radians and then calculate the distance using the haversine formula in great
     * circle distance calculations
     * @param lat value of latitude for the person
     * @param lon value of longitude for the person
     * @param centre_lat value of latitude for the vaccination centre
     * @param centre_lon value of longitude for the vaccination centre
     * @return double distance between the two location
     */
    private static double distance(double lat, double lon, double centre_lat, double centre_lon){

        // Radius of earth in kilometers.
        final double radius = 6371;

        double latInRad = (lat)*(Math.PI/180);
        double lonInRad = (lon)*(Math.PI/180);
        double centre_latInRad = (centre_lat)*(Math.PI/180);
        double centre_lonInRad = (centre_lon)*(Math.PI/180);

        double deltalat = Math.abs(latInRad - centre_latInRad);
        double deltalon = Math.abs(lonInRad - centre_lonInRad);

        //Haversine Formula
        /*double formulae_a = Math.pow(Math.sin(deltalat / 2), 2)
                + Math.cos(lat) * Math.cos(centre_latInRad)
                * Math.pow(Math.sin(deltalon / 2),2);

        double d_distance = 2 * Math.asin(Math.sqrt(formulae_a));*/


        //spherical law of cosines
        double d_distance = Math.acos((Math.sin(latInRad) * Math.sin(centre_latInRad))
                +(Math.cos(latInRad) * Math.cos(centre_latInRad)));




        //return the distance between the two locations
        return d_distance * radius;

    }


    /**
     * printing the details of people present in the list
     * @param people list of people going to a specific vaccination centre
     * @param city name of the city where vaccination centre is located
     */
    public static void printList(ArrayList<People> people, String city){

        System.out.println("List for " +city + " City is: ");
        for(People p: people){
            System.out.println("\nName " +p.getName());
            System.out.println("Age: " +p.getAge());
        }
    }


    /**
     * This is the main method which is used to read the text (json) file,
     * parsing the data and storing it into JsonArray. The array then fetches
     * each key value pair and stores into node object which is then further
     * saved in different arraylist based on vaccination centre location. The
     * values are sorted by Age priority in People class.
     * @param args Unused
     * @throws IOException On input error exception
     * @throws ParseException On parsing error exception
     */
    public static void main(String[] args) throws IOException, ParseException {

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
        for(Node s: data){
            People people = new People(s.getName(), s.getAge());
            String centre = nearestCentre(s.getLatitude(), s.getLongitude());
            if(centre.equals("Cork"))
                corkCityHall.add(people);
             else if(centre.equals("Dublin"))
                dublinConventionCentre.add(people);
             else
                galwayRaceCourse.add(people);
        }


        //printing the list according
        printList(corkCityHall, "Cork");
        printList(dublinConventionCentre, "Dublin");
        printList(galwayRaceCourse, "Galway");


        //test cases
        //nearestCentreMapperTest();;

    }






    public static void nearestCentreMapperTest(){

        double dublin_person1_lat = 53.390891674126856;
        double dublin_person1_lon = -6.243581771850587;

        String dublin_centre = nearestCentre(dublin_person1_lat, dublin_person1_lon);
        System.out.println("Nearest Centre for the first person " +dublin_centre);

        double dublin_person2_lat = 53.39104522988466;
        double dublin_person2_lon = -6.244568824768067;

        String dublin_centre1 = nearestCentre(dublin_person2_lat, dublin_person2_lon);
        System.out.println("Nearest Centre for second person " +dublin_centre1);

        double cork_person1_lat = 51.889418262664115;
        double cork_person1_lon = -8.481445312500002;

        System.out.println("Nearest centre for the third person " + nearestCentre(cork_person1_lat, cork_person1_lon));

        double cork_person2_lat = 51.88284911979613;
        double cork_person2_lon = -8.531227111816408;

        System.out.println("Nearest centre for the fourth person " +nearestCentre(cork_person2_lat, cork_person2_lon));

        double galway_person1_lat = 53.270551578402454;
        double galway_person1_lon = -9.021148681640627;

        System.out.println("Nearest centre for the fifth person " +nearestCentre(galway_person1_lat, galway_person1_lon));

        double galway_person2_lat = 53.260284357597115;
        double galway_person2_lon = -9.071960449218752;

        System.out.println("Nearest centre for the sixth person " +nearestCentre(galway_person2_lat, galway_person2_lon));


    }
}
