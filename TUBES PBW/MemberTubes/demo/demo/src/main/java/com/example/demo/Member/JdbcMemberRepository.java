package com.example.demo.Member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

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
    public List<Member> findByName(String name) {
        String sql = "SELECT * FROM member WHERE nama ILIKE ?";
        return jdbcTemplate.query(sql, this::mapRowToMember, "%" + name + "%");
    }

    @Override
    public void save(Member member) {
        String sql = "INSERT INTO member (nama, email, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, member.getNama(), member.getEmail(), member.getPassword());
    }

    public void register(String username, String email, String password) {
        String sql = "INSERT INTO member (nama, email, password) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, username, email, password);
    }

    @Override
    public void update(int id, Member updatedMember) {
        String sql = "UPDATE member SET nama = ?, email = ?, password = ? WHERE id = ?";
        jdbcTemplate.update(sql, updatedMember.getNama(), updatedMember.getEmail(), updatedMember.getPassword(), id);
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM member WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    private Member mapRowToMember(ResultSet resultSet, int rowNum) throws SQLException {
        return new Member(
            resultSet.getInt("id"),
            resultSet.getString("nama"),
            resultSet.getString("email"),
            resultSet.getString("password")
        );
    }
}