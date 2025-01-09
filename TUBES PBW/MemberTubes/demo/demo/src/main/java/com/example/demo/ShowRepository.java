package com.example.demo;

import java.util.List;

public interface ShowRepository {
    void addShow(String namaShow); // Tambah Show
    List<Show> findAllShows(); // Ambil semua Show
    Show findShowById(int showId); // Ambil Show berdasarkan ID
}