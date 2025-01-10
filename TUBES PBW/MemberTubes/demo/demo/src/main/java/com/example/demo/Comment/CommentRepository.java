package com.example.demo.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class CommentRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Method to add a new comment
    public void addComment(String commentText, int showId) {
        String sql = "INSERT INTO comments (show_id, comment_text) VALUES (?, ?)";
        jdbcTemplate.update(sql, showId, commentText);
    }

    // Method to get all comments for a particular show
    public List<Comment> getCommentsForShow(int showId) {
        String sql = "SELECT * FROM comments WHERE show_id = ?";
        return jdbcTemplate.query(sql, this::mapRowToComment, showId);
    }

    // Helper method to map database rows to Comment objects
    private Comment mapRowToComment(ResultSet rs, int rowNum) throws SQLException {
        return new Comment(
                rs.getInt("id"),
                rs.getInt("show_id"),
                rs.getString("comment_text"),
                rs.getTimestamp("created_at")
        );
    }
}