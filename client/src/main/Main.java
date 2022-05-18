package main;

import client.Client;
import exceptions.ConnectionException;
import exceptions.InvalidPortException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



/*public class Main extends Application {

    public static void main(String args[]) {
        //App.main(args);
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }
}*/
public class Main {
    public static void main(String[] args) {
        App.main(args);
    }
}

