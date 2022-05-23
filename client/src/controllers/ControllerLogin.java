package controllers;


import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

import client.Client;
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
        //client.interrupt();
        loginButton.setOnAction(event -> {
            //loginButton.getScene().getWindow().hide();
            String loginText = loginField.getText().trim();
            String passwordText = passwordField.getText().trim();
            if (!loginText.equals("") && !passwordText.equals("")) {
                client.processAuthentication(loginText, passwordText, false);
            } else {
                System.out.println("Не введен логин или пароль");
            }
            if (client.isAuthSuccess()) {
                app.setMainWindow();
                try {
                    client.start();
                } catch (IllegalThreadStateException e) {

                }
            } else {
                System.out.println("Неверный логин или пароль");
            }
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

    public void initLangs(ObservableResourceFactory r) {
        resourceFactory = r;
    }
}
