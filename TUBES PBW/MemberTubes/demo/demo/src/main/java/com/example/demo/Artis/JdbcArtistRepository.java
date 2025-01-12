package com.example.demo.Artis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcArtistRepository implements ArtisRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Artis> findAllArtists() {
        String sql = "SELECT * FROM artis";
        return jdbcTemplate.query(sql, this::mapRowToArtist);
    }
    @Override
    public String getArtisNameById(int artisId) {
        String sql = "SELECT nama_artis FROM artis WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, String.class, artisId);
    }

    @Override
    public List<Artis> findByNameContainingIgnoreCase(String name) {
        String sql = "SELECT * FROM artis WHERE LOWER(nama_artis) LIKE LOWER(?)";
        return jdbcTemplate.query(sql, new Object[]{"%" + name + "%"}, artisRowMapper());
    }

    @Override
    public List<Artis> findArtistsByShow(String namaShow) {
        String sql = """
            SELECT DISTINCT artisDupe.id, artisDupe.nama_artis, artisDupe.genre_musik
            FROM artis artisDupe
            JOIN setlist setlistDupe ON artisDupe.id = setlistDupe.artist_id
            JOIN show showDupe ON setlistDupe.show_id = showDupe.id
            WHERE LOWER(showDupe.nama_show) LIKE LOWER(CONCAT('%', ?, '%'))
        """;

        return jdbcTemplate.query(sql, new Object[]{namaShow}, (rs, rowNum) -> new Artis(
            rs.getInt("id"),
            rs.getString("nama_artis"),
            rs.getString("genre_musik")
        ));
    }
    private Artis mapRowToArtist(ResultSet resultSet, int rowNum) throws SQLException {
        return new Artis(
                resultSet.getInt("id"),
                resultSet.getString("nama_artis"),
                resultSet.getString("genre_musik")
        );
    }

    private RowMapper<Artis> artisRowMapper() {
        return (rs, rowNum) -> new Artis(
                rs.getInt("id"),
                rs.getString("nama_artis"),
                rs.getString("genre_musik")
        );
    }
}
