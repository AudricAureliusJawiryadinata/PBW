package com.example.demo.Show;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository {
    void addShow(String namaShow, String lokasiShow, LocalDate tanggalShow);
    List<Show> findAllShows();
    Show findShowById(int showId);
    List<Show> findByNameContainingIgnoreCase(String name);
    List<Show> findShowByArtisId(int artisId);
}