package com.example.demo.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistrationController {

    @Autowired
    private JdbcMemberRepository memberRepository;

    @PostMapping("/register")
    public String handleRegister(@RequestParam String username,
                             @RequestParam String email,
                             @RequestParam String password,
                             RedirectAttributes redirectAttributes) {
    memberRepository.register(username, email, password);

    // Tambahkan pesan keberhasilan sebagai query parameter
    redirectAttributes.addAttribute("message", "Registrasi berhasil! Silakan login.");
    return "redirect:/member/loginmember";
}

}
