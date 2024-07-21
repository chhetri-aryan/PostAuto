package org.example.linkedin.Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.example.linkedin.HelloApplication;
import org.example.linkedin.Model.Session;
import org.example.linkedin.Util.JsonExtractor;
import org.example.linkedin.Util.LinkedInAPI;
import org.example.linkedin.Util.OpenAi;
import org.example.linkedin.Util.UI;

import static org.example.linkedin.Util.CreateAlert.createAlert;

public class HomeController {
    public Text name;
    public TextField title;
    public TextArea content;
    public TextField url;
    public ImageView profileImageView;
    private ContextMenu contextMenu;


    public String getTitle () {
        return title.getText();
    }

    public void setTitle(String s) {
        title.setText(s);
    }


    public String getContent() {
        return content.getText();
    }

    public void setContent(String s) {
        content.setText(s);
    }

    public String getURl() {
        return url.getText();
    }


    public void initialize() {
        name.setText(Session.getCurrentUser().getUsername().split(" ")[0]);
        url.setText(Session.getCurrentUser().getUrl());

        contextMenu = new ContextMenu();

//        MenuItem changeIconMenuItem = new MenuItem("Change Icon");
//        changeIconMenuItem.setOnAction(event -> changeIcon());
//
//        MenuItem removeIconMenuItem = new MenuItem("Remove Icon");
//        removeIconMenuItem.setOnAction(event -> removeIcon());

        MenuItem logoutMenuItem = new MenuItem("Logout");
        logoutMenuItem.setOnAction(event -> logout());

        contextMenu.getItems().addAll(logoutMenuItem);
    }

    private void logout() {
        UI.Switch(HelloApplication.class, (Stage) title.getScene().getWindow(), "login" );
        Session.endSession();
    }

      public void generateContent(ActionEvent actionEvent) {
        if (getTitle().isEmpty()) {
            createAlert("Please make sure the Title is not Empty", Alert.AlertType.INFORMATION);
            return;
        }

        String generateTitle = OpenAi.generateTitle(getTitle());
        String generatedContent = OpenAi.generateContent(getTitle());
        String t = JsonExtractor.extractTitle(generateTitle);
        String c = JsonExtractor.extractSubject(generatedContent);

        System.out.println(t);
        System.out.println(c);

        setTitle(t);
        setContent(c);


    }

    public void postBtn(ActionEvent actionEvent) {
        if (getTitle().isEmpty() || getTitle().isEmpty() || getURl().isEmpty()) {
            createAlert("Do not leave anything empty", Alert.AlertType.INFORMATION);
            return;
        }
        boolean posted = LinkedInAPI.createTextPost(getTitle(), getContent(), getURl()) ;
        if (posted) {
            createAlert("Posted Successfully ✔️", Alert.AlertType.INFORMATION);
            setTitle("");
            setContent("");
        } else {
            createAlert("Error while Posting, Please try again later", Alert.AlertType.INFORMATION);

        }
    }

    public void showContextMenu(MouseEvent event) {
        contextMenu.show(profileImageView, event.getScreenX(), event.getScreenY());
    }

}
