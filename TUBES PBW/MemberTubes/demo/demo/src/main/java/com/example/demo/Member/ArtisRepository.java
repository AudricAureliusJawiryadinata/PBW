package com.example.demo.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ArtisRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addArtist(String namaArtis, String genreMusik) {
        String sql = "INSERT INTO artis (nama_artis, genre_musik) VALUES (?, ?)";
        jdbcTemplate.update(sql, namaArtis, genreMusik);
    }
}
