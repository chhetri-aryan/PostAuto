package org.example.linkedin.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.example.linkedin.HelloApplication;
import org.example.linkedin.Model.Session;
import org.example.linkedin.Model.User;
import org.example.linkedin.ODM.MongoODM;
import org.example.linkedin.Util.UI;

import static org.example.linkedin.Util.CreateAlert.createAlert;
import static org.example.linkedin.Util.Crypt.Decrypt;

public class LoginController {

    public TextField email;
    public PasswordField password;

    public String getEmail () {
        return email.getText();
    }
    public String getPassword() {
        return password.getText();
    }

    public void toRegister(MouseEvent event) {
        UI.Switch(HelloApplication.class, (Stage) email.getScene().getWindow(), "register" );

    }

    public void login(ActionEvent actionEvent) {
        MongoODM odm = new MongoODM();
        if (!odm.isUser(getEmail())) {
            createAlert("Not a Existing user, Please Register first", Alert.AlertType.INFORMATION);
        } else {
            User u = odm.getUser(getEmail());
            if (Decrypt(getPassword() , u.getPassword())) {
                createAlert("Login Successful", Alert.AlertType.INFORMATION);
                Session.startSession(u); // creating a session

                UI.Switch(HelloApplication.class, (Stage) email.getScene().getWindow(), "home" );

            } else {
                createAlert("Invalid Credentials", Alert.AlertType.INFORMATION);
            }
        }
    }
}