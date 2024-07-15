package com.magic3.magic3.controller;

import com.magic3.magic3.model.Usera;
import com.magic3.magic3.repository.UserRepository;
import com.magic3.magic3.token.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;

@Controller
public class MagicLinkController {
    @Autowired
    private UserRepository repo;
    @Autowired
    private TokenGenerator tokenGenerator;

    @GetMapping("/email_success")
    public String magicLinkCallBack(@RequestParam("token") String token) {

        System.out.println("Alınan token: " + token);
        String[] parts = token.split("_");
        System.out.println("Token parçaları: " + Arrays.toString(parts));
        if (parts.length == 3) {
            try {
                long timestamp = Long.parseLong(parts[1].trim());
                System.out.println("Zaman damgası: " + timestamp);
                long expirationTime = timestamp + Long.parseLong(parts[2].trim());
                System.out.println("Sona erme süresi: " + expirationTime);
                if (System.currentTimeMillis() < expirationTime) {
                    // Token'ın geçerli olduğu durumda kullanıcıyı bulma
                    Usera usera = repo.findByToken(token);
                    System.out.println("Kullanıcı: " + usera);
                    if (usera != null) {
                        System.out.println("/email_success adresine yönlendiriliyor.");
                        //return "redirect:/email_success?token="+ token; // Burada adresin doğru olduğundan emin olun
                        return "email_success";
                    } else {
                        System.out.println("Kullanıcı bulunamadı");
                    }
                } else {
                    System.out.println("Token süresi dolmuş");
                }
            } catch (NumberFormatException e) {
                System.out.println("Token zaman damgası geçersiz: " + e.getMessage());
            }
        } else {
            System.out.println("Token geçersiz formatta");
        }
        System.out.println("/ adresine yönlendiriliyor");
        return "redirect:/";
//        if (parts.length == 3) {
//            long timestamp = Long.parseLong(parts[1]);
//            System.out.println("Zaman damgası: " + timestamp);
//            long expirationTime = timestamp + 60000;
//            System.out.println("Sona erme süresi: " + expirationTime);
//            if (System.currentTimeMillis() < expirationTime) {
//                Usera usera = repo.findByToken(token);
//                System.out.println("Kullanıcı: " + usera);
//                if (usera != null) {
//                    System.out.println("/email_success adresine yönlendiriliyor.");
//                    return "redirect:/email_success";
//                }
//            }
//
//        }
//        System.out.println("/ adresine yönlendiriliyor");
//        return "redirect:/";


    }
}
