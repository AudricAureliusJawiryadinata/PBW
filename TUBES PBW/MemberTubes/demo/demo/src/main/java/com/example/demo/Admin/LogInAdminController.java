package com.example.demo.Admin;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LogInAdminController {
    @GetMapping("/loginAdmin")
    public String showMemberPage(Model model) {
        return "LoginAdmin";
    }
}