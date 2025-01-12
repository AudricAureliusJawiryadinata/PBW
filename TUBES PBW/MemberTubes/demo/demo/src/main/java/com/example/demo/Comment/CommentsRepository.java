package com.example.demo.Comment;

import java.util.List;

public interface CommentsRepository {

    // Mendapatkan semua komentar
    List<Comment> findAll();

    // Mendapatkan komentar berdasarkan ID Show
    List<Comment> findByShowId(int showId);

    // Menambahkan komentar baru
    void save(Comment comment);

    // Menghapus komentar berdasarkan ID
    void delete(int id);
}
