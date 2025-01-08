package com.example.demo.Guest;

public class Album {
    private String name;
    private int songCount;

    public Album(String name, int songCount) {
        this.name = name;
        this.songCount = songCount;
    }

    public String getName() {
        return name;
    }

    public int getSongCount() {
        return songCount;
    }
}
