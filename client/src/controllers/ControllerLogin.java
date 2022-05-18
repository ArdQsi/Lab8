package controllers;

import java.io.IOException;
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
        loginButton.setOnAction(event -> {
            String loginText = loginField.getText().trim();
            String passwordText = passwordField.getText().trim();

            if(!loginText.equals("") && !passwordText.equals("")) {
               App.getClient().processAuthentication(loginText,passwordText,false);
            }
            else {
                System.out.println("Не введен логин или пароль");
            }
            if (App.getClient().isAuthSuccess()) {
                System.out.println("good");
            }
            else {
                System.out.println("Неверный логин или пароль");
            }
        });

        signupButton.setOnAction(event -> {
            signupButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/controllers/signUp.fxml"));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent parent = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(parent));
            stage.showAndWait();
        });
    }

    public void openNewScene(String window) {

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
