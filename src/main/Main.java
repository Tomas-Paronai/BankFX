package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public final static String URL = "192.168.22.69";
    public final static String DATABASE = "itbank14";
    public final static String USER = "parohy";
    public final static String PASS = "banka";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("gui/layout/employeeLogin.fxml"));
        primaryStage.setTitle("EmployeeLogin");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
