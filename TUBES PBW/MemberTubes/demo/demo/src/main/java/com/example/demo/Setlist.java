package com.example.demo;

public class Setlist {
    private int id;
    private String namaLagu;
    private String showTerkait;
    private int showId;
    private String namaArtis;  // Added artist name field

    public Setlist(int id, String namaLagu, String showTerkait, int showId, String namaArtis) {
        this.id = id;
        this.namaLagu = namaLagu;
        this.showTerkait = showTerkait;
        this.showId = showId;
        this.namaArtis = namaArtis;  // Initialize artist name
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

    public String getNamaArtis() {
        return namaArtis;
    }

    public void setNamaArtis(String namaArtis) {
        this.namaArtis = namaArtis;
    }
}
