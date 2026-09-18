package com.student.jobportal.controller;
import com.student.jobportal.model.User;
import com.student.jobportal.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    @Autowired private UserRepository userRepo;

    @GetMapping("/register")
    public String registerPage(Model model){
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/register")
    public String registerUser(User user){
        userRepo.save(user);
        return "redirect:/login";
    }
    @GetMapping("/login")
    public String loginPage(){ return "login"; }

    @PostMapping("/login")
    public String loginUser(String email, String password, HttpSession session, Model model){
        User user = userRepo.findByEmailAndPassword(email, password);
        if(user != null){
            session.setAttribute("loggedInUser", user);
            return "redirect:/jobs";
        } else {
            model.addAttribute("error", "Invalid Email or Password");
            return "login";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/login";
    }
}