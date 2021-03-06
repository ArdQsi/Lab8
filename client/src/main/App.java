package main;

import client.Client;
import io.OutputManager;
import io.OutputterUI;
import controllers.ControllerLogin;
import controllers.ControllerSignUp;
import controllers.MainWindowController;
import exceptions.ConnectionException;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tools.ObservableResourceFactory;

import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import static io.ConsoleOutputter.print;


public class App extends Application {
    static Client client;
    static String address;
    static int port;
    private Stage primaryStage;
    private static ObservableResourceFactory resourceFactory;
    private OutputterUI outputter;
    private OutputManager outputManager;
    public static String BUNDLE = "bundles.GuiLabels";

    public static void main(String[] args) {
        if (initialize(args)) {
            launch(args);
        } else {
            System.exit(0);
        }

    }

    public OutputManager getOutputManager() {
        return outputManager;
    }

    public static boolean initialize(String[] args) {
        //Scanner scanner = new Scanner(System.in);
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
        port = 8081;
        try {
            client = new Client(address, port);
        } catch (ConnectionException e) {

        }
        return true;
    }

    public static Client getClient() {
        return client;
    }


    @Override
    public void init() {

        resourceFactory = new ObservableResourceFactory();
        resourceFactory.setResources(ResourceBundle.getBundle("bundles.GuiLabels",new Locale("en")));
        outputter = new OutputterUI(resourceFactory);

        try {
            client = new Client(address, port);
            client.setResourceFactory(resourceFactory);
            client.connectionTest();
            client.setOutputManager(outputter);
        } catch (ConnectionException e) {
            e.printStackTrace();
        }
    }


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
            stage.setTitle("LOGIN");
            stage.setScene(loginWindowScene);
            stage.setResizable(false);
            stage.show();

        } catch (Exception e) {
            getOutputter().error("Не получилось загрузить fxml файл");
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
            primaryStage.setScene(signUpWindowScene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setMainWindow() {
        try {
            FXMLLoader mainWindowLoader = new FXMLLoader();
            mainWindowLoader.setResources(ResourceBundle.getBundle("bundles.GuiLabels",new Locale("en")));
            mainWindowLoader.setLocation(getClass().getResource("../controllers/main.fxml"));
            Parent mainWindowRootNode = mainWindowLoader.load();
            Scene mainWindowScene = new Scene(mainWindowRootNode);
            MainWindowController mainWindowController = mainWindowLoader.getController();
            mainWindowController.initLangs(resourceFactory);
            //client.sendHello();

            mainWindowController.setClient(client);
            mainWindowController.setUsername(client.getUser()!=null?client.getUser().getLogin():"");
            mainWindowController.setPrimaryStage(primaryStage);
            mainWindowController.setApp(this);
                        Platform.runLater(()->{
                primaryStage.setScene(mainWindowScene);
                primaryStage.setResizable(true);
                primaryStage.setOnCloseRequest((e) ->{
                    print("Main window close!!!");
                    client.close();
                });
                primaryStage.show();
            });
        } catch (Exception e) {
            System.out.println(e);
            e.printStackTrace();
        }
    }
    public OutputterUI getOutputter() {
        return outputter;
    }
}
