package com.example.demo.Admin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcAdminRepository implements AdminRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<Admin> findAll() {
        String sql = "SELECT * FROM admin";
        return jdbcTemplate.query(sql, this::mapRowToAdmin);
    }

    @Override
    public Admin findById(int id) {
        String sql = "SELECT * FROM admin WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToAdmin, id);
    }

    @Override
    public Admin findByUsername(String username) {
        String sql = "SELECT * FROM admin WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToAdmin, username);
        } catch (Exception e) {
            return null; // Jika tidak ditemukan, kembalikan null
        }
    }

    @Override
    public void save(Admin admin) {
        String sql = "INSERT INTO admin (username, password) VALUES (?, ?)";
        jdbcTemplate.update(sql, admin.getUsername(), admin.getPassword());
    }

    @Override
    public void update(int id, Admin updatedAdmin) {
        String sql = "UPDATE admin SET username = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, updatedAdmin.getUsername(), updatedAdmin.getPassword(), id);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM admin WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public boolean isValidAdmin(String username, String password) {
        String sql = "SELECT COUNT(*) FROM admin WHERE username = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        return count != null && count > 0;
    }
    
    public void addArtist(String namaArtis, String genreMusik) {
        String sql = "INSERT INTO artis (nama_artis, genre_musik) VALUES (?, ?)";
        jdbcTemplate.update(sql, namaArtis, genreMusik);
    }

    private Admin mapRowToAdmin(ResultSet resultSet, int rowNum) throws SQLException {
        return new Admin(
            resultSet.getInt("id"),
            resultSet.getString("username"),
            resultSet.getString("password")
        );
    }
}