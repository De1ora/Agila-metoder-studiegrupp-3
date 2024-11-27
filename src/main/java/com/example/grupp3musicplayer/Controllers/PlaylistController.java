package com.example.grupp3musicplayer.Controllers;

import com.example.grupp3musicplayer.Models.Playlist;
import com.example.grupp3musicplayer.Models.Track;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PlaylistController {
    @FXML
    private ListView<String> playlistView;
    @FXML
    private ListView<String> songListView;
    @FXML
    private TextField playlistNameField;

    private MediaPlayer mediaPlayer;
    private final ObservableList<String> playlists = FXCollections.observableArrayList();
    private final ObservableList<String> songList = FXCollections.observableArrayList();
    private final ArrayList<Playlist> playlistObjects = new ArrayList<>();
    private final File playlistFile = new File("playlists.json");
    @FXML
    private Slider volumeSlider;

    @FXML
    public void initialize() {
        volumeSlider.setValue(0.5);
        volumeSlider.setMin(0);
        volumeSlider.setMax(1);

        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (mediaPlayer != null) {
                mediaPlayer.setVolume(newValue.doubleValue());
            }
        });

        playlistView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            updateSongListView();
        });
        loadPlaylistsFromFile();
    }

    @FXML
    void createPlaylist(MouseEvent event) {
        String playlistName = playlistNameField.getText().trim();
        if (!playlistName.isEmpty()) {
            playlists.add(playlistName);
            Playlist newPlaylist = new Playlist(playlistName);
            playlistObjects.add(newPlaylist);

            playlistView.setItems(playlists);
            playlistNameField.clear();
            playlistView.getSelectionModel().select(newPlaylist.getName());
            updateSongListView();

            savePlaylistsToFile();
        }
    }

    @FXML
    void loadSongs(MouseEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Songs");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Audio Files", "*.mp3", "*.wav"));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(null);

        if (selectedFiles != null) {
            Playlist selectedPlaylist = getSelectedPlaylist();
            if (selectedPlaylist != null) {
                for (File file : selectedFiles) {
                    String filePath = file.toURI().toString();
                    songList.add(file.getName());
                    selectedPlaylist.addTrack(new Track(file.getName(), "-", "-", filePath));
                }
                songListView.setItems(songList);
                savePlaylistsToFile();
            }
        }
    }

    @FXML
    void playSelectedSong(MouseEvent event) {
        String selectedSong = songListView.getSelectionModel().getSelectedItem();
        if (selectedSong != null) {
            for (Playlist playlist : playlistObjects) {
                for (Track track : playlist.getTracks()) {
                    if (track.getTitle().equals(selectedSong)) {
                        if (mediaPlayer != null && mediaPlayer.getMedia().getSource().equals(track.getFilePath())) {
                            if (mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
                                mediaPlayer.pause();
                            } else {
                                mediaPlayer.play();
                            }
                            return;
                        }
                        if (mediaPlayer != null) {
                            mediaPlayer.stop();
                        }
                        Media media = new Media(track.getFilePath());
                        mediaPlayer = new MediaPlayer(media);
                        mediaPlayer.play();
                    }
                }
            }
        }
    }

    @FXML
    void goBack(MouseEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/grupp3musicplayer/ViewCollections/MainController.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Playlist getSelectedPlaylist() {
        String selectedPlaylistName = playlistView.getSelectionModel().getSelectedItem();
        if (selectedPlaylistName != null) {
            for (Playlist playlist : playlistObjects) {
                if (playlist.getName().equals(selectedPlaylistName)) {
                    return playlist;
                }
            }
        }
        return null;
    }

    private void updateSongListView() {
        Playlist selectedPlaylist = getSelectedPlaylist();
        songList.clear();
        if (selectedPlaylist != null) {
            for (Track track : selectedPlaylist.getTracks()) {
                songList.add(track.getTitle());
            }
        }
        songListView.setItems(songList);
    }

    private void savePlaylistsToFile() {
        try (FileWriter writer = new FileWriter(playlistFile)) {
            Gson gson = new Gson();
            writer.write(gson.toJson(playlistObjects));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadPlaylistsFromFile() {
        if (playlistFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(playlistFile))) {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<Playlist>>() {}.getType();
                List<Playlist> loadedPlaylists = gson.fromJson(reader, listType);
                playlistObjects.clear();
                playlistObjects.addAll(loadedPlaylists);

                playlists.setAll(loadedPlaylists.stream().map(Playlist::getName).collect(Collectors.toList()));
                playlistView.setItems(playlists);

                if (!playlists.isEmpty()) {
                    playlistView.getSelectionModel().selectFirst();
                    updateSongListView();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
