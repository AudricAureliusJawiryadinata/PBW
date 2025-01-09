package com.example.demo.Guest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class guestController {
    @GetMapping("/guest")
    public String showMemberPage(Model model) {
        return "Guest";
    }

}