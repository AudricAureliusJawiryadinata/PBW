package com.example.demo.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.JdbcSetlistRepository;
import com.example.demo.SetListRepository;
import com.example.demo.Show;
import com.example.demo.ShowRepository;

import java.util.List; // Tambahkan ini!

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private SetListRepository setListRepository;
    @Autowired
    private JdbcSetlistRepository setlistRepository;
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
    public String showMemberAddShowPage(Model model) {
        model.addAttribute("pageTitle", "Tambah Show");
        return "MemberAddShow"; // The view name to show Member Add Show form
    }
    @Autowired
    private ShowRepository showRepository;
    @PostMapping("/member/add-show")
    public String addShow(@RequestParam("showName") String namaShow, RedirectAttributes redirectAttributes) {
        try {
            showRepository.addShow(namaShow);
            redirectAttributes.addFlashAttribute("successMessage", "Show berhasil ditambahkan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menambahkan show: " + e.getMessage());
        }
        return "redirect:/MemberAddShow"; // Redirect back to the Member Add Show page
    }

    /**
     * Menampilkan halaman MemberAddSetlist.html
     */
    @GetMapping("/MemberAddSetlist")
    public String showMemberAddSetlistPage(Model model) {
        // Menambahkan data show untuk dropdown
        model.addAttribute("shows", setListRepository.findAllShows());
        return "MemberAddSetlist"; // Halaman HTML untuk Member
    }
  
    @PostMapping("/member/add-setlist")
    public String addSetlist(@RequestParam("setlistName") String namaLagu,
                             @RequestParam("showId") int showId,
                             RedirectAttributes redirectAttributes) {
        try {
            String showTerkait = setListRepository.getShowNameById(showId); // Ambil nama show
            setListRepository.addSetlist(namaLagu, showTerkait, showId); // Simpan ke database
            redirectAttributes.addFlashAttribute("successMessage", "Setlist berhasil ditambahkan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menambahkan setlist: " + e.getMessage());
        }
        return "redirect:/MemberAddSetlist"; // Kembali ke
    }
    
    /**
     * Menampilkan halaman MemberKomentar.html
     */
    @GetMapping("/MemberKomentar")
    public String showMemberKomentarPage(Model model) {
        // Fetch all shows
        List<Show> shows = setlistRepository.findAllShows();
        // Add shows to model
        model.addAttribute("shows", shows);
        return "MemberKomentar"; // The page where you want to show the dropdown
    }
    @Autowired
    private CommentRepository commentRepository;

    @PostMapping("/member/comment")
    public String submitComment(@RequestParam String commentText, @RequestParam int showId, RedirectAttributes redirectAttributes) {
        // Save the comment
        commentRepository.addComment(commentText, showId);

        // Add success message
        redirectAttributes.addFlashAttribute("successMessage", "Komentar berhasil dikirim!");

        // Redirect to the same page
        return "redirect:/MemberKomentar?showId=" + showId;
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