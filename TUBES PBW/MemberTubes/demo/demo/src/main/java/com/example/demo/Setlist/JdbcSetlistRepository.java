package com.example.demo.Setlist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.example.demo.Show.Show;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcSetlistRepository implements SetListRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void addSetlist(String namaLagu, String showTerkait, int showId, int artistId, String namaArtis) {
        String sql = "INSERT INTO setlist (nama_lagu, show_terkait, show_id, artist_id, nama_artis) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql, namaLagu, showTerkait, showId, artistId, namaArtis);  // Insert both artistId and namaArtis
    }

    @Override
    public List<Setlist> findSetlistsByShowId(int showId) {
        String sql = "SELECT * FROM setlist WHERE show_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToSetlist, showId);
    }
    @Override
    public List<Setlist> findAllSetList() {
        // Join with the artis table to get the artist's name (nama_artis)
        String sql = "SELECT setlist.id, setlist.nama_lagu, setlist.show_terkait, setlist.show_id, artis.nama_artis " +
                     "FROM setlist " +
                     "JOIN artis ON setlist.artist_id = artis.id";  // Join with artis to get nama_artis
    
        return jdbcTemplate.query(sql, this::mapRowToSetlist);
    }
    
    private Setlist mapRowToSetlist(ResultSet resultSet, int rowNum) throws SQLException {
        return new Setlist(
                resultSet.getInt("id"),
                resultSet.getString("nama_lagu"),
                resultSet.getString("show_terkait"),
                resultSet.getInt("show_id"),
                resultSet.getString("nama_artis")  // This fetches the artist's name correctly
        );
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
    private Show mapRowToShow(ResultSet resultSet, int rowNum) throws SQLException {
        return new Show(
                resultSet.getInt("id"),
                resultSet.getString("nama_show"),
                resultSet.getString("lokasi_show"), // Menambahkan lokasi_show
                resultSet.getDate("tanggal_show").toLocalDate() // Mengubah tanggal_show menjadi LocalDate
        );
    }

    @Override
    public Setlist findById(int id) {
        String sql = "SELECT * FROM setlist WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToSetlist, id);
        } catch (EmptyResultDataAccessException e) {
            return null; // Kembalikan null jika data tidak ditemukan
        }
    }
    @Override
    public void save(Setlist setlist) {
        String sql = "UPDATE setlist SET nama_lagu = ?, show_terkait = ?, nama_artis = ? WHERE id = ?";
        jdbcTemplate.update(sql, setlist.getNamaLagu(), setlist.getShowTerkait(), setlist.getNamaArtis(), setlist.getId());
    }

    @Override
    public List<Setlist> findByArtistName(String artistName) {
        String sql = """
            SELECT * 
            FROM setlist
            WHERE LOWER(nama_artis) = LOWER(?)
        """;

        return jdbcTemplate.query(sql, new Object[]{artistName}, setlistRowMapper());
    }

    @Override
    public List<Setlist> findAllSetlistForHistory() {
        String sql = "SELECT id, nama_lagu, show_terkait, nama_artis FROM setlist";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new Setlist(
                rs.getInt("id"),
                rs.getString("nama_lagu"),
                rs.getString("show_terkait"),
                0, // Jika Anda tidak menggunakan showId untuk fitur ini
                rs.getString("nama_artis")
        ));
    }

    private RowMapper<Setlist> setlistRowMapper() {
        return this::mapRowToSetlist;
    }
    
}