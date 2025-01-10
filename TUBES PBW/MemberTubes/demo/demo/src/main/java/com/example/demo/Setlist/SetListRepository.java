package com.example.demo.Setlist;

import java.util.List;

import com.example.demo.Show.*;
import com.example.demo.Artis.*;


public interface SetListRepository {
    void addSetlist(String namaLagu, String showTerkait, int showId, String artisTerkait, int artisId); // Tambah Setlist
    List<Setlist> findSetlistsByShowId(int showId); // Ambil Setlist berdasarkan ID Show
    List<Show> findAllShows(); // Ambil semua data Show
    String getShowNameById(int showId); // Ambil nama Show berdasarkan ID
    List<Setlist> findSetlistsByArtisId(int artisId); // Ambil Setlist berdasarkan ID Artis
    List<Artis> findAllArtis(); // Ambil semua data Artis
    String getArtisNameById(int artisId); // Ambil nama Artis berdasarkan ID
}