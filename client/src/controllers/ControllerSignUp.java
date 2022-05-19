package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import client.Client;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import main.App;
import tools.ObservableResourceFactory;

public class ControllerSignUp {
    App app;
    Client client;
    private ObservableResourceFactory resourceFactory;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button signUpInButton;

    @FXML
    void initialize() {
        signUpInButton.setOnAction(event -> {
            signUpInButton.getScene().getWindow().hide();
            try {
                String loginText = loginField.getText().trim();
                String passwordText = passwordField.getText().trim();
                if (!loginText.equals("") && !passwordText.equals("")) {
                    client.processAuthentication(loginText, passwordText, true);
                } else {
                    System.out.println("Введите логин и пароль");
                }
                if (client.isAuthSuccess()) {
                    app.setMainWindow();
                    client.start();
                }
            } catch (NullPointerException e) {
                System.out.println("этот пользователь уже зарегистрирован");
            }

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

