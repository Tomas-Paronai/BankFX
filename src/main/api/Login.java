package main.api;

import main.Main;
import main.api.database.HandlerDB;
import main.api.person.Employee;
import main.api.person.Person;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by tomas on 4/15/2016.
 */
public class Login {

    private HandlerDB handlerDB;
    private int userLoginID;
    private Person loggedInPerson;
    private LoginType loginType;

    public enum LoginType{
        CLIENT,EMPLOYEE
    }

    public Login(LoginType loginType){
        handlerDB = new HandlerDB(Main.URL, Main.DATABASE, Main.USER, Main.PASS);
        this.loginType = loginType;
    }

    public boolean checkEmployeeLogin(String username, String password){
        HashMap<String,ArrayList<String>> result = null;
        try {
            result = handlerDB.executeForResult("SELECT * FROM employee_login WHERE Username='"+username+"'");
        } catch (HandlerDB.DBHandlerException e) {
            e.printStackTrace();
            return false;
        }

        if(result == null || result.size() == 0){
            return false;
        }

        if(!result.get("Password").get(0).equals(password)){
            return false;
        }

        userLoginID = Integer.parseInt(result.get("EmployeeID").get(0));
        setLoggedInPerson(userLoginID);

        return true;
    }

    public int getUserLoginID() {
        return userLoginID;
    }

    public Person getLoggedInPerson() {
        return loggedInPerson;
    }

    private void setLoggedInPerson(int id){
        try{

            if(loginType == LoginType.EMPLOYEE){
                loadEmployee(id);
            }
            else{
                loadClient(id);
            }

        }
        catch (HandlerDB.DBHandlerException ex){
            ex.printStackTrace();
        }


    }

    private void loadClient(int id) {

    }

    private void loadEmployee(int id) throws HandlerDB.DBHandlerException {
        HashMap<String,ArrayList<String>> result = handlerDB.executeForResult("SELECT * FROM employees WHERE EmployeeID='"+id+"'");

        if(result == null || result.size() ==0){
            return;
        }

        String firstName = result.get("FirstName").get(0);
        String lastName = result.get("LastName").get(0);

        int position = Integer.parseInt(result.get("PositionID").get(0));
        result = handlerDB.executeForResult("SELECT * FROM positions WHERE PositionID='"+position+"'");
        String positionName = result.get("Name").get(0);

        loggedInPerson = new Employee(id,firstName,lastName,positionName);
    }
}
