package com.example.demo;

import java.util.List;


public interface SetListRepository {
    void addSetlist(String namaLagu, String showTerkait, int showId, int artistId, String namaArtis);
    List<Setlist> findSetlistsByShowId(int showId); // Ambil Setlist berdasarkan ID Show
    List<Show> findAllShows(); // Ambil semua data Show
    String getShowNameById(int showId); // Ambil nama Show berdasarkan ID
    List<Setlist> findAllSetList();
    Setlist findById(int id); // Deklarasi untuk findById
    void save(Setlist setlist); // Deklarasi untuk save
    List<Setlist> findAllSetlistForHistory();

}
