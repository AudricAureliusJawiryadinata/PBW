// package com.example.demo.Comment;

// import java.sql.ResultSet;
// import java.sql.SQLException;
// import java.util.List;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.jdbc.core.JdbcTemplate;
// import org.springframework.jdbc.core.RowMapper;
// import org.springframework.stereotype.Repository;

// @Repository
// public class JdbcCommentsRepository implements CommentsRepository {

//     @Autowired
//     private JdbcTemplate jdbcTemplate;

//     // Menampilkan semua komentar
//     @Override
//     public List<Comment> findAll() {
//         String sql = "SELECT * FROM comments";
//         // Return a list of comments by executing the query and mapping the results
//         return jdbcTemplate.query(sql, new CommentRowMapper());
//     }
    
//     // Menampilkan komentar berdasarkan ID show
//     @Override
//     public List<Comment> findByShowId(int showId) {
//         String sql = "SELECT * FROM comments WHERE show_id = ?";
//         // Execute query with showId parameter and map results
//         return jdbcTemplate.query(sql, new CommentRowMapper(), showId);
//     }
    
//     // Menambahkan komentar baru
//     @Override
//     public void save(Comment comment) {
//         String sql = "INSERT INTO comments (member_id, show_id, comment_text, created_at) VALUES (?, ?, ?, ?)";
//         // Insert the comment into the database
//         jdbcTemplate.update(sql, comment.getMemberId(), comment.getShowId(), comment.getCommentText(), comment.getCreatedAt());
//     }

//     // Menghapus komentar berdasarkan ID
//     @Override
//     public void delete(int id) {
//         String sql = "DELETE FROM comments WHERE id = ?";
//         // Delete comment by its ID
//         jdbcTemplate.update(sql, id);
//     }

//     // RowMapper implementation for Comment
//     private static class CommentRowMapper implements RowMapper<Comment> {
//         @Override
//         public Comment mapRow(ResultSet resultSet, int rowNum) throws SQLException {
//             return new Comment(
//                 resultSet.getInt("id"),               // ID Komentar
//                 resultSet.getInt("member_id"),        // ID Member yang memberi komentar
//                 resultSet.getInt("show_id"),          // ID Show
//                 resultSet.getString("comment_text"),  // Teks komentar
//                 resultSet.getTimestamp("created_at")  // Tanggal komentar dibuat
//             );
//         }
//     }
// }