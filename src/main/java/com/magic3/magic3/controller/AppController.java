package com.magic3.magic3.controller;

import com.magic3.magic3.model.Usera;
import com.magic3.magic3.repository.UserRepository;
import com.magic3.magic3.service.EmailService;
import com.magic3.magic3.token.TokenGenerator;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class AppController {
    @Autowired
    private UserRepository repo;
    @Autowired
    private EmailService emailService;
    private TokenGenerator tokenGenerator;

    @GetMapping("")
    public String viewHomePage() {
        return "index";
    }

    @GetMapping("/register")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new Usera());

        return "signup-form";
    }

    @PostMapping("/process_register")
    public String processRegisteration(@ModelAttribute Usera usera, HttpSession session) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(usera.getPassword());
        usera.setPassword(encodedPassword);


        repo.save(usera);
        session.setAttribute("usera", usera);
        return "register_success";
    }

    @RequestMapping("/director")//email işi buraya
    public String viewDirector(HttpSession session) throws MessagingException {
//        try {
//            emailService.sendEmail(usera);
//            System.out.println("Email gönderildi");
//            return "director";
//        } catch (Exception e) {
//            System.out.println("Email gönderilemedi" + e.getMessage());
//            return "index";
//        }
        Usera usera = (Usera) session.getAttribute("usera");

        System.out.println("User email: " + usera.getEmail());
        if ((usera == null) || (usera.getEmail() == null) || usera.getEmail().isEmpty()) {
            System.out.println("User email boş olamaz");
            return "index";
        }

        try {
            emailService.sendEmail(usera);
            System.out.println("Email gönderildi");
            return "director";
        } catch (Exception e) {
            System.out.println("Email gönderilemedi: " + e.getMessage());
            e.printStackTrace();
            return "index";
        }
    }

//    @GetMapping("/email_success") //Linke tıkladıgında çalışacak
//    public String emailSuccessfull() {
//        return "email_success";
//    }





//    @GetMapping("/list_users") //Askıya alındı
//    public String viewUsersList(Model model) {
//        List<Usera> listUsers = repo.findAll();
//        model.addAttribute("listUsers", listUsers);
//        return "users";
//    }
}
