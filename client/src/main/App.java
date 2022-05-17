/*package main;

import client.Client;
import exceptions.ConnectionException;
import exceptions.InvalidPortException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;

import static io.OutputManager.print;

public class App extends Application {


    public static boolean initialize(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String addr = "";
        int port = 0;
        try {
            addr = args[0];
            try {
                System.out.println("Введите порт");
                String s = scanner.nextLine();
                port = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                throw new InvalidPortException();
            }
            Client client = new Client(addr, port);
            client.start();
        } catch (ConnectionException e) {
            print(e.getMessage());
        }
        return true;
    }

    public static void main(String[] args) {
        if (initialize(args)) launch(args);
        else System.exit(0);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }

}*/
