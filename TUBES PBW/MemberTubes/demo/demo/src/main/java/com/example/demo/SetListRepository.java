package com.example.demo;

import java.util.List;


public interface SetListRepository {
    void addSetlist(String namaLagu, String showTerkait, int showId); // Tambah Setlist
    List<Setlist> findSetlistsByShowId(int showId); // Ambil Setlist berdasarkan ID Show
    List<Show> findAllShows(); // Ambil semua data Show
    String getShowNameById(int showId); // Ambil nama Show berdasarkan ID
}
