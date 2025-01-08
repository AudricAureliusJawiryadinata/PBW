package com.example.demo.Guest;

public class Song {
    private final String title;
    private final String notes;

    public Song(String title, String notes) {
        this.title = title;
        this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public String getNotes() {
        return notes;
    }
}
