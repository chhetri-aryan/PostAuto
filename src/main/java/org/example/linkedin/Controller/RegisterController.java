package org.example.linkedin.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.linkedin.HelloApplication;
import org.example.linkedin.Model.User;
import org.example.linkedin.ODM.MongoODM;
import org.example.linkedin.Util.Crypt;
import org.example.linkedin.Util.UI;


import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.regex.Pattern;

import static org.example.linkedin.Util.CreateAlert.createAlert;


public class RegisterController {


    public TextField username;
    public TextField email;
    public TextField phone;
    public PasswordField password;
    public TextArea url;

    public String getUsername () {
        return username.getText();
    }
    public String getPhone() {
        return phone.getText();
    }
    public String getEmail () {
        return email.getText();
    }
    public String getUrl () {
        return url.getText();
    }
    public String getPassword() {
        return password.getText();
    }

    public void register(ActionEvent actionEvent) throws NoSuchAlgorithmException, InvalidKeySpecException {
        MongoODM odm = new MongoODM();
        String hashedPassword = Crypt.Encrypt(getPassword());

        if (odm.isUser(getEmail())) {
            createAlert("Email is Already Registered", Alert.AlertType.INFORMATION);
            return;
        } else if (getUsername().length() <= 3) {
            createAlert("Username should be least 3 characters", Alert.AlertType.WARNING);
            return;
        } else if (!validateEmail(getEmail())) {
            createAlert("Email is Invalid", Alert.AlertType.WARNING);
            return;
        } else if (!validatePhoneNumber(getPhone())) {
            createAlert("Phone number is Invalid", Alert.AlertType.WARNING);
            return;
        } else if(!validatePassword(getPassword())) {
            createAlert("Password is Invalid", Alert.AlertType.WARNING);
            return;
        }

        boolean ack = odm.insertIntoDbUser(new User(getUsername(),
                getEmail(),
                getPhone(),
                getUrl(),
                hashedPassword));

        if (ack) {
            createAlert("You are Registered Successfully, Please Login", Alert.AlertType.INFORMATION);
            UI.Switch(HelloApplication.class, (Stage) username.getScene().getWindow(), "login" );
        }
    }

    public void toLogin(MouseEvent mouseEvent) {
        UI.Switch(HelloApplication.class, (Stage) username.getScene().getWindow(), "login" );
    }

    public static boolean validatePassword(String password) {
        return password.length() >= 8 &&
                Pattern.compile("[A-Z]").matcher(password).find() &&
                Pattern.compile("[a-z]").matcher(password).find() &&
                Pattern.compile("[0-9]").matcher(password).find() &&
                Pattern.compile("[!@#$%^&*(),.?\":{}|<>]").matcher(password).find();
    }

    public static boolean validateEmail(String email) {
        return Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$").matcher(email).matches();
    }

    public static boolean validatePhoneNumber(String phoneNumber) {
        return Pattern.compile("^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$").matcher(phoneNumber).matches();
    }

}
