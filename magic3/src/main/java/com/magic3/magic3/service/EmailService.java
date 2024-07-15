package com.magic3.magic3.service;

import com.magic3.magic3.model.Usera;
import com.magic3.magic3.repository.UserRepository;
import com.magic3.magic3.token.TokenGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class EmailService {
    private final TemplateEngine TemplateEngine;
    private final JavaMailSender javaMailSender;
    private final TokenGenerator tokenGenerator;
    private final UserRepository repo;


    public EmailService(TemplateEngine templateEngine, JavaMailSender javaMailSender, TokenGenerator tokenGenerator, UserRepository repo) {
       this.TemplateEngine = templateEngine;
        this.javaMailSender = javaMailSender;
        this.tokenGenerator = tokenGenerator;
        this.repo = repo;
    }

    public void sendEmail(Usera usera) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);

        if(usera == null){
            throw new RuntimeException("User Bulunamadı.");
        }
        String email = usera.getEmail();
        usera.setEmail(email);

        if(email == null || email.isEmpty()){
            throw new RuntimeException("User email boş olamaz");
        }
        ///////
        String token = tokenGenerator.generateToken(usera);
        repo.save(usera);

        Context context = new Context();
        context.setVariable("token", token);
        context.setVariable("user",usera);

        mimeMessageHelper.setFrom("melih199906@gmail.com");
        mimeMessageHelper.setTo(email);
        mimeMessageHelper.setText("Hoşgeldiniz. Siteye erişmek için aşağıdaki linke tıklayınız.");
        mimeMessageHelper.setSubject("KırlangıçBank");




        String htmlContent;
        try {
            htmlContent = TemplateEngine.process("emailTemplate", context);
        } catch (Exception e) {
            System.out.println("Email şablonunu işlerken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Email şablonunu işlerken bir hata oluştu.", e);
        }

        mimeMessageHelper.setText(htmlContent, true);

        try {
            javaMailSender.send(mimeMessage);
            System.out.println("Mail send to " + email);
        } catch (MailException e) {
            System.out.println("E-posta gönderilirken bir hata oluştu: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

//        String htmlContent = TemplateEngine.process("emailTemplate", context);
//        mimeMessageHelper.setText(htmlContent,true);
//        javaMailSender.send(mimeMessage);
//
//        System.out.println("Mail send to" + email);
    }

}
