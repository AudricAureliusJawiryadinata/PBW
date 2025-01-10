package com.example.demo.Guest;

import java.util.List;

import com.example.demo.Show.*;
import com.example.demo.Artis.*;

public interface GuestRepository {
    List<Show> findShowByName(String namaShow);
    List<Artis> findArtisByName(String namaArtis);
    // Show findById()
    boolean isValidShow(String namaShow);
    boolean isValidArtis(String namaArtis);
}
