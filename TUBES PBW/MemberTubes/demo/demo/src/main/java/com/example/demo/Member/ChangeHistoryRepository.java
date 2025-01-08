package com.example.demo.Member;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ChangeHistoryRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addChangeHistory(int userId, String actionType, String entityType, Integer entityId, String description) {
        String sql = "INSERT INTO change_history (user_id, action_type, entity_type, entity_id, description, created_at) VALUES (?, ?, ?, ?, ?, NOW())";
        jdbcTemplate.update(sql, userId, actionType, entityType, entityId, description);
    }
    public List<ChangeHistory> getHistoryForUser(int userId) {
    String sql = "SELECT * FROM change_history WHERE user_id = ? ORDER BY created_at DESC";  // Query to fetch change history
    return jdbcTemplate.query(sql, this::mapRowToChangeHistory, userId);
    }

    private ChangeHistory mapRowToChangeHistory(ResultSet rs, int rowNum) throws SQLException {
        return new ChangeHistory(
            rs.getInt("id"),
            rs.getInt("user_id"),
            rs.getString("action_type"),
            rs.getString("object_type"),
            rs.getInt("object_id"),
            rs.getString("description"),
            rs.getTimestamp("created_at")
        );
    }

}
