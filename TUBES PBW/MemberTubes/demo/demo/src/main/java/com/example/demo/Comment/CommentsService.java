package com.example.demo.Comment;
// package com.example.demo.Comment;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import com.example.demo.Member.Member;
// import com.example.demo.Member.MemberRepository;

// @Service
// @Transactional  
// public class CommentsService {

//     @Autowired
//     private CommentsRepository commentsRepository;  // Injecting CommentsRepository interface

//     @Autowired
//     private MemberRepository memberRepository;  // Injecting MemberRepository

//     // Menampilkan semua komentar
//     public List<Comment> getAllComments() {
//         return commentsRepository.findAll();
//     }

//     // Menampilkan komentar berdasarkan ID show
//     public List<Comment> getCommentsByShowId(int showId) {
//         return commentsRepository.findByShowId(showId);
//     }

//     // Menambahkan komentar baru
//     public void addComment(Comment comment) {
//         commentsRepository.save(comment);  // Saving the new comment
//     }

//     // Menghapus komentar berdasarkan ID
//     public void deleteComment(int id) {
//         commentsRepository.delete(id);  // Deleting the comment by ID
//     }

//     // Menemukan memberId berdasarkan username
//     public Integer findIdByUsername(String username) {
//         // Menggunakan repository Member untuk mencari member berdasarkan username
//         Member member = memberRepository.findByUsername(username);  // Finding the Member by username
//         if (member != null) {
//             return member.getId();  // Returning the memberId if member is found
//         }
//         return null;  // If member not found, returning null
//     }
// }