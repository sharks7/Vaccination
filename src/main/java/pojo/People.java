package pojo;

/**
 * People class to store create custom object consisting
 * of name and age variables
 */
public class People {

    private String name;
    private int age;

    /**
     * Constructor to set people's name and age
     * @param name value to set the name of the person
     * @param age value to set the age of the person
     */
    public People(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public long getAge() {
        return age;
    }


}
