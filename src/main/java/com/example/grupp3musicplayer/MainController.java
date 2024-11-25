package com.example.grupp3musicplayer;

import com.example.grupp3musicplayer.Controllers.PlaylistController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class MainController {
    private Scene scene;
    private Parent root;

    private Stage stage;

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
        @FXML
        void playlistScene(MouseEvent event) {
            try {
                root = FXMLLoader.load(getClass().getResource("/com/example/grupp3musicplayer/ViewCollections/PlaylistMenu.fxml"));
                stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
}