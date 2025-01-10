package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcSetlistRepository implements SetListRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addSetlist(String namaLagu, String showTerkait, int showId) {
        String sql = "INSERT INTO setlist (nama_lagu, show_terkait, show_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, namaLagu, showTerkait, showId);
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

    private Setlist mapRowToSetlist(ResultSet resultSet, int rowNum) throws SQLException {
        return new Setlist(
                resultSet.getInt("id"),
                resultSet.getString("nama_lagu"),
                resultSet.getString("show_terkait"),
                resultSet.getInt("show_id")
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
    
}