package main.api.person;

import main.api.person.Person;

/**
 * Created by tomas on 4/15/2016.
 */
public class Employee extends Person{

    private String position;

    public Employee(int employeeID, String firstName, String lastName, String position) {
        super(employeeID,firstName,lastName);
        this.position = position;
    }

    public String getPosition() {
        return position;
    }
}
