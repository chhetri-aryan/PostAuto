module org.example.linkedin {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.mongodb.driver.sync.client;
    requires org.mongodb.bson;
    requires bcrypt;
    requires org.mongodb.driver.core;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires org.json;

    opens org.example.linkedin to javafx.fxml;
    exports org.example.linkedin;
    exports org.example.linkedin.Controller;
    opens org.example.linkedin.Controller to javafx.fxml;
}