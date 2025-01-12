package com.example.demo.Show;

import java.time.LocalDate;

public class Show {
    private int id;
    private String namaShow;
    private String lokasiShow;
    private LocalDate tanggalShow;

    public Show(int id, String namaShow, String lokasiShow, LocalDate tanggalShow) {
        this.id = id;
        this.namaShow = namaShow;
        this.lokasiShow = lokasiShow;
        this.tanggalShow = tanggalShow;
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

    public String getLokasiShow() {
        return lokasiShow;
    }

    public void setLokasiShow(String lokasiShow) {
        this.lokasiShow = lokasiShow;
    }

    public LocalDate getTanggalShow() {
        return tanggalShow;
    }

    public void setTanggalShow(LocalDate tanggalShow) {
        this.tanggalShow = tanggalShow;
    }
}
