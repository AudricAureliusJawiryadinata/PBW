package com.example.demo.Admin;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.ArtistRepository;
import com.example.demo.SetListRepository;
import com.example.demo.Show;
import com.example.demo.ShowRepository;
import com.example.demo.Member.Member; // Impor kelas Member
import com.example.demo.Member.MemberRepository; // Impor MemberRepository
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
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

    @PostMapping("/admin/add-artist")
    public String addArtist(@RequestParam("namaArtis") String namaArtis,
                            @RequestParam("genreMusik") String genreMusik,
                            RedirectAttributes redirectAttributes) {
        try {
            adminRepository.addArtist(namaArtis, genreMusik); // Simpan artis ke database
            redirectAttributes.addFlashAttribute("successMessage", "Artis berhasil ditambahkan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menambahkan artis: " + e.getMessage());
        }
        return "redirect:/admin/AddArtist"; // Kembali ke halaman form
    }
    
    
    @GetMapping("/admin/AddShow")
    public String showAddShowPage(Model model) {
        model.addAttribute("pageTitle", "Tambah Show");
        return "AddShow"; // Harus sesuai dengan add-show.html
    }
    @Autowired
    private ShowRepository showRepository;

    @PostMapping("/admin/add-show")
    public String addShow(
            @RequestParam("showName") String namaShow,
            @RequestParam("showLocation") String lokasiShow,
            @RequestParam("showDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate tanggalShow,
            RedirectAttributes redirectAttributes) {
        try {
            showRepository.addShow(namaShow, lokasiShow, tanggalShow); // Memasukkan lokasi dan tanggal
            redirectAttributes.addFlashAttribute("successMessage", "Show berhasil ditambahkan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menambahkan show: " + e.getMessage());
        }
        return "redirect:/admin/AddShow"; // Redirect kembali ke halaman AddShow
    }
    @Autowired
    private ArtistRepository artistRepository; 
    @GetMapping("/admin/AddSetList")
    public String showAddSetlistPage(Model model) {
        model.addAttribute("shows", showRepository.findAllShows()); // Pass daftar shows ke view
        model.addAttribute("artisList", artistRepository.findAllArtists()); // Pass daftar artis ke view
        return "AddSetList"; // Pastikan nama file HTML sesuai
    }
    
    
    @Autowired
    private SetListRepository setListRepository; // Correctly autowiring the repository
    
    @PostMapping("/admin/add-setlist")
    public String addSetlist(@RequestParam("setlistName") String namaLagu,
                             @RequestParam("showId") int showId,
                             RedirectAttributes redirectAttributes) {
        try {
            String showTerkait = setListRepository.getShowNameById(showId); // Correct call
            setListRepository.addSetlist(namaLagu, showTerkait, showId); // Correct call
            redirectAttributes.addFlashAttribute("successMessage", "Setlist berhasil ditambahkan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menambahkan setlist: " + e.getMessage());
        }
        return "redirect:/admin/AddSetList"; // Redirect to the same page
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
        List<Member> members = memberRepository.findAll(); // Ambil data member
        model.addAttribute("members", members);
        
        List<Show> shows = showRepository.findAllShows(); // Ambil data show
        model.addAttribute("shows", shows);
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
