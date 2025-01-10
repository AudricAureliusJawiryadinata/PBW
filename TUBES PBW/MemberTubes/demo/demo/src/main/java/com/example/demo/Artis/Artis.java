package com.example.demo.Artis;

public class Artis {
    private int id;
    private String namaArtis;
    private String genreMusik;

    public Artis(int id, String namaArtis, String genreMusik) {
        this.id = id;
        this.namaArtis = namaArtis;
        this.genreMusik = genreMusik;
    }

    // Getters dan Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaArtis() {
        return namaArtis;
    }

    public void setNamaArtis(String namaArtis) {
        this.namaArtis = namaArtis;
    }

    public String getGenreMusik() {
        return genreMusik;
    }

    public void setGenreMusik(String genreMusik) {
        this.genreMusik = genreMusik;
    }
}