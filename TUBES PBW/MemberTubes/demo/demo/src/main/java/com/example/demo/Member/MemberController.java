package com.example.demo.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List; // Tambahkan ini!

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;
    
   @GetMapping("/member/loginmember")
    public String showLogInMember(Model model){
        return "LoginMember";
    }

    /**
     * Menampilkan halaman MemberAddArtist.html sebagai halaman utama
     */
    @GetMapping("/MemberAddArtist")
    public String showMemberAddArtistPage(Model model) {
        return "MemberAddArtist"; // Mengarahkan ke MemberAddArtist.html
    }

    /**
     * Menampilkan halaman MemberAddShow.html
     */
    @GetMapping("/MemberAddShow")
    public String showMemberAddShowPage() {
        return "MemberAddShow"; // Mengarahkan ke MemberAddShow.html
    }

    /**
     * Menampilkan halaman MemberAddSetlist.html
     */
    @GetMapping("/MemberAddSetlist")
    public String showMemberAddSetlistPage() {
        return "MemberAddSetlist"; // Mengarahkan ke MemberAddSetlist.html
    }

    /**
     * Menampilkan halaman MemberKomentar.html
     */
    @GetMapping("/MemberKomentar")
    public String showMemberKomentarPage() {
        return "MemberKomentar"; // Mengarahkan ke MemberKomentar.html
    }

    /**
     * Menampilkan halaman MemberHistory.html
     */
    @GetMapping("/MemberHistory")
    public String showMemberHistoryPage() {
        return "MemberHistory"; // Mengarahkan ke MemberHistory.html
    }

    /**
     * Memproses login dan memvalidasi username & password
     */
    @PostMapping("/login")
    public String handleLogin(@RequestParam String username,
                              @RequestParam String password,
                              RedirectAttributes redirectAttributes) {
        // Validasi username dan password menggunakan repository
        if (memberRepository.isValidUser(username, password)) {
            return "redirect:/MemberAddArtist"; // Jika valid, arahkan ke halaman MemberAddArtist.html
        }

        // Jika tidak valid, tambahkan pesan error sebagai query parameter
        redirectAttributes.addAttribute("error", "Username atau password salah.");
        return "redirect:/member/loginmember"; // Redirect ke halaman login yang benar
    }

    /**
     * Logout dan hapus sesi pengguna
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Hapus data sesi jika ada
        session.invalidate();
        // Redirect ke halaman login
        return "redirect:/member/loginmember";
    }
    
    @PostMapping("/add-artist")
    public String addArtist(@RequestParam("namaArtis") String namaArtis,
                            @RequestParam("genreMusik") String genreMusik,
                            RedirectAttributes redirectAttributes) {
        try {
            memberRepository.addArtist(namaArtis, genreMusik); // Simpan artis ke database
            redirectAttributes.addFlashAttribute("successMessage", "Artis berhasil ditambahkan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menambahkan artis: " + e.getMessage());
        }
        return "redirect:/MemberAddArtist"; // Kembali ke halaman form
    }

    @GetMapping("/member/registrasimember")
        public String showSignUpMember(Model model) {
            return "RegistrasiMember"; // Mengarahkan ke RegistrasiMember.html
        }

        
    // Tampilkan semua pengguna atau hasil pencarian
    @PostMapping("/admin/ManageUser")
    public String searchUsers(@RequestParam(required = false) String keyword, Model model) {
        List<Member> members;
        if (keyword != null && !keyword.isEmpty()) {
            members = memberRepository.findByName(keyword); // Pencarian berdasarkan keyword
        } else {
            members = memberRepository.findAll(); // Ambil semua pengguna
        }
        model.addAttribute("members", members);
        return "ManageUser"; // Nama file HTML
    }

    // Edit password pengguna
    @PostMapping("/admin/ManageUser/edit")
    public String editPassword(@RequestParam int id, @RequestParam String password, RedirectAttributes redirectAttributes) {
        Member member = memberRepository.findById(id);
        if (member != null) {
            // Pastikan data yang tidak diubah tetap sama
            member.setPassword(password); // Ubah hanya password
            memberRepository.update(id, member);
            redirectAttributes.addFlashAttribute("message", "Password berhasil diperbarui.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Pengguna tidak ditemukan.");
        }
        return "redirect:/admin/ManageUser";
    }
    
    


    // Hapus pengguna
    @PostMapping("/admin/ManageUser/delete")
    public String deleteUser(@RequestParam int id, RedirectAttributes redirectAttributes) {
        memberRepository.delete(id);
        redirectAttributes.addFlashAttribute("message", "Pengguna berhasil dihapus.");
        return "redirect:/admin/ManageUser";
    }

}
