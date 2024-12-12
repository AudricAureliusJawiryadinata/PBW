package com.example.demo.Guest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GuestController {
    @GetMapping("/guest/homepageguest")
    public String showHomePageMember(Model model) {
        model.addAttribute("pageTitle", "Home Page Guest");
        return "HomePageGuest"; // Harus sesuai dengan add-artist.html
    }
}
