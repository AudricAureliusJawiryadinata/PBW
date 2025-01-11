package com.example.demo.Setlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.Artis.*;
import com.example.demo.Show.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcSetlistRepository implements SetListRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addSetlist(String namaLagu, String showTerkait, int showId, String artisTerkait, int artisId) {
        String sql = "INSERT INTO setlist (nama_lagu, show_terkait, show_id, artis_terkait, artis_id) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, namaLagu, showTerkait, showId, artisTerkait, artisId);
    }

    @Override
    public List<Setlist> findSetlistsByShowId(int showId) {
        String sql = "SELECT * FROM setlist WHERE show_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToSetlist, showId);
    }

    @Override
    public List<Show> findAllShows() {
        String sql = "SELECT * FROM show";
        return jdbcTemplate.query(sql, this::mapRowToShow);
    }

    @Override
    public String getShowNameById(int showId) {
        String sql = "SELECT nama_show FROM show WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, showId);
    }

    @Override
    public List<Setlist> findSetlistsByArtisId(int artisId) {
        String sql = "SELECT * FROM setlist WHERE artis_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToSetlist, artisId);
    }

    @Override
    public List<Artis> findAllArtis() {
        String sql = "SELECT * FROM artis";
        return jdbcTemplate.query(sql, this::mapRowToArtis);
    }

    @Override
    public String getArtisNameById(int artisId) {
        String sql = "SELECT nama_artis FROM artis WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, artisId);
    }

    @Override
    public List<Setlist> findByArtistName(String artistName) {
        String sql = """
            SELECT * 
            FROM setlist
            WHERE LOWER(artis_terkait) = LOWER(?)
        """;

        return jdbcTemplate.query(sql, new Object[]{artistName}, setlistRowMapper());
    }

    private RowMapper<Setlist> setlistRowMapper() {
        return this::mapRowToSetlist;
    }

    private Setlist mapRowToSetlist(ResultSet resultSet, int rowNum) throws SQLException {
        return new Setlist(
                resultSet.getInt("id"),
                resultSet.getString("nama_lagu"),
                resultSet.getString("show_terkait"),
                resultSet.getInt("show_id"),
                resultSet.getString("artis_terkait"),
                resultSet.getInt("artis_id")
        );
    }

    private Show mapRowToShow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Show(
                resultSet.getInt("id"),
                resultSet.getString("nama_show"),
                resultSet.getString("lokasi_show"), // Menambahkan lokasi_show
                resultSet.getDate("tanggal_show").toLocalDate() // Mengubah tanggal_show menjadi LocalDate
        );
    }
    
    private Artis mapRowToArtis(ResultSet resultSet, int rowNum) throws SQLException {
        return new Artis(
                resultSet.getInt("id"),
                resultSet.getString("nama_artis"),
                resultSet.getString("genre_musik")
        );
    }
}