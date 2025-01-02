package com.example.demo.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.demo.Member.Member; // Impor kelas Member
import com.example.demo.Member.MemberRepository; // Impor MemberRepository

@Controller

public class AdminController {
    @Autowired
    private AdminRepository adminRepository;
    @Autowired
    private MemberRepository memberRepository;


    @GetMapping("/admin/loginAdmin")
    public String showLogInAdmin(Model model) {
        return "LoginAdmin";
    }
    @GetMapping("/admin/AddArtist")
    public String showAddArtistPage(Model model) {
        model.addAttribute("pageTitle", "Tambah Artis");
        return "AddArtist"; // Harus sesuai dengan add-artist.html
    }

    @GetMapping("/admin/AddShow")
    public String showAddShowPage(Model model) {
        model.addAttribute("pageTitle", "Tambah Show");
        return "AddShow"; // Harus sesuai dengan add-show.html
    }

    @GetMapping("/admin/AddSetList")
    public String showAddSetlistPage(Model model) {
        model.addAttribute("pageTitle", "Tambah Setlist");
        return "AddSetList"; // Harus sesuai dengan add-setlist.html
    }

    @GetMapping("/admin/ManageUser")
    public String showManageUsersPage(Model model) {
        List<Member> members = memberRepository.findAll(); // Ambil data member
        model.addAttribute("members", members);
        model.addAttribute("pageTitle", "Kelola Pengguna");
        return "ManageUser";
    }

    @GetMapping("/admin/Report")
    public String showGenerateReportPage(Model model) {
        model.addAttribute("pageTitle", "Laporan");
        return "Report"; // Harus sesuai dengan generate-report.html
    }
    @PostMapping("/admin/loginAdmin")
    public String handleAdminLogin(@RequestParam String username, @RequestParam String password, Model model) {
        // Gunakan metode isValidAdmin untuk memeriksa username dan password di database
        if (adminRepository.isValidAdmin(username, password)) {
            return "redirect:/admin/AddArtist"; // Login berhasil
        }

        // Jika login gagal, tambahkan pesan error ke model
        model.addAttribute("error", "Username atau password salah!");
        return "LoginAdmin"; // Kembali ke halaman login jika gagal
    }


}