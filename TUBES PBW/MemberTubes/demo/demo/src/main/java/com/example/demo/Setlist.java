package com.example.demo;

public class Setlist {
    private int id;
    private String namaLagu;
    private String showTerkait;
    private int showId;

    public Setlist(int id, String namaLagu, String showTerkait, int showId) {
        this.id = id;
        this.namaLagu = namaLagu;
        this.showTerkait = showTerkait;
        this.showId = showId;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaLagu() {
        return namaLagu;
    }

    public void setNamaLagu(String namaLagu) {
        this.namaLagu = namaLagu;
    }

    public String getShowTerkait() {
        return showTerkait;
    }

    public void setShowTerkait(String showTerkait) {
        this.showTerkait = showTerkait;
    }

    public int getShowId() {
        return showId;
    }

    public void setShowId(int showId) {
        this.showId = showId;
    }
}