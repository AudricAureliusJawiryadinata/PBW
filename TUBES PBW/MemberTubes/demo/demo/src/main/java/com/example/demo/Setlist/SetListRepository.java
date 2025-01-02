package com.example.demo.Setlist;

import java.util.List;

public interface SetListRepository {
    void addSetlist(String namaLagu, String showTerkait, int showId); // Tambah Setlist
    List<Setlist> findSetlistsByShowId(int showId); // Ambil Setlist berdasarkan ID Show
}
