module com.example.grupp3musicplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;
    requires com.google.gson;


    exports com.example.grupp3musicplayer.Controllers;
    opens com.example.grupp3musicplayer.Controllers to javafx.fxml;
    opens com.example.grupp3musicplayer.Models to com.google.gson;


    exports com.example.grupp3musicplayer;
    opens com.example.grupp3musicplayer to javafx.fxml;
    exports com.example.grupp3musicplayer.Classes;
    opens com.example.grupp3musicplayer.Classes to javafx.fxml;
}