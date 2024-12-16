package com.example.demo.Member;

import java.util.List;

public interface MemberRepository {
    List<Member> findAll(); // Mendapatkan semua data member
    Member findById(int id); // Mencari member berdasarkan ID
    List<Member> findByName(String name); // Mencari member berdasarkan nama (opsional jika "username" yang dipakai)
    void save(Member member); // Menyimpan member baru
    void update(int id, Member updatedMember); // Memperbarui member berdasarkan ID
    void delete(int id); // Menghapus member berdasarkan ID

    // Metode tambahan
    Member findByUsername(String username); // Mencari member berdasarkan username
    boolean isValidUser(String username, String password); // Memvalidasi username dan password
}
