package com.example.grupp3musicplayer.Models;

import java.util.ArrayList;

public class Playlist {
    private String name;
    private ArrayList<Track> tracks;

    public Playlist(String name) {
        this.name = name;
        this.tracks = new ArrayList<>();
    }
    public void addTrack(Track track) {
        tracks.add(track);
    }
    public void removeTrack(Track track) {
        tracks.remove(track);
    }
    public ArrayList<Track> getTracks() {
        return tracks;
    }
}
