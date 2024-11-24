package com.example.grupp3musicplayer;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;

import java.io.File;

public class MainController {

    @FXML
    private Label chooseSong;

    private MediaPlayer mediaPlayer;

        @FXML
        void chooseSong(MouseEvent event) {
            FileChooser chooser = new FileChooser();
            chooser.setTitle("Select your music");
            File file = chooser.showOpenDialog(null);
            if(file != null){
                String selectedFile = file.toURI().toString();
                Media media = new Media(selectedFile);
                mediaPlayer = new MediaPlayer(media);
                mediaPlayer.setOnReady(() -> chooseSong.setText(file.getName()));
            }
        }
        @FXML
        void pause(MouseEvent event) {
            mediaPlayer.pause();
        }

        @FXML
        void play(MouseEvent event) {
            mediaPlayer.play();
        }

        @FXML
        void skip(MouseEvent event) {
            mediaPlayer.stop();
        }
}