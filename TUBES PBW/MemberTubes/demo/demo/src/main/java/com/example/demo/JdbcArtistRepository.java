package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcArtistRepository implements ArtistRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Artist> findAllArtists() {
        String sql = "SELECT * FROM artis";
        return jdbcTemplate.query(sql, this::mapRowToArtist);
    }

    private Artist mapRowToArtist(ResultSet resultSet, int rowNum) throws SQLException {
        return new Artist(
                resultSet.getInt("id"),
                resultSet.getString("nama_artis"),
                resultSet.getString("genre_musik")
        );
    }
}
