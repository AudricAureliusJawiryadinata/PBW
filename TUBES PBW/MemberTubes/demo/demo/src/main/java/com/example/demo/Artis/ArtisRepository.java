package com.example.demo.Artis;

import java.util.List;

public interface ArtisRepository {
    List<Artis> findAllArtists();
    List<Artis> findByNameContainingIgnoreCase(String name);
    String getArtisNameById(int artisId);
}