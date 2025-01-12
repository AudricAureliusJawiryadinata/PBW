package com.example.demo.Comment;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentsController {

    @Autowired
    private CommentsService commentsService;

    // Menampilkan semua komentar
    @GetMapping
    public List<Comment> getAllComments() {
        return commentsService.getAllComments();  // Mengambil semua komentar
    }

    // Menampilkan komentar berdasarkan ID show
    @GetMapping("/show/{showId}")
    public ResponseEntity<List<Comment>> getCommentsByShowId(@PathVariable int showId) {
        // Fetch comments from your service layer
        List<Comment> comments = commentsService.getCommentsByShowId(showId);

        // If no comments, return a 404 response, otherwise return the comments list
        if (comments == null || comments.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ArrayList<>());
        }

        return ResponseEntity.ok(comments);
    }

    // Menambahkan komentar baru
    @PostMapping
    public ResponseEntity<String> addComment(@RequestBody Comment comment) {
        // Add comment to the database or service layer
        commentsService.addComment(comment);

        // Return a success message
        return ResponseEntity.status(HttpStatus.CREATED).body("Komentar berhasil ditambahkan");
    }

    // Menghapus komentar berdasarkan ID
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable int id) {
        commentsService.deleteComment(id);  // Menghapus komentar berdasarkan id

        // Return a success message after deletion
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Komentar berhasil dihapus");
    }

    // Menemukan memberId berdasarkan username
    @GetMapping("/findMemberId/{username}")
    public ResponseEntity<Integer> findMemberIdByUsername(@PathVariable String username) {
        Integer memberId = commentsService.findIdByUsername(username);

        // If memberId not found, return 404
        if (memberId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // Return the memberId if found
        return ResponseEntity.ok(memberId);
    }

} 
