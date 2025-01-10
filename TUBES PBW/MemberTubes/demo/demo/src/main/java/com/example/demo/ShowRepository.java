package com.example.demo;

import java.time.LocalDate;
import java.util.List;

public interface ShowRepository {
    void addShow(String namaShow, String lokasiShow, LocalDate tanggalShow);
    List<Show> findAllShows();
    Show findShowById(int showId);
}
