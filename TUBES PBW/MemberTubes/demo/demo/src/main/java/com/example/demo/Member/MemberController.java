package com.example.demo.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

    @Autowired
    private MemberRepository memberRepository;

    /**
     * Menampilkan halaman Member.html
     */
    @GetMapping("/member")
    public String showMemberPage(Model model) {
        return "Member"; // Mengarahkan ke Member.html
    }

    /**
     * Menampilkan halaman LoginMember.html
     */
    @GetMapping("/member/loginmember")
    public String showLogInMember(Model model) {
        return "LoginMember"; // Mengarahkan ke LoginMember.html
    }

    /**
     * Menampilkan halaman RegistrasiMember.html
     */
    @GetMapping("/member/registrasimember")
    public String showSignUpMember(Model model) {
        return "RegistrasiMember"; // Mengarahkan ke RegistrasiMember.html
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
            return "redirect:/member"; // Jika valid, arahkan ke halaman Member.html
        }
    
        // Jika tidak valid, tambahkan pesan error sebagai query parameter
        redirectAttributes.addAttribute("error", "Username atau password salah.");
        return "redirect:/member/loginmember"; // Redirect ke halaman login yang benar
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Hapus data sesi jika ada
        session.invalidate();

        // Redirect ke halaman login
        return "redirect:/member/loginmember";
    }

    

    
}
