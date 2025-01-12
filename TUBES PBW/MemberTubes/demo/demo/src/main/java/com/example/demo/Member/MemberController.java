package com.example.demo.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.Artis.Artis;
import com.example.demo.Artis.ArtisRepository;
import com.example.demo.Comment.Comment;
import com.example.demo.Comment.CommentsService;
import com.example.demo.Setlist.JdbcSetlistRepository;
import com.example.demo.Setlist.SetListRepository;

import java.io.PrintWriter;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import jakarta.servlet.http.HttpSession;
import com.example.demo.Setlist.Setlist;
import com.example.demo.Show.Show;
import com.example.demo.Show.ShowRepository;

import java.io.IOException;
import jakarta.servlet.http.HttpServletResponse;
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
    public String addShow(
            @RequestParam("showName") String namaShow,
            @RequestParam("showLocation") String lokasiShow,
            @RequestParam("showDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate tanggalShow,
            RedirectAttributes redirectAttributes) {
        try {
            showRepository.addShow(namaShow, lokasiShow, tanggalShow);
            redirectAttributes.addFlashAttribute("successMessage", "Show berhasil ditambahkan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menambahkan show: " + e.getMessage());
        }
        return "redirect:/MemberAddShow";
    }

    @Autowired
    private ArtisRepository artistRepository; // Repository untuk tabel artis

    @GetMapping("/MemberAddSetlist")
    public String showAddSetlistForm(Model model) {
        // Fetch all artists and shows from the database
        List<Artis> artisList = artistRepository.findAllArtists();
        List<Show> shows = showRepository.findAllShows();
        
        // Add the data to the model so that Thymeleaf can use it
        model.addAttribute("artisList", artisList);
        model.addAttribute("shows", shows);
    
        return "memberAddSetlist";  // Name of your HTML file (view)
    }
    
  
    @PostMapping("/member/add-setlist")
    public String addSetlist(@RequestParam("setlistName") String namaLagu,
                             @RequestParam("showId") int showId,
                             @RequestParam("artistId") int artistId,  // Capture artistId from the form
                             @RequestParam("namaArtis") String namaArtis, // Capture artist name
                             RedirectAttributes redirectAttributes) {
        try {
            String showTerkait = setListRepository.getShowNameById(showId); // Get show name by ID
            // Call the addSetlist method with artistId and namaArtis
            setListRepository.addSetlist(namaLagu, showTerkait, showId, artistId, namaArtis);
    
            redirectAttributes.addFlashAttribute("successMessage", "Setlist berhasil ditambahkan!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Gagal menambahkan setlist: " + e.getMessage());
        }
        return "redirect:/MemberAddSetlist"; // Redirect back
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
    private CommentsService commentsService;
      // Menyuntikkan MemberRepository
    
    @PostMapping("/member/comment")
    public String submitComment(@RequestParam String commentText, 
                                @RequestParam int showId, 
                                HttpSession session, 
                                RedirectAttributes redirectAttributes) {
        
        // Get the username from the session
        String username = (String) session.getAttribute("username");
        
        if (username == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Username tidak ditemukan. Silakan login terlebih dahulu.");
            return "redirect:/MemberKomentar";  // Redirect to login page
        }
        
        // Fetch the member by username
        Member member = memberRepository.findByUsername(username);
        
        if (member == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Username tidak ditemukan.");
            return "redirect:/MemberKomentar?showId=" + showId;
        }
        
        // Create the comment and save it
        Comment comment = new Comment(0, member.getId(), showId, commentText, new Timestamp(System.currentTimeMillis()));
        commentsService.addComment(comment);
        
        redirectAttributes.addFlashAttribute("successMessage", "Komentar berhasil dikirim!");
        return "redirect:/MemberKomentar?showId=" + showId;
    }
    
    

    
    
    /**
     * Menampilkan halaman MemberHistory.html
     */
    @GetMapping("/MemberHistory")
    public String showMemberHistoryPage(Model model) {
        // Ambil data langsung dari tabel setlist
        List<Setlist> setlists = setlistRepository.findAllSetlistForHistory();
    
        // Debug log untuk memastikan data
        setlists.forEach(setlist ->
            System.out.println("Setlist: " + setlist.getNamaLagu() +
                               ", Show: " + setlist.getShowTerkait() +
                               ", Artist: " + setlist.getNamaArtis())
        );
    
        // Kirim data ke view
        model.addAttribute("setlists", setlists);
        return "MemberHistory";
    }

   

    /**
     * Memproses login dan memvalidasi username & password
     */
    @PostMapping("/login")
public String handleLogin(@RequestParam String username,
                          @RequestParam String password,
                          HttpSession session,  // Menambahkan HttpSession untuk menyimpan data sesi
                          RedirectAttributes redirectAttributes) {
    // Validasi username dan password menggunakan repository
    if (memberRepository.isValidUser(username, password)) {
        // Menyimpan username dalam sesi setelah login berhasil
        session.setAttribute("username", username);
        return "redirect:/MemberAddArtist"; // Jika valid, arahkan ke halaman MemberAddArtist
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

    @GetMapping("/member/MemberAddSetlist")
    public void downloadSetlist(HttpServletResponse response) throws IOException {
        // Set header for file download
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"setlist.csv\"");
    
        // Get setlist data from the database
        List<Setlist> setlists = setlistRepository.findAllSetList();
    
        // Write data to the output stream as CSV
        PrintWriter writer = response.getWriter();
        writer.println("ID, Nama Artis, Nama Lagu, Nama Show");  // Column headers
    
        // Write each setlist row to the CSV
        for (Setlist setlist : setlists) {
            writer.println(setlist.getId() + "," + setlist.getNamaArtis() + "," + setlist.getNamaLagu() + "," + setlist.getShowTerkait());
        }
    
        writer.flush();
        writer.close();
    }
    
    @GetMapping("/editSetlist")
    public String editSetlist(@RequestParam int id, Model model) {
        // Ambil data setlist berdasarkan ID
        Setlist setlist = setlistRepository.findById(id);
        if (setlist == null) {
            throw new RuntimeException("Setlist tidak ditemukan");
        }
    
        // Ambil daftar artis dari repository
        List<Artis> artisList = artistRepository.findAllArtists();
    
        // Tambahkan data ke model
        model.addAttribute("setlist", setlist);
        model.addAttribute("artisList", artisList); // Data artis untuk dropdown
    
        return "edit"; // Nama file HTML
    }
    
    @PostMapping("/updateSetlist")
public String updateSetlist(@RequestParam int id,
                             @RequestParam String namaLagu,
                             @RequestParam String showTerkait,
                             @RequestParam String namaArtis,  // Nama artis diambil dari input teks
                             RedirectAttributes redirectAttributes) {
    try {
        // Cari setlist berdasarkan ID
        Setlist setlist = setlistRepository.findById(id);
        if (setlist == null) {
            redirectAttributes.addFlashAttribute("errorMessage", "Setlist tidak ditemukan.");
            return "redirect:/MemberHistory";
        }

        // Update data
        setlist.setNamaLagu(namaLagu);
        setlist.setShowTerkait(showTerkait);
        setlist.setNamaArtis(namaArtis); // Set nama artis sesuai dengan input pengguna

        // Simpan perubahan ke repository
        setlistRepository.save(setlist);

        // Pesan sukses
        redirectAttributes.addFlashAttribute("successMessage", "Setlist berhasil diperbarui!");
    } catch (Exception e) {
        redirectAttributes.addFlashAttribute("errorMessage", "Gagal memperbarui setlist: " + e.getMessage());
        e.printStackTrace(); // Debug error ke console
    }
    return "redirect:/MemberHistory";
}
}

