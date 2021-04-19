package entity;

/**
 * Node class to map each json line consisting of
 * name, age, longitude and latitude variables
 */

public class Person implements Comparable<Person> {

    private String name;
    private int age;
    private double latitude;
    private double longitude;

    /**
     * Node Constructor to set the person's data for a node object
     *
     * @param name      value to set the name of the person
     * @param age       value to set the age
     * @param latitude  value to signify the latitude of the person
     * @param longitude value to signify the longitude of the person
     */
    public Person(String name, int age, double latitude, double longitude) {
        this.name = name;
        this.age = age;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    /**
     * to print the node object
     *
     * @return string value
     */
    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    /**
     * compare the nodes according to the age and sort them
     * in ascending order
     *
     * @param o object signifies the node object
     * @return int value which gives the difference between two age
     */

    @Override
    public int compareTo(Person o) {
        int compareAge = o.getAge();
        return compareAge - this.getAge();
    }
}

