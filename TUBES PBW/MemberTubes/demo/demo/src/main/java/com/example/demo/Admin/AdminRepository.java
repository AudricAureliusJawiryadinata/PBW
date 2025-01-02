package com.example.demo.Admin;

import java.util.List;

public interface AdminRepository {
    List<Admin> findAll(); // Mendapatkan semua data admin
    Admin findById(int id); // Mencari admin berdasarkan ID
    Admin findByUsername(String username); // Mencari admin berdasarkan username
    void save(Admin admin); // Menyimpan admin baru
    void update(int id, Admin updatedAdmin); // Memperbarui admin berdasarkan ID
    void delete(int id); // Menghapus admin berdasarkan ID
    boolean isValidAdmin(String username, String password); // Validasi username dan password
}
