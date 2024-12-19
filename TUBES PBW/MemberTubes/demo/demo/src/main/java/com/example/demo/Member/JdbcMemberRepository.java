package com.example.demo.Member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
public class JdbcMemberRepository implements MemberRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;



    @Override
    public List<Member> findAll() {
        String sql = "SELECT * FROM member";
        return jdbcTemplate.query(sql, this::mapRowToMember);
    }

    @Override
    public Member findById(int id) {
        String sql = "SELECT * FROM member WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, this::mapRowToMember, id);
    }

    @Override
    public List<Member> findByName(String username) {
        String sql = "SELECT * FROM member WHERE username ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToMember, "%" + username + "%");
    }

    @Override
    public void save(Member member) {
        String sql = "INSERT INTO member (username, email, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, member.getUsername(), member.getEmail(), member.getPassword());
    }

    public void register(String username, String email, String password) {
        String sql = "INSERT INTO member (username, email, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, username, email, password);
    }

    @Override
    public void update(int id, Member updatedMember) {
        String sql = "UPDATE member SET username = ?, email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, updatedMember.getUsername(), updatedMember.getEmail(), updatedMember.getPassword(), id);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM member WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    // Cari berdasarkan username
    public Member findByUsername(String username) {
        String sql = "SELECT * FROM member WHERE username = ?";
        try {
            return jdbcTemplate.queryForObject(sql, this::mapRowToMember, username);
        } catch (Exception e) {
            return null; // Jika tidak ditemukan, kembalikan null
        }
    }

    // Validasi username dan password
    public boolean isValidUser(String username, String password) {
        String sql = "SELECT COUNT(*) FROM member WHERE username = ? AND password = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, username, password);
        return count != null && count > 0;
    }

    private Member mapRowToMember(ResultSet resultSet, int rowNum) throws SQLException {
        return new Member(
            resultSet.getInt("id"),
            resultSet.getString("username"),
            resultSet.getString("email"),
            resultSet.getString("password")
        );
    }
    public void addArtist(String namaArtis, String genreMusik) {
        String sql = "INSERT INTO artis (nama_artis, genre_musik) VALUES (?, ?)";
        jdbcTemplate.update(sql, namaArtis, genreMusik);
    }    
}
