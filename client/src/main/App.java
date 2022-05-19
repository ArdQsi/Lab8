package main;

import client.Client;
import controllers.ControllerLogin;
import controllers.ControllerSignUp;
import controllers.MainWindowController;
import controllers.MainWindowController1;
import exceptions.ConnectionException;
import exceptions.InvalidPortException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ResourceBundle;
import java.util.Scanner;

import static io.ConsoleOutputter.print;

public class App extends Application {
    static Client client;
    static String address;
    static int port;
    private Stage primaryStage;
    private static ObservableResourceFactory resourceFactory;
    private static final String BUNDLE = "bundles.gui";
    private static final String APP_TITTLE = "Product Manager";

    public static void main(String[] args) {
        //resourceFactory = new ObservableResourceFactory();
        //resourceFactory.setResources(ResourceBundle.getBundle(BUNDLE));
        if (initialize(args)) {
            launch(args);
        } else {
            System.exit(0);
        }
    }

    public static boolean initialize(String[] args) {
        Scanner scanner = new Scanner(System.in);
        args = new String[]{"localhost"};
        //String addr = "";
        //int port = 0;
        /*try {
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
        }*/
        port = 8080;
        try {
            client = new Client(address, port);
        } catch (ConnectionException e) {

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

    /*@Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("../controllers/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.show();
    }
*/


    @Override
    public void start(Stage stage) {
        try {
            primaryStage = stage;
            FXMLLoader loginWindowLoader = new FXMLLoader();
            loginWindowLoader.setLocation(getClass().getResource("../controllers/login.fxml"));
            Parent loginWindowRootNode = loginWindowLoader.load();
            Scene loginWindowScene = new Scene(loginWindowRootNode);
            ControllerLogin controllerLogin = loginWindowLoader.getController();
            controllerLogin.setApp(this);
            controllerLogin.setClient(client);
            controllerLogin.initLangs(resourceFactory);

            stage.setTitle("LOGIN");
            stage.setScene(loginWindowScene);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {

        }
    }

    public void setSignUp() {
        try {
            FXMLLoader signUpWindowLoader = new FXMLLoader();
            signUpWindowLoader.setLocation(getClass().getResource("../controllers/signUp.fxml"));
            signUpWindowLoader.load();
            Parent signUpWindowRootNode = signUpWindowLoader.getRoot();
            Scene signUpWindowScene = new Scene(signUpWindowRootNode);
            ControllerSignUp controllerSignUp = signUpWindowLoader.getController();
            controllerSignUp.setApp(this);
            controllerSignUp.setClient(client);
            controllerSignUp.initLangs(resourceFactory);
            primaryStage.setScene(signUpWindowScene);
            primaryStage.setResizable(false);
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*public void setMainWindow() {
        try {
            FXMLLoader mainWindowLoader = new FXMLLoader();
            mainWindowLoader.setLocation(getClass().getResource("/controllers/main.fxml"));
            Parent mainWindowRootNode = mainWindowLoader.load();
            Scene mainWindowScene = new Scene(mainWindowRootNode);
            MainWindowController mainWindowController = mainWindowLoader.getController();

            mainWindowController.setClient(client);
            mainWindowController.setUserName(client.getUser()!=null?client.getUser().getLogin():"");
            mainWindowController.setPrimaryStage(primaryStage);



            primaryStage.setScene(mainWindowScene);
            primaryStage.setResizable(true);
            primaryStage.setOnCloseRequest((e) ->{
                print("Main window close!!!");
                client.close();
            });
            primaryStage.show();




        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }*/

    public void setMainWindow() {
        try {
            FXMLLoader mainWindowLoader = new FXMLLoader();
            mainWindowLoader.setLocation(getClass().getResource("/controllers/main.fxml"));
            Parent mainWindowRootNode = mainWindowLoader.load();
            Scene mainWindowScene = new Scene(mainWindowRootNode);
            MainWindowController1 mainWindowController = mainWindowLoader.getController();

            mainWindowController.setClient(client);
            mainWindowController.setUserName(client.getUser()!=null?client.getUser().getLogin():"");
            mainWindowController.setPrimaryStage(primaryStage);
            //mainWindowController.initFilter();
            //mainWindowController.initLangs(resourceFactory);


            primaryStage.setScene(mainWindowScene);
            primaryStage.setResizable(true);
            primaryStage.setOnCloseRequest((e) ->{
                print("Main window close!!!");
                client.close();
            });
            primaryStage.show();




        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
}
