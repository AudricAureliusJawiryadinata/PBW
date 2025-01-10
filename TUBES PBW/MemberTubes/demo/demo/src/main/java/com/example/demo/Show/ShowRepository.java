package com.example.demo.Show;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository {
    void addShow(String namaShow, String lokasiShow, LocalDate tanggalShow);
    List<Show> findAllShows();
    List<Show> findByNameContainingIgnoreCase(String name);
    Show findShowById(int showId);
}