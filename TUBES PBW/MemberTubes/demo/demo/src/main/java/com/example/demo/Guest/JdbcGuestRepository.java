// package com.example.demo.Guest;

// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.stereotype.Repository;

// import com.example.demo.Show;

// @Repository
// public class JdbcGuestRepository implements GuestRepository {

//     @Autowired
//     private JdbcTemplate jdbcTemplate;

//     @Override
//     public List<Show> findShowByName(String namaShow) {
//         String sql = "SELECT * FROM show WHERE nama_show = ?";
//         return jdbcTemplate.query(sql, this::mapRowToShow, namaShow);
//     }

//     @Override
//     public List<Artis> findArtisByName(String namaArtis) {

//     }

//     @Override
//     public boolean isValidShow(String namaShow) {

//     }

//     @Override
//     public boolean isValidArtis(String namaArtis) {

//     }

//     private Show mapRowToShow(ResultSet resultSet, int rowNum) throws SQLException {
//         return new Show(
//             resultSet.getInt("id"),
//             resultSet.getString("namaShow")
//         );
//     }

//     private Artis mapRowToArtis(ResultSet resultSet, int rowNum) throws SQLException {
//         return new Artis(
//             resultSet.getInt("id"),
//             resultSet.getString("namaArtis")
//         );
//     }
// }