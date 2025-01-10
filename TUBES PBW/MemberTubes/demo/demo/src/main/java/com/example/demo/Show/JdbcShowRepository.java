package com.example.demo.Show;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Repository
public class JdbcShowRepository implements ShowRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addShow(String namaShow, String lokasiShow, LocalDate tanggalShow) {
        String sql = "INSERT INTO show (nama_show, lokasi_show, tanggal_show) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, namaShow, lokasiShow, tanggalShow);
    }

    @Override
    public List<Show> findAllShows() {
        String sql = "SELECT * FROM show";
        return jdbcTemplate.query(sql, this::mapRowToShow);
    }

    @Override
    public Show findShowById(int showId) {
        String sql = "SELECT * FROM show WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToShow, showId);
    }

    @Override
    public List<Show> findByNameContainingIgnoreCase(String name) {
        String sql = "SELECT * FROM show WHERE LOWER(nama_show) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, new Object[]{"%" + name + "%"}, showRowMapper());
    }

    private RowMapper<Show> showRowMapper() {
        return (rs, rowNum) -> new Show(
                rs.getInt("id"),
                rs.getString("nama_show"),
                rs.getString("lokasi_show"),
                rs.getDate("tanggal_show").toLocalDate()
        );
    }

    private Show mapRowToShow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Show(
                resultSet.getInt("id"),
                resultSet.getString("nama_show"),
                resultSet.getString("lokasi_show"),
                resultSet.getDate("tanggal_show").toLocalDate()
        );
    }
}