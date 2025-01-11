package com.example.demo.Setlist;

public class Setlist {
    private int id;
    private String namaLagu;
    private String showTerkait;
    private int showId;
    private String artisTerkait;
    private int artisId;

    public Setlist(int id, String namaLagu, String showTerkait, int showId, String artisTerkait, int artisId) {
        this.id = id;
        this.namaLagu = namaLagu;
        this.showTerkait = showTerkait;
        this.showId = showId;
        this.artisTerkait = artisTerkait;
        this.artisId = artisId;
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

    public String getArtisTerkait() {
        return artisTerkait;
    }

    public void setArtisTerkait(String artisTerkait) {
        this.artisTerkait = artisTerkait;
    }

    public int getArtisId() {
        return artisId;
    }

    public void setArtisId(int artisId) {
        this.artisId = artisId;
    }
}