package com.example.demo.Setlist;

import java.util.List;

import com.example.demo.Show.Show;


public interface SetListRepository {
    void addSetlist(String namaLagu, String showTerkait, int showId, int artistId, String namaArtis);
    List<Setlist> findSetlistsByShowId(int showId); // Ambil Setlist berdasarkan ID Show
    List<Show> findAllShows(); // Ambil semua data Show
    String getShowNameById(int showId); // Ambil nama Show berdasarkan ID
    List<Setlist> findAllSetList();
    Setlist findById(int id);
    void save(Setlist setlist);
    List<Setlist> findAllSetlistForHistory();
    List<Setlist> findByArtistName(String artistName);
}
