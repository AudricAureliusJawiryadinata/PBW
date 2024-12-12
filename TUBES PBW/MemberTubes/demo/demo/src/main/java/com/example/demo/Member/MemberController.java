package com.example.demo.Member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MemberController {
    @GetMapping("/member")
    public String showMemberPage(Model model) {
        return "Member";
    }
    @GetMapping("/member/loginmember")
    public String showLogInMember(Model model) {
        return "LoginMember";
    }
    @GetMapping("/member/registrasimember")
    public String showSignUpMember(Model model) {
        return "RegistrasiMember";
    }
}
