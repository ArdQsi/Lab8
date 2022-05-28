package controllers;


import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import client.Client;
import collection.ProductObservableManager;
import connection.Request;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.App;
import tools.ObservableResourceFactory;

public class ControllerLogin {
    App app;
    Client client;
    private ObservableResourceFactory resourceFactory;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button loginButton;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signupButton;

    @FXML
    void initialize() {
        loginButton.setOnAction(event -> {
            /*String loginText = loginField.getText().trim();
            String passwordText = passwordField.getText().trim();
            if (!loginText.equals("") && !passwordText.equals("")) {
                client.processAuthentication(loginText, passwordText, false);
            } else {
                app.getOutputter().error("не введен логин или пароль");
            }
            if (client.isAuthSuccess()) {
                app.setMainWindow();
                try {
                    client.start();
                } catch (IllegalThreadStateException e) {
                    System.out.println("Беда");
                }
            } else {
                app.getOutputter().error("неверный логин");
            }*/
            doMainWindow();
        });

        signupButton.setOnAction(event -> {
            signupButton.getScene().getWindow().hide();
            app.setSignUp();
        });
    }

    public void setApp(App app) {
        this.app = app;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Void doMainWindow() {
        Service<Void> service = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {
                        String loginText = loginField.getText().trim();
                        String passwordText = passwordField.getText().trim();
                        if (!loginText.equals("") && !passwordText.equals("")) {
                            client.processAuthentication(loginText, passwordText, false);
                        } else {
                            app.getOutputter().error("не введен логин или пароль");
                        }
                        if (client.isAuthSuccess()) {
                            app.setMainWindow();
                            if (MainWindowController.flag){
                                client.sendHello();
                                MainWindowController.flag=false;
                            }

                            try {
                                client.start();
                            } catch (IllegalThreadStateException e) {

                            }
                        } else {
                            app.getOutputter().error("неверный логин");
                        }
                        return null;
                    }
                };
            }
        };
        service.start();
        return null;
    }

}
