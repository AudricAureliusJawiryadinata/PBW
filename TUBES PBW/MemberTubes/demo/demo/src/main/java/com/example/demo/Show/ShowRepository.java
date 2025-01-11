package com.example.demo.Show;

import java.time.LocalDate;
import java.util.List;

import com.example.demo.Setlist.Setlist;

public interface ShowRepository {
    void addShow(String namaShow, String lokasiShow, LocalDate tanggalShow);
    List<Show> findAllShows();
    List<Show> findByNameContainingIgnoreCase(String name);
    List<Show> findShowByArtisId(int artisId);
    Show findShowById(int showId);
}