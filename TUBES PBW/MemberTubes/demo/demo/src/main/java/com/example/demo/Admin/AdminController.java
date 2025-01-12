package com.example.demo.Admin;
import com.example.demo.Setlist.Setlist;
import com.example.demo.Show.Show;
import com.example.demo.Show.ShowRepository;

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

import com.example.demo.Artis.ArtisRepository;
import com.example.demo.Member.Member; // Impor kelas Member
import com.example.demo.Member.MemberRepository; // Impor MemberRepository
import com.example.demo.Setlist.SetListRepository;

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
    private ArtisRepository artistRepository; 
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
                             @RequestParam("artistId") int artistId, 
                             @RequestParam("namaArtis") String namaArtis, // Capture artist name // Capture artistId from the form
                             RedirectAttributes redirectAttributes) {
        try {
            String showTerkait = setListRepository.getShowNameById(showId); // Get show name by showId
            // Call the addSetlist method with artistId
            setListRepository.addSetlist(namaLagu, showTerkait, showId, artistId, namaArtis);
    
            redirectAttributes.addFlashAttribute("successMessage", "Setlist berhasil ditambahkan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menambahkan setlist: " + e.getMessage());
        }
        return "redirect:/admin/AddSetList"; // Redirect back to the setlist form
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
        // Fetch all members
        List<Member> members = memberRepository.findAll();
        model.addAttribute("members", members);  // Add members to model
    
        // Fetch all shows
        List<Show> shows = showRepository.findAllShows();
        model.addAttribute("shows", shows);  // Add shows to model
    
        // Fetch all setlists
        List<Setlist> setlists = setListRepository.findAllSetlistForHistory();
        model.addAttribute("setlists", setlists);  // Add setlists to model
    
        // Return the view name "Report" that will render the generate-report.html template
        return "Report";
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
