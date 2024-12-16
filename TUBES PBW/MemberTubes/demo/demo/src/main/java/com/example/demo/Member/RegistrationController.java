package com.example.demo.Member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RegistrationController {

    @Autowired
    private JdbcMemberRepository memberRepository;

    @PostMapping("/register")
    public ModelAndView register(@RequestParam String username,
                                 @RequestParam String email,
                                 @RequestParam String password) {
        memberRepository.register(username, email, password);

        // Redirect to the login page with a success message
        return new ModelAndView("redirect:/member/loginmember?message=Registrasi berhasil! Silakan login.");
    }
}
