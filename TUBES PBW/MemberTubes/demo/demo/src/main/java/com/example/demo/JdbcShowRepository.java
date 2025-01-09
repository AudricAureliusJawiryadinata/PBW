package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcShowRepository implements ShowRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addShow(String namaShow) {
        String sql = "INSERT INTO show (nama_show) VALUES (?)";
        jdbcTemplate.update(sql, namaShow);
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

    private Show mapRowToShow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Show(
                resultSet.getInt("id"),
                resultSet.getString("nama_show")
        );
    }
}