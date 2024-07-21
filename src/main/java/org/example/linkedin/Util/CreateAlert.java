package org.example.linkedin.Util;

import javafx.scene.control.Alert;

public class CreateAlert {
    public static void createAlert(String message, Alert.AlertType t) {
        Alert alert =new Alert(t);
        alert.setTitle("Alert");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
