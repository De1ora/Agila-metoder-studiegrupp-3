package com.example.grupp3musicplayer.Classes;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/com/example/grupp3musicplayer/ViewCollections/MainController.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("Music Player");
        /* String path = getClass().getResource("/com/musik/Song.mp3").toExternalForm();
        Media media = new Media(path);
        MediaPlayer mediaplayer = new MediaPlayer(media);
        mediaplayer.setAutoPlay(true); */
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}