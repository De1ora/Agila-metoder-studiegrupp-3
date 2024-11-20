module com.example.grupp3musicplayer {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.grupp3musicplayer to javafx.fxml;
    exports com.example.grupp3musicplayer;
}