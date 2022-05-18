package main;

import client.Client;
import exceptions.ConnectionException;
import exceptions.InvalidPortException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Scanner;
import static io.ConsoleOutputter.print;

public class App extends Application {
    static Client client;
    static String address;
    static int port;

    public static void main(String[] args) {
        if (initialize(args)) {
            launch(args);
        }
        else {
            System.exit(0);
        }
    }

    public static boolean initialize(String[] args) {
        Scanner scanner = new Scanner(System.in);
        args = new String[]{"localhost"};
        //String addr = "";
        //int port = 0;
        try {
            address = args[0];
            try {
                System.out.println("Введите порт");
                String s = scanner.nextLine();
                port = Integer.parseInt(s);
            } catch (NumberFormatException e) {
                throw new InvalidPortException();
            }
            client = new Client(address, port);
            //client.consoleMode();
        } catch (ConnectionException e) {
            print(e.getMessage());
            return false;
        }
        return true;
    }

    public static Client getClient() {
        return client;
    }


    /*public void init() {
        try {
            client = new Client(address, port);
            client.connectionTest();
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
    }*/

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }

}
