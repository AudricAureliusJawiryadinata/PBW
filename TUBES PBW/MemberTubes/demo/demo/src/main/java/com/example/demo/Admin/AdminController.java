package com.example.demo.Admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @GetMapping("/admin")
    public String showAdminDashboard(Model model) {
        // Tambahkan data ke model jika diperlukan
        model.addAttribute("pageTitle", "Dashboard Admin");
        return "admin"; // Harus sesuai dengan nama file HTML tanpa ekstensi (e.g., admin.html)
    }
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
        model.addAttribute("pageTitle", "Kelola Pengguna");
        return "ManageUser"; // Harus sesuai dengan manage-users.html
    }

    @GetMapping("/admin/Report")
    public String showGenerateReportPage(Model model) {
        model.addAttribute("pageTitle", "Laporan");
        return "Report"; // Harus sesuai dengan generate-report.html
    }
}
