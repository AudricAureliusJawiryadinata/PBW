package com.example.demo;

public class Show {
    private int id;
    private String namaShow;

    public Show(int id, String namaShow) {
        this.id = id;
        this.namaShow = namaShow;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamaShow() {
        return namaShow;
    }

    public void setNamaShow(String namaShow) {
        this.namaShow = namaShow;
    }
}