package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.App;

public class ControllerSignUp {

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
            String loginText = loginField.getText().trim();
            String passwordText = passwordField.getText().trim();

            if(!loginText.equals("") && !passwordText.equals("")) {
                App.getClient().processAuthentication(loginText,passwordText,true);
            }
            else {
                System.out.println("Введите логин и пароль");
            }
            if (App.getClient().isAuthSuccess()) {
                System.out.println("register good");
            }
        });
    }

}

