package org.example.linkedin.Util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UI {
    public static void Switch(Class<?> cls, Stage stage, String view) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(cls.getResource(view + ".fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 800, 500);
            stage.setScene(scene);
            stage.setTitle(view);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
