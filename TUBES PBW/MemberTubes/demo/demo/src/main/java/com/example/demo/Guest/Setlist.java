package com.example.demo.Guest;

import java.util.List;

public class Setlist {
    private final String venue;
    private final String date;
    private final List<Song> songs;

    public Setlist(String venue, String date, List<Song> songs) {
        this.venue = venue;
        this.date = date;
        this.songs = songs;
    }

    public String getVenue() {
        return venue;
    }

    public String getDate() {
        return date;
    }

    public List<Song> getSongs() {
        return songs;
    }
}