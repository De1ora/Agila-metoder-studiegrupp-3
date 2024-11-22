module com.example.grupp3musicplayer {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.media;


    opens com.example.grupp3musicplayer to javafx.fxml;
    exports com.example.grupp3musicplayer;
}