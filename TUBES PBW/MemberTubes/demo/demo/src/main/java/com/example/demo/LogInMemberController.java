package com.example.demo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogInMemberController {
    @GetMapping("/loginMember")
    public String showMemberPage(Model model) {
        return "LoginMember";
    }
}
