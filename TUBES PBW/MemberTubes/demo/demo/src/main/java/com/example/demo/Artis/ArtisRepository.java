package com.example.demo.Artis;

import java.util.List;

public interface ArtisRepository {
    List<Artis> findAllArtists();
    List<Artis> findByNameContainingIgnoreCase(String name);
    List<Artis> findArtistsByShow(String namaShow);
    String getArtisNameById(int artisId);
}