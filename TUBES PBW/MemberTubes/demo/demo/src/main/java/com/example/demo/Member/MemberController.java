package com.example.demo.Member;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MemberController {
    
    @GetMapping("/member")
    public String showMemberPage(Model model) {
        return "Member"; // Maps to Member.html
    }
    
    @GetMapping("/member/loginmember")
    public String showLogInMember(Model model) {
        return "LoginMember"; // Maps to LoginMember.html
    }

    @GetMapping("/member/registrasimember")
    public String showSignUpMember(Model model) {
        return "RegistrasiMember"; // Maps to RegistrasiMember.html
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String username, 
                              @RequestParam String password, 
                              Model model) {
        // Example authentication logic
        if ("validUser".equals(username) && "validPassword".equals(password)) {
            System.out.println("berhasil");
            return "redirect:/member"; // Redirects to Member.html
        }
        // On failure, return to the login page with an error message
        model.addAttribute("error", "Invalid username or password");
        return "LoginMember";
    }
}
